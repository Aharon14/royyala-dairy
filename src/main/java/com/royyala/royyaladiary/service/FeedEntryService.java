package com.royyala.royyaladiary.service;

import com.royyala.royyaladiary.entity.FeedEntry;
import com.royyala.royyaladiary.entity.HarvestCycle;
import com.royyala.royyaladiary.repository.FeedEntryRepository;
import com.royyala.royyaladiary.repository.HarvestCycleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FeedEntryService {

    private final FeedEntryRepository feedEntryRepository;
    private final HarvestCycleRepository harvestCycleRepository;

    public FeedEntryService(
            FeedEntryRepository feedEntryRepository,
            HarvestCycleRepository harvestCycleRepository) {
        this.feedEntryRepository = feedEntryRepository;
        this.harvestCycleRepository = harvestCycleRepository;
    }

    public void addFeedEntry(FeedEntry feedEntry, Long cycleId) {
        HarvestCycle cycle = harvestCycleRepository
                .findById(cycleId)
                .orElseThrow(() ->
                        new RuntimeException("Cycle not found"));
        feedEntry.setHarvestCycle(cycle);
        feedEntryRepository.save(feedEntry);
    }

    public List<FeedEntry> getFeedEntriesByCycle(Long cycleId) {
        return feedEntryRepository.findByHarvestCycleId(cycleId);
    }

    public double getTotalFeedCost(Long cycleId) {
        return feedEntryRepository
                .findByHarvestCycleId(cycleId)
                .stream()
                .mapToDouble(FeedEntry::getFeedCost)
                .sum();
    }

    public HarvestCycle getCycleById(Long cycleId) {
        return harvestCycleRepository.findById(cycleId)
                .orElseThrow(() ->
                        new RuntimeException("Cycle not found"));
    }
}