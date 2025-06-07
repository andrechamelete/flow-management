package com.chamelete.flowManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.model.UserPermission;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {

    boolean existsByUserAndCompany(User user, Companies company);

    List<UserPermission> findByUser(User user);
    
}
