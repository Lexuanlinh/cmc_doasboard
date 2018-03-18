package com.cmc.dashboard.dto;

import java.util.Date;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The persistent class for the project_css database table.
 * 
 */
public class ProjectCssUtilizationDto {

	@Id
	private int projectCssId;
	private int projectId;
	private String projectName;
	private float scoreValue;
	private int time;
	private Date startDate;
	private Date endDate;
	private String deliveryUnit;

	/**
	 * Constructure
	 */
	public ProjectCssUtilizationDto() {
		super();
	}

	/**
	 * Constructure
	 */
	public ProjectCssUtilizationDto(int projectCssId, int projectId, String projectName, float scoreValue, int time,
			Date startDate, Date endDate, String deliveryUnit) {
		super();
		this.projectCssId = projectCssId;
		this.projectId = projectId;
		this.projectName = projectName;
		this.scoreValue = scoreValue;
		this.time = time;
		this.startDate = startDate;
		this.endDate = endDate;
		this.deliveryUnit = deliveryUnit;
	}

	/**
	 * @return the projectCssId
	 */
	public int getProjectCssId() {
		return projectCssId;
	}

	/**
	 * @param projectCssId
	 *            the projectCssId to set
	 */
	public void setProjectCssId(int projectCssId) {
		this.projectCssId = projectCssId;
	}

	/**
	 * @return the projectId
	 */
	public int getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId
	 *            the projectId to set
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the scoreValue
	 */
	public float getScoreValue() {
		return scoreValue;
	}

	/**
	 * @param scoreValue
	 *            the scoreValue to set
	 */
	public void setScoreValue(float scoreValue) {
		this.scoreValue = scoreValue;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return the startDate
	 */
	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

}