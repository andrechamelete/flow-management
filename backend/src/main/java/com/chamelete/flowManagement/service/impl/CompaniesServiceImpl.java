package com.chamelete.flowManagement.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.Permissions;
import com.chamelete.flowManagement.repository.CompaniesRepository;
import com.chamelete.flowManagement.repository.PermissionsRepository;
import com.chamelete.flowManagement.repository.UserPermissionRepository;
import com.chamelete.flowManagement.repository.UserRepository;
import com.chamelete.flowManagement.security.dto.CompaniesRequest;
import com.chamelete.flowManagement.service.CompaniesService;

import jakarta.persistence.EntityNotFoundException;

import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.model.UserPermission;

@Service
public class CompaniesServiceImpl implements CompaniesService{

    private final CompaniesRepository companiesRepository;
    private final UserRepository userRepository;
    private final PermissionsRepository permissionsRepository;
    private final UserPermissionRepository userPermissionRepository;

    @Autowired
    public CompaniesServiceImpl(
            CompaniesRepository companiesRepository, 
            UserRepository userRepository, 
            PermissionsRepository permissionsRepository,
            UserPermissionRepository userPermissionRepository) {
        this.companiesRepository = companiesRepository;
        this.userRepository = userRepository;
        this.permissionsRepository = permissionsRepository;
        this.userPermissionRepository = userPermissionRepository;
    }

    @Override
    public Companies findById(long id) {
        return companiesRepository.findById(id);
    }

    @Override
    public Companies findByName(String name) {
        return companiesRepository.findByName(name)
            .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada: " + name));    
    }

    @Override
    public List<Companies> getCompaniesByUser(User user) {
        List<UserPermission> permissions = userPermissionRepository.findByUser(user);
        return permissions.stream()
                      .map(UserPermission::getCompany)
                      .distinct()
                      .collect(Collectors.toList());
        
    //return companiesRepository.findAllByCreatedBy(user);

    }

    @Override
    public Companies create(CompaniesRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NoSuchElementException("User not found"));

        if(companiesRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("You already have a company with this name");
        }       
        
        Companies company = new Companies();
        company.setName(request.getName());
        company.setCreatedAt(LocalDateTime.now());
        company.setCreatedBy(user);
        Companies savedCompany = companiesRepository.save(company);

        UserPermission userPermission = new UserPermission();
        userPermission.setUser(user);
        userPermission.setCompany(company);
        userPermission.setAssignedAt(LocalDateTime.now());
        userPermission.setAssignedBy(user);
        userPermission.setPermission(permissionsRepository.findById(1));
        userPermissionRepository.save(userPermission);

        return savedCompany;

    }

    public UserPermission givePermission(String email, Long companyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User userAdmin = userRepository.findByEmail(userEmail).orElseThrow(() -> new NoSuchElementException("User not found"));
        Companies company = companiesRepository.findById(companyId)
            .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada: " + companyId));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + email));

        if(!userPermissionRepository.existsByUserAndCompany(userAdmin, company)) {
            throw new IllegalArgumentException("Usuário não tem permissão de conceder acesso nesta empresa");
        }

        if(userPermissionRepository.existsByUserAndCompany(user, company)) {
            throw new IllegalArgumentException("Usuário já tem permissão para acessar esta empresa");
        }

        UserPermission userPermission = new UserPermission();
        Permissions permission = permissionsRepository.findByType("COLAB");
        userPermission.setCompany(company);
        userPermission.setUser(user);
        userPermission.setAssignedAt(LocalDateTime.now());
        userPermission.setAssignedBy(userAdmin);
        userPermission.setPermission(permission);

        return userPermissionRepository.save(userPermission);

    }
    
}
