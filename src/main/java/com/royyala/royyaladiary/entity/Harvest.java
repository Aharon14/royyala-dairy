package com.royyala.royyaladiary.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "harvests")
@Data
public class Harvest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate harvestDate;

	private Double totalKg;

	private Double pricePerKg;

	private Double totalRevenue;

	private Double totalFeedCost;

	private Double profitOrLoss;

	@OneToOne
	@JoinColumn(name = "cycle_id", nullable = false)
	private HarvestCycle harvestCycle;
}
