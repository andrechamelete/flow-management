package com.chamelete.flowManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chamelete.flowManagement.model.CardType;
import com.chamelete.flowManagement.model.Cards;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.model.ServiceClasses;
import com.chamelete.flowManagement.model.Stage;
import com.chamelete.flowManagement.model.User;

public interface CardsRepository extends JpaRepository<Cards, Long> {

    List<Cards> findByFlow(Flows flow);

    List<Cards> findByAssignedTo(User user);

    List<Cards> findByType(CardType type);

    List<Cards> findByClassOfService(ServiceClasses serviceClasses);

    List<Cards> findByBlocked(boolean blocked);

    List<Cards> findByStage(Stage stage);
    
    Cards findById(long id);
    
}
