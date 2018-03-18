package com.cmc.dashboard.dto;

import java.util.List;

import com.cmc.dashboard.model.ProjectBillable;

public class ProjectBillableDTO {

	List<ProjectBillable> billableDetail;
	private String message;

	public List<ProjectBillable> getBillableDetail() {
		return billableDetail;
	}

	public void setBillableDetail(List<ProjectBillable> billableDetail) {
		this.billableDetail = billableDetail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ProjectBillableDTO(List<ProjectBillable> billableDetail, String message) {
		super();
		this.billableDetail = billableDetail;
		this.message = message;
	}
}
