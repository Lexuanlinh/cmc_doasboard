package com.cmc.dashboard.dto;

import java.text.DecimalFormat;

public class EfficiencyDTO {
	private String du_name;
	private float efficiency;
	public EfficiencyDTO() {
		super();
	}
	public EfficiencyDTO(String du_name, int efficiency) {
		super();
		this.du_name = du_name;
		this.efficiency = efficiency;
	}
	public String getDu_name() {
		return du_name;
	}
	public void setDu_name(String du_name) {
		this.du_name = du_name;
	}
	public float getEfficiency() {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		return Float.valueOf(formatter.format(efficiency));
	}
	public void setEfficiency(float efficiency) {
		this.efficiency = efficiency;
	}
	
}
