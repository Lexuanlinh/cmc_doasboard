/**
 * 
 */
package com.cmc.dashboard.controller.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.CssChartDTO;
import com.cmc.dashboard.dto.EfficiencyDTO;
import com.cmc.dashboard.dto.EffortEfficiencyDetails;
import com.cmc.dashboard.dto.ProjectCssDetailDTO;
import com.cmc.dashboard.dto.ProjectDTO;
import com.cmc.dashboard.repository.ProjectBillableRepository;
import com.cmc.dashboard.service.ProjectService;
import com.cmc.dashboard.service.ReportService;
import com.cmc.dashboard.util.MethodUtil;

/**
 * @author nahung
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class ReportController {

	@Autowired
	ReportService reportService;

	@Autowired
	ProjectService projectService;

	@Autowired
	ProjectBillableRepository projectBillableRepository;

	/**
	 * Get DU_name and CSS_ score by month and year
	 * 
	 * @param year
	 * @param month
	 * @return ResponseEntity<List<CssChartDTO>>
	 * @author: DVNgoc
	 */
	@RequestMapping(value = "/css", method = RequestMethod.GET)
	public ResponseEntity<?> getProjectCSS(@RequestParam("year") int year, @RequestParam("month") int month,
			@RequestParam("userId") int userId) {
		List<CssChartDTO> listCssChart = new ArrayList<CssChartDTO>();
		if (MethodUtil.isValidDate(month, year)) {
			try {
				listCssChart = reportService.getProjectCSSService(month, year, userId);
			} catch (SQLException e) {
				e.printStackTrace();
				return new ResponseEntity<List<CssChartDTO>>(listCssChart, HttpStatus.OK);
			}
		}
		return new ResponseEntity<List<CssChartDTO>>(listCssChart, HttpStatus.OK);
	}

	/**
	 * Call API get list of project details (css).
	 * 
	 * @param deliveryUnit
	 * @param year
	 * @param month
	 * @return ResponseEntity<List<ProjectCssDetailDto>>
	 * @author: DVNgoc
	 */
	@RequestMapping(value = "/css/detail", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectCssDetailDTO>> getCssDetails(@RequestParam("duName") String duName,
			@RequestParam("year") int year, @RequestParam("month") int month) {

		List<ProjectCssDetailDTO> listCssDetail = new ArrayList<ProjectCssDetailDTO>();
		if (MethodUtil.isValidDate(month, year)) {
			listCssDetail = reportService.getCssDetails(duName, month, year);
		}

		return new ResponseEntity<List<ProjectCssDetailDTO>>(listCssDetail, HttpStatus.OK);
	}

	/**
	 * Get total project by delivery unit, project type
	 * 
	 * @param month
	 * @param year
	 * @return ResponseEntity<List<Map<String,String>>>
	 * @author: nvkhoa
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/projects/type", method = RequestMethod.GET)
	public ResponseEntity<List<Map<String, String>>> getProjectByType(@RequestParam("month") int month,
			@RequestParam("year") int year, @RequestParam("userId") int userId) {
		List<Map<String, String>> map = new ArrayList<Map<String, String>>();

		map = reportService.getProjectByType(month, year, userId);
		return new ResponseEntity<List<Map<String, String>>>(map, HttpStatus.OK);

	}

	/**
	 * Get list project by type, duName, month, year
	 * 
	 * @param duName
	 * @param projectType
	 * @param month
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "/projects/type/list", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectDTO>> getListProjectByType(@RequestParam("duName") String duName,
			@RequestParam("projectType") List<String> projectType, @RequestParam("month") int month,
			@RequestParam("year") int year) {
		List<ProjectDTO> lstProjectDto = new ArrayList<ProjectDTO>();
		try {
			lstProjectDto = reportService.getProjectListByType(duName, projectType, month, year);
			return new ResponseEntity<List<ProjectDTO>>(lstProjectDto, HttpStatus.OK);
		} catch (SQLException e) {
			return new ResponseEntity<List<ProjectDTO>>(lstProjectDto, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Get effort efficiency for each delivery unit
	 * 
	 * @param month
	 * @param year
	 * @param userId
	 * @return ResponseEntity<List<EfficiencyDTO>>
	 * @author: NVKhoa
	 */
	@RequestMapping(value = "/effort/efficiency", method = RequestMethod.GET)
	public ResponseEntity<List<EfficiencyDTO>> getEffortEfficiency(@RequestParam("month") int month,
			@RequestParam("year") int year, @RequestParam("userId") int userId) {
		List<EfficiencyDTO> lstEfficiency = new ArrayList<EfficiencyDTO>();
		try {
			lstEfficiency = reportService.getEffortEfficiency(month, year, userId);
			return new ResponseEntity<List<EfficiencyDTO>>(lstEfficiency, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<List<EfficiencyDTO>>(lstEfficiency, HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(value = "/date", method = RequestMethod.GET)
	public ResponseEntity<String> getDateTime() {
		return new ResponseEntity<String>(MethodUtil.getCurrentDate(), HttpStatus.OK);
	}

	/**
	 * method return all project billable revert from database.
	 * 
	 * @param duName
	 * @return ResponseEntity<ProjectBillableDTO>
	 * @author: Hoai-Nam
	 */
	@RequestMapping(value = "/billable/detail", method = RequestMethod.GET)
	public ResponseEntity<List<EffortEfficiencyDetails>> getProjectBillableDetail(@RequestParam("duName") String duName,
			@RequestParam("month") String month, @RequestParam("year") String year) {
		if (month.length() == 1) {
			month = "0".concat(month);
		}
		String period = month.concat("-").concat(year);
		List<EffortEfficiencyDetails> billableEffortDTOs = new ArrayList<EffortEfficiencyDetails>();
		if (MethodUtil.isNull(duName)) {
			return new ResponseEntity<List<EffortEfficiencyDetails>>(billableEffortDTOs, HttpStatus.BAD_REQUEST);
		} else {
			billableEffortDTOs = reportService.getListProjectBillabeByDU(duName, period);
			return new ResponseEntity<List<EffortEfficiencyDetails>>(billableEffortDTOs, HttpStatus.OK);
		}
	}
}
