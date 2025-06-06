package com.chamelete.flowManagement.repository;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.Flows;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowsRepository extends JpaRepository<Flows, Long> {
    
    List<Flows> getFlowByCompany(Companies company);
}
