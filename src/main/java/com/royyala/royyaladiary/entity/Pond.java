package com.royyala.royyaladiary.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ponds")
@Data
public class Pond {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String pondName;

	private Double areaInAcres;

	private String location;

	@ManyToOne
	@JoinColumn(name = "farmer_id", nullable = false)
	private Farmer farmer;

	@OneToMany(mappedBy = "pond", cascade = CascadeType.ALL)
	private List<HarvestCycle> harvestCycles;
}
