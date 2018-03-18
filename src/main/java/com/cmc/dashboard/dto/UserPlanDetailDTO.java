package com.cmc.dashboard.dto;

public class UserPlanDetailDTO {
	private String fullName;
	private String projectName;
	private String pmName;
	private float manDay;
	private String planMonth;
	private int projectId;

	public UserPlanDetailDTO(String fullName, String projectName, String pmName, float manDay, String planMonth) {
		super();
		this.fullName = fullName;
		this.projectName = projectName;
		this.pmName = pmName;
		this.manDay = manDay;
		this.planMonth = planMonth;
	}
	
	public UserPlanDetailDTO(float manDay, String planMonth, int projectId) {
		super();
		this.manDay = manDay;
		this.planMonth = planMonth;
		this.projectId = projectId;
	}



	public UserPlanDetailDTO(int projectId,String projectName, String pmName) {
		super();
		this.projectName = projectName;
		this.pmName = pmName;
		this.projectId = projectId;
	}


	public UserPlanDetailDTO(String fullName, float manDay, String planMonth, int projectId) {
		super();
		this.fullName = fullName;
		this.manDay = manDay;
		this.planMonth = planMonth;
		this.projectId = projectId;
	}


	public UserPlanDetailDTO() {
		super();
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public float getManDay() {
		return Math.round(manDay);
	}

	public void setManDay(float manDay) {
		this.manDay = manDay;
	}

	public String getPlanMonth() {
		return planMonth;
	}

	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}

}
