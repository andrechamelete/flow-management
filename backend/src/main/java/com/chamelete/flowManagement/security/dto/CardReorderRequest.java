package com.chamelete.flowManagement.security.dto;

public class CardReorderRequest {

    private int newPosition;

    public CardReorderRequest() {
    }

    public CardReorderRequest(int newPosition) {
        this.newPosition = newPosition;
    }

    public int getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(int newPosition) {
        this.newPosition = newPosition;
    }
    
    
    
}
