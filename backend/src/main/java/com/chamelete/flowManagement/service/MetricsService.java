package com.chamelete.flowManagement.service;

import java.time.LocalDateTime;
import java.util.List;

import com.chamelete.flowManagement.model.Cards;
import com.chamelete.flowManagement.model.Flows;

public interface MetricsService {

    List<Cards> getCardsByDone(Flows flow);

    int getLeadTime(Flows flow, LocalDateTime startDate, LocalDateTime endDate);

    List<Cards> getCardsByDoneInRange (Flows flow, LocalDateTime startDate, LocalDateTime endDate);
}
