package com.chamelete.flowManagement.service;

import java.time.LocalDateTime;

import com.chamelete.flowManagement.model.Flows;

public interface ThroughputService {
    
    double getThroughput(Flows flow, LocalDateTime startDate, LocalDateTime endDate);
}
