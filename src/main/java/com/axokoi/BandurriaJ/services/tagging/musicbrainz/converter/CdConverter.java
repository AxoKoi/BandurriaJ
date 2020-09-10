package com.axokoi.BandurriaJ.services.tagging.musicbrainz.converter;

import java.util.stream.Collectors;

import org.musicbrainz.model.entity.ReleaseWs2;

import com.axokoi.BandurriaJ.model.Band;
import com.axokoi.BandurriaJ.model.Disc;

public class CdConverter implements Converter<ReleaseWs2, Disc> {

	@Override
	public Disc convert(ReleaseWs2 releaseWs2) {
		Disc disc = new Disc();
		disc.setName(releaseWs2.getTitle());
		disc.setBand(new Band());
		disc.getBand().setName(releaseWs2.getArtistCreditString());
		TrackConverter trackConverter = new TrackConverter();
		disc.setTracks(releaseWs2.getMediumList().getCompleteTrackList().stream()
				.map(trackConverter::convert).collect(Collectors.toList()));
		disc.setComment(releaseWs2.getLabelInfoString());
	return  disc;
	}
}
