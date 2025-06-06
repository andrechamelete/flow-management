package com.chamelete.flowManagement.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.User;

public interface CompaniesRepository extends JpaRepository<Companies, Long> {

    boolean existsByName(String name);

    Optional<Companies> findByName(String name);

    List<Companies> findAllByCreatedBy(User user);

    Companies findById(long id);
}
