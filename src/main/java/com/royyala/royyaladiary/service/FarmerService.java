package com.royyala.royyaladiary.service;

import com.royyala.royyaladiary.entity.Farmer;
import com.royyala.royyaladiary.repository.FarmerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FarmerService implements UserDetailsService {

    private final FarmerRepository farmerRepository;
    private final PasswordEncoder passwordEncoder;

    public FarmerService(FarmerRepository farmerRepository,
                         PasswordEncoder passwordEncoder) {
        this.farmerRepository = farmerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Farmer farmer = farmerRepository.findByEmail(email)
            .orElseThrow(() ->
                new UsernameNotFoundException("Farmer not found: " + email));

        return User.builder()
            .username(farmer.getEmail())
            .password(farmer.getPassword())
            .roles("FARMER")
            .build();
    }

    public void registerFarmer(Farmer farmer) {
        farmer.setPassword(passwordEncoder.encode(farmer.getPassword()));
        farmerRepository.save(farmer);
    }

    public boolean emailExists(String email) {
        return farmerRepository.findByEmail(email).isPresent();
    }
}