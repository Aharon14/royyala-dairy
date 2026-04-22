package com.royyala.royyaladiary.service;

import com.royyala.royyaladiary.entity.HarvestCycle;
import com.royyala.royyaladiary.entity.Pond;
import com.royyala.royyaladiary.repository.HarvestCycleRepository;
import com.royyala.royyaladiary.repository.PondRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HarvestCycleService {

    private final HarvestCycleRepository harvestCycleRepository;
    private final PondRepository pondRepository;
    private final PondService pondService;

    public HarvestCycleService(HarvestCycleRepository harvestCycleRepository,
                                PondRepository pondRepository,
                                PondService pondService) {
        this.harvestCycleRepository = harvestCycleRepository;
        this.pondRepository = pondRepository;
        this.pondService = pondService;
    }

    public void startCycle(HarvestCycle cycle, Long pondId) {
        Pond pond = pondRepository.findById(pondId)
                .orElseThrow(() -> new RuntimeException("Pond not found"));
        cycle.setPond(pond);
        cycle.setStatus("ACTIVE");
        harvestCycleRepository.save(cycle);
    }

    public List<HarvestCycle> getCyclesByPond(Long pondId) {
        return harvestCycleRepository.findByPondId(pondId);
    }

    public long getActiveCycleCount() {
        Long farmerId = pondService.getLoggedInFarmer().getId();
        List<Pond> ponds = pondRepository.findByFarmerId(farmerId);
        return ponds.stream()
                .flatMap(pond -> harvestCycleRepository
                        .findByPondId(pond.getId()).stream())
                .filter(cycle -> "ACTIVE".equals(cycle.getStatus()))
                .count();
    }
}