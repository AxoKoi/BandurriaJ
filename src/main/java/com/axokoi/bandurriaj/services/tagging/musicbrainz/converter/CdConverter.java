package com.axokoi.bandurriaj.services.tagging.musicbrainz.converter;

import java.util.stream.Collectors;

import org.musicbrainz.model.entity.ReleaseGroupWs2;
import org.musicbrainz.model.entity.ReleaseWs2;

import com.axokoi.bandurriaj.model.Band;
import com.axokoi.bandurriaj.model.Disc;

public class CdConverter implements Converter<ReleaseGroupWs2, Disc> {

	public Disc convert(ReleaseGroupWs2 releaseGroupWs2) {
		Disc disc = new Disc();
		disc.setName(releaseGroupWs2.getTitle());
		disc.setBand(new Band());
		disc.getBand().setName(releaseGroupWs2.getArtistCreditString());
		TrackConverter trackConverter = new TrackConverter();
		/*disc.setTracks(releaseGroupWs2.getReleases().get(0).getMediumList().getCompleteTrackList().stream()
				.map(trackConverter::convert).collect(Collectors.toList()));*/
		disc.setComment(releaseGroupWs2.getYear());
	return  disc;
	}
}
