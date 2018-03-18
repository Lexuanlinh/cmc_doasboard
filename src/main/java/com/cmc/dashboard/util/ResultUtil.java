package com.cmc.dashboard.util;

public class ResultUtil<T> {
	private T user;
	private String message;
	
	public ResultUtil() {
		super();
	}

	public ResultUtil(String message) {
		super();
		this.message = message;
	}

	public ResultUtil(T user, String message) {
		super();
		this.user = user;
		this.message = message;
	}


	public T getuser() {
		return user;
	}

	public void setuser(T user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
