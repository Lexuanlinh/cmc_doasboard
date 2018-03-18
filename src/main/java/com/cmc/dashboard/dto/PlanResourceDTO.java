package com.cmc.dashboard.dto;

public class PlanResourceDTO {
	private int userPlanId;
	private String planmonth;
	private float manday;

	public PlanResourceDTO(int userPlanId, String planmonth, float manday) {
		super();
		this.userPlanId = userPlanId;
		this.planmonth = planmonth;
		this.manday = manday;
	}

	public PlanResourceDTO() {
		super();
	}

	public int getUserPlanId() {
		return userPlanId;
	}

	public void setUserPlanId(int userPlanId) {
		this.userPlanId = userPlanId;
	}

	public String getPlanmonth() {
		return planmonth;
	}

	public void setPlanmonth(String planmonth) {
		this.planmonth = planmonth;
	}

	public float getManday() {
		return manday;
	}

	public void setManday(float manday) {
		this.manday = manday;
	}

}
