package com.cmc.dashboard.dto;

import com.cmc.dashboard.util.MethodUtil;

public class ResourceAllocateDTO  {
	private int userPlanDetailId;
	private String planMonth;
	private float manDay;
	private float ratio;
	private int totalWorkingDay ;
	private int userPlanId;

	
	public ResourceAllocateDTO(int userPlanDetailId, String planMonth, float manDay, int totalWorkingDay) {
		super();
		this.userPlanDetailId = userPlanDetailId;
		this.planMonth = planMonth;
		this.manDay = manDay;
		this.totalWorkingDay = totalWorkingDay;
	}

	public ResourceAllocateDTO(int userPlanDetailId, String planMonth, float manDay) {
		super();
		this.userPlanDetailId = userPlanDetailId;
		this.planMonth = planMonth;
		this.manDay = manDay;
	}

	public ResourceAllocateDTO(int userPlanDetailId, String planMonth, float manDay, int totalWorkingDay,
			int userPlanId) {
		super();
		this.userPlanDetailId = userPlanDetailId;
		this.planMonth = planMonth;
		this.manDay = manDay;
		this.totalWorkingDay = totalWorkingDay;
		this.userPlanId = userPlanId;
	}

	public int getTotalWorkingDay() {
		return totalWorkingDay;
	}

	
	public int getUserPlanId() {
		return userPlanId;
	}

	public void setUserPlanId(int userPlanId) {
		this.userPlanId = userPlanId;
	}

	public void setTotalWorkingDay(int totalWorkingDay) {
		this.totalWorkingDay = totalWorkingDay;
	}

	public float getRatio() {
		ratio = MethodUtil.formatFloatNumberType((manDay/totalWorkingDay) *100);
		if (Float.isNaN(ratio)) {
			ratio = 0;
		}
		return ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public int getUserPlanDetailId() {
		return userPlanDetailId;
	}

	public void setUserPlanDetailId(int userPlanDetailId) {
		this.userPlanDetailId = userPlanDetailId;
	}

	public String getPlanMonth() {
		return planMonth;
	}

	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}

	public float getManDay() {
		return Math.round(manDay);
	}

	public void setManDay(float manDay) {
		this.manDay = manDay;
	}


}
