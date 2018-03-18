package com.cmc.dashboard.controller.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.BillableDTO;
import com.cmc.dashboard.dto.ListBillableDTO;
import com.cmc.dashboard.dto.ProjectCssUtilizationDto;
import com.cmc.dashboard.dto.ProjectDTO;
import com.cmc.dashboard.dto.RestResponse;
import com.cmc.dashboard.model.ProjectCss;
import com.cmc.dashboard.service.ProjectService;
import com.cmc.dashboard.util.MessageUtil;
import com.cmc.dashboard.util.MethodUtil;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class ProjectController {

	@Autowired
	ProjectService projectService;

	/**
	 * call service get list css value of selected project0 call service get list
	 * css value of selected project
	 * 
	 * @param projectId
	 * @return ResponseEntity<List<ProjectCssUtilizationDto>>
	 * @author: DVNgoc
	 */
	@RequestMapping(value = "/project/auth/css", method = RequestMethod.GET)
	public ResponseEntity<?> getProjectCssDetail(@RequestParam("projectId") int projectId) {
		if (MethodUtil.hasRole(com.cmc.dashboard.util.Role.CSS_ACCESS)) {
			List<ProjectCssUtilizationDto> listProjectCss = new ArrayList<ProjectCssUtilizationDto>();
			try {
				listProjectCss = projectService.getListProjectCssByProjectId(projectId);
				return new ResponseEntity<List<ProjectCssUtilizationDto>>(listProjectCss, HttpStatus.OK);
			} catch (SQLException e) {
				e.printStackTrace();
				return new ResponseEntity<List<ProjectCssUtilizationDto>>(listProjectCss, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>("Access denied", HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Call service add project's css value
	 * 
	 * @param json
	 * @return ResponseEntity<String>
	 * @author: DVNgoc
	 */
	@RequestMapping(value = "/project/auth/css", method = RequestMethod.POST)
	public ResponseEntity<?> createProjectCss(@RequestBody String json) {
		if (MethodUtil.hasRole(com.cmc.dashboard.util.Role.CSS_CREATE)) {
			RestResponse result = RestResponse.error(HttpStatus.OK, MessageUtil.DATA_INVALID);
			try {
				result = projectService.createProjectCss(json);
				return new ResponseEntity<RestResponse>(result, HttpStatus.OK);
			} catch (SQLException e) {
				e.printStackTrace();
				return new ResponseEntity<RestResponse>(RestResponse.errorSQL(), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>("Access denied", HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Call service update project's css value
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/project/auth/css", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProjectCss(@RequestBody String json) {
		if (MethodUtil.hasRole(com.cmc.dashboard.util.Role.CSS_EDIT)) {
			Gson gson = new Gson();
			TypeToken<List<ProjectCss>> token = new TypeToken<List<ProjectCss>>() {
			};
			RestResponse result = RestResponse.error(HttpStatus.OK, MessageUtil.DATA_INVALID);
			List<ProjectCss> listCss = new ArrayList<>();

			try {
				listCss = gson.fromJson(json, token.getType());
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new ResponseEntity<RestResponse>(result, HttpStatus.OK);
			} catch (JsonParseException e) {
				e.printStackTrace();
				return new ResponseEntity<RestResponse>(result, HttpStatus.OK);
			}

			try {
				result = projectService.updateProjectCss(listCss);
				return new ResponseEntity<RestResponse>(result, HttpStatus.OK);
			} catch (SQLException e) {
				e.printStackTrace();
				return new ResponseEntity<RestResponse>(RestResponse.errorSQL(), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>("Access denied", HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Call service to delete css value of project
	 * 
	 * @param projectCssId
	 * @return ResponseEntity<String>
	 * @author: DVNgoc
	 */
	@RequestMapping(value = "/project/auth/css", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteProjectCss(@RequestParam("projectCssId") int projectCssId) {
		if (MethodUtil.hasRole(com.cmc.dashboard.util.Role.CSS_DELETE)) {
			String result = MessageUtil.ERROR;
			try {
				result = projectService.deleteProjectCss(projectCssId);
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} catch (SQLException e) {
				e.printStackTrace();
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>("Access denied", HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Return list project by user
	 * 
	 * @param userId
	 * @return ResponseEntity<ProjectListDTO>
	 * @author: nvkhoa
	 */
	@RequestMapping(value = "/user/projects", method = RequestMethod.GET)
	public ResponseEntity<?> getProjectListByUser(@RequestParam("userId") int userId, @RequestParam("page") int page,
			@RequestParam("size") int size) {
		Page<ProjectDTO> lstProjectDto = new PageImpl<>(new ArrayList<>(), null, 0);
		try {
			lstProjectDto = projectService.getProjectListByUser(userId, page, size);
			return new ResponseEntity<Page<ProjectDTO>>(lstProjectDto, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<Page<ProjectDTO>>(lstProjectDto, HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Get project's billable by project
	 * 
	 * @param projectId
	 * @return ResponseEntity<List<ProjectBillable>>
	 * @author: nvkhoa
	 */
	@RequestMapping(value = "/project/auth/billable", method = RequestMethod.GET)
	public ResponseEntity<?> getBillableByProject(@RequestParam("projectId") int projectId) {
		if (MethodUtil.hasRole(com.cmc.dashboard.util.Role.BILLABLE_ACCESS)) {
			List<BillableDTO> lstBillable = new ArrayList<>();
			try {
				lstBillable = projectService.getBillableByProject(projectId);
				return new ResponseEntity<List<BillableDTO>>(lstBillable, HttpStatus.OK);
			} catch (SQLException e) {
				e.printStackTrace();
				return new ResponseEntity<List<BillableDTO>>(lstBillable, HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("Access denied", HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Update list of project billable
	 * 
	 * @param listBillAble
	 * @return ResponseEntity<String>
	 * @author: nvkhoa
	 */
	@RequestMapping(value = "/project/auth/billable", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProjectBillable(@RequestBody String listBillAble) {
		if (MethodUtil.hasRole(com.cmc.dashboard.util.Role.BILLABLE_EDIT)) {
			ListBillableDTO listBillable = projectService.updateProjectBillable(listBillAble);
			return new ResponseEntity<ListBillableDTO>(listBillable, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Access denied", HttpStatus.FORBIDDEN);
		}
	}

}
