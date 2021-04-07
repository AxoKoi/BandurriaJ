package com.axokoi.bandurriaj.services.tagging.musicbrainz.converter;

import org.musicbrainz.model.TrackWs2;

import com.axokoi.bandurriaj.model.Track;
import org.springframework.stereotype.Component;

@Component
public class TrackConverter implements Converter<TrackWs2, Track> {
	@Override
	public Track convert(TrackWs2 trackWs2) {
		Track newTrack = new Track();
		newTrack.setName(trackWs2.getRecording().getTitle());
		newTrack.setDuration(trackWs2.getDuration());
		newTrack.setNumber(trackWs2.getPosition());

		return newTrack;
	}
}
