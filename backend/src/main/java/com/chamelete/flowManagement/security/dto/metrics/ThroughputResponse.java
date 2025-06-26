package com.chamelete.flowManagement.security.dto.metrics;

public class ThroughputResponse {
    
    private double throughput;

    public ThroughputResponse(double throughput) {
        this.throughput = throughput;
    }

    public double getThroughput() {
        return throughput;
    }

    public void setThroughput(double throughput) {
        this.throughput = throughput;
    }
}
