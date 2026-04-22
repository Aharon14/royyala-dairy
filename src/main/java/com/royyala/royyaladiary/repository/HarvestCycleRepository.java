package com.royyala.royyaladiary.repository;

import com.royyala.royyaladiary.entity.HarvestCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HarvestCycleRepository extends JpaRepository<HarvestCycle, Long> {
	List<HarvestCycle> findByPondId(Long pondId);
}