package com.cmc.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmc.dashboard.model.Permission;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer>{

    @Query(value = "select permissions.permission_name from permissions", nativeQuery = true)
    List<String> getAllPermission();
}
