package com.royyala.royyaladiary.entity;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;;

@Entity
@Table(name = "harvest_cycles")
@Data
public class HarvestCycle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate startDate;

	private Integer seedCount;

	private Double seedCost;

	private String status; // ACTIVE or COMPLETED

	@ManyToOne
	@JoinColumn(name = "pond_id", nullable = false)
	private Pond pond;

	@OneToMany(mappedBy = "harvestCycle", cascade = CascadeType.ALL)
	private List<FeedEntry> feedEntries;

	@OneToOne(mappedBy = "harvestCycle", cascade = CascadeType.ALL)
	private Harvest harvest;

}
