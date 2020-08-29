package com.axokoi.BandurriaJ.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Catalogue {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	//todo name should be unique too.
	private String name;
	@OneToMany(targetEntity = Disc.class, fetch = FetchType.EAGER)
	private List<Disc> discs;


}
