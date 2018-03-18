package com.cmc.dashboard.service;

import java.sql.SQLException;
import java.util.List;

import com.cmc.dashboard.dto.AuthorizationUtilzationDTO;
import com.cmc.dashboard.model.Role;

public interface PermissionService {

	/**
	 * TODO description
	 * 
	 * @return
	 * @throws SQLException
	 *             List<Role>
	 * @author: ngocd
	 */
	public List<Role> getAllRole() throws SQLException;

	/**
	 * TODO description
	 * 
	 * @return
	 * @throws SQLException
	 *             List<AuthorizationUtilzationDTO>
	 * @author: ngocd
	 */
	public List<AuthorizationUtilzationDTO> getRolePermission() throws SQLException;

	List<String> getAllPermission();
}
