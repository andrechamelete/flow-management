package com.chamelete.flowManagement.security.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.chamelete.flowManagement.model.CardType;
import com.chamelete.flowManagement.model.ServiceClasses;

public class CardRequest {

    private String name;
    private String description;
    private int position;
    private LocalDate dueDate;
    private String assigneeEmail;
    private ServiceClasses classOfService;
    private CardType type;
    private Long companyId;
    private Long flowId;
    private Long stageId;
    private LocalDateTime createdAt;
    
    public CardRequest() {
    }

    public CardRequest(String name, String description, int position, LocalDate due_date, String assigneeEmail,
            ServiceClasses classOfService, CardType type, Long companyId, Long flowId, Long stageId, LocalDateTime created_at) {
        this.name = name;
        this.description = description;
        this.position = position;
        this.dueDate = due_date;
        this.assigneeEmail = assigneeEmail;
        this.classOfService = classOfService;
        this.type = type;
        this.companyId = companyId;
        this.flowId = flowId;
        this.stageId = stageId;
        this.createdAt = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssigneeEmail() {
        return assigneeEmail;
    }

    public void setAssigned_to(String assigneeEmail) {
        this.assigneeEmail = assigneeEmail;
    }

    public ServiceClasses getClassOfService() {
        return classOfService;
    }

    public void setClassOfService(ServiceClasses classOfService) {
        this.classOfService = classOfService;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
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

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stage) {
        this.stageId = stage;
    }
    
    public LocalDateTime getCreated_at() {
        return createdAt;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.createdAt = created_at;
    }
    
}
