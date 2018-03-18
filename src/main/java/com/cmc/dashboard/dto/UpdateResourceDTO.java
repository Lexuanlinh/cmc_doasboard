package com.cmc.dashboard.dto;

import java.util.List;

public class UpdateResourceDTO {
	private String message;
	private List<ResourceDTO> listResourceUpdate;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ResourceDTO> getListResourceUpdate() {
		return listResourceUpdate;
	}

	public void setListResourceUpdate(List<ResourceDTO> listResourceUpdate) {
		this.listResourceUpdate = listResourceUpdate;
	}

	public UpdateResourceDTO(String message, List<ResourceDTO> listResourceUpdate) {
		super();
		this.message = message;
		this.listResourceUpdate = listResourceUpdate;
	}

}
