package com.chamelete.flowManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.security.dto.CompaniesRequest;
import com.chamelete.flowManagement.service.CompaniesService;
import com.chamelete.flowManagement.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/companies")
public class CompaniesController {
    
    @Autowired
    private final CompaniesService companiesService;

    @Autowired
    private final UserService userService;

    public CompaniesController(CompaniesService companiesService, UserService userService) {
        this.companiesService = companiesService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Companies> create(@RequestBody CompaniesRequest dot) {
        Companies company = companiesService.create(dot);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }

    @GetMapping("/my-companies")
    public ResponseEntity<List<Companies>> getMyCompanies() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.findByEmail(email);

        List<Companies> companies = companiesService.getCompaniesByUser(user);
        return ResponseEntity.ok(companies);
    }
}
