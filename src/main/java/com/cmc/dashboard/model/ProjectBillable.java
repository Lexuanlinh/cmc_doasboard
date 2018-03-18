package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the project_billable database table.
 * 
 */
@Entity
@Table(name = "project_billable")
@NamedQuery(name = "ProjectBillable.findAll", query = "SELECT p FROM ProjectBillable p")
public class ProjectBillable implements Serializable {

	private static final long serialVersionUID = 3754474296091993526L;

	@Id
	@Column(name = "project_billable_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int projectBillableId;

	@Lob
	@Column(name = "billable_month")
	private String billableMonth;

	@Column(name = "billable_value")
	private float billableValue;

	@Column(name = "delivery_unit")
	private String deliveryUnit;

	@Column(name = "issue_code")
	private String issueCode;

	@Column(name = "pm_name")
	private String pmName;

	@Column(name = "project_id")
	private int projectId;

	@Column(name = "project_name")
	private String projectName;

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	private Date endDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date updatedOn;

	// @Transient
	// private float manDay;
	@Transient
	private float effortEfficiency;

	public ProjectBillable() {
	}

	public ProjectBillable(int projectBillableId, String billableMonth, float billableValue, Date createdOn,
			Date endDate, String issueCode, String pmName, int projectId, String projectName, Date startDate,
			Date updatedOn) {
		super();
		this.projectBillableId = projectBillableId;
		this.billableMonth = billableMonth;
		this.billableValue = billableValue;
		this.createdOn = createdOn;
		this.endDate = endDate;
		this.issueCode = issueCode;
		this.pmName = pmName;
		this.projectId = projectId;
		this.projectName = projectName;
		this.startDate = startDate;
		this.updatedOn = updatedOn;
	}

	public ProjectBillable(float manDay, float billableValue, int projectId, String projectName, String pmName,
			Date startDate, Date endDate) {
		super();
		// this.manDay = manDay;
		this.billableValue = billableValue;
		this.projectId = projectId;
		this.projectName = projectName;
		this.pmName = pmName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectName = projectName;
	}

	public int getProjectBillableId() {
		return this.projectBillableId;
	}

	public void setProjectBillableId(int projectBillableId) {
		this.projectBillableId = projectBillableId;
	}

	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public String getBillableMonth() {
		return this.billableMonth;
	}

	public void setBillableMonth(String billableMonth) {
		this.billableMonth = billableMonth;
	}

	public float getBillableValue() {
		return this.billableValue;
	}

	public void setBillableValue(float billableValue) {
		this.billableValue = billableValue;
	}

	@JsonIgnore
	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIssueCode() {
		return this.issueCode;
	}

	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}

	public String getPmName() {
		return this.pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

	@JsonIgnore
	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonIgnore
	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@JsonIgnore
	public float getEffortEfficiency() {
		return effortEfficiency;
	}

	public void setEffortEfficiency(float effortEfficiency) {
		this.effortEfficiency = effortEfficiency;
	}

	@JsonIgnore
	public String getDeliveryUnit() {
		return deliveryUnit;
	}

	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}

	// public float getManDay() {
	// return manDay;
	// }
	//
	// public void setManDay(float manDay) {
	// this.manDay = manDay;
	// }

}