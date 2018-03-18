package com.cmc.dashboard.dto;

import java.util.Date;

public class BillableEffortDTO {
	private String projectName;
	private String pmName;
	private Date startDate, endDate;
	private float ee;

	public BillableEffortDTO(String projectName, String pmName, Date startDate, Date endDate, float ee) {
		super();
		this.projectName = projectName;
		this.pmName = pmName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.ee = ee;
	}

	public BillableEffortDTO() {
		super();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPmName() {
		return pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public float getEe() {
		return ee;
	}

	public void setEe(float ee) {
		this.ee = ee;
	}

}
