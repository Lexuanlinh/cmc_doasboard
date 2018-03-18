package com.cmc.dashboard.dto;

import java.util.Date;

public class UserPlanTimeDTO {
	private int userPlanId;
	private Date fromDate;
	private Date toDate;
	private float manday;

	public UserPlanTimeDTO(int userPlanId, Date fromDate, Date toDate, float manday) {
		super();
		this.userPlanId = userPlanId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.manday = manday;
	}


	public int getUserPlanId() {
		return userPlanId;
	}

	public void setUserPlanId(int userPlanId) {
		this.userPlanId = userPlanId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public float getManday() {
		return manday;
	}

	public void setManday(float manday) {
		this.manday = manday;
	}

	public UserPlanTimeDTO() {
		super();
	}

}
