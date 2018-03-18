package com.cmc.dashboard.util;

public enum Role {

	RESOURCE_ACCESS("RESOURCE_ACCESS"),
	RESOURCE_DELETE("RESOURCE_DELETE"),
	RESOURCE_CREATE("RESOURCE_CREATE"),
	RESOURCE_EDIT("RESOURCE_EDIT"),
	
	BILLABLE_ACCESS("BILLABLE_ACCESS"),
	BILLABLE_DELETE("BILLABLE_DELETE"),
	BILLABLE_CREATE("BILLABLE_CREATE"),
	BILLABLE_EDIT("BILLABLE_EDIT"),
	
	CSS_ACCESS("CSS_ACCESS"),
	CSS_DELETE("CSS_DELETE"),
	CSS_CREATE("CSS_CREATE"),
	CSS_EDIT("CSS_EDIT"),
	ADMIN("ADMIN");
	
	Role(String name) {
		this.name = name;
	}


	private final String name;

	/**
	 * Return the Description of this status code.
	 */
	public String getName() {
		return this.name;
	}
}
