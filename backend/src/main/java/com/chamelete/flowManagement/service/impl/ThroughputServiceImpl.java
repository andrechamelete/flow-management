package com.chamelete.flowManagement.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamelete.flowManagement.model.Cards;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.service.ThroughputService;

@Service
public class ThroughputServiceImpl implements ThroughputService {

    @Autowired
    private final LeadTimeServiceImpl leadTimeService;
    
    @Autowired
    public ThroughputServiceImpl(LeadTimeServiceImpl leadTimeService) {
        this.leadTimeService = leadTimeService;
    }

    @Override
    public double getThroughput(Flows flow, LocalDateTime startDate, LocalDateTime endDate) {
        List<Cards> cards = leadTimeService.getCardsByDoneInRange(flow, startDate, endDate);
        if (cards.isEmpty()) {
            return 0.0;
        }

        double cycle = Duration.between(startDate, endDate).toDays();

        double throughput = cards.size() / cycle;
        return throughput;
    }
}
