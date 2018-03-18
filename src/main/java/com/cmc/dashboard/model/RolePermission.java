package com.cmc.dashboard.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the role_permission database table.
 * 
 */
@Entity
@Table(name = "role_permission")
@NamedQuery(name = "RolePermission.findAll", query = "SELECT r FROM RolePermission r")
public class RolePermission implements Serializable {

	private static final long serialVersionUID = 1236146391377581201L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int enable;

	// bi-directional many-to-one association to Permission
	@ManyToOne
	@JoinColumn(name = "permission_id")
	private Permission permission;

	// bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	public RolePermission() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEnable() {
		return this.enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public Permission getPermission() {
		return this.permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	@JsonIgnore
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public RolePermission(int id, int enable, Permission permission, Role role) {
		super();
		this.id = id;
		this.enable = enable;
		this.permission = permission;
		this.role = role;
	}

}