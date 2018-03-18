package com.cmc.dashboard.dto;

import java.util.List;

import com.cmc.dashboard.model.UserPlanDetail;

public class UserPlanUtilizationDTO {
	private int userId;
	private int projectId;
	private int userPlanId;
	private String fullName;
	private String role;
	private List<UserPlanDetail> userPlanDetails;
	private int total;

	public UserPlanUtilizationDTO() {
	}

	public UserPlanUtilizationDTO(int userId, String fullName, String role) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.role = role;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserPlanId() {
		return userPlanId;
	}

	public void setUserPlanId(int userPlanId) {
		this.userPlanId = userPlanId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getTotal() {
		for (UserPlanDetail resourceAllocateDTO : userPlanDetails) {
			total += resourceAllocateDTO.getManDay();
		}
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<UserPlanDetail> getUserPlanDetails() {
		return userPlanDetails;
	}

	public void setUserPlanDetails(List<UserPlanDetail> userPlanDetails) {
		this.userPlanDetails = userPlanDetails;
	}

	public UserPlanUtilizationDTO(int userId, int projectId, int userPlanId, String fullName, String role,
			List<UserPlanDetail> userPlanDetails, int total) {
		super();
		this.userId = userId;
		this.projectId = projectId;
		this.userPlanId = userPlanId;
		this.fullName = fullName;
		this.role = role;
		this.userPlanDetails = userPlanDetails;
		this.total = total;
	}

}
