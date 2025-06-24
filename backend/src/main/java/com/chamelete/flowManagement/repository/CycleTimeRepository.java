package com.chamelete.flowManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chamelete.flowManagement.model.Cards;
import com.chamelete.flowManagement.model.CycleTime;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.model.Stage;

public interface CycleTimeRepository extends JpaRepository<CycleTime, Long> {

    CycleTime findByFlowAndCard(Flows flow, Cards card);

    CycleTime findByCardAndStage(Cards card, Stage stage);

    CycleTime findByFlowAndStage(Flows flow, Stage stage);

    
}
