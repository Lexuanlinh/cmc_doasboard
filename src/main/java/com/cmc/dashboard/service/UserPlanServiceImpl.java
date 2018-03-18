package com.cmc.dashboard.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.ListResourceDetailDTO;
import com.cmc.dashboard.dto.OverLoadPlanRemovedDTO;
import com.cmc.dashboard.dto.OverloadedPlanDTO;
import com.cmc.dashboard.dto.ProjectTimeDTO;
import com.cmc.dashboard.dto.RemoveResourcePlanDTO;
import com.cmc.dashboard.dto.ResourceAllocationDTO;
import com.cmc.dashboard.dto.ResourceDTO;
import com.cmc.dashboard.dto.ResourcePDetailDTO;
import com.cmc.dashboard.dto.ResourcePlanDTO;
import com.cmc.dashboard.dto.UpdateResourceDTO;
import com.cmc.dashboard.dto.UserPlanMessageDTO;
import com.cmc.dashboard.dto.UserPlanTimeDTO;
import com.cmc.dashboard.model.OverloadedPlans;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.model.UserPlan;
import com.cmc.dashboard.model.UserPlanDetail;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.OverloadedPlanRepository;
import com.cmc.dashboard.repository.ProjectBillableRepository;
import com.cmc.dashboard.repository.ProjectRepository;
import com.cmc.dashboard.repository.UserPlanDetailRepository;
import com.cmc.dashboard.repository.UserPlanRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.CustomValueUtil;
import com.cmc.dashboard.util.MessageUtil;
import com.cmc.dashboard.util.MethodUtil;
import com.cmc.dashboard.util.WriteCsv;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class UserPlanServiceImpl implements UserPlanService {

	@Autowired
	protected UserPlanRepository userPlanRepository;

	@Autowired
	private UserPlanDetailRepository userPlanDetailRepository;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected UserQmsRepository userQmsRepository;

	@Autowired
	protected UserPlanService userPlanService;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	OverloadedPlanRepository oPlanRepository;

	@Autowired
	protected ProjectBillableRepository projectBillableRepository;

	@Autowired
	protected ProjectService projectService;

	@Override
	public List<ResourceAllocationDTO> getListResourcesByProjectId(int projectId) {

		List<ResourceAllocationDTO> resourceAllocationDTOs = new ArrayList<ResourceAllocationDTO>();
		List<UserPlan> userPlans;
		ProjectTimeDTO pTimeDTO = null;
		try {
			 projectService.getBillableByProject(projectId);
			resourceAllocationDTOs = this.getListMemberOfProject(projectId);
			pTimeDTO = projectBillableRepository.getDateProject(projectId);
			userPlans = userPlanRepository.getAllUserEachProject(projectId);
		} catch (NullPointerException | SQLException e) {
			return resourceAllocationDTOs;
		}
		List<String> listMonths = MethodUtil.getMonthBetween2Date(pTimeDTO.getStartDate().toString(),
				pTimeDTO.getEndDate().toString());
		if (MethodUtil.checkList(listMonths) || MethodUtil.checkList(resourceAllocationDTOs)) {
			return resourceAllocationDTOs;
		}
		for (ResourceAllocationDTO dto : resourceAllocationDTOs) {
			dto.setProjectId(projectId);
			dto.setUserPlanDetails(userPlans, listMonths);
		}
		return resourceAllocationDTOs;
	}
	
	@Override
	public UpdateResourceDTO updatePlan(String plan) {
		UpdateResourceDTO updateRessourceList;
		UpdateResourceDTO updateError = new UpdateResourceDTO(MessageUtil.ERROR, null);
		JsonArray jPlanUpdates;
		List<UserPlan> uPlanUpdates = new ArrayList<>();
		List<UserPlanTimeDTO> getListUserPlanByUserId = new ArrayList<>();
		List<Integer> ltsUserPlanId;
		int projectId = 0;
		try {
			JsonObject jObj = MethodUtil.getJsonObjectByString(plan);
			projectId = jObj.get("projectId").getAsInt();
			if (projectId == 0) {
				return updateError;
			}
			jPlanUpdates = jObj.getAsJsonArray("listPlans");
			UserPlan userPlan = null;
			for (JsonElement jsonEle : jPlanUpdates) {
				JsonObject firstObj = jsonEle.getAsJsonObject();
				userPlan = getUserPlanUpdateByJson(firstObj);
				if (MethodUtil.isNull(userPlan)) {
					return updateError;
				}
				if (userPlan.getProjectId() == projectId) {
					uPlanUpdates.add(userPlan);
				}
			}
			getListUserPlanByUserId = userPlanRepository.getListUserPlanByUserId(uPlanUpdates.get(0).getUserId());
			ltsUserPlanId = getUpdatePlanId(getListUserPlanByUserId, uPlanUpdates);
			if (MethodUtil.checkList(ltsUserPlanId)) {
				return new UpdateResourceDTO(MessageUtil.SUCCESS,
						this.getResourcePlanByUser(uPlanUpdates.get(0).getUserId(),projectId));
			}
			userPlanDetailRepository.deleteListUserPlanDetail(ltsUserPlanId);
			oPlanRepository.deleteOverPlanAfterRemovePlan(ltsUserPlanId);
			for (UserPlan uplan : uPlanUpdates) {
				if (ltsUserPlanId.contains(uplan.getUserPlanId())) {
					updateResourcePlan(uplan);
				}
			}
		} catch (SQLException e) {
			updateRessourceList = new UpdateResourceDTO(MessageUtil.SUCCESS, null);
		} catch (ParseException p) {
			updateRessourceList = new UpdateResourceDTO(MessageUtil.SUCCESS, null);
		} catch (NumberFormatException e) {
			return updateError;
		} catch (NullPointerException e) {
			return updateError;
		} catch (IndexOutOfBoundsException e) {
			return updateError;
		}
		updateRessourceList = new UpdateResourceDTO(MessageUtil.SUCCESS,
				this.getResourcePlanByUser(uPlanUpdates.get(0).getUserId(),projectId));
		return updateRessourceList;
	}
	
	private void updateResourcePlan(UserPlan userPlan) throws SQLException, ParseException {
		int count = userPlanRepository.countAllUserPlaned(userPlan.getUserId());
		Object overPeriod = null;
		if (count==1) {
		}else {
			overPeriod = oPlanRepository.getListOfOverloadedPlansForUpdate(userPlan.getFromDate(), userPlan.getToDate(),
					userPlan.getUserId(),userPlan.getUserPlanId());
		}
			UserPlan savedPlan;
			OverloadedPlanDTO opObject = MethodUtil.getOverloadedPlanDTOUpdate(overPeriod);
			OverloadedPlans overloadedPlan;
			boolean isOverloadedItSelf = false;
			float effortPerDay = 0f;
			if (opObject != null) {
				isOverloadedItSelf = userPlan.getEffortPerDay() > (float) CustomValueUtil.WORKING_DAY;
				Date oFromDate = (isOverloadedItSelf) ? userPlan.getFromDate() : opObject.getFrom_date();
				Date oToDate = (isOverloadedItSelf) ? userPlan.getToDate() : opObject.getTo_date();
				// If the planned effort per day of the plan itself is greater then 8, then
				// definitely it will be overloaded
				if (opObject.getCount()!= 0) {
					overloadedPlan = new OverloadedPlans(opObject.getUser_id(), opObject.getTotal_effort(), oFromDate,
							oToDate);
					effortPerDay = (float) overloadedPlan.getTotal_effort() + userPlan.getEffortPerDay();
				}else {
					overloadedPlan = new OverloadedPlans(userPlan.getUserId(), userPlan.getEffortPerDay(), oFromDate,
							oToDate);
					effortPerDay = userPlan.getEffortPerDay();
				}

				if (effortPerDay > (float) CustomValueUtil.WORKING_DAY) {
					userPlan.setOverloaded(true);
					savedPlan = userPlanRepository.save(userPlan);
					int savedPlanId = savedPlan.getUserPlanId();
					overloadedPlan.setUser_plan_id(savedPlanId);
					oPlanRepository.save(overloadedPlan);
				} else {
					savedPlan = userPlanRepository.save(userPlan);
				}
			} else {
				isOverloadedItSelf = (userPlan.getEffortPerDay() > (float) CustomValueUtil.WORKING_DAY);
				if (isOverloadedItSelf) {
					overloadedPlan = new OverloadedPlans(userPlan.getUserId(), userPlan.getEffortPerDay(),
							userPlan.getFromDate(), userPlan.getToDate());
					userPlan.setOverloaded(true);
					savedPlan = userPlanRepository.save(userPlan);
					int savedPlanId = savedPlan.getUserPlanId();
					overloadedPlan.setUser_plan_id(savedPlanId);
					oPlanRepository.save(overloadedPlan);
					opObject = new OverloadedPlanDTO(savedPlan.getUserId(), userPlan.getEffortPerDay(),
							userPlan.getFromDate(), userPlan.getToDate());
				} else {
					savedPlan = userPlanRepository.save(userPlan);
				}
			}
			checkOverLoadPlan(userPlan);
			this.insertUserPlanDetail(savedPlan);
	}

	private boolean checkOverLoadPlan(UserPlan uPlan) {
		List<OverLoadPlanRemovedDTO> ltsOverLoadId;
		List<Object>ltsObj ;
		List<Integer> overPlanIds = new ArrayList<>();
		List<Integer> userPlanIds = new ArrayList<>();
		Object overPeriod;
		try {
			overPeriod = oPlanRepository.getListOfOverloadedPlans(uPlan.getFromDate(), uPlan.getToDate(),
					uPlan.getUserId());
			OverloadedPlanDTO opObject = MethodUtil.getOverloadedPlanDTO(overPeriod);
			boolean isOverloadedItSelf = false;
			if (opObject != null) {
				isOverloadedItSelf = opObject.getTotal_effort() > (float) CustomValueUtil.WORKING_DAY;
			}
			if (isOverloadedItSelf == false) {
				ltsObj = oPlanRepository.getListOverLoadPlanIdUpdate(uPlan.getUserId(),uPlan.getFromDate(),uPlan.getToDate());
				ltsOverLoadId = MethodUtil.getOverLoadPlanRemovedDTO(ltsObj);
				if (MethodUtil.checkList(ltsOverLoadId)) {
					return true;
				}
				for (OverLoadPlanRemovedDTO overLoadPlanRemovedDTO : ltsOverLoadId) {
					overPlanIds.add(overLoadPlanRemovedDTO.getOverLoadPlanId());
					userPlanIds.add(overLoadPlanRemovedDTO.getUserPlanId());
				}
				oPlanRepository.deleteOverPlanAfterRemovePlan(overPlanIds);
				oPlanRepository.updateIsOverLoaded(userPlanIds);
			}
		} catch (SQLException e) {
			return true;
		} catch (ParseException e) {
			return true;
		}
		return true;
	}

	private List<Integer> getUpdatePlanId(List<UserPlanTimeDTO> getListUserPlanByUserId, List<UserPlan> uPlanUpdates) {
		List<Integer> ltsUserPlanId = new ArrayList<>();
		for (UserPlan userPlan : uPlanUpdates) {
			for (UserPlanTimeDTO userPlanTO : getListUserPlanByUserId) {
				if (userPlan.getUserPlanId() == userPlanTO.getUserPlanId()) {
					if (userPlan.getFromDate().equals(userPlanTO.getFromDate())
							&& userPlan.getToDate().equals(userPlanTO.getToDate())
							&& userPlan.getManDay() == userPlanTO.getManday()) {
					} else {
						ltsUserPlanId.add(userPlanTO.getUserPlanId());
					}
				}
			}
		}
		return ltsUserPlanId;
	}

	@Override
	public UserPlanMessageDTO saveNewPlan(String plan) {

 		UserPlanMessageDTO userPlanMessageError = new UserPlanMessageDTO(MessageUtil.ERROR,
				MessageUtil.PARAMETERS_WERE_INVALID, null, false, null);
		if (MethodUtil.isNull(plan) || !MethodUtil.checkJson(plan)) {
			return userPlanMessageError;
		}
		JsonObject jObj;
		UserPlan userPlan = null;
		try {
			jObj = MethodUtil.getJsonObjectByString(plan);
			userPlan = getUserPlanByJson(jObj);
			if (MethodUtil.isNull(userPlan)) {
				return userPlanMessageError;
			}
			ProjectTimeDTO pTimeDTO = null;

			pTimeDTO = projectBillableRepository.getDateProject(userPlan.getProjectId());

			if (MethodUtil.isNull(pTimeDTO)) {
				return userPlanMessageError;
			}
			if (pTimeDTO != null && userPlan.getFromDate().before(pTimeDTO.getStartDate())
					|| userPlan.getToDate().after(pTimeDTO.getEndDate())
					|| userPlan.getFromDate().after(userPlan.getToDate())) {
				return userPlanMessageError;
			}
			int userPlanId = userPlan.getUserPlanId();
			int userId = userPlan.getUserId();
			if (userPlanId != 0) {
				userPlan.setUpdatedOn(new Date());
			}
			int count = userPlanRepository.countAllUserPlaned(userId);
			Object overPeriod = null;
			if (count!=0) {
				overPeriod = oPlanRepository.getListOfOverloadedPlans(userPlan.getFromDate(), userPlan.getToDate(),
						userId);
			}

			UserPlan savedPlan;
			OverloadedPlanDTO opObject = MethodUtil.getOverloadedPlanDTO(overPeriod);
			OverloadedPlans overloadedPlan;
			boolean isOverloadedItSelf = false;
			if (opObject != null) {
				isOverloadedItSelf = userPlan.getEffortPerDay() > (float) CustomValueUtil.WORKING_DAY;
				float overLoadValue = userPlan.getEffortPerDay() + opObject.getTotal_effort(); 
				Date oFromDate = (isOverloadedItSelf) ? userPlan.getFromDate() : opObject.getFrom_date();
				Date oToDate = (isOverloadedItSelf) ? userPlan.getToDate() : opObject.getTo_date();
				overloadedPlan = new OverloadedPlans(userId,overLoadValue , oFromDate,
						oToDate);
				// If the planned effort per day of the plan itself is greater then 8, then
				// definitely it will be overloaded

				//float effortPerDay = (float) overloadedPlan.getTotal_effort() + userPlan.getEffortPerDay();
				float effortPerDay =overLoadValue;

				if (effortPerDay > (float) CustomValueUtil.WORKING_DAY) {
					userPlan.setOverloaded(true);
					savedPlan = userPlanRepository.save(userPlan);
					int savedPlanId = savedPlan.getUserPlanId();
					overloadedPlan.setUser_plan_id(savedPlanId);
					oPlanRepository.save(overloadedPlan);
				} else {
					savedPlan = userPlanRepository.save(userPlan);
				}
			} else {
				isOverloadedItSelf = (userPlan.getEffortPerDay() > (float) CustomValueUtil.WORKING_DAY);
				if (isOverloadedItSelf) {
					overloadedPlan = new OverloadedPlans(userPlan.getUserId(), userPlan.getEffortPerDay(),
							userPlan.getFromDate(), userPlan.getToDate());
					userPlan.setOverloaded(true);
					savedPlan = userPlanRepository.save(userPlan);
					int savedPlanId = savedPlan.getUserPlanId();
					overloadedPlan.setUser_plan_id(savedPlanId);
					oPlanRepository.save(overloadedPlan);
					opObject = new OverloadedPlanDTO(savedPlan.getUserId(), userPlan.getEffortPerDay(),
							userPlan.getFromDate(), userPlan.getToDate());
				} else {
					savedPlan = userPlanRepository.save(userPlan);
				}
			}
			this.insertUserPlanDetail(savedPlan);
			return new UserPlanMessageDTO(MessageUtil.SUCCESS, MessageUtil.CREATED_PLAN_SUCCESSFULLY, savedPlan,
					isOverloadedItSelf, opObject);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return userPlanMessageError;
		} catch (ParseException e2) {
			e2.printStackTrace();
			return userPlanMessageError;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return userPlanMessageError;
		}
	}

	@Override
	public RemoveResourcePlanDTO removePlan(int planId, int userId, int projectId) {
		RemoveResourcePlanDTO removeResourceError,removeSuccess;
		removeResourceError=new RemoveResourcePlanDTO(MessageUtil.ERROR,new ArrayList<>());
		UserPlan uPlan = null;
		List<OverLoadPlanRemovedDTO> ltsOverLoadId;
		List<Integer> overPlanIds = new ArrayList<>();
		List<Integer> userPlanIds = new ArrayList<>();
		try {
			uPlan = userPlanRepository.findOne(planId);
			if (uPlan.getUserId() != userId || uPlan.getProjectId() != projectId) {
				return removeResourceError;
			}
			userPlanDetailRepository.deleteUserPlanDetail(planId);
			oPlanRepository.deleteOverPlan(planId);
			userPlanRepository.delete(planId);
			Object overPeriod = oPlanRepository.getListOfOverloadedPlans(uPlan.getFromDate(), uPlan.getToDate(),
					uPlan.getUserId());
			OverloadedPlanDTO opObject = MethodUtil.getOverloadedPlanDTO(overPeriod);
			List<Object> obj = userPlanRepository.getListObjectAfterDeletePlan(uPlan.getFromDate(), uPlan.getToDate(),
					uPlan.getUserId());
			if (MethodUtil.isNull(opObject)||MethodUtil.isNull(obj)) {
				return new RemoveResourcePlanDTO(MessageUtil.SUCCESS,this.getResourcePlanByUser(userId,projectId));
			}
			List<Integer> ltsId = new ArrayList<>();
			for (Object ob : obj) {
				Object[] o = (Object[])ob;
				ltsId.add(Integer.parseInt(o[0].toString()));
			}
			if (MethodUtil.checkList(ltsId)) {
				return new RemoveResourcePlanDTO(MessageUtil.SUCCESS,this.getResourcePlanByUser(userId,projectId));
			}
			if (opObject.getFrom_date().equals(opObject.getTo_date())) {
				if (opObject.isOverload()) {
					oPlanRepository.deleteOverPlanAfterRemovePlan(ltsId);
					oPlanRepository.updateIsOverLoaded(ltsId);	
				}
				return new RemoveResourcePlanDTO(MessageUtil.SUCCESS,this.getResourcePlanByUser(userId,projectId));
			}
			if (opObject.getCount() == 1 && opObject.isOverload() == false) {
				return new RemoveResourcePlanDTO(MessageUtil.SUCCESS,this.getResourcePlanByUser(userId,projectId));
			}
			boolean isOverloadedItSelf = false;
			if (opObject != null) {
				isOverloadedItSelf = opObject.getTotal_effort() > (float) CustomValueUtil.WORKING_DAY;
			}
			if (isOverloadedItSelf == false) {
				ltsOverLoadId = oPlanRepository.getListOverLoadPlanId(uPlan.getUserId());
				if (MethodUtil.checkList(ltsOverLoadId)) {
					return new RemoveResourcePlanDTO(MessageUtil.SUCCESS,this.getResourcePlanByUser(userId,projectId));
				}
				for (OverLoadPlanRemovedDTO overLoadPlanRemovedDTO : ltsOverLoadId) {
					if (ltsId.contains(overLoadPlanRemovedDTO.getUserPlanId())) {
						userPlanIds.add(overLoadPlanRemovedDTO.getUserPlanId());	
					}
				}
				oPlanRepository.deleteOverPlanAfterRemovePlan(userPlanIds);
				oPlanRepository.updateIsOverLoaded(userPlanIds);
			}
			return new RemoveResourcePlanDTO(MessageUtil.SUCCESS,this.getResourcePlanByUser(userId,projectId));
		} catch (IllegalArgumentException e) {
			return removeResourceError;
		} catch (SQLException e1) {
			return removeResourceError;
		} catch (ParseException e) {
			return removeResourceError;
		}catch (EmptyResultDataAccessException e) {
			return removeResourceError;
		}catch (NullPointerException e) {
			return removeResourceError;
		}

	}

	/**
	 * Method return UserPlan object from json element.
	 * 
	 * @param jsonElement
	 * @return UserPlan
	 * @author: Hoai-Nam
	 */
	public UserPlan getUserPlanByJson(JsonElement jsonElement) {
		JsonObject userPlan = jsonElement.getAsJsonObject();
		UserPlan uPlan = null;

		try {
			float manDay = Float.parseFloat(MethodUtil.getStringValue(userPlan, "manDay"));
			if (manDay <= 0) {
				return uPlan;
			}
			int projectId = Integer.parseInt(MethodUtil.getStringValue(userPlan, "projectId"));
			int id = Integer.parseInt(MethodUtil.getStringValue(userPlan, "userId"));

			String jsUserPlanId = MethodUtil.getStringValue(userPlan, "userPlanId");
			String role = MethodUtil.getStringValue(userPlan, "role");
			int userPlanId = MethodUtil.isNull(jsUserPlanId) ? 0 : Integer.parseInt(jsUserPlanId);
			Date fromDate = MethodUtil.convertStringToDate(MethodUtil.getStringValue(userPlan, "fromDate"));
			Date toDate = MethodUtil.convertStringToDate(MethodUtil.getStringValue(userPlan, "toDate"));
			long totalWorkingDay = MethodUtil.getTotalWorkingDaysBetweenDate(fromDate, toDate);
			if (manDay > totalWorkingDay) {
				return uPlan;
			}
			Date curDate = MethodUtil.getDate(MethodUtil.getCurrentDate());
			if ((fromDate.before(toDate) || fromDate.equals(toDate)) && (fromDate.equals(curDate) || fromDate.after(curDate))) {
				uPlan = new UserPlan(userPlanId, fromDate, manDay, projectId, toDate, id, role);
				uPlan.setManMonth(MethodUtil.formatFloatNumberType(manDay / CustomValueUtil.MAN_MONTH));
				uPlan.setEffortPerDay(MethodUtil.formatFloatNumberType((manDay
						/ MethodUtil.getTotalWorkingDaysBetweenDate(fromDate, toDate)) * CustomValueUtil.WORKING_DAY));
			}
			return uPlan;
		} catch (ParseException e1) {
			e1.printStackTrace();
			return null;
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			return null;
		} catch (DateTimeException dateException) {
			dateException.printStackTrace();
			return null;
		}catch (NullPointerException e) {
			return null;
		}
	}
	public UserPlan getUserPlanUpdateByJson(JsonElement jsonElement) {
		JsonObject userPlan = jsonElement.getAsJsonObject();
		UserPlan uPlan = null;

		try {
			ProjectTimeDTO pTimeDTO = null;
			int projectId = Integer.parseInt(MethodUtil.getStringValue(userPlan, "projectId"));
			pTimeDTO = projectBillableRepository.getDateProject(projectId);
			if (MethodUtil.isNull(pTimeDTO)) {
				return uPlan;
			}
			
			float manDay = Float.parseFloat(MethodUtil.getStringValue(userPlan, "manDay"));
			if (manDay <= 0) {
				return uPlan;
			}
			int id = Integer.parseInt(MethodUtil.getStringValue(userPlan, "userId"));
			String jsUserPlanId = MethodUtil.getStringValue(userPlan, "userPlanId");
			String role = MethodUtil.getStringValue(userPlan, "role");
			int userPlanId = MethodUtil.isNull(jsUserPlanId) ? 0 : Integer.parseInt(jsUserPlanId);
			Date fromDate = MethodUtil.convertStringToDate(MethodUtil.getStringValue(userPlan, "fromDate"));
			Date toDate = MethodUtil.convertStringToDate(MethodUtil.getStringValue(userPlan, "toDate"));
			long totalWorkingDay = MethodUtil.getTotalWorkingDaysBetweenDate(fromDate, toDate);
			if (manDay>totalWorkingDay) {
				return uPlan;
			}
			boolean checkAfterDate = fromDate.before(pTimeDTO.getStartDate());
			if (checkAfterDate==true) {
				return uPlan;	
			}
			if (fromDate.equals(pTimeDTO.getStartDate())||fromDate.before(toDate)&&toDate.before(pTimeDTO.getEndDate())||toDate.equals(pTimeDTO.getEndDate())) {
				uPlan = new UserPlan(userPlanId, fromDate, manDay, projectId, toDate, id, role);
				uPlan.setManMonth(MethodUtil.formatFloatNumberType(manDay / CustomValueUtil.MAN_MONTH));
				uPlan.setEffortPerDay(MethodUtil.formatFloatNumberType((manDay
						/ MethodUtil.getTotalWorkingDaysBetweenDate(fromDate, toDate)) * CustomValueUtil.WORKING_DAY));
			}
			return uPlan;
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			return null;
		} catch (DateTimeException dateException) {
			dateException.printStackTrace();
			return null;
		}catch (NullPointerException e) {
			return null;
		} catch (SQLException e) {
			return null;
		}
	}
	@Override
	public ListResourceDetailDTO getResourceDetail(String userLoginId, String userId, String planMonth) {
		ListResourceDetailDTO ltsDetail = new ListResourceDetailDTO();
		List<ResourcePlanDTO> ltsResourceDetail = new ArrayList<>();
		User userLogin = null;
		String groupName = null;
		if (!MethodUtil.validateInterger(userLoginId) || !MethodUtil.validateInterger(userId)) {
			return ltsDetail;
		}
		List<ResourcePDetailDTO> ltsUserPlans = null;
		List<Integer> ltsId = new ArrayList<>();
		try {
			userLogin = userRepository.findOne(Integer.parseInt(userLoginId));
		if (userLogin != null) {
			groupName = userLogin.getGroup().getGroupName();
		}
		String deliveryUnit = groupName.substring(3,groupName.length());
		
		if (deliveryUnit.equals(CustomValueUtil.QA)||
				deliveryUnit.equals(CustomValueUtil.BOD) ||
						deliveryUnit.equalsIgnoreCase(CustomValueUtil.PROJECT_MANAGER)) {
			ltsResourceDetail = userPlanRepository.getResourceDetail(Integer.parseInt(userId), planMonth);
		}else {
			 ltsResourceDetail=userPlanRepository.getResourceDetailWithDeleveryUnit(Integer.parseInt(userId), planMonth, deliveryUnit);
		}
		
		if (MethodUtil.checkList(ltsResourceDetail)) {
			return ltsDetail;
		}
		for (ResourcePlanDTO resourcePlanDTO : ltsResourceDetail) {
			ltsId.add(resourcePlanDTO.getProjectId());
		}
			ltsUserPlans = projectRepository.getNameProjectDetails(ltsId);
		} catch (SQLException e) {
			return ltsDetail;
		}
		int projectId = 0;
		for (ResourcePDetailDTO userPDetail : ltsUserPlans) {
			projectId = userPDetail.getProjectId();
			for (ResourcePlanDTO resource : ltsResourceDetail) {
				if (projectId == resource.getProjectId()) {
					userPDetail.setManDay(resource.getManDay());
				}
			}
		}
		ltsDetail = new ListResourceDetailDTO(ltsUserPlans);
		return ltsDetail;
	}

	public List<UserPlanDetail> insertUserPlanDetail(UserPlan userPlan) throws SQLException, ParseException {

		Map<String, Float> mapsPlanDate = MethodUtil.getEffortForEachMonth(userPlan.getFromDate(), userPlan.getToDate(),
				userPlan.getManDay());
		String duName = projectRepository.getDuNameByProjectId(userPlan.getProjectId());
		int userId = userPlan.getUserId();
		String resduName = userQmsRepository.getDeliveryUnitByUserId(userId);
		UserPlanDetail userPlanDetail;
		List<UserPlanDetail> listOfPlanDetails = new ArrayList<UserPlanDetail>();
		List<String> planMonths = mapsPlanDate.keySet().stream().collect(Collectors.toList());

		for (String planMonth : planMonths) {
			userPlanDetail = new UserPlanDetail(planMonth, mapsPlanDate.get(planMonth), duName,resduName, userPlan);
			listOfPlanDetails.add(userPlanDetail);
		}
		return userPlanDetailRepository.save(listOfPlanDetails);
	}

	@Override
	public String exportResourceAllocate(int projectId) throws SQLException {
		LocalDate today = LocalDate.now();
		String fileName = CustomValueUtil.FILE_NAME_RESOURCE + today + ".csv";
		int stt = 1;
		ProjectTimeDTO pTimeDTO = null;
		try {
			pTimeDTO = projectBillableRepository.getDateProject(projectId);
		} catch (SQLException e) {
			return fileName;
		}
		if (MethodUtil.isNull(pTimeDTO)) {
			return fileName;
		}
		List<String> headerMonths = MethodUtil.getMonthBetween2Date(pTimeDTO.getStartDate().toString(),
				pTimeDTO.getEndDate().toString());
		List<String> _header = new ArrayList<String>();
		_header.add("No");
		_header.add("Fullname");
		_header.add("Role");
		_header.add("Total");
		for (String header : headerMonths) {
			_header.add(header);
		}
		try {
			List<ResourceAllocationDTO> ltsUserPlanUtil = this.getListResourcesByProjectId(projectId);
			List<ArrayList<String>> resource = new ArrayList<ArrayList<String>>();
			ArrayList<String> resourceAllocate = null;
			List<UserPlanDetail> ltsPlans;
			for (ResourceAllocationDTO userPlan : ltsUserPlanUtil) {
				resourceAllocate = new ArrayList<String>();
				resourceAllocate.add("" + stt++);
				resourceAllocate.add(userPlan.getFullName());
				resourceAllocate.add(userPlan.getRole());
				resourceAllocate.add(String.valueOf(userPlan.getTotal()));
				ltsPlans = userPlan.getUserPlanDetails();
				for (UserPlanDetail resourceAllocateDTO : ltsPlans) {
					resourceAllocate.add(resourceAllocateDTO.getManDay() + "");
				}
				resource.add(resourceAllocate);
			}
			WriteCsv.writeCsv(fileName, _header, resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	public List<ResourceDTO> getResourcePlanByUser(int userId, int projectId) {
		List<ResourceDTO> listOfResourcePlans = new ArrayList<ResourceDTO>();
		try {
			listOfResourcePlans = userPlanRepository.getResourcePlanByUser(userId, projectId);
		//	listOfResourcePlans = userPlanRepository.getAllResourcePlanByUser(userId);
			return listOfResourcePlans;
		} catch (SQLException e) {
			e.printStackTrace();
			return listOfResourcePlans;
		}

	}

	private List<ResourceAllocationDTO> getListMemberOfProject(int projectId) {
		List<ResourceAllocationDTO> listResource = new ArrayList<>();
		List<Object> listMembers;
		try {
			listMembers = userQmsRepository.getAllUserEachProject(projectId);
			for (Object member : listMembers) {
				Object[] memberProperties = (Object[]) member;
				int userId = Integer.parseInt(memberProperties[0].toString());
				String fullName = memberProperties[1].toString();
				String role = memberProperties[2].toString();
				listResource.add(new ResourceAllocationDTO(userId, fullName, role));
			}
		} catch (SQLException e) {
			return listResource;
		}
		return listResource;
	}

}
