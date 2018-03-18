package com.cmc.dashboard.qms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: nvkhoa
 * @Date: Dec 20, 2017
 */
@Entity
@Table(name = "users")
public class QmsUser implements Serializable {

	private static final long serialVersionUID = -505781541336430186L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "login")
	private String login;
	@Column(name = "hashed_password")
	private String hashed_password;
	@Column(name = "firstname")
	private String firstname;
	@Column(name = "lastname")
	private String lastname;
	@Column(name = "admin")
	private boolean admin;
	@Column(name = "status")
	private int status;
	@Column(name = "salt")
	private String salt;

	public QmsUser() {
		super();
	}

	public QmsUser(int id, String firstname, String lastname) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public QmsUser(int id, String login, String firstname, String lastname, boolean admin, int status) {
		super();
		this.id = id;
		this.login = login;
		this.firstname = firstname;
		this.lastname = lastname;
		this.admin = admin;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHashed_password() {
		return hashed_password;
	}

	public void setHashed_password(String hashed_password) {
		this.hashed_password = hashed_password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}
