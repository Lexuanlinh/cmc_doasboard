package com.cmc.dashboard.dto;

public class ResourcePlanDTO {
	private int projectId;
	private double manDay;

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public double getManDay() {
		return manDay;
	}

	public void setManDay(double manDay) {
		this.manDay = manDay;
	}

	public ResourcePlanDTO(int projectId, double manDay) {
		super();
		this.projectId = projectId;
		this.manDay = manDay;
	}

	public ResourcePlanDTO() {
		super();
	}

}
