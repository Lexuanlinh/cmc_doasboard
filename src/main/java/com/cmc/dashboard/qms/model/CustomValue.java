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
@Table(name = "custom_values")
public class CustomValue implements Serializable {

	private static final long serialVersionUID = 2517503266789868560L;

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "customized_type")
	private String customizedType;

	@Column(name = "customized_id")
	private int customizedId;

	@Column(name = "custom_field_id")
	private int customFieldId;

	@Column(name = "value")
	private String value;

	public CustomValue() {
		super();
	}

	public CustomValue(int id, String customizedType, int customizedId, int customFieldId, String value) {
		super();
		this.id = id;
		this.customizedType = customizedType;
		this.customizedId = customizedId;
		this.customFieldId = customFieldId;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomizedId() {
		return customizedId;
	}

	public void setCustomizedId(int customizedId) {
		this.customizedId = customizedId;
	}

	public int getCustomFieldId() {
		return customFieldId;
	}

	public void setCustomFieldId(int customFieldId) {
		this.customFieldId = customFieldId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCustomizedType() {
		return customizedType;
	}

	public void setCustomizedType(String customizedType) {
		this.customizedType = customizedType;
	}

}
