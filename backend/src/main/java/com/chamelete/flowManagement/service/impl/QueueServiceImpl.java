package com.chamelete.flowManagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamelete.flowManagement.model.Cards;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.repository.CardsRepository;
import com.chamelete.flowManagement.security.dto.metrics.QueueResponse;
import com.chamelete.flowManagement.service.QueueService;

@Service
public class QueueServiceImpl implements QueueService {
    
    @Autowired
    private final CardsRepository cardsRepository;

    public QueueServiceImpl(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
    }

    public QueueResponse getQueue(Flows flow) {
        List<Cards> cards = cardsRepository.findByFlow(flow);

        int queue = 0;
        int wip = 0;

        for (Cards c: cards) {
            switch (c.getStage().getType()) {
                case "process": wip += 1;                        
                    break;
                case "queue": queue += 1;
                    break;                
                default:
                    break;
            }
  
        }

        return new QueueResponse(wip, queue);
    }

}
