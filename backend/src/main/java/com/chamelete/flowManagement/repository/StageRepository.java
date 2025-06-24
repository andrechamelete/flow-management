package com.chamelete.flowManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.model.Stage;

public interface StageRepository extends JpaRepository<Stage, Long> {
    
    List<Stage> getStagesByFlow(Flows flow);

    boolean existsByFlowAndPosition(Flows flow, int position);

    Stage findByFlowAndPosition(Flows flow, int position);

    boolean existsByFlowAndName(Flows flow, String name);

    Stage findByFlowAndName(Flows flow, String name);

    Stage findById(long id);
}
