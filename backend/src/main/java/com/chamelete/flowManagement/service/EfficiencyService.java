package com.chamelete.flowManagement.service;

import java.time.LocalDateTime;
import java.util.List;

import com.chamelete.flowManagement.model.CycleTime;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.security.dto.metrics.EfficiencyResponse;

public interface EfficiencyService {

    List<CycleTime> getCycleTimes(Flows flow, LocalDateTime startDate, LocalDateTime endDate);

    EfficiencyResponse getEfficiency(Flows flow, LocalDateTime startDate, LocalDateTime endDate);
    
}
