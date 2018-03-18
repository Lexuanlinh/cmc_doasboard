package com.cmc.dashboard.dto;

import java.text.DecimalFormat;

public class BillableDTO {
	private int projectBillableId;
	private String billableMonth;
	private float billableValue;
	private String issueCode;
	private double manMonth;

	public BillableDTO() {
		super();
	}

	public BillableDTO(int projectBillableId, String billableMonth, float billableValue, String issueCode) {
		super();
		this.projectBillableId = projectBillableId;
		this.billableMonth = billableMonth;
		this.billableValue = billableValue;
		this.issueCode = issueCode;
	}

	public BillableDTO(int projectBillableId, String billableMonth, int billableValue, String issueCode,
			double manMonth, String pmName) {
		super();
		this.projectBillableId = projectBillableId;
		this.billableMonth = billableMonth;
		this.billableValue = billableValue;
		this.issueCode = issueCode;
		this.manMonth = manMonth;
	}

	public int getProjectBillableId() {
		return projectBillableId;
	}

	public void setProjectBillableId(int projectBillableId) {
		this.projectBillableId = projectBillableId;
	}

	public String getBillableMonth() {
		return billableMonth;
	}

	public void setBillableMonth(String billableMonth) {
		this.billableMonth = billableMonth;
	}

	public float getBillableValue() {
		return billableValue;
	}

	public void setBillableValue(float billableValue) {
		this.billableValue = billableValue;
	}

	public String getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}

	public double getManMonth() {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		return Double.valueOf(formatter.format(manMonth));
	}

	public void setManMonth(double manMonth) {
		this.manMonth = manMonth;
	}
}
