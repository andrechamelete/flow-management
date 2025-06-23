package com.chamelete.flowManagement.security.dto;

import java.time.LocalDate;


public class UpdateCardRequest {

    private String name;
    private String description;
    private LocalDate dueDate;
    private String assignedTo;
    private Long classOfService;
    private Long type;
    private boolean blocked;
    
    public UpdateCardRequest() {
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedToEmail(String assignedToEmail) {
        this.assignedTo = assignedToEmail;
    }

    public Long getClassOfService() {
        return classOfService;
    }

    public void setClassOfService(Long classOfService) {
        this.classOfService = classOfService;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    
}
