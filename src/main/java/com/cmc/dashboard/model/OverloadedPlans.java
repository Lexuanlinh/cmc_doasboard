package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "overloaded_plans")
@NamedQuery(name = "OverloadedPlans.findAll", query = "SELECT o FROM OverloadedPlans o")
public class OverloadedPlans implements Serializable {

	private static final long serialVersionUID = -5573824882671321691L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "overloaded_plans_id")
	private int overloadedPlanId;

	@Column(name = "user_id")
	private int user_id;

	@Column(name = "user_plan_id")
	private int user_plan_id;

	@Column(name = "total_effort")
	private float total_effort;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "from_date")
	private Date from_date;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "to_date")
	private Date to_date;

	public int getUser_id() {
		return user_id;
	}

	public OverloadedPlans() {
		super();
	}

	public OverloadedPlans(int user_id, float total_effort, Date from_date, Date to_date) {
		super();
		this.user_id = user_id;
		this.total_effort = total_effort;
		this.from_date = from_date;
		this.to_date = to_date;
	}

	public OverloadedPlans(int user_id, float total_effort, Date from_date, Date to_date, int user_plan_id) {
		super();
		this.user_id = user_id;
		this.total_effort = total_effort;
		this.from_date = from_date;
		this.to_date = to_date;
		this.user_plan_id = user_plan_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getUser_plan_id() {
		return user_plan_id;
	}

	public void setUser_plan_id(int user_plan_id) {
		this.user_plan_id = user_plan_id;
	}

	public float getTotal_effort() {
		return total_effort;
	}

	public void setTotal_effort(float total_effort) {
		this.total_effort = total_effort;
	}

	public Date getFrom_date() {
		return from_date;
	}

	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}

	public Date getTo_date() {
		return to_date;
	}

	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}

}
