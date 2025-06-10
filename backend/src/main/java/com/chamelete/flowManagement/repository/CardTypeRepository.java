package com.chamelete.flowManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chamelete.flowManagement.model.CardType;
import com.chamelete.flowManagement.model.Companies;

public interface CardTypeRepository extends JpaRepository<CardType, Long> {

    List<CardType> findByCompany(Companies name);

    CardType findByCompanyAndCardType(Companies company, String cardType);
    
}
