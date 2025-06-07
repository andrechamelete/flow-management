package com.chamelete.flowManagement.security.dto;

public class PermissionRequest {

    private String email;
    private Long companyId;

    public PermissionRequest() {}

    public PermissionRequest(String email, Long companyId) {
        this.email = email;
        this.companyId = companyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }    
}
