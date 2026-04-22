package com.royyala.royyaladiary.service;

import com.royyala.royyaladiary.entity.Farmer;
import com.royyala.royyaladiary.entity.Pond;
import com.royyala.royyaladiary.repository.FarmerRepository;
import com.royyala.royyaladiary.repository.PondRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PondService {

    private final PondRepository pondRepository;
    private final FarmerRepository farmerRepository;

    public PondService(PondRepository pondRepository,
                       FarmerRepository farmerRepository) {
        this.pondRepository = pondRepository;
        this.farmerRepository = farmerRepository;
    }

    public Farmer getLoggedInFarmer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return farmerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));
    }

    public void addPond(Pond pond) {
        Farmer farmer = getLoggedInFarmer();
        pond.setFarmer(farmer);
        pondRepository.save(pond);
    }

    public List<Pond> getMyPonds() {
        Farmer farmer = getLoggedInFarmer();
        return pondRepository.findByFarmerId(farmer.getId());
    }

    public long getPondCount() {
        Farmer farmer = getLoggedInFarmer();
        return pondRepository.findByFarmerId(farmer.getId()).size();
    }
}