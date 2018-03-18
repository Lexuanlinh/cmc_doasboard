package com.cmc.dashboard.dto;

import java.util.List;

public class RemoveResourcePlanDTO {
	private String message;
	private List<ResourceDTO> ltsResource;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ResourceDTO> getLtsResource() {
		return ltsResource;
	}

	public void setLtsResource(List<ResourceDTO> ltsResource) {
		this.ltsResource = ltsResource;
	}

	public RemoveResourcePlanDTO(String message, List<ResourceDTO> ltsResource) {
		super();
		this.message = message;
		this.ltsResource = ltsResource;
	}

	public RemoveResourcePlanDTO() {
		super();
	}

}
