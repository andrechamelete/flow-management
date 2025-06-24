package com.chamelete.flowManagement.service;

import java.util.List;

import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.model.Stage;

public interface StageService {

    List<Stage> getStagesByFlow(Flows flow);

    Stage createStage(Flows flow, String name, int position, String type);

    Stage createStage(Stage stage);

    void changeName(Flows flow, String name, String newName);

}
