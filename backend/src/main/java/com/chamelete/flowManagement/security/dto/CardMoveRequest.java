package com.chamelete.flowManagement.security.dto;

public class CardMoveRequest {

    private int targetStageId;
    private int newPosition;
    
    public CardMoveRequest() {
    }

    public int getTargetStageId() {
        return targetStageId;
    }

    public void setTargetStageId(int targetStageId) {
        this.targetStageId = targetStageId;
    }

    public int getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(int newPosition) {
        this.newPosition = newPosition;
    }
}
