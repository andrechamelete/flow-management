package com.chamelete.flowManagement.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chamelete.flowManagement.model.Cards;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.repository.CardsRepository;
import com.chamelete.flowManagement.service.MetricsService;

@Service
public class MetricsServiceImpl implements MetricsService {

    private final CardsRepository cardsRepository;

    @Autowired
    public MetricsServiceImpl(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
    }

    @Override
    public List<Cards> getCardsByDone(Flows flow) {
        List<Cards> cards = cardsRepository.findByFlow(flow);
        List<Cards> cardList = new ArrayList<>();

        for (Cards card: cards) {
            if(card.getStage().getType() == "done") {
                cardList.add(card);
            }
        }
        return cardList;        
    }

    @Override
    public List<Cards> getCardsByDoneInRange (Flows flow, LocalDateTime startDate, LocalDateTime endDate) {
        List<Cards> cards = cardsRepository.findByFlow(flow);
        cards.removeIf(card -> card.getStage().getType() != "done");
        cards.removeIf(card -> !card.getFinishedAt().isAfter(startDate));
        cards.removeIf(card -> !card.getFinishedAt().isBefore(endDate));

        return cards;
    }

    @Override
    public int getLeadTime(Flows flow, LocalDateTime startDate, LocalDateTime endDate) {
        List<Cards> cards = this.getCardsByDoneInRange(flow, startDate, endDate);
        int leadTime = 0;

        if (cards.isEmpty()) {
            return 0;
        }

        for (Cards card: cards) {
            LocalDateTime start = card.getCreatedAt();
            LocalDateTime end = card.getFinishedAt();
            Long lt = Duration.between(start, end).toMinutes();
            leadTime += lt;
        }
        return leadTime/cards.size();
    }
}