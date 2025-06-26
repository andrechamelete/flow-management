package com.chamelete.flowManagement.security.dto.metrics;

public class EfficiencyResponse {

    private double inProcess;

    private double inQueue;

    public EfficiencyResponse() {
    }

    public EfficiencyResponse(double inProcess, double inQueue) {
        this.inProcess = inProcess;
        this.inQueue = inQueue;
    }

    public double getInProcess() {
        return inProcess;
    }

    public void setInProcess(double inProcess) {
        this.inProcess = inProcess;
    }

    public double getInQueue() {
        return inQueue;
    }

    public void setInQueue(double inQueue) {
        this.inQueue = inQueue;
    }

    
}