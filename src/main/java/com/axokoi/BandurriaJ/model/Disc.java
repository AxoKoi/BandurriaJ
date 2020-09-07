package com.axokoi.BandurriaJ.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Disc implements Searchable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;

	@ManyToOne(targetEntity = Band.class, cascade = CascadeType.REFRESH)
	private Band band;

	@OneToMany(targetEntity = Track.class, cascade = CascadeType.ALL)
	private List<Track> tracks;

	private String comment;

}
