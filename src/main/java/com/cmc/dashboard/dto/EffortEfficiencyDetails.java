/**
 * 
 */
package com.cmc.dashboard.dto;

import java.util.Date;

import com.cmc.dashboard.util.MethodUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Blue-Snake's Laptop
 *
 */

public class EffortEfficiencyDetails{
	
	private String projectName;
	private String pmName;
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+7")
	private Date startDate;
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+7")
	private Date endDate;
	private float billableValue;
	private float manDay;
	private float effortEfficiency;
	
	public EffortEfficiencyDetails(String projectName, String pmName, Date startDate, Date endDate, float billableValue,
			float manDay, float effortEfficiency) {
		super();
		this.projectName = projectName;
		this.pmName = pmName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.billableValue = billableValue;
		this.manDay = manDay;
		this.effortEfficiency = effortEfficiency;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public String getPmName() {
		return pmName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public float getBillableValue() {
		return billableValue;
	}
	public float getManDay() {
		return MethodUtil.formatFloatNumberType(manDay);
	}
	public float getEffortEfficiency() {
		return MethodUtil.formatFloatNumberType(effortEfficiency);
	}
}
