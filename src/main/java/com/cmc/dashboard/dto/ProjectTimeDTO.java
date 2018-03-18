package com.cmc.dashboard.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProjectTimeDTO {
	private Date startDate;
	private Date endDate;

	public ProjectTimeDTO() {
		super();
	}

	public ProjectTimeDTO(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}
	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
