package com.cmc.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

import com.cmc.dashboard.model.RolePermission;

public class AuthorizationUtilzationWrapper {
	List<RolePermission> rolePermissions = new ArrayList<RolePermission>();

	public List<RolePermission> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

	public AuthorizationUtilzationWrapper(List<RolePermission> rolePermissions) {
		super();
		this.rolePermissions = rolePermissions;
	}

	public AuthorizationUtilzationWrapper() {
		super();
	}

}
