/**
 * DashboardSystem - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

/**
 * @author: DVNgoc
 * @Date: Dec 15, 2017
 */
public class CssTimeDto {
	private int time;
	private String values;

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
	 * @return the values
	 */
	public String getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(String values) {
		this.values = values;
	}

	/**
	 * Constructure
	 */
	public CssTimeDto(int time, String values) {
		super();
		this.time = time;
		this.values = values;
	}
	
	/**
	 * Constructure
	 */
	public CssTimeDto(int time, float values) {
		super();
		this.time = time;
		this.values = String.valueOf(values);
	}

	/**
	 * Constructure
	 */
	public CssTimeDto() {
		super();
	}

}
