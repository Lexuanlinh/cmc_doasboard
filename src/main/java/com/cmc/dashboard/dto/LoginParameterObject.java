package com.cmc.dashboard.dto;

import com.cmc.dashboard.util.MessageUtil;
import com.cmc.dashboard.util.MethodUtil;
import com.cmc.dashboard.util.RegularExpressions;

public class LoginParameterObject {
	private String username;
	
	private String password;
	
	public LoginParameterObject(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public LoginParameterObject() {}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	
	public String validate() {
		if (this.username.isEmpty() || this.username.equals(null) || this.password.isEmpty() || this.password.equals(null)) {
			return MessageUtil.DATA_EMPTY;
		}
		boolean isUserName = MethodUtil.validateLoginParams(username, RegularExpressions.USERNAME_PATTERN);
		boolean isPassword = MethodUtil.validateLoginParams(password, RegularExpressions.PASSWORD_PATTERN);
		if (!isUserName || !isPassword) {
			return MessageUtil.DATA_INVALID;
		}
		return MessageUtil.DATA_VALID;
	}
	public boolean compareTo(String hashed_password, String salt) {
		String hash_pass = MethodUtil.sha1(salt.concat(MethodUtil.sha1(this.password)));
		
		if (hash_pass.equals(hashed_password)) {
			return true;
		}
		return false;
	}
	
}
