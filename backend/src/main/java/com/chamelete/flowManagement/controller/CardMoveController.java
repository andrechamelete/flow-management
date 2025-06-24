package com.chamelete.flowManagement.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.chamelete.flowManagement.model.Cards;
import com.chamelete.flowManagement.model.CycleTime;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.model.Stage;

import com.chamelete.flowManagement.repository.CardsRepository;
import com.chamelete.flowManagement.repository.CycleTimeRepository;
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

    @Autowired
    private CycleTimeRepository cycleTimeRepository;

    public CardMoveController(CardsRepository cardsRepository, StageRepository stageRepository, CycleTimeRepository cycleTimeRepository) {
        this.cardsRepository = cardsRepository;
        this.stageRepository = stageRepository;
        this.cycleTimeRepository = cycleTimeRepository;

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
            return ResponseEntity.ok().body("new position is the same as the previous position");
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
        Flows flow = card.getFlow();

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

        if (targetStage.getType() == "done") {
            card.setFinishedAt(LocalDateTime.now());
        }

        card.setStage(targetStage);
        card.setPosition(newPosition);
        cardsRepository.save(card);
        
        CycleTime cycleTimePreviousStage = cycleTimeRepository.findByCardAndStage(card, previousStage);
        
        if (previousStage.getPosition() > targetStage.getPosition()) {
            if (previousStage.getType() != "done") {
                cycleTimeRepository.delete(cycleTimePreviousStage);
            }

            if (previousStage.getPosition() - targetStage.getPosition() > 1) {
                for (int i = targetStage.getPosition() + 1; i < previousStage.getPosition(); i++) {
                    CycleTime cycleTime = cycleTimeRepository.findByCardAndStage(card, stageRepository.findByFlowAndPosition(flow, i));
                    cycleTimeRepository.delete(cycleTime);

                }
            }
            
            CycleTime cycleTimeTargetStage = cycleTimeRepository.findByCardAndStage(card, targetStage);                      
            cycleTimeTargetStage.setFinishedAt(null);
            cycleTimeRepository.save(cycleTimeTargetStage);
        }

        if (previousStage.getPosition() < targetStage.getPosition()) {
            //implementar lógica para quando o card é movido pulando stages 
            //(da stage.position 1 para a stage.position 3)
            cycleTimePreviousStage.setFinishedAt(LocalDateTime.now());
            cycleTimeRepository.save(cycleTimePreviousStage);
            
            if (targetStage.getPosition() - previousStage.getPosition() > 1) {
                for (int i = previousStage.getPosition() + 1; i < targetStage.getPosition(); i++) {
                    CycleTime cycleTime = cycleTimeRepository.findByCardAndStage(card, stageRepository.findByFlowAndPosition(flow, i));
                    cycleTime.setCreatedAt(LocalDateTime.now());
                    cycleTime.setFinishedAt(LocalDateTime.now());
                    cycleTimeRepository.save(cycleTime);
                } 
            }

            if (targetStage.getType() != "done") {
                CycleTime cycleTimeTargetStage = new CycleTime();
                cycleTimeTargetStage.setCard(card);
                cycleTimeTargetStage.setStage(targetStage);
                cycleTimeTargetStage.setFlow(flow);
                cycleTimeTargetStage.setCreatedAt(LocalDateTime.now());
                cycleTimeRepository.save(cycleTimeTargetStage);
            }            
        }        
        return ResponseEntity.ok(card);
    }
}
