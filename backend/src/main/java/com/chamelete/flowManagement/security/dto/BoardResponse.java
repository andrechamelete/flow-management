package com.chamelete.flowManagement.security.dto;

import java.util.List;

import com.chamelete.flowManagement.model.Cards;
import com.chamelete.flowManagement.model.ServiceClasses;
import com.chamelete.flowManagement.model.Stage;

public class BoardResponse {
    
    private List<Stage> stages;
    private List<Cards> cards;
    private List<ServiceClasses> classOfService;

    public BoardResponse(List<Stage> stages, List<Cards> cards, List<ServiceClasses> classOfService) {
        this.stages = stages;
        this.cards = cards;
        this.classOfService = classOfService;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public List<Cards> getCards() {
        return cards;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public void setCards(List<Cards> cards) {
        this.cards = cards;
    }

    public List<ServiceClasses> getServiceClasses() {
        return classOfService;
    }

    public void setServiceClasses(List<ServiceClasses> classOfService) {
        this.classOfService = classOfService;
    }

    
    
}
