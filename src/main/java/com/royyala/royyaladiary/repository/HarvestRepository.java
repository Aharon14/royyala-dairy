package com.royyala.royyaladiary.repository;

import com.royyala.royyaladiary.entity.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HarvestRepository extends JpaRepository<Harvest, Long> {
    Optional<Harvest> findByHarvestCycleId(Long cycleId);
}