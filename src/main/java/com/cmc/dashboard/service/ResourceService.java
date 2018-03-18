/**
 * DashboardSystem - com.cmc.dashboard.service
 */
package com.cmc.dashboard.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.cmc.dashboard.dto.RestRespondResourceDTO;

/**
 * @author: DVNgoc
 * @Date: Dec 27, 2017
 */
public interface ResourceService {

	/**
	 * Get Resource Plan for Resource Monitor Feature
	 * 
	 * @param name
	 * @param duName
	 * @param fromDate
	 * @param toDate
	 * @param userId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws ParseException
	 *             List<ResourceUtilizationDTO>
	 * @author: DVNgoc
	 */
	RestRespondResourceDTO getResourcePlan(String name, String duName, String fromDate, String toDate, int userId,
			int pageNumber, int pageSize) throws ParseException, SQLException;

	/**
	 * Get all Delivery Unit in Office
	 * 
	 * @return
	 * @throws SQLException
	 *             List<String>
	 * @author: ngocdv
	 */
	List<String> getAllUserDeliveryUnit() throws SQLException;
}
