package com.chamelete.flowManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.chamelete.flowManagement.model.Cards;
import com.chamelete.flowManagement.model.Stage;

import com.chamelete.flowManagement.repository.CardsRepository;
import com.chamelete.flowManagement.repository.StageRepository;
import com.chamelete.flowManagement.security.dto.CardMoveRequest;
//import com.chamelete.flowManagement.repository.UserRepository;
import com.chamelete.flowManagement.security.dto.CardReorderRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/board/card/")
public class CardMoveController {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private StageRepository stageRepository;

    public CardMoveController(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;

    }

    @PatchMapping("/reorder/{cardId}")
    public ResponseEntity<?> moveWithinStage(@PathVariable Long cardId, @RequestBody CardReorderRequest request) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Cards card = cardsRepository.findById(cardId).orElse(null);
        if (card == null) {
            return ResponseEntity.badRequest().body("card not found");
        }

        int previousPosition = card.getPosition();
        Stage stage = card.getStage();
        int newPosition = request.getNewPosition();

        List<Cards> stageCards = cardsRepository.findByStage(stage);

        if (newPosition < previousPosition) {
            for (Cards c : stageCards) {
                if (c.getPosition() >= newPosition && c.getPosition() < previousPosition) {
                    c.setPosition(c.getPosition() + 1);
                    cardsRepository.save(c);
                }
            }
        } else if (newPosition > previousPosition) {
            for (Cards c: stageCards) {
                if (c.getPosition() > previousPosition && c.getPosition() <= newPosition) {
                    c.setPosition(c.getPosition() - 1);
                    cardsRepository.save(c);
                }
            }
        } else {
            return ResponseEntity.badRequest().body("new position is the same as the previous position");
        }

        card.setPosition(newPosition);
        cardsRepository.save(card);

        return  ResponseEntity.ok(card);
    }
    
    @PatchMapping("/move/{cardId}")
    public ResponseEntity<?> moveToAnotherStage(@PathVariable Long cardId, @RequestBody CardMoveRequest request) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Cards card = cardsRepository.findById(cardId).orElse(null);
        if (card == null) {
            return ResponseEntity.badRequest().body("card not found");
        }

        Stage previousStage = card.getStage();
        Stage targetStage = stageRepository.findById(request.getTargetStageId());
        int newPosition = request.getNewPosition();
        int previousPosition = card.getPosition();

        if (targetStage == null) {
            return ResponseEntity.badRequest().body("target stage not found");
        }

        List<Cards> previousStageCards = cardsRepository.findByStage(previousStage);
        List<Cards> targetStageCards = cardsRepository.findByStage(targetStage);

        for (Cards c: previousStageCards) {
            if (c.getPosition() > previousPosition) {
                c.setPosition(c.getPosition() - 1);
                cardsRepository.save(c);
            }
        }

        for (Cards c: targetStageCards) {
            if (c.getPosition() >= newPosition) {
                c.setPosition(c.getPosition() + 1);
                cardsRepository.save(c);
            }
        }

        card.setStage(targetStage);
        card.setPosition(newPosition);
        cardsRepository.save(card);

        return ResponseEntity.ok(card);
    }
}
