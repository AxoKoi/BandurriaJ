package com.axokoi.bandurriaj.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Artist implements Searchable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String role;
	@Lob
	private String comment;
	private String mbIdentifier;
	private Artist.Type type;

	@ManyToMany(targetEntity = Artist.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Artist> composingArtists;

	@ManyToMany(targetEntity = Disc.class, cascade = CascadeType.ALL)
	private List<Disc> discs;


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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<Artist> getComposingArtists() {
		return composingArtists;
	}

	public void setComposingArtists(List<Artist> composingArtists) {
		this.composingArtists = composingArtists;
	}


	public String getMbIdentifier() {
		return mbIdentifier;
	}

	public void setMbIdentifier(String mbIdentifier) {
		this.mbIdentifier = mbIdentifier;
	}

	public List<Disc> getDiscs() {
		return discs;
	}

	public void setDiscs(List<Disc> discs) {
		this.discs = discs;
	}

	public void addDisc(Disc disc) {
		if(this.getDiscs() == null ) {
			this.discs = new ArrayList<>();
		}
		this.discs.add(disc);
	}


	public enum Type {
		GROUP, SINGLE;
	}
}

