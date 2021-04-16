package com.axokoi.bandurriaj.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;


@Entity
public class Catalogue extends BusinessEntity implements Searchable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(unique = true, updatable = false)
	private final String businessIdentifier;

	@Column(unique = true)
	private String name;
	@OneToMany(targetEntity = Disc.class, fetch = FetchType.EAGER)
	private List<Disc> discs;
	@Lob
	private String comment;

	public Catalogue() {
		businessIdentifier = super.getBusinessIdentifier();
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

	@Override
	public String getBusinessIdentifier() {
		return businessIdentifier;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Catalogue catalogue = (Catalogue) o;
		return businessIdentifier.equals(catalogue.businessIdentifier);
	}

	@Override
	public int hashCode() {
		return Objects.hash(businessIdentifier);
	}
}
