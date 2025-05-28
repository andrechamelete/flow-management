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
    public Companies findByName(String name) {
        return companiesRepository.findByName(name)
            .orElseThrow(NoSuchElementException::new);    
    }

    @Override
    public List<Companies> getCompaniesByUser(User user) {
        return companiesRepository.findAllByCreatedBy(user);
    }

    @Override
    public Companies create(CompaniesRequest companiesDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NoSuchElementException("User not found"));

        if(companiesRepository.existsByName(companiesDto.getName())) {
            throw new IllegalArgumentException("You already have a company with this name");
        }
        
        Companies company = new Companies();
        company.setName(companiesDto.getName());
        company.setCreatedAt(LocalDateTime.now());
        company.setCreatedBy(user);

        return companiesRepository.save(company);

    }
    
}
