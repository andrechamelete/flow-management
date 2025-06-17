package com.chamelete.flowManagement.security.dto;

public class UpdateStageRequest {

    private String name;
    private Integer wipLimit;
    private Integer position;

    public UpdateStageRequest(String name, Integer wipLimit, Integer position) {
        this.name = name;
        this.wipLimit = wipLimit;
        this.position = position;
    }
    public UpdateStageRequest() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getWipLimit() {
        return wipLimit;
    }
    public void setWipLimit(Integer wipLimit) {
        this.wipLimit = wipLimit;
    }
    public Integer getPosition() {
        return position;
    }
    public void setPosition(Integer position) {
        this.position = position;
    }
    
}
