/**
 * 
 */
package com.cmc.dashboard.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cmc.dashboard.qms.model.CustomValue;
import com.cmc.dashboard.qms.model.MemberQms;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.util.CustomValueUtil;

/**
 * @author nvkhoa
 *
 */
public class ProjectDTO {
	private Integer projectId;
	private String projectName;
	private Set<String> projectManager;
	private String projectType;
	private String deliveryUnit;
	private String projectSize;
	private int status;
	private String startDate;
	private String endDate;;

	public ProjectDTO() {
		super();
	}
	public ProjectDTO(Integer projectId, String projectName, int status) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.status = status;
	}
	public ProjectDTO(Integer projectId, String projectName, Set<String> projectManager) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectManager = projectManager;
	}

	public ProjectDTO(Integer projectId, String projectName, Set<String> projectManager, String projectType,
			String deliveryUnit, String projectSize, String startDate, String endDate) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectManager = projectManager;
		this.projectType = projectType;
		this.deliveryUnit = deliveryUnit;
		this.projectSize = projectSize;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getDeliveryUnit() {
		return deliveryUnit;
	}

	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}

	public String getProjectSize() {
		return projectSize;
	}

	public void setProjectSize(String projectSize) {
		this.projectSize = projectSize;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public Set<String> getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(Set<String> projectManager) {
		this.projectManager = projectManager;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setProjectManager(List<QmsUser> users, List<MemberQms> members) {
		Set<String> set = new HashSet<>();
		for(MemberQms member: members) {
			for(QmsUser user: users) {
				if (member.getProjectId() == this.projectId && member.getUserId() == user.getId()) {
					set.add(user.getLastname() + " " + user.getFirstname());
				}
			}
		}
		this.projectManager = set;
	}
	
	public void setCustomValue(List<CustomValue> customValues) {
		for (CustomValue customValue : customValues) {

			if (projectId == customValue.getCustomizedId()) {
				
				switch(customValue.getCustomFieldId()) {
					case CustomValueUtil.DELIVERY_UNIT_ID: {
						this.deliveryUnit = customValue.getValue();
						break;
					}
					case CustomValueUtil.MAN_DAY_ID: {
						this.projectSize= customValue.getValue();
						break;
					}
					case CustomValueUtil.START_DATE_ID: {
						this.startDate = customValue.getValue() ;
						break;
					}
					case CustomValueUtil.END_DATE_ID: {
						this.endDate = customValue.getValue() ;
						break;
					}
					case CustomValueUtil.PROJECT_TYPE_ID: {
						this.projectType = customValue.getValue();
						break;
					}
						
				}
			}
		}
	}
	public String toStringManager() {
		String manager = "";
		List<String> list = new ArrayList<>();
		list.addAll(this.projectManager);
		
		for (int i=0 ; i < list.size(); i++) {
			if (i == 0) {
				manager = list.get(i);
			} else {
				manager = manager + ", " + list.get(i);
			} 
		}
		return manager;
	}
}
