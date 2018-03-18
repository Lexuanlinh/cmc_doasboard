package com.cmc.dashboard.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cmc.dashboard.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.AuthorizationUtilzationDTO;
import com.cmc.dashboard.model.Role;
import com.cmc.dashboard.model.RolePermission;
import com.cmc.dashboard.repository.RoleRepository;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    /*
     * (non-Javadoc)
     *
     * @see com.cmc.dashboard.service.PermissionService#getAllRole()
     */
    @Override
    public List<Role> getAllRole() throws SQLException {
        List<Role> roles = roleRepository.findAll();
        for (Iterator<Role> iterator = roles.iterator(); iterator.hasNext(); ) {
            Role role = iterator.next();
            if (role.getRoleName().trim().equals("ADMIN")) {
                iterator.remove();
            }
        }
        return roles;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cmc.dashboard.service.PermissionService#getRolePermission()
     */
    @Override
    public List<AuthorizationUtilzationDTO> getRolePermission() throws SQLException {
        List<AuthorizationUtilzationDTO> result = new ArrayList<AuthorizationUtilzationDTO>();
        AuthorizationUtilzationDTO authorizationUtilzationDTO = null;
        List<String> listResourceName = new ArrayList<String>();
        listResourceName.add("Resource Allocate");
        listResourceName.add("Billable");
        listResourceName.add("CSS");
        for (int i = 0; i < 3; i++) {
            authorizationUtilzationDTO = new AuthorizationUtilzationDTO();
            authorizationUtilzationDTO.setResourceName(listResourceName.get(i));
            authorizationUtilzationDTO.setRolePermission_DUL(getListRolePermission(1).get(i));
            authorizationUtilzationDTO.setRolePermission_PM(getListRolePermission(2).get(i));
            authorizationUtilzationDTO.setRolePermission_QA(getListRolePermission(3).get(i));
            authorizationUtilzationDTO.setRolePermission_BOD(getListRolePermission(4).get(i));
            result.add(authorizationUtilzationDTO);
        }
        return result;
    }

    /**
     * TODO description
     *
     * @param i
     * @return
     * @throws SQLException List<List<RolePermission>>
     * @author: ngocd
     */
    public List<List<RolePermission>> getListRolePermission(int i) throws SQLException {
        List<List<RolePermission>> listRolePermissions = new ArrayList<>();
        List<RolePermission> rolePermissions = new ArrayList<>();
        List<RolePermission> rolePermissions_Role = new ArrayList<>();
        rolePermissions = roleRepository.getOne(i).getRolePermissions();
        for (int j = 0; j < 4; j++) {
            rolePermissions_Role.add(rolePermissions.get(j));
        }
        listRolePermissions.add(rolePermissions_Role);
        rolePermissions_Role = new ArrayList<>();
        for (int j = 4; j < 8; j++) {
            rolePermissions_Role.add(rolePermissions.get(j));
        }
        listRolePermissions.add(rolePermissions_Role);
        rolePermissions_Role = new ArrayList<>();
        for (int j = 8; j < 12; j++) {
            rolePermissions_Role.add(rolePermissions.get(j));
        }
        listRolePermissions.add(rolePermissions_Role);
        return listRolePermissions;
    }

    @Override
    public List<String> getAllPermission() {
        return permissionRepository.getAllPermission();
    }
}
