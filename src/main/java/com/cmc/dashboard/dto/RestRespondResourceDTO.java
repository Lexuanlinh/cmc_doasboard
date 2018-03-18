package com.cmc.dashboard.dto;

import org.springframework.http.HttpStatus;

import com.cmc.dashboard.util.MessageUtil;

public class RestRespondResourceDTO {

	private Object data;
	private Object listDU;
	private HttpStatus status;
	private Long totalElement;

	public RestRespondResourceDTO(HttpStatus status, Long totalElement, Object data) {
		super();
		this.data = data;
		this.status = status;
		this.totalElement = totalElement;
	}

	public RestRespondResourceDTO(RestRespondResourceDTO dto, Object listDu) {
		super();
		this.data = dto.getData();
		this.listDU = listDu;
		this.status = dto.getStatus();
		this.totalElement = dto.getTotalElement();
	}

	public RestRespondResourceDTO(Object data, Object listDU, HttpStatus status, Long totalElement) {
		super();
		this.data = data;
		this.listDU = listDU;
		this.status = status;
		this.totalElement = totalElement;
	}

	public RestRespondResourceDTO() {
		super();
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Long getTotalElement() {
		return totalElement;
	}

	public void setTotalElement(Long totalElement) {
		this.totalElement = totalElement;
	}

	public Object getListDU() {
		return listDU;
	}

	public void setListDU(Object listDU) {
		this.listDU = listDU;
	}

	public static RestRespondResourceDTO success(Object data, Long totalElement) {
		return new RestRespondResourceDTO(HttpStatus.OK, totalElement, data);
	}

	public static RestRespondResourceDTO success(RestRespondResourceDTO data, Object listDU) {
		return new RestRespondResourceDTO(data, listDU);
	}

	public static RestRespondResourceDTO error(HttpStatus status, String message) {
		return new RestRespondResourceDTO(status, null, message);
	}

	public static RestRespondResourceDTO errorSQL() {
		return new RestRespondResourceDTO(HttpStatus.INTERNAL_SERVER_ERROR, null, MessageUtil.DATABASE_ERROR);
	}

}
