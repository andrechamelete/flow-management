package com.chamelete.flowManagement.security.dto;

import java.util.List;

import com.chamelete.flowManagement.model.CardType;
import com.chamelete.flowManagement.model.Cards;
import com.chamelete.flowManagement.model.ServiceClasses;
import com.chamelete.flowManagement.model.Stage;

public class BoardResponse {
    
    private List<Stage> stages;
    private List<Cards> cards;
    private List<ServiceClasses> classOfService;
    private List<CardType> cardTypes;

    public BoardResponse(List<Stage> stages, List<Cards> cards, List<ServiceClasses> classOfService, List<CardType> cardTypes) {
        this.stages = stages;
        this.cards = cards;
        this.classOfService = classOfService;
        this.cardTypes = cardTypes;
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

    public List<CardType> getCardTypes() {
        return cardTypes;
    }

    public void setCardTypes(List<CardType> cardTypes) {
        this.cardTypes = cardTypes;
    }
    
}
