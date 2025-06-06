package com.chamelete.flowManagement.service;

import java.util.List;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.security.dto.FlowsRequest;

public interface FlowsService {
    
    Flows createFlow(FlowsRequest request);

    List<Flows> getFlowsByCompany(Companies company);
}
