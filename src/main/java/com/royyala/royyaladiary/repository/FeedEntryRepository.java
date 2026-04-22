package com.royyala.royyaladiary.repository;

import com.royyala.royyaladiary.entity.FeedEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedEntryRepository extends JpaRepository<FeedEntry, Long> {
	List<FeedEntry> findByHarvestCycleId(Long cycleId);
}