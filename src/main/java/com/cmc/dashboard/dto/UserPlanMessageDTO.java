package com.cmc.dashboard.dto;

import com.cmc.dashboard.model.UserPlan;

public class UserPlanMessageDTO {
    private String message;
    private String errorDetail;
    private UserPlan data;
    private boolean isOverloaded;
    private OverloadedPlanDTO overloadedObj;
    
	public UserPlanMessageDTO(String message, String errorDetail, UserPlan data, boolean isOverloaded,
			OverloadedPlanDTO overloadedObj) {
		super();
		this.message = message;
		this.errorDetail = errorDetail;
		this.data = data;
		this.isOverloaded = isOverloaded;
		this.overloadedObj = overloadedObj;
	}

	public UserPlanMessageDTO(String message, String errorDetail) {
		super();
		this.message = message;
		this.errorDetail = errorDetail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	public UserPlan getData() {
		return data;
	}

	public void setData(UserPlan data) {
		this.data = data;
	}

	public boolean isOverloaded() {
		return isOverloaded;
	}

	public void setOverloaded(boolean isOverloaded) {
		this.isOverloaded = isOverloaded;
	}

	public OverloadedPlanDTO getOverloadedObj() {
		return overloadedObj;
	}

	public void setOverloadedObj(OverloadedPlanDTO overloadedObj) {
		this.overloadedObj = overloadedObj;
	}
	
    
}
