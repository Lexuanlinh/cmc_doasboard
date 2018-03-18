package com.cmc.dashboard.util;

import java.util.List;

public class ResultUtilization<T> {
	private List<T> listObject;
	private String message;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the listObject
	 */
	public List<T> getListObject() {
		return listObject;
	}

	/**
	 * @param listObject
	 *            the listObject to set
	 */
	public void setListObject(List<T> listObject) {
		this.listObject = listObject;
	}

	/**
	 * Constructure
	 */
	public ResultUtilization() {
		super();
	}

	/**
	 * Constructure
	 */
	public ResultUtilization(List<T> listObject, String message) {
		super();
		this.listObject = listObject;
		this.message = message;
	}

}
