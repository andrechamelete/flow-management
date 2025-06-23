package com.chamelete.flowManagement.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chamelete.flowManagement.security.dto.metrics.LeadTimeResponse;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/metrics")
public class MetricsController {

    private LeadTimeResponse leadTimeResponse;
    
}
