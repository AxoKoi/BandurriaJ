package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.tagging.ProviderFacade;

@Component
public class MusicBrainzFacade implements ProviderFacade {

	@Override
	public Disc getDiscInfo(String discID) {
		return CdQuery.getDiscInfo(discID);
	}
}
