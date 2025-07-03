package com.chamelete.flowManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chamelete.flowManagement.model.Snapshot;

public interface SnapshotRepository extends JpaRepository<Snapshot, Long> {
    
}
