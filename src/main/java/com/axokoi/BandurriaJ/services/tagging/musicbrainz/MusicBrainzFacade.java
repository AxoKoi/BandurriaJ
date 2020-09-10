package com.axokoi.BandurriaJ.services.tagging.musicbrainz;

import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Disc;
import com.axokoi.BandurriaJ.services.tagging.ProviderFacade;

@Component
public class MusicBrainzFacade implements ProviderFacade {

	@Override
	public Disc getDiscInfo(String discID) {
		return CdQuery.getDiscInfo(discID);
	}
}
