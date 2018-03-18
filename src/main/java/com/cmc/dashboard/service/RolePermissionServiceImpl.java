package com.cmc.dashboard.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.model.RolePermission;
import com.cmc.dashboard.repository.RolePermissionRepository;
import com.cmc.dashboard.util.MessageUtil;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

	@Autowired
	RolePermissionRepository rolePermissionRepository;

	@Override
	public List<RolePermission> getAllRolePermission() throws SQLException {
		return rolePermissionRepository.findAll();
	}

	@Override
	public String saveAllRolePermission(List<RolePermission> listRolePermission) throws SQLException {
		List<RolePermission> result = rolePermissionRepository.save(listRolePermission);
		return result != null ? MessageUtil.SAVE_SUCCESS : MessageUtil.SAVE_ERROR;
	}

}
