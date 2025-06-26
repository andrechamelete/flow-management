package com.chamelete.flowManagement.security.dto.metrics;

public class QueueResponse {

    private int wip;
    private int queue;
    
    public QueueResponse() {
    }

    public QueueResponse(int wip, int queue) {
        this.wip = wip;
        this.queue = queue;
    }

    public int getWip() {
        return wip;
    }

    public void setWip(int wip) {
        this.wip = wip;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    
    
}
