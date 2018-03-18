package com.cmc.dashboard.dto;

import java.util.Date;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResourceDTO {

	@Id
	private int userPlanId;
	private int userId;
	private int projectId;
	private float effortPerDay;
	private Date fromDate;
	private Date toDate;
	private float manDay;
	private boolean isOverloaded;
	
	public ResourceDTO() {
	}

	public ResourceDTO(Date fromDate, float manDay) {
		super();
		this.fromDate = fromDate;
		this.manDay = manDay;
	}

	public ResourceDTO(int userPlanId, int userId, Date fromDate, Date toDate, float manDay) {
		super();
		this.userPlanId = userPlanId;
		this.userId = userId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.manDay = manDay;
	}

	public ResourceDTO(int userPlanId, int userId,int projectId, Date fromDate, Date toDate, float manDay, float effortPerDay,boolean isOverloaded) {
		super();
		this.userPlanId = userPlanId;
		this.userId = userId;
		this.projectId=projectId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.manDay = manDay;
		this.effortPerDay = effortPerDay;
		this.isOverloaded = isOverloaded;
	}
	
	
	public ResourceDTO(int userPlanId, int userId, Date fromDate, Date toDate, float effortPerDay, float manDay,
			boolean isOverloaded) {
		super();
		this.userPlanId = userPlanId;
		this.userId = userId;
		this.effortPerDay = effortPerDay;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.manDay = manDay;
		this.isOverloaded = isOverloaded;
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

	public float getEffortPerDay() {
		return effortPerDay;
	}

	public void setEffortPerDay(float effortPerDay) {
		this.effortPerDay = effortPerDay;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public float getManDay() {
		return manDay;
	}

	public void setManDay(float manDay) {
		this.manDay = manDay;
	}

	public boolean isOverloaded() {
		return isOverloaded;
	}

	public void setOverloaded(boolean isOverloaded) {
		this.isOverloaded = isOverloaded;
	}

}
