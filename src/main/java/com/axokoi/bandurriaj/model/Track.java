package com.axokoi.bandurriaj.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Track extends BusinessEntity<Track> implements Searchable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(unique = true, updatable = false)
	private final String businessIdentifier;

	private int number;
	private String name;
	private String duration;
	@Lob
	private String comment;

	public Track() {
		this.businessIdentifier = super.getBusinessIdentifier();
	}

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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Track track = (Track) o;
		return businessIdentifier.equals(track.businessIdentifier);
	}

	@Override
	public int hashCode() {
		return Objects.hash(businessIdentifier);
	}

	@Override
	public String getBusinessIdentifier() {
		return businessIdentifier;


	}
}
