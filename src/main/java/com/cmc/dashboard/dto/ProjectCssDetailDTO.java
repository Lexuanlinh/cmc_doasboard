/**
 * DashboardSystem - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cmc.dashboard.qms.model.CustomValue;
import com.cmc.dashboard.qms.model.MemberQms;
import com.cmc.dashboard.qms.model.ProjectQms;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.util.CustomValueUtil;
import com.cmc.dashboard.util.MethodUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author: DVNgoc
 * @Date: Dec 15, 2017
 */
public class ProjectCssDetailDTO {

	private int projectId;
	private String projectName;
	private Set<String> projectManager;
	private String startDate;
	private String endDate;
	private List<CssTimeDto> css;

	/**
	 * @return the projectId
	 */
	public int getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId
	 *            the projectId to set
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @param listProjectQms
	 *            void
	 * @author: DVNgoc
	 */
	public void setProjectName(List<ProjectQms> listProjectQms) {
		for (ProjectQms projectQms : listProjectQms) {
			if (this.projectId == projectQms.getId()) {
				this.projectName = projectQms.getProjectName();
			}
		}
	}

	/**
	 * @return the projectManager
	 */
	public Set<String> getProjectManager() {
		return projectManager;
	}

	/**
	 * @param projectManager
	 *            the projectManager to set
	 */
	public void setProjectManager(Set<String> projectManager) {
		this.projectManager = projectManager;
	}

	public void setProjectManager(List<QmsUser> users, List<MemberQms> members) {
		Set<String> list = new HashSet<>();
		for (MemberQms member : members) {
			for (QmsUser user : users) {
				if (member.getProjectId() == this.projectId && member.getUserId() == user.getId()) {
					list.add(user.getLastname() + " " + user.getFirstname());
				}
			}
		}
		this.projectManager = list;
	}

	/**
	 * @return the startDate
	 */
	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param listCustomValue
	 *            void
	 */
	public void setStartDate(List<CustomValue> listCustomValue) {
		for (CustomValue customValue : listCustomValue) {
			if (projectId == customValue.getCustomizedId()) {
				if (customValue.getCustomFieldId() == CustomValueUtil.START_DATE_ID) {
					this.startDate = customValue.getValue();
				}
			}
		}
	}

	/**
	 * @param listCustomValue
	 *            void
	 */
	public void setEndDate(List<CustomValue> listCustomValue) {
		for (CustomValue customValue : listCustomValue) {
			if (projectId == customValue.getCustomizedId()) {
				if (customValue.getCustomFieldId() == CustomValueUtil.END_DATE_ID) {
					this.endDate = customValue.getValue();
				}
			}
		}
	}

	/**
	 * @return the endDate
	 */
	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the csss
	 */
	public List<CssTimeDto> getCss() {
		return css;
	}

	/**
	 * @param csss
	 *            the csss to set
	 */
	public void setCss(List<CssTimeDto> css) {
		this.css = css;
	}

	/**
	 * @param css
	 * @param maxCssValue
	 *            void
	 */
	public void setCss(List<CssTimeDto> css, int maxCssValue) {
		if (!css.isEmpty()) {
			CssTimeDto avgTotal = null;
			int count = 0;
			float total = 0;
			for (CssTimeDto cssTimeDto : css) {
				total += Float.valueOf(cssTimeDto.getValues());
				count++;
			}

			if (count == 0) {
				avgTotal = new CssTimeDto(-1, 0);
			} else {
				avgTotal = new CssTimeDto(-1, MethodUtil.formatFloatNumberType(total / count));
			}

			while ((css.size() < maxCssValue)) {
				css.add(new CssTimeDto((css.size() + 1), CustomValueUtil.CSS_VALUE_UNKNOWN));
			}

			css.add(avgTotal);
		}
		this.css = css;
	}

	/**
	 * Constructure
	 */
	public ProjectCssDetailDTO(int projectId, String projectName, Set<String> projectManager, String startDate,
			String endDate, List<CssTimeDto> css, String message) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectManager = projectManager;
		this.startDate = startDate;
		this.endDate = endDate;
		this.css = css;
	}

	/**
	 * Constructure
	 */
	public ProjectCssDetailDTO() {
		super();
	}

}
