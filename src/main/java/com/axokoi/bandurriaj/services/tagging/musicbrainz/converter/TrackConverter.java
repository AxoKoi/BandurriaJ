package com.axokoi.bandurriaj.services.tagging.musicbrainz.converter;

import org.musicbrainz.model.TrackWs2;

import com.axokoi.bandurriaj.model.Track;

public class TrackConverter implements Converter<TrackWs2, Track> {
	@Override
	public Track convert(TrackWs2 trackWs2) {
		Track newTrack = new Track();
		newTrack.setName(trackWs2.getTitle());
		//todo complete others
		return newTrack;
	}
}