package com.cmc.dashboard.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SearchResourcePlanDTO {

	private int userPlanId;
	private int userId;
	private String fullName;
	private Date fromDate;
	private Date toDate;
	private float manDay;

	public SearchResourcePlanDTO(int userPlanId, int userId, String fullName, Date fromDate, Date toDate,
			float manDay) {
		super();
		this.userPlanId = userPlanId;
		this.userId = userId;
		this.fullName = fullName;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.manDay = manDay;
	}

	public SearchResourcePlanDTO() {
		super();
	}

	public int getUserPlanId() {
		return userPlanId;
	}

	public void setUserPlanId(int userPlanId) {
		this.userPlanId = userPlanId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public float getManDay() {
		return manDay;
	}

	public void setManDay(float manDay) {
		this.manDay = manDay;
	}

}
