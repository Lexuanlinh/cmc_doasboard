package com.cmc.dashboard.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;

import com.cmc.dashboard.dto.BillableDTO;
import com.cmc.dashboard.dto.ListBillableDTO;
import com.cmc.dashboard.dto.ProjectCssUtilizationDto;
import com.cmc.dashboard.dto.ProjectDTO;
import com.cmc.dashboard.dto.RestResponse;
import com.cmc.dashboard.model.ProjectCss;

public interface ProjectService {

	/**
	 * Call Repository: get list css of project
	 * 
	 * @param projectId:
	 *            the id's selected project
	 * @return List<ProjectCss>
	 * @author: DVNgoc
	 */
	public List<ProjectCssUtilizationDto> getListProjectCssByProjectId(int projectId) throws SQLException;

	/**
	 * Add project's css value
	 * 
	 * @param listProjectCss
	 * @return String
	 * @author: DVNgoc
	 * @throws ParseException
	 */
	public RestResponse createProjectCss(String json) throws SQLException;

	/**
	 * Update project's css value
	 * 
	 * @param listProjectCss
	 * @return String
	 * @author: DVNgoc
	 * @throws ParseException
	 */
	public RestResponse updateProjectCss(List<ProjectCss> listProjectCss) throws SQLException;

	/**
	 * Delete css value by project_css_id
	 * 
	 * @param projectCssId
	 * @return String
	 * @author: DVNgoc
	 */
	public String deleteProjectCss(int projectCssId) throws SQLException;

	/**
	 * Get list of project by user
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 *             List<ProjectDTO>
	 * @author: NVKhoa
	 */
	public Page<ProjectDTO> getProjectListByUser(int userId, int page, int size) throws SQLException;

	/**
	 * Get list of project billable
	 * 
	 * @param projectId
	 * @return
	 * @throws SQLException
	 *             List<ProjectBillable>
	 * @author: NVKhoa
	 */
	public List<BillableDTO> getBillableByProject(int projectId) throws SQLException;

	/**
	 * Update project billable
	 * 
	 * @param projectBillable
	 * @return String
	 * @author: NVKhoa
	 * @throws SQLException 
	 */
	public ListBillableDTO updateProjectBillable(String projectBillable);

	/**
	 * Export project billable data into excel file
	 * 
	 * @param projectId
	 * @return String
	 * @author: NVKhoa
	 * @throws SQLException
	 */
	public String exportProjectBillable(int projectId) throws SQLException;

	/**
	 * Export project css data into excel file
	 * 
	 * @param projectId
	 * @return String
	 * @author: NVKhoa
	 */
	public String exportProjectCss(int projectId) throws SQLException;

}
