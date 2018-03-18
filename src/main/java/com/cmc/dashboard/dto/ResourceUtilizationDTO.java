/**
 * DashboardSystem - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cmc.dashboard.qms.model.QmsUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author: DVNgoc
 * @Date: Dec 27, 2017
 */
public class ResourceUtilizationDTO {

	private int userId;
	private String fullName;
	private List<Map<String, Float>> listPlan;
	private String duName;

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(List<QmsUser> users) {
		for (QmsUser user : users) {
			if (user.getId() == this.userId) {
				this.fullName = user.getLastname().concat(" ").concat(user.getFirstname());
			}
		}
	}

	/**
	 * @return the listPlan
	 */
	public List<Map<String, Float>> getListPlan() {
		return listPlan;
	}

	/**
	 * @param listPlan
	 *            the listPlan to set
	 */
	public void setListPlan(List<Map<String, Float>> listPlan) {
		this.listPlan = listPlan;
	}

	public void setListPlans(List<ResourcePlanUtilizationDTO> listResourcePlans) {
		List<Map<String, Float>> listPlan = new ArrayList<>();
		Map<String, Float> map;
		int count = 0;
		for (ResourcePlanUtilizationDTO detailDTO : listResourcePlans) {
			if (detailDTO.getValue() != 0) {
				count++;
			}
			map = new HashMap<String, Float>();
			map.put(detailDTO.getMonth(), detailDTO.getValue());
			listPlan.add(map);
		}
		this.listPlan = (count == 0) ? new ArrayList<Map<String, Float>>() : listPlan;
	}

	/**
	 * @return the duName
	 */
	@JsonIgnore
	public String getDuName() {
		return duName;
	}

	/**
	 * @param duName
	 *            the duName to set
	 */
	public void setDuName(String duName) {
		this.duName = duName;
	}

	/**
	 * Constructure
	 */
	public ResourceUtilizationDTO(int userId, String fullName, List<Map<String, Float>> listPlan, String duName) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.listPlan = listPlan;
		this.duName = duName;
	}

	/**
	 * Constructure
	 */
	public ResourceUtilizationDTO() {
		super();
	}

	/**
	 * Constructure
	 */
	public ResourceUtilizationDTO(int userId, String fullName, String duName) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.duName = duName;
	}

	/**
	 * Constructure
	 */
	public ResourceUtilizationDTO(int userId, String fullName) {
		super();
		this.userId = userId;
		this.fullName = fullName;
	}

	/**
	 * Constructure
	 */
	public ResourceUtilizationDTO(int userId) {
		super();
		this.userId = userId;
	}

	/**
	 * @return the userId
	 */

	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
