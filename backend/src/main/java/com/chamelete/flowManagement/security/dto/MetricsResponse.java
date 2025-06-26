package com.chamelete.flowManagement.security.dto;

import com.chamelete.flowManagement.security.dto.metrics.EfficiencyResponse;
import com.chamelete.flowManagement.security.dto.metrics.LeadTimeResponse;
import com.chamelete.flowManagement.security.dto.metrics.QueueResponse;
import com.chamelete.flowManagement.security.dto.metrics.ThroughputResponse;

public class MetricsResponse {

    private LeadTimeResponse leadTime;

    private ThroughputResponse throughput;

    private EfficiencyResponse efficiency;

    private QueueResponse queue;

    public MetricsResponse(
            LeadTimeResponse leadTime, 
            ThroughputResponse throughput, 
            EfficiencyResponse efficiency,
            QueueResponse queue) {
        this.leadTime = leadTime;
        this.throughput = throughput;
        this.efficiency = efficiency;
        this.queue = queue;
    }

    public LeadTimeResponse getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(LeadTimeResponse leadTime) {
        this.leadTime = leadTime;
    }

    public ThroughputResponse getThroughput() {
        return throughput;
    }

    public void setThroughput(ThroughputResponse throughput) {
        this.throughput = throughput;
    }

    public EfficiencyResponse getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(EfficiencyResponse efficiency) {
        this.efficiency = efficiency;
    }

    public QueueResponse getQueue() {
        return queue;
    }

    public void setQueue(QueueResponse queue) {
        this.queue = queue;
    }
    
}
