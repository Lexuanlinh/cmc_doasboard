package com.cmc.dashboard.dto;

public class OverLoadPlanRemovedDTO {
	private int overLoadPlanId;
	private int userPlanId;

	public int getOverLoadPlanId() {
		return overLoadPlanId;
	}

	public void setOverLoadPlanId(int overLoadPlanId) {
		this.overLoadPlanId = overLoadPlanId;
	}

	public int getUserPlanId() {
		return userPlanId;
	}

	public void setUserPlanId(int userPlanId) {
		this.userPlanId = userPlanId;
	}

	public OverLoadPlanRemovedDTO(int overLoadPlanId, int userPlanId) {
		super();
		this.overLoadPlanId = overLoadPlanId;
		this.userPlanId = userPlanId;
	}

}
