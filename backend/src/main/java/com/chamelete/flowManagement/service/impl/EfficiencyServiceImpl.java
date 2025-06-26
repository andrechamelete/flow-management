package com.chamelete.flowManagement.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamelete.flowManagement.model.CycleTime;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.repository.CycleTimeRepository;
import com.chamelete.flowManagement.security.dto.metrics.EfficiencyResponse;
import com.chamelete.flowManagement.service.EfficiencyService;


@Service
public class EfficiencyServiceImpl implements EfficiencyService {

    @Autowired
    private final CycleTimeRepository cycleTimeRepository;
    
    @Autowired
    public EfficiencyServiceImpl(CycleTimeRepository cycleTimeRepository) {
        this.cycleTimeRepository = cycleTimeRepository;
        
    }

    @Override
    public List<CycleTime> getCycleTimes(Flows flow, LocalDateTime startDate, LocalDateTime endDate) {
        List<CycleTime> cyclesTimes = cycleTimeRepository.findByFlow(flow);
        cyclesTimes.removeIf(ct -> ct.getCreatedAt().isAfter(endDate));
        cyclesTimes.removeIf(ct -> ct.getFinishedAt() != null && ct.getFinishedAt().isBefore(startDate));
        return cyclesTimes;
    }   

    @Override
    public EfficiencyResponse getEfficiency(Flows flow, LocalDateTime startDate, LocalDateTime endDate) {
        List<CycleTime> cycleTimes = getCycleTimes(flow, startDate, endDate);
        double inProcess = 0;
        double inQueue = 0;
        for (CycleTime ct: cycleTimes) {
            if(ct.getStage().getType().equalsIgnoreCase("process")) {
                if (ct.getFinishedAt() == null) {
                    double time = Duration.between(ct.getCreatedAt(), endDate).toMinutes();
                    inProcess += time;
                } else {
                    double time = Duration.between(ct.getCreatedAt(), ct.getFinishedAt()).toMinutes();
                    inProcess += time;
                }
            }

            if (ct.getStage().getType().equalsIgnoreCase("queue")) {
                if (ct.getFinishedAt() == null) {
                    double time = Duration.between(ct.getCreatedAt(), endDate).toMinutes();
                    inQueue += time;
                } else {
                    double time = Duration.between(ct.getCreatedAt(), ct.getFinishedAt()).toMinutes();
                    inQueue += time;
                }
            }
        }

        double total = inProcess + inQueue;

        double inProcess$ = inProcess/ total;
        double inQueue$ = inQueue/ total;

        EfficiencyResponse er = new EfficiencyResponse(inProcess$, inQueue$);

        return er;

    }
}
