package com.axokoi.bandurriaj.model;

import javax.persistence.*;

@Entity
public class Track implements Searchable{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private int number;
	private String name;
	private String duration;
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
}
