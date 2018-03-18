package com.cmc.dashboard.dto;

import org.springframework.http.HttpStatus;

import com.cmc.dashboard.util.MessageUtil;

public class RestResponse {

	private Object data;
	private HttpStatus status;
	private String message;

	public RestResponse() {

	}

	public RestResponse(HttpStatus status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static RestResponse success(Object data) {
		return new RestResponse(HttpStatus.OK, "success", data);
	}

	public static RestResponse error(HttpStatus status, String message) {
		return new RestResponse(status, null, message);
	}

	public static RestResponse errorWithData(HttpStatus status, String message, Object data) {
		return new RestResponse(status, message, data);
	}

	public static RestResponse errorSQL() {
		return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR, null, MessageUtil.DATABASE_ERROR);
	}

}
