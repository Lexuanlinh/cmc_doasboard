/**
 * DashboardSystem - com.cmc.dashboard.controller.rest
 */
package com.cmc.dashboard.controller.rest;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.RestRespondResourceDTO;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.service.ResourceService;

/**
 * @author: DVNgoc
 * @Date: Dec 27, 2017
 */
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class ResourceController {

	@Autowired
	ResourceService resourceService;

	@Autowired
	UserRepository userRepository;

	/**
	 * Get Resource Info for Resource Monitor Feuture
	 * 
	 * @param name
	 * @param fromDate
	 * @param toDate
	 * @param duName
	 * @param pageNumber
	 * @param pageSize
	 * @param userId
	 * @return ResponseEntity<List<ResourceUtilizationDTO>>
	 * @author: DVNgoc
	 */
	@RequestMapping(value = "/resource", method = RequestMethod.GET)
	public ResponseEntity<?> getProjectCssDetail(@RequestParam("name") String name,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("duName") String duName, @RequestParam("pageNumber") int pageNumber,
			@RequestParam("pageSize") int pageSize, @RequestParam("userId") int userId) {

		pageNumber = (pageNumber < 1) ? 0 : pageNumber - 1;
		pageSize = (pageSize < 1) ? 5 : pageSize;

		List<String> listDeliveryUnit = new ArrayList<String>();
		RestRespondResourceDTO result = RestRespondResourceDTO.success(new ArrayList<>(), null);
		try {
			result = resourceService.getResourcePlan(name.trim(), duName.trim(), fromDate.trim(), toDate.trim(), userId,
					pageNumber, pageSize);
			listDeliveryUnit = resourceService.getAllUserDeliveryUnit();
		} catch (ParseException e) {
			e.printStackTrace();
			return new ResponseEntity<RestRespondResourceDTO>(result, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<RestRespondResourceDTO>(RestRespondResourceDTO.errorSQL(), HttpStatus.OK);
		}

		return new ResponseEntity<RestRespondResourceDTO>(RestRespondResourceDTO.success(result, listDeliveryUnit),
				HttpStatus.OK);
	}

}
