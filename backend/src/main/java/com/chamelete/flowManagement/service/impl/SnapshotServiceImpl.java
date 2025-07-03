package com.chamelete.flowManagement.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.chamelete.flowManagement.model.Snapshot;
import com.chamelete.flowManagement.model.Stage;
import com.chamelete.flowManagement.repository.CardsRepository;
import com.chamelete.flowManagement.repository.SnapshotRepository;
import com.chamelete.flowManagement.repository.StageRepository;
import com.chamelete.flowManagement.service.SnapshotService;

@Service
public class SnapshotServiceImpl implements SnapshotService {

    private final StageRepository stageRepository;
    private final SnapshotRepository snapshotRepository;
    private final CardsRepository cardsRepository;

    public SnapshotServiceImpl(StageRepository stageRepository, SnapshotRepository snapshotRepository, CardsRepository cardsRepository) {
        this.stageRepository = stageRepository;
        this.snapshotRepository = snapshotRepository;
        this.cardsRepository = cardsRepository;
    }
    
    @Scheduled(cron = "0 0 18 * * MON-FRI")
    public void saveSnapshot() {
        List<Stage> stages = stageRepository.findAll();

        for (Stage stage : stages) {
            Snapshot snapshot = new Snapshot();
            snapshot.setFlow(stage.getFlow());
            snapshot.setStage(stage);
            snapshot.setTime(LocalDate.now());
            snapshot.setCard_quantity(cardsRepository.findByStage(stage).size());
            snapshotRepository.save(snapshot);
            
        }
    }
}
