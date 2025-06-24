package com.chamelete.flowManagement.security.dto;

public class StageRequest {
    private Long companyId;
    private Long flowId;
    private String name;
    private Integer position;
    private Integer wipLimit;
    private String type;
    
    StageRequest() {}

    StageRequest(String name, int position) {
        this.name = name;
        this.position = position;
    }

    StageRequest(String name, Integer position, Long companyId, Long flowId, Integer wipLimit) {
        this.name = name;
        this.position = position;
        this.companyId = companyId;
        this.flowId = flowId;
        this.wipLimit = wipLimit;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getWipLimit() {
        return wipLimit;
    }

    public void setWipLimit(Integer wipLimit) {
        this.wipLimit = wipLimit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
