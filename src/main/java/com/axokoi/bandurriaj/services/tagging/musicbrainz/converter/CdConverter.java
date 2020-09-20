package com.axokoi.bandurriaj.services.tagging.musicbrainz.converter;

import com.axokoi.bandurriaj.model.Band;
import com.axokoi.bandurriaj.model.Disc;
import org.musicbrainz.model.entity.ReleaseGroupWs2;
import org.springframework.stereotype.Component;

@Component
public class CdConverter implements Converter<ReleaseGroupWs2, Disc> {

	public Disc convert(ReleaseGroupWs2 releaseGroupWs2) {
		Disc disc = new Disc();
		disc.setName(releaseGroupWs2.getTitle());
		disc.setBand(new Band());
		disc.getBand().setName(releaseGroupWs2.getArtistCreditString());
		TrackConverter trackConverter = new TrackConverter();
//todo complete the track converter
		disc.setComment(releaseGroupWs2.getYear());
	return  disc;
	}
}
