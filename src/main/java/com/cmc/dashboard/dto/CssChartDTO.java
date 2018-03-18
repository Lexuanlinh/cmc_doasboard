package com.cmc.dashboard.dto;

import java.io.Serializable;
import java.text.DecimalFormat;

public class CssChartDTO implements Serializable {

	/**
	 * 
	 */
	public static final long serialVersionUID = 521918319285399612L;
	public String du_name;
	public double average_css;

	public CssChartDTO(String du_name, double average_css) {
		this.du_name = du_name;
		this.average_css = average_css;
	}

	public CssChartDTO() {
		super();
	}

	public String getDu_name() {
		return du_name;
	}

	public void setDu_name(String du_name) {
		this.du_name = du_name;
	}

	public double getAverage_css() {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		return Double.valueOf(formatter.format(average_css));
	}

	public void setAverage_css(double average_css) {
		this.average_css = average_css;
	}

}
