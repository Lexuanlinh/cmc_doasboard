package com.cmc.dashboard.dto;

import java.text.DecimalFormat;

public class ResourcePlanUtilizationDTO {
	private String month;
	private float value;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public float getValue() {
		DecimalFormat newFormat = new DecimalFormat("#.##");
		return Float.valueOf(newFormat.format(value));
	}

	public void setValue(float value) {
		this.value = value;
	}

	public ResourcePlanUtilizationDTO(String month, float value) {
		super();
		this.month = month;
		this.value = value;
	}

	public ResourcePlanUtilizationDTO() {
		super();
	}

}
