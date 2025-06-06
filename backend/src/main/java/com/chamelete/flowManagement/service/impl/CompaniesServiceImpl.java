package com.chamelete.flowManagement.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.repository.CompaniesRepository;
import com.chamelete.flowManagement.repository.UserRepository;
import com.chamelete.flowManagement.security.dto.CompaniesRequest;
import com.chamelete.flowManagement.service.CompaniesService;

import jakarta.persistence.EntityNotFoundException;

import com.chamelete.flowManagement.model.User;

@Service
public class CompaniesServiceImpl implements CompaniesService{

    private final CompaniesRepository companiesRepository;
    private final UserRepository userRepository;

    @Autowired
    public CompaniesServiceImpl(CompaniesRepository companiesRepository, UserRepository userRepository) {
        this.companiesRepository = companiesRepository;
        this.userRepository = userRepository;
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
        return companiesRepository.findAllByCreatedBy(user); // vai precisar mudar pra trazer nao por quem criou, mas por quem tem permissao
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

        return companiesRepository.save(company);

    }
    
}
