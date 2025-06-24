package com.chamelete.flowManagement.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.repository.UserPermissionRepository;
import com.chamelete.flowManagement.service.MetricsService;
import com.chamelete.flowManagement.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @Autowired
    private final MetricsService metricsService;

    @Autowired
    private final UserPermissionRepository userPermissionRepository;

    private final UserService userService;

    private MetricsController(
            MetricsService metricsService,
            UserPermissionRepository userPermissionRepository,
            UserService userService) {
        this.metricsService = metricsService;
        this.userPermissionRepository = userPermissionRepository;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getMetrics(@RequestParam Companies company, 
                                        @RequestParam Flows flow, 
                                        @RequestParam LocalDateTime startDate, 
                                        @RequestParam LocalDateTime endDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        if (!userPermissionRepository.existsByUserAndCompany(user, company)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not have permission to access this company.");
        }        

        int leadTime = metricsService.getLeadTime(flow, startDate, endDate);


        return ResponseEntity.status(HttpStatus.ACCEPTED).body(leadTime);
    }
    
}
