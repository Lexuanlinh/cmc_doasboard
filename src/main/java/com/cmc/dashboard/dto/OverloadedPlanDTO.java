package com.cmc.dashboard.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * Created by NMAn
 * On 20/01/2018
 * 
 */
public class OverloadedPlanDTO {
	
	private int user_id;
	private float total_effort;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date from_date;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date to_date;
	private int count;
	private boolean isOverload;
	
	
	public OverloadedPlanDTO(int user_id, float total_effort, Date from_date, Date to_date) {
		super();
		this.user_id = user_id;
		this.total_effort = total_effort;
		this.from_date = from_date;
		this.to_date = to_date;
	}
	
	
	public OverloadedPlanDTO(int user_id, float total_effort, Date from_date, Date to_date, int count) {
		super();
		this.user_id = user_id;
		this.total_effort = total_effort;
		this.from_date = from_date;
		this.to_date = to_date;
		this.count = count;
	}





	public OverloadedPlanDTO(int user_id, float total_effort, Date from_date, Date to_date, int count,
			boolean isOverload) {
		super();
		this.user_id = user_id;
		this.total_effort = total_effort;
		this.from_date = from_date;
		this.to_date = to_date;
		this.count = count;
		this.isOverload = isOverload;
	}


	public boolean isOverload() {
		return isOverload;
	}


	public void setOverload(boolean isOverload) {
		this.isOverload = isOverload;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public int getUser_id() {
		return user_id;
	}
	
	public float getTotal_effort() {
		return total_effort;
	}
	
	public Date getFrom_date() {
		return from_date;
	}
	
	public Date getTo_date() {
		return to_date;
	}
	
	
	
}
