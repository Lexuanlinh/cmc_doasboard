package com.cmc.dashboard.dto;

import java.util.List;

public class ListBillableDTO {
	private String message;
	private List<BillableDTO> listbillable;
	
	public ListBillableDTO(String message, List<BillableDTO> listbillable) {
		super();
		this.message = message;
		this.listbillable = listbillable;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<BillableDTO> getListbillable() {
		return listbillable;
	}
	public void setListbillable(List<BillableDTO> listbillable) {
		this.listbillable = listbillable;
	}
	
	
}
