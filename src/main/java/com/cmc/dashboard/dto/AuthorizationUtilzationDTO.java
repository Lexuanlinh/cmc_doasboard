package com.cmc.dashboard.dto;

import java.util.List;

import com.cmc.dashboard.model.RolePermission;

public class AuthorizationUtilzationDTO {

	private String resourceName;
	private List<RolePermission> rolePermission_DUL;
	private List<RolePermission> rolePermission_PM;
	private List<RolePermission> rolePermission_QA;
	private List<RolePermission> rolePermission_BOD;

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public List<RolePermission> getRolePermission_DUL() {
		return rolePermission_DUL;
	}

	public void setRolePermission_DUL(List<RolePermission> rolePermission_DUL) {
		this.rolePermission_DUL = rolePermission_DUL;
	}

	public List<RolePermission> getRolePermission_PM() {
		return rolePermission_PM;
	}

	public void setRolePermission_PM(List<RolePermission> rolePermission_PM) {
		this.rolePermission_PM = rolePermission_PM;
	}

	public List<RolePermission> getRolePermission_QA() {
		return rolePermission_QA;
	}

	public void setRolePermission_QA(List<RolePermission> rolePermission_QA) {
		this.rolePermission_QA = rolePermission_QA;
	}

	public List<RolePermission> getRolePermission_BOD() {
		return rolePermission_BOD;
	}

	public void setRolePermission_BOD(List<RolePermission> rolePermission_BOD) {
		this.rolePermission_BOD = rolePermission_BOD;
	}


	public AuthorizationUtilzationDTO(Integer id, String resourceName, List<RolePermission> rolePermission_DUL,
			List<RolePermission> rolePermission_PM, List<RolePermission> rolePermission_QA,
			List<RolePermission> rolePermission_BOD) {
		super();
		this.resourceName = resourceName;
		this.rolePermission_DUL = rolePermission_DUL;
		this.rolePermission_PM = rolePermission_PM;
		this.rolePermission_QA = rolePermission_QA;
		this.rolePermission_BOD = rolePermission_BOD;
	}

	public AuthorizationUtilzationDTO(String resourceName, List<RolePermission> rolePermission_DUL,
			List<RolePermission> rolePermission_PM, List<RolePermission> rolePermission_QA,
			List<RolePermission> rolePermission_BOD) {
		super();
		this.resourceName = resourceName;
		this.rolePermission_DUL = rolePermission_DUL;
		this.rolePermission_PM = rolePermission_PM;
		this.rolePermission_QA = rolePermission_QA;
		this.rolePermission_BOD = rolePermission_BOD;
	}

	public AuthorizationUtilzationDTO() {
		super();
	}
}
