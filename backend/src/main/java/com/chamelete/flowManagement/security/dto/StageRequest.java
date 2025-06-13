package com.chamelete.flowManagement.security.dto;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.Flows;

public class StageRequest {
    private Companies company;
    private Flows flow;
    private String name;
    private int position;
    private String wipLimit;
    
    StageRequest() {}

    StageRequest(String name, int position) {
        this.name = name;
        this.position = position;
    }

    StageRequest(String name, int position, Companies company, Flows flow) {
        this.name = name;
        this.position = position;
        this.company = company;
        this.flow = flow;
    }

    public Companies getCompany() {
        return company;
    }

    public void setCompany(Companies company) {
        this.company = company;
    }

    public Flows getFlow() {
        return flow;
    }

    public void setFlow(Flows flow) {
        this.flow = flow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getWipLimit() {
        return wipLimit;
    }

    public void setWipLimit(String wipLimit) {
        this.wipLimit = wipLimit;
    }

    
    
}
