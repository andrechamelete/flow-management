package com.chamelete.flowManagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.model.Stage;
import com.chamelete.flowManagement.repository.StageRepository;
import com.chamelete.flowManagement.service.StageService;

@Service
public class StageServiceImpl implements StageService {
    
    private StageRepository stageRepository;

    @Autowired
    public StageServiceImpl(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    @Override
    public List<Stage> getStagesByFlow(Flows flow) {
        return stageRepository.getStagesByFlow(flow);
    }

    @Override
    public Stage createStage(Flows flow, String name, int position, boolean done) {
        Stage stage = new Stage();
        stage.setFlow(flow);
        stage.setName(name);
        stage.setPosition(position);
        stage.setDone(done);
        return stageRepository.save(stage);
    }

    @Override
    public Stage createStage(Stage stage) {
        return stageRepository.save(stage);
    }

    @Override
    public void changeName(Flows flow, String name, String newName) {
        Stage stage$ = stageRepository.findByFlowAndName(flow, name);
        stage$.setName(stage$.getName());
    }
}
