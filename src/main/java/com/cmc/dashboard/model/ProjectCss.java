package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The persistent class for the project_css database table.
 * 
 */
@Entity
@Table(name = "project_css")
@NamedQuery(name = "ProjectCss.findAll", query = "SELECT p FROM ProjectCss p")
public class ProjectCss implements Serializable {

	private static final long serialVersionUID = -6795921611232416100L;

	@Id
	@Column(name = "project_css_id")
	private int projectCssId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "project_id")
	private int projectId;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "score_value")
	private float scoreValue;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "time")
	private int time;

	@Column(name = "delivery_unit")
	private String deliveryUnit;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date updatedOn;

	public ProjectCss() {
	}

	public int getProjectCssId() {
		return this.projectCssId;
	}

	public void setProjectCssId(int projectCssId) {
		this.projectCssId = projectCssId;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

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

	public float getScoreValue() {
		return this.scoreValue;
	}

	public void setScoreValue(float scoreValue) {
		this.scoreValue = scoreValue;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getTime() {
		return this.time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * @return the deliveryUnit
	 */
	public String getDeliveryUnit() {
		return deliveryUnit;
	}

	/**
	 * @param deliveryUnit
	 *            the deliveryUnit to set
	 */
	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}

	/**
	 * Constructure
	 */
	public ProjectCss(int projectCssId, Date createdOn, Date endDate, int projectId, String projectName,
			float scoreValue, Date startDate, int time, String deliveryUnit, Date updatedOn) {
		super();
		this.projectCssId = projectCssId;
		this.createdOn = createdOn;
		this.endDate = endDate;
		this.projectId = projectId;
		this.projectName = projectName;
		this.scoreValue = scoreValue;
		this.startDate = startDate;
		this.time = time;
		this.deliveryUnit = deliveryUnit;
		this.updatedOn = updatedOn;
	}

}