package com.cmc.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

import com.cmc.dashboard.model.UserPlan;
import com.cmc.dashboard.model.UserPlanDetail;
import com.cmc.dashboard.util.MethodUtil;

public class ResourceAllocationDTO {
	private int userId;
	private int projectId;
	private int userPlanId;
	private String fullName;
	private String role;
	private List<UserPlanDetail> userPlanDetails;
	private float total;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getUserPlanId() {
		return userPlanId;
	}

	public void setUserPlanId(int userPlanId) {
		this.userPlanId = userPlanId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<UserPlanDetail> getUserPlanDetails() {
		return userPlanDetails;
	}

	public void setUserPlanDetails(List<UserPlanDetail> userPlanDetails) {
		this.userPlanDetails = userPlanDetails;
	}

	public void setUserPlanDetails(List<UserPlan> userPlans, List<String> listMonth) {
		List<UserPlanDetail> userPlanDetails = new ArrayList<UserPlanDetail>();
		List<UserPlanDetail> results = new ArrayList<UserPlanDetail>();
		UserPlanDetail detail = null;

		for (UserPlan userPlan : userPlans) {
			if (MethodUtil.checkList(userPlan.getUserPlanDetails())) {
				continue;
			}
			for (UserPlanDetail userPlanDetail : userPlan.getUserPlanDetails()) {
				if (userPlan.getUserId() == this.userId) {
					userPlanDetails.add(userPlanDetail);
				}
			}
		}

		float value = 0;
		for (String month : listMonth) {
			detail = new UserPlanDetail();
			detail.setPlanMonth(month);
			value = 0;
			for (UserPlanDetail userPlanDetail : userPlanDetails) {
				if (userPlanDetail.getPlanMonth().trim().equals(month.trim())) {
					value += userPlanDetail.getManDay();
				}
			}
			detail.setManDay(MethodUtil.formatFloatNumberType(value));
			results.add(detail);
		}

		this.userPlanDetails = results;
	}

	public float getTotal() {
		for (UserPlanDetail userPlanDetail : userPlanDetails) {
			total+=userPlanDetail.getManDay();
		}
		return MethodUtil.formatFloatNumberType(total);
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public ResourceAllocationDTO(int userId, int projectId, int userPlanId, String fullName, String role,
			List<UserPlanDetail> userPlanDetails, float total) {
		super();
		this.userId = userId;
		this.projectId = projectId;
		this.userPlanId = userPlanId;
		this.fullName = fullName;
		this.role = role;
		this.userPlanDetails = userPlanDetails;
		this.total = total;
	}

	public ResourceAllocationDTO() {
		super();
	}

	public ResourceAllocationDTO(int userId, String fullName, String role) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.role = role;
	}

}
