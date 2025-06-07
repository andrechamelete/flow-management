package com.chamelete.flowManagement.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.repository.FlowsRepository;
import com.chamelete.flowManagement.repository.UserRepository;
import com.chamelete.flowManagement.security.dto.FlowsRequest;
import com.chamelete.flowManagement.service.FlowsService;
import com.chamelete.flowManagement.service.CompaniesService;

@Service
public class FlowsServiceImpl implements FlowsService {

    private final FlowsRepository flowsRepository;
    private final CompaniesService companiesService;
    private final UserRepository userRepository;


    @Autowired
    public FlowsServiceImpl(
            FlowsRepository flowsRepository, 
            CompaniesService companiesService, 
            UserRepository userRepository) {
        this.flowsRepository = flowsRepository;
        this.companiesService = companiesService;
        this.userRepository = userRepository;
      
    }

    @Override
    public Flows createFlow(FlowsRequest request) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Long companyId = request.getCompanyId();
        Companies company = companiesService.findById(companyId);  
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NoSuchElementException("User not found"));
        if(!companiesService.getCompaniesByUser(user).contains(company)){
            throw new NoSuchElementException("User does not have access to this company");
        }
      

        Flows flow = new Flows();
        flow.setName(request.getName());
        flow.setDescription(request.getDescription());
        flow.setCreatedBy(user);
        flow.setCompany(company);
        flow.setCreatedAt(LocalDateTime.now());
        flow.setDepartment(request.getDepartment());

        try {
            
            return flowsRepository.save(flow);
        } catch (Exception e) {
            throw new RuntimeException("Error creating flow", e);
        }

    }

    @Override
    public List<Flows> getFlowsByCompany(Companies company) {
        
        try {
            return flowsRepository.getFlowByCompany(company);
        } catch (Exception e) {
            throw new RuntimeException("Error getting flows by company", e);
        }
    }
    
}
