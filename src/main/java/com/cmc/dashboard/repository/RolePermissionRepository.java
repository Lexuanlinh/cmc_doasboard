package com.cmc.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmc.dashboard.model.RolePermission;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {

}
