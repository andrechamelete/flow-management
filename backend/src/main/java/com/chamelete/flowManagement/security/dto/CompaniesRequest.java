package com.chamelete.flowManagement.security.dto;


public class CompaniesRequest {

    private String name;

    public CompaniesRequest() {}

    public CompaniesRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
