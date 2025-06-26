package com.chamelete.flowManagement.security.dto.metrics;

public class LeadTimeResponse {

    private double leadTime;

    public LeadTimeResponse(double leadTime) {
        this.leadTime = leadTime;
    }

    public double getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(double leadTime) {
        this.leadTime = leadTime;
    }    
}
