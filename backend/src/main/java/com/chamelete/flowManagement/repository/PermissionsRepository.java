package com.chamelete.flowManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chamelete.flowManagement.model.Permissions;

public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
    
    Permissions findByType(String type);

    Permissions findById(long id);
}
