package com.royyala.royyaladiary.service;

import com.royyala.royyaladiary.entity.Harvest;
import com.royyala.royyaladiary.entity.HarvestCycle;
import com.royyala.royyaladiary.repository.HarvestCycleRepository;
import com.royyala.royyaladiary.repository.HarvestRepository;
import com.royyala.royyaladiary.repository.PondRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class HarvestService {

    private final HarvestRepository harvestRepository;
    private final HarvestCycleRepository harvestCycleRepository;
    private final FeedEntryService feedEntryService;
    private final PondRepository pondRepository;

    public HarvestService(
            HarvestRepository harvestRepository,
            HarvestCycleRepository harvestCycleRepository,
            FeedEntryService feedEntryService,
            PondRepository pondRepository) {
        this.harvestRepository = harvestRepository;
        this.harvestCycleRepository = harvestCycleRepository;
        this.feedEntryService = feedEntryService;
        this.pondRepository = pondRepository;
    }

    public void logHarvest(Harvest harvest, Long cycleId) {
        HarvestCycle cycle = harvestCycleRepository
                .findById(cycleId)
                .orElseThrow(() ->
                        new RuntimeException("Cycle not found"));

        double totalRevenue = harvest.getTotalKg()
                * harvest.getPricePerKg();
        double totalFeedCost = feedEntryService
                .getTotalFeedCost(cycleId);
        double totalCost = cycle.getSeedCost() + totalFeedCost;
        double profitOrLoss = totalRevenue - totalCost;

        harvest.setTotalRevenue(totalRevenue);
        harvest.setTotalFeedCost(totalFeedCost);
        harvest.setProfitOrLoss(profitOrLoss);
        harvest.setHarvestCycle(cycle);

        cycle.setStatus("COMPLETED");
        harvestCycleRepository.save(cycle);
        harvestRepository.save(harvest);
    }

    public Optional<Harvest> getHarvestByCycle(Long cycleId) {
        return harvestRepository
                .findByHarvestCycleId(cycleId);
    }

    public long getTotalHarvestCount(Long farmerId) {
        return pondRepository.findByFarmerId(farmerId)
                .stream()
                .flatMap(pond ->
                        harvestCycleRepository
                                .findByPondId(pond.getId())
                                .stream())
                .filter(cycle ->
                        "COMPLETED".equals(cycle.getStatus()))
                .count();
    }

    public double getTotalProfit(Long farmerId) {
        return pondRepository.findByFarmerId(farmerId)
                .stream()
                .flatMap(pond ->
                        harvestCycleRepository
                                .findByPondId(pond.getId())
                                .stream())
                .filter(cycle ->
                        "COMPLETED".equals(cycle.getStatus()))
                .mapToDouble(cycle ->
                        harvestRepository
                                .findByHarvestCycleId(cycle.getId())
                                .map(h -> h.getProfitOrLoss())
                                .orElse(0.0))
                .sum();
    }
}