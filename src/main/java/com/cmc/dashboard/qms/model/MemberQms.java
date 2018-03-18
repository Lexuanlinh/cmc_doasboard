package com.cmc.dashboard.qms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: nvkhoa
 * @Date: Dec 20, 2017
 */
@Entity
@Table(name = "members")
public class MemberQms implements Serializable {

	private static final long serialVersionUID = -822733441206588271L;

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "project_id")
	private int projectId;

	public MemberQms() {
		super();
	}

	public MemberQms(int id, int userId, int projectId) {
		super();
		this.id = id;
		this.userId = userId;
		this.projectId = projectId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

}
