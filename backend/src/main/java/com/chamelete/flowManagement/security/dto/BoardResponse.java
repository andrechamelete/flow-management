package com.chamelete.flowManagement.security.dto;

import java.util.List;

import com.chamelete.flowManagement.model.Cards;
import com.chamelete.flowManagement.model.Stage;

public class BoardResponse {
    
    private List<Stage> stages;
    private List<Cards> cards;

    public BoardResponse(List<Stage> stages, List<Cards> cards) {
        this.stages = stages;
        this.cards = cards;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public List<Cards> getCards() {
        return cards;
    }
    
}
