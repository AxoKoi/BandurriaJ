package com.axokoi.bandurriaj.model;

import java.util.List;

import javax.persistence.*;


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

	@Lob
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public List<Disc> getDiscs() {
		return discs;
	}

	public void setDiscs(List<Disc> discs) {
		this.discs = discs;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
