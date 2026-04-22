package com.royyala.royyaladiary.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "feed_entries")
@Data
public class FeedEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate feedDate;

	private Double feedQuantityKg;

	private Double feedCost;

	private Integer weekNumber;

	@ManyToOne
	@JoinColumn(name = "cycle_id", nullable = false)
	private HarvestCycle harvestCycle;
}
