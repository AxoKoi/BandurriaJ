package com.axokoi.BandurriaJ.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Band implements Searchable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@OneToMany(targetEntity = Artist.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Artist> artists;

	@OneToMany(targetEntity = Disc.class, cascade = CascadeType.ALL)
	private List<Disc> discs;

	private String comment;

}
