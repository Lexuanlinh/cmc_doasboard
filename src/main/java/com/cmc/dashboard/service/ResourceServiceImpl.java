/**
 * DashboardSystem - com.cmc.dashboard.service
 */
package com.cmc.dashboard.service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.ResourcePlanUtilizationDTO;
import com.cmc.dashboard.dto.ResourceUtilizationDTO;
import com.cmc.dashboard.dto.RestRespondResourceDTO;
import com.cmc.dashboard.model.UserPlanDetail;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.UserPlanDetailRepository;
import com.cmc.dashboard.repository.UserPlanRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.CustomValueUtil;
import com.cmc.dashboard.util.MethodUtil;

/**
 * @author: DVNgoc
 * @Date: Dec 27, 2017
 */
@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

	private static final String PATTERN_DATE_DDMMYYYY = "dd-MM-yyyy";
	private static final String PATTERN_DATE_YYYYMMDD = "yyyy-MM-dd";

	@Autowired
	UserPlanRepository userPlanRepository;

	@Autowired
	UserPlanDetailRepository userPlanDetailRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserQmsRepository userQmsRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ResourceService#getResourcePlan()
	 */
	@Override
	public RestRespondResourceDTO getResourcePlan(String name, String duName, String fromDate, String toDate,
			int userId, int pageNumber, int pageSize) throws ParseException, SQLException {
		String groupName = null;

		try {
			groupName = userQmsRepository.getGroupByUserId(userId);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return RestRespondResourceDTO.success(new ArrayList<>(), null);
		}
		if (groupName == null || groupName.isEmpty() || groupName.length() <= 3) {
			return RestRespondResourceDTO.success(new ArrayList<>(), null);
		}

		String deliveryUnit = groupName.substring(groupName.lastIndexOf(CustomValueUtil.DA_PREFIX) + 3);

		if (deliveryUnit.equals(CustomValueUtil.BOD) || deliveryUnit.equals(CustomValueUtil.QA)
				|| deliveryUnit.equals(CustomValueUtil.PROJECT_MANAGER)) {
			List<ResourceUtilizationDTO> listResourceUtilizationDTO = this.getAllResourcePlan(name, duName, pageNumber,
					pageSize);
			List<ResourceUtilizationDTO> listResult = new ArrayList<ResourceUtilizationDTO>();

			for (ResourceUtilizationDTO resourceUtilizationDTO : listResourceUtilizationDTO) {
				this.setUserPlanDetail(resourceUtilizationDTO, fromDate, toDate, duName);
				if (!resourceUtilizationDTO.getListPlan().isEmpty()) {
					listResult.add(resourceUtilizationDTO);
				}
			}
			long totalElement = userPlanRepository
					.getResourceIdByNameAndDU(name, duName, new PageRequest(pageNumber, pageSize)).getTotalElements();
			return RestRespondResourceDTO.success(listResult, totalElement);
		} else {
			if ((duName.equals("")) || !duName.equals(deliveryUnit)) {
				List<ResourceUtilizationDTO> listResourceUtilizationDTO = this.getAllResourcePlan(name, deliveryUnit,
						pageNumber, pageSize);
				List<ResourceUtilizationDTO> listResult = new ArrayList<ResourceUtilizationDTO>();

				for (ResourceUtilizationDTO resourceUtilizationDTO : listResourceUtilizationDTO) {
					this.setUserPlanDetail(resourceUtilizationDTO, fromDate, toDate, duName);
					if (!resourceUtilizationDTO.getListPlan().isEmpty()) {
						listResult.add(resourceUtilizationDTO);
					}
				}
				long totalElement = userPlanRepository
						.getResourceIdByNameAndDU(name, duName, new PageRequest(pageNumber, pageSize))
						.getTotalElements();
				return RestRespondResourceDTO.success(listResult, totalElement);
			} else {
				return RestRespondResourceDTO.success(new ArrayList<>(), null);
			}
		}
	}

	/**
	 * Get Resources's info
	 * 
	 * @param resourceUtilizationDTO
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws ParseException
	 *             ResourceUtilizationDTO
	 * @author: DVNgoc
	 */
	private ResourceUtilizationDTO setUserPlanDetail(ResourceUtilizationDTO resourceUtilizationDTO, String fromDate,
			String toDate, String duName) throws ParseException {
		Date fromDateUtil = getDate("01-" + fromDate);
		Date toDateUtil = getDate("02-" + toDate);

		List<UserPlanDetail> listUserPlanDetail = userPlanDetailRepository
				.getUserPlanDetailByUserId(resourceUtilizationDTO.getUserId(), duName);

		float valueResourcePerMonth;
		List<ResourcePlanUtilizationDTO> listResourcePlan = new ArrayList<ResourcePlanUtilizationDTO>();
		List<ResourcePlanUtilizationDTO> listResourcePlans = new ArrayList<ResourcePlanUtilizationDTO>();
		for (UserPlanDetail detail : listUserPlanDetail) {
			if (fromDateUtil.before(getDate("02-" + detail.getPlanMonth()))
					&& (toDateUtil.after(getDate("01-" + detail.getPlanMonth())))) {
				valueResourcePerMonth = (detail.getManDay() * 100) / MethodUtil.getWokingDaysOfMonth(detail.getPlanMonth());
				ResourcePlanUtilizationDTO resourcePlan = new ResourcePlanUtilizationDTO(detail.getPlanMonth(),
						valueResourcePerMonth);

				listResourcePlan.add(resourcePlan);
			}
			listResourcePlans = this.setEmptyMonth(listResourcePlan, fromDate, toDate);
		}
		resourceUtilizationDTO.setListPlans(listResourcePlans);
		return resourceUtilizationDTO;
	}

	private static Date getDate(String strDate) throws ParseException {
		return new SimpleDateFormat(PATTERN_DATE_DDMMYYYY).parse(strDate);
	}

	/**
	 * Set empty month for resource plan
	 * 
	 * @param listResourcePlan
	 * @param fromDate
	 * @param endDate
	 * @return List<ResourcePlanUtilizationDTO>
	 * @author: ngocd
	 * @throws ParseException
	 */
	private List<ResourcePlanUtilizationDTO> setEmptyMonth(List<ResourcePlanUtilizationDTO> listResourcePlan,
			String fromDate, String endDate) throws ParseException {
		List<String> listMonth = new ArrayList<String>();
		Date fromDateUtil = getDate("01-" + fromDate);
		Date toDateUtil = getDate("02-" + endDate);
		DateFormat formatter = new SimpleDateFormat(PATTERN_DATE_YYYYMMDD);
		listMonth = MethodUtil.getMonthBetween2Date(formatter.format(fromDateUtil), formatter.format(toDateUtil));
		List<ResourcePlanUtilizationDTO> result = new ArrayList<ResourcePlanUtilizationDTO>();
		for (String month : listMonth) {
			result.add(new ResourcePlanUtilizationDTO(month, 0));
		}
		for (ResourcePlanUtilizationDTO dtoResult : result) {
			for (ResourcePlanUtilizationDTO dto : listResourcePlan) {
				if (dtoResult.getMonth().trim().equals(dto.getMonth().trim())) {
					dtoResult.setValue(dto.getValue());
				}
			}
		}
		return result;
	}

	/**
	 * @param name
	 * @param duName
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author ngocd
	 */
	private List<ResourceUtilizationDTO> getAllResourcePlan(String name, String duName, int pageNumber, int pageSize)
			throws SQLException {
		Page<Integer> result = userPlanRepository.getResourceIdByNameAndDU(name, duName,
				new PageRequest(pageNumber, pageSize));
		List<ResourceUtilizationDTO> resourceUtilizationDTOs = new ArrayList<ResourceUtilizationDTO>();
		ResourceUtilizationDTO dto = null;

		List<Integer> listResourceId = result.getContent();
		List<QmsUser> qmsUsers = userQmsRepository.findAll();
		for (Integer id : listResourceId) {
			dto = new ResourceUtilizationDTO(id);
			dto.setFullName(qmsUsers);
			resourceUtilizationDTOs.add(dto);
		}
		return resourceUtilizationDTOs;
	}

	@Override
	public List<String> getAllUserDeliveryUnit() throws SQLException {
		List<String> listDeliveryUnit = new ArrayList<String>();
		String userDeliveryUnit = userQmsRepository.getAllUserDeliveryUnit();
		String[] listDU = userDeliveryUnit.split("-");
		for (String dU : listDU) {
			if (!dU.trim().isEmpty()) {
				listDeliveryUnit.add(dU.trim());
			}
		}
		return listDeliveryUnit;
	}

}
