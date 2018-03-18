package com.cmc.dashboard.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.CssChartDTO;
import com.cmc.dashboard.dto.EfficiencyDTO;
import com.cmc.dashboard.dto.EffortEfficiencyDetails;
import com.cmc.dashboard.dto.ProjectCssDetailDTO;
import com.cmc.dashboard.dto.ProjectDTO;
import com.cmc.dashboard.model.ProjectBillable;
import com.cmc.dashboard.model.UserPlanDetail;
import com.cmc.dashboard.qms.model.CustomValue;
import com.cmc.dashboard.qms.model.MemberQms;
import com.cmc.dashboard.qms.model.ProjectQms;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.CustomValueRepository;
import com.cmc.dashboard.qms.repository.MemberQmsRepository;
import com.cmc.dashboard.qms.repository.ProjectQmsRepository;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.ProjectBillableRepository;
import com.cmc.dashboard.repository.ProjectCssRepository;
import com.cmc.dashboard.repository.UserPlanDetailRepository;
import com.cmc.dashboard.repository.UserPlanRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.CustomValueUtil;
import com.cmc.dashboard.util.MethodUtil;

/**
 * @author nahung
 *
 */
@Service
@Transactional
public class ReportServiceIpml implements ReportService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProjectCssRepository projectCssRepository;

	@Autowired
	ProjectQmsRepository projectQmsRepository;

	@Autowired
	CustomValueRepository customValueRepository;

	@Autowired
	UserQmsRepository userQmsRepository;

	@Autowired
	MemberQmsRepository memberQmsRepository;

	@Autowired
	UserPlanRepository userPlanRepository;

	@Autowired
	UserPlanDetailRepository detailPlanRepository;

	@Autowired
	ProjectBillableRepository projectBillableRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ReportService#getAllProject()
	 */

	@Override
	public List<CssChartDTO> getProjectCSSService(int month, int year, int userId) throws SQLException {
		List<CssChartDTO> listCssChartDto = new ArrayList<CssChartDTO>();
		List<String> listDU = projectCssRepository.getAllDeliveryUnitCSS();
		if (listDU.isEmpty()) {
			return listCssChartDto;
		} else {
			CssChartDTO cssChartDTO = null;
			for (String deliveryUnit : listDU) {
				cssChartDTO = new CssChartDTO(deliveryUnit, 0);
				listCssChartDto.add(cssChartDTO);
			}
			listCssChartDto.add(new CssChartDTO(CustomValueUtil.COMPANY, 0));
			try {
				listCssChartDto = this.getListCssChart(month, year, listDU);
			} catch (NullPointerException e) {
				e.printStackTrace();
				return listCssChartDto;
			}
		}
		// listCssChartDto = projectCssRepository.getProjectCSS(month, year);
		listCssChartDto = this.addObjectTotal(listCssChartDto, month, year);

		String groupName = null;

		try {
			groupName = userQmsRepository.getGroupByUserId(userId);
		} catch (NullPointerException e) {
			return listCssChartDto;
		}

		if (groupName == null || groupName.isEmpty() || groupName.length() <= 3) {
			return listCssChartDto;
		}

		String deliveryUnit = groupName.substring(groupName.lastIndexOf(CustomValueUtil.DA_PREFIX) + 3);

		switch (deliveryUnit) {
		case CustomValueUtil.BOD:
			return listCssChartDto;
		case CustomValueUtil.QA:
			return listCssChartDto;
		case CustomValueUtil.PROJECT_MANAGER:
			return listCssChartDto;
		default:
			return this.setListCssChartByDU(listCssChartDto, deliveryUnit);
		}
	}

	private List<CssChartDTO> getListCssChart(int month, int year, List<String> deliveryUnits)
			throws SQLException, NullPointerException {
		List<CssChartDTO> cssChartDTOs = new ArrayList<CssChartDTO>();
		CssChartDTO cssChartDTO = null;
		double avgCss = 0;
		for (String deliveryUnit : deliveryUnits) {
			avgCss = projectCssRepository.getAvgCssByDeliveryUnit(month, year, deliveryUnit);
			cssChartDTO = new CssChartDTO(deliveryUnit, avgCss);
			cssChartDTOs.add(cssChartDTO);
		}
		return cssChartDTOs;
	}

	/**
	 * Set list Css chart if user login is DU Lead
	 * 
	 * @param listCssChartDto
	 * @param deliveryUnit
	 * @return List<CssChartDTO>
	 * @author: DVNgoc
	 */
	private List<CssChartDTO> setListCssChartByDU(List<CssChartDTO> listCssChartDto, String deliveryUnit) {
		List<CssChartDTO> listCssChartDtoDU = new ArrayList<CssChartDTO>();
		for (CssChartDTO chartDTO : listCssChartDto) {
			if (chartDTO.getDu_name().trim().equals(deliveryUnit.trim())) {
				listCssChartDtoDU.add(chartDTO);
			}
		}
		return listCssChartDtoDU;
	}

	/**
	 * Add Object Total Css for Company
	 * 
	 * @param listCssChartDto
	 * @param month
	 * @param year
	 * @return List<CssChartDTO>
	 * @author: DVNgoc
	 */
	private List<CssChartDTO> addObjectTotal(List<CssChartDTO> listCssChartDto, int month, int year) {
		if (listCssChartDto.isEmpty()) {
			List<String> deliveryUnits = projectQmsRepository.getListDeliveryUnit();
			for (String deliveryUnit : deliveryUnits) {
				listCssChartDto.add(new CssChartDTO(deliveryUnit, 0));
			}
			listCssChartDto.add(new CssChartDTO(CustomValueUtil.COMPANY, 0));
			return listCssChartDto;
		} else {
			listCssChartDto.add(new CssChartDTO(CustomValueUtil.COMPANY,
					MethodUtil.formatDoubleNumberType(projectCssRepository.getAvgCssByDeliveryUnit(month, year, ""))));
			return listCssChartDto;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ReportService#getCssDetails(java.lang.String,
	 * int, int)
	 */
	@Override
	public List<ProjectCssDetailDTO> getCssDetails(String deliveryUnit, int month, int year) {

		if (deliveryUnit.trim().equals(CustomValueUtil.COMPANY)) {
			deliveryUnit = "";
		}

		List<Integer> listProjectId = projectCssRepository.getListProjectId(deliveryUnit, month, year);

		if (listProjectId.isEmpty() || listProjectId == null) {
			return new ArrayList<ProjectCssDetailDTO>();
		}

		List<ProjectCssDetailDTO> listResult = new ArrayList<ProjectCssDetailDTO>();
		int maxCssValue = projectCssRepository.getMaxOfCssValueNumber(deliveryUnit, month, year);
		ProjectCssDetailDTO projectCssDetailDto = new ProjectCssDetailDTO();
		List<ProjectQms> listProjectQms = projectQmsRepository.findAll();
		List<CustomValue> listCustomValue = customValueRepository.getAllValueProject();
		List<QmsUser> users = userQmsRepository.getUserIsPm();
		List<MemberQms> members = memberQmsRepository.getAllMemberRolePM();

		for (Integer projectId : listProjectId) {
			projectCssDetailDto = new ProjectCssDetailDTO();
			projectCssDetailDto.setProjectId(projectId);
			projectCssDetailDto.setProjectName(listProjectQms);
			projectCssDetailDto.setProjectManager(users, members);
			projectCssDetailDto.setStartDate(listCustomValue);
			projectCssDetailDto.setEndDate(listCustomValue);
			projectCssDetailDto.setCss(projectCssRepository.getCssTimeByProjectId(projectId, month, year), maxCssValue);
			listResult.add(projectCssDetailDto);
		}

		return (listResult);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ReportService#getProjectByType(int, int, int)
	 */
	@Override
	public List<Map<String, String>> getProjectByType(int month, int year, int userId) {
		List<Map<String, String>> lstMapType = new LinkedList<>();
		String groupName = new String();

		// Get group info from qms db
		groupName = userQmsRepository.findGroup(userId).get().getLastname();

		if (groupName.isEmpty() || groupName == null || !MethodUtil.isValidDate(month, year)) {
			return lstMapType;
		}
		Set<String> units = new LinkedHashSet<String>(this.getDeliveryUnit(groupName, userId));

		if (units.isEmpty() || units == null) {
			return lstMapType;
		}
		
		Map<String, String> map = MethodUtil.getDayOfDate(month, year);
		String startDate = map.get(CustomValueUtil.START_DATE_KEY);
		String endDate = map.get(CustomValueUtil.END_DATE_KEY);

		String possibleValue = customValueRepository.getPossibleValue(CustomValueUtil.PROJECT_TYPE_ID).get();
		Set<String> projectType = new LinkedHashSet<>(this.splitPossibleValue(possibleValue));

		List<Object> projects = projectQmsRepository.getProjectByType(units, startDate, endDate).get();

		lstMapType = this.countProjectByType(projects, units, projectType, groupName);
		return lstMapType;
	}

	/**
	 * Count project by type for each unit
	 * 
	 * @param objects
	 * @param units
	 * @param projectType
	 * @param groupName
	 * @return List<Map<String,String>>
	 * @author: NVKhoa
	 */
	public List<Map<String, String>> countProjectByType(List<Object> objects, Set<String> units,
			Set<String> projectType, String groupName) {
		List<Map<String, String>> projectByType = new LinkedList<>();

		Map<String, String> map;
		for (String unit : units) {
			map = new TreeMap<>();
			map.put(CustomValueUtil.DU_NAME, unit);
			int total = 0;
			for (String type : projectType) {
				int _count = this.countProject(unit, type, objects);
				map.put(type, Integer.toString(_count));
				total += _count;
			}
			map.put(CustomValueUtil.TOTAL, Integer.toString(total));
			projectByType.add(map);
		}
		return projectByType;
	}

	/**
	 * Total project by type for each delivery unit
	 * 
	 * @param unit
	 * @param projectType
	 * @param list
	 * @return int
	 * @author: CMC-GLOBAL
	 */
	private int countProject(String unit, String projectType, List<Object> objects) {
		int count = 0;
		for (Object object : objects) {

			Object[] _object = (Object[]) object;

			if (unit.equals(_object[0].toString()) && projectType.equals(_object[1].toString())) {
				count = Integer.parseInt(_object[2].toString());
			} else if (unit.equals(CustomValueUtil.COMPANY) && projectType.equals(_object[1].toString())) {
				count += Integer.parseInt(_object[2].toString());
			}
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ReportService#getProjectListByType(java.lang.
	 * String, java.util.List, int, int)
	 */
	@Override
	public List<ProjectDTO> getProjectListByType(String duName, List<String> projectType, int month, int year)
			throws SQLException {
		List<ProjectDTO> lstProjectDto = new ArrayList<ProjectDTO>();

		if (duName.isEmpty() || duName == null || projectType.isEmpty() || projectType == null
				|| !MethodUtil.isValidDate(month, year)) {
			return lstProjectDto;
		}

		List<ProjectQms> projects = new ArrayList<ProjectQms>();

		projects = this.getProjectQms(duName, projectType, month, year);

		if (projects.isEmpty() || projects == null) {
			return lstProjectDto;
		}

		lstProjectDto = this.createProjectList(projects);
		return lstProjectDto;
	}

	/**
	 * Get list of project by type
	 * 
	 * @param projects
	 * @return
	 * @throws SQLException
	 * @return List<ProjectDTO>
	 * @author: NVKhoa
	 */
	private List<ProjectQms> getProjectQms(String duName, List<String> projectType, int month, int year)
			throws NoSuchElementException {
		List<ProjectQms> projects = new ArrayList<ProjectQms>();

		Map<String, String> map = MethodUtil.getDayOfDate(month, year);
		String startDate = map.get(CustomValueUtil.START_DATE_KEY);
		String endDate = map.get(CustomValueUtil.END_DATE_KEY);
		if (!projectType.isEmpty() || projectType != null) {
			if (duName.equals(CustomValueUtil.COMPANY)) {
				projects = projectQmsRepository.getProjectByAllDu(projectType, startDate, endDate).get();
			} else {
				projects = projectQmsRepository.getListProjectByType(duName, projectType, startDate, endDate).get();
			}
		}
		return projects;
	}

	/**
	 * Create list of project from project qms
	 * 
	 * @param projects
	 * @return
	 * @throws SQLException
	 * @return List<ProjectDTO>
	 * @author: NVKhoa
	 */
	private List<ProjectDTO> createProjectList(List<ProjectQms> projects) throws SQLException, NullPointerException {
		List<ProjectDTO> lstprojectDto = new ArrayList<ProjectDTO>();

		List<CustomValue> customValues = customValueRepository.getAllValueProject();
		List<QmsUser> users = userQmsRepository.getUserIsPm();
		List<MemberQms> members = memberQmsRepository.getAllMemberRolePM();

		ProjectDTO projectDto;
		for (ProjectQms project : projects) {

			projectDto = new ProjectDTO(project.getId(), project.getProjectName(), project.getStatus());

			projectDto.setProjectManager(users, members);

			projectDto.setCustomValue(customValues);

			lstprojectDto.add(projectDto);
		}

		return lstprojectDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ReportService#getEffortEfficiency(int, int,
	 * int)
	 */
	@Override
	public List<EfficiencyDTO> getEffortEfficiency(int month, int year, int userId) throws SQLException {
		List<EfficiencyDTO> lstEfficiency = new ArrayList<EfficiencyDTO>();
		if (!MethodUtil.isValidDate(month, year)) {
			return lstEfficiency;
		}
		LocalDate date = LocalDate.of(year, month, 1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");

		String monthYear = date.format(formatter).toString();
		try {
			List<ProjectBillable> lstProjectBillable = Optional
					.ofNullable(projectBillableRepository.getBillableByMonth(monthYear)).get();

			List<UserPlanDetail> lstUserPlanDetail = Optional.ofNullable(detailPlanRepository.getPlanMonth(monthYear))
					.get();

			/*
			 * ===Get group info from dashboard db=== String groupName =
			 * userRepository.getGroupByUser(userId).get().getGroupName();
			 */

			// Get group info from qms db
			String groupName = userQmsRepository.findGroup(userId).get().getLastname();

			List<String> lstUnit = this.getDeliveryUnit(groupName, userId);

			EfficiencyDTO efficiency;
			for (String unit : lstUnit) {
				efficiency = new EfficiencyDTO();
				efficiency.setDu_name(unit);
				efficiency.setEfficiency(calculateEfficiency(unit, lstProjectBillable, lstUserPlanDetail));
				lstEfficiency.add(efficiency);
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return lstEfficiency;
	}

	/**
	 * Calculate effort efficiency
	 * 
	 * @param deliveryUnit
	 * @param lstProjectBillable
	 * @param lstUserPlanDetail
	 * @return int
	 * @author: NVKhoa
	 */
	private float calculateEfficiency(String deliveryUnit, List<ProjectBillable> lstProjectBillable,
			List<UserPlanDetail> lstUserPlanDetail) {
		float efficiency = 0;
		float totalBillable = 0, totalManDay = 0;

		for (ProjectBillable projectBillable : lstProjectBillable) {
			if (projectBillable.getDeliveryUnit().equals(deliveryUnit)) {
				totalBillable += projectBillable.getBillableValue();
			} else if (deliveryUnit.equals(CustomValueUtil.COMPANY)) {
				totalBillable += projectBillable.getBillableValue();
			}
		}

		for (UserPlanDetail userPlanDetail : lstUserPlanDetail) {
			if (userPlanDetail.getDeliveryUnit().equals(deliveryUnit)) {
				totalManDay += userPlanDetail.getManDay();
			} else if (deliveryUnit.equals(CustomValueUtil.COMPANY)) {
				totalManDay += userPlanDetail.getManDay();
			}
		}
		if (totalManDay > 0) {
			efficiency = (totalBillable / totalManDay) * 100;
		}
		return efficiency;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ReportService#getListProjectBillabeByDU(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public List<EffortEfficiencyDetails> getListProjectBillabeByDU(String duName, String billableMonth) {
		List<EffortEfficiencyDetails> listEffortEfficiencyDetails = new ArrayList<EffortEfficiencyDetails>();
		try {
			List<Object> listOfObjects = new ArrayList<>();
			if (duName.contains(CustomValueUtil.COMPANY.trim())) {
				listOfObjects = detailPlanRepository.getListUserPlanBillableDetail(billableMonth).get();
			} else {
				listOfObjects = detailPlanRepository.getListUserPlanBillableDetail(duName, billableMonth);
			}

			EffortEfficiencyDetails effortEfficiencyObj;
			for (Object object : listOfObjects) {
				Object[] listProps = (Object[]) object;
				String projectName = listProps[0].toString();
				String pmName = listProps[1].toString();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date startDate = formatter.parse(listProps[2].toString());
				Date endDate = formatter.parse(listProps[3].toString());
				float billableValue = Float.parseFloat(listProps[4].toString());
				float manDay = Float.parseFloat(listProps[5].toString());
				float effortEfficiency = Float.parseFloat(listProps[6].toString());

				effortEfficiencyObj = new EffortEfficiencyDetails(projectName, pmName, startDate, endDate,
						billableValue, manDay, effortEfficiency);
				listEffortEfficiencyDetails.add(effortEfficiencyObj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return listEffortEfficiencyDetails;
		} catch (ParseException e1) {
			e1.printStackTrace();
			return listEffortEfficiencyDetails;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return listEffortEfficiencyDetails;
		}

		return listEffortEfficiencyDetails;
	}

	/**
	 * Get list of unit by group's name and user identify
	 * 
	 * @param groupName
	 * @param userId
	 * @return List<String>
	 * @author: NVKhoa
	 */
	private List<String> getDeliveryUnit(String groupName, int userId) throws NoSuchElementException {
		List<String> lstUnit = new LinkedList<>();

		if (groupName.contains(CustomValueUtil.DU_LEAD) || groupName.contains(CustomValueUtil.GROUP_RRC)) {

			String duName = groupName.substring(CustomValueUtil.SUBSTRING_INDEX_GROUP, groupName.length());
			lstUnit.add(duName);

		} else if (groupName.contains(CustomValueUtil.QA) || groupName.contains(CustomValueUtil.BOD)) {

			lstUnit.addAll(this.splitPossibleValue(
					customValueRepository.getPossibleValue(CustomValueUtil.DELIVERY_UNIT_ID).get()));
			lstUnit.add(CustomValueUtil.COMPANY);
		}
		return lstUnit;
	}

	/**
	 * Split possible string from custom field
	 * 
	 * @param possible
	 * @return List<String>
	 * @author: NVKhoa
	 */
	public List<String> splitPossibleValue(String possible) {
		List<String> lstPossible = new LinkedList<>();
		String[] _possible = possible.split(CustomValueUtil.SPLIT_CHAR);
		for (String element : _possible) {
			if (!element.trim().isEmpty()) {
				lstPossible.add(element.trim());
			}
		}

		return lstPossible;
	}
}
