package com.cmc.dashboard.dto;

import com.cmc.dashboard.util.MethodUtil;

public class ResourcePDetailDTO {
	private String projectName;
	private String pmName;
	private double manDay;
	private int projectId;
	public ResourcePDetailDTO( int projectId,String projectName, String pmName) {
		super();
		this.projectName = projectName;
		this.pmName = pmName;
		this.projectId = projectId;
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

	public double getManDay() {
		return MethodUtil.formatDoubleNumberType(manDay);
	}

	public void setManDay(double manDay) {
		this.manDay = manDay;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public ResourcePDetailDTO(String projectName, String pmName, double manDay, int projectId) {
		super();
		this.projectName = projectName;
		this.pmName = pmName;
		this.manDay = manDay;
		this.projectId = projectId;
	}

	public ResourcePDetailDTO() {
		super();
	}

}
