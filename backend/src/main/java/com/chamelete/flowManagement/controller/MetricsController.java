package com.chamelete.flowManagement.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.chamelete.flowManagement.repository.CompaniesRepository;
import com.chamelete.flowManagement.repository.FlowsRepository;
import com.chamelete.flowManagement.repository.UserPermissionRepository;
import com.chamelete.flowManagement.service.EfficiencyService;
import com.chamelete.flowManagement.service.LeadTimeService;
import com.chamelete.flowManagement.service.QueueService;
import com.chamelete.flowManagement.service.ThroughputService;
import com.chamelete.flowManagement.service.UserService;
import com.chamelete.flowManagement.security.dto.MetricsResponse;
import com.chamelete.flowManagement.security.dto.metrics.EfficiencyResponse;
import com.chamelete.flowManagement.security.dto.metrics.LeadTimeResponse;
import com.chamelete.flowManagement.security.dto.metrics.QueueResponse;
import com.chamelete.flowManagement.security.dto.metrics.ThroughputResponse;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @Autowired
    private final LeadTimeService leadTimeService;

    @Autowired
    private final UserPermissionRepository userPermissionRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final CompaniesRepository companiesRepository;

    @Autowired
    private final FlowsRepository flowsRepository;

    @Autowired
    private final ThroughputService throughputService;

    @Autowired
    private final EfficiencyService efficiencyService;

    @Autowired
    private final QueueService queueService;

    private MetricsController(
            LeadTimeService leadTimeService,
            UserPermissionRepository userPermissionRepository,
            UserService userService,
            CompaniesRepository companiesRepository,
            FlowsRepository flowsRepository,
            ThroughputService throughputService,
            EfficiencyService efficiencyService,
            QueueService queueService) {
        this.leadTimeService = leadTimeService;
        this.userPermissionRepository = userPermissionRepository;
        this.userService = userService;
        this.companiesRepository = companiesRepository;
        this.flowsRepository = flowsRepository;
        this.throughputService = throughputService;
        this.efficiencyService = efficiencyService;
        this.queueService = queueService;
    }

    @GetMapping
    public ResponseEntity<?> getMetrics(@RequestParam Long companyId, 
                                        @RequestParam Long flowId, 
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, 
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        
        //conferindo permissão do usuário
        Companies company = companiesRepository.findById(companyId).orElse(null);
        Flows flow = flowsRepository.findById(flowId).orElse(null);

        if (!userPermissionRepository.existsByUserAndCompany(user, company)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not have permission to access this company.");
        }        

        double leadTime = leadTimeService.getLeadTime(flow, startDate, endDate);
        double throughput = throughputService.getThroughput(flow, startDate, endDate);
        EfficiencyResponse efficiencyResponse = efficiencyService.getEfficiency(flow, startDate, endDate);
        QueueResponse queueResponse = queueService.getQueue(flow);


        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
            new MetricsResponse(
                new LeadTimeResponse(leadTime), 
                new ThroughputResponse(throughput),
                efficiencyResponse,
                queueResponse));
    }
    
}
