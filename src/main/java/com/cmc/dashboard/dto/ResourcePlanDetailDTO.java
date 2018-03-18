package com.cmc.dashboard.dto;

public class ResourcePlanDetailDTO {
	private int userPlanId;
	private int userId;
	private int userPlanDetailId;
	private String planMonth;
	private float manDay;

	public ResourcePlanDetailDTO() {
		super();
	}

	public ResourcePlanDetailDTO(int userPlanId,int userPlanDetailId, String planMonth, float manDay) {
		super();
		this.userPlanId = userPlanId;
		this.userPlanDetailId = userPlanDetailId;
		this.planMonth = planMonth;
		this.manDay = manDay;
	}

	public ResourcePlanDetailDTO(int userPlanId, int userId, int userPlanDetailId, String planMonth, float manDay) {
		super();
		this.userPlanId = userPlanId;
		this.userId = userId;
		this.userPlanDetailId = userPlanDetailId;
		this.planMonth = planMonth;
		this.manDay = manDay;
	}

	public int getUserPlanDetailId() {
		return userPlanDetailId;
	}

	public void setUserPlanDetailId(int userPlanDetailId) {
		this.userPlanDetailId = userPlanDetailId;
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

	public String getPlanMonth() {
		return planMonth;
	}

	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}

	public float getManDay() {
		return manDay;
	}

	public void setManDay(float manDay) {
		this.manDay = manDay;
	}

}
