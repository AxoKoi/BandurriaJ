package com.axokoi.BandurriaJ.model;

public class DefaultArtist implements ArtistInterface{

	private String UNKNOWN = "UNKNOWN";

	@Override
	public Long getId() {
		return null;
	}

	@Override
	public void setId(Long id) {

	}

	@Override
	public String getName() {
		return UNKNOWN ;
	}

	@Override
	public void setName(String name) {

	}

	@Override
	public String getRole() {
		return UNKNOWN;
	}

	@Override
	public void setRole(String role) {

	}

	@Override
	public String getComment() {
		return UNKNOWN;
	}

	@Override
	public void setComment(String comment) {

	}
}
