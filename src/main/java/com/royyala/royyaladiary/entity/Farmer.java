package com.royyala.royyaladiary.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "farmers")
@Data
public class Farmer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String fullName;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	private String phone;

	private String village;

	@OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL)
	private List<Pond> ponds;

}
