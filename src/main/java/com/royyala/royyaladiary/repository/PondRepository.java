package com.royyala.royyaladiary.repository;

import com.royyala.royyaladiary.entity.Pond;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PondRepository extends JpaRepository<Pond, Long> {
	List<Pond> findByFarmerId(Long farmerId);
}