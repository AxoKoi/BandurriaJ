package com.axokoi.BandurriaJ.services.tagging.musicbrainz;

import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Disc;
import com.axokoi.BandurriaJ.services.tagging.musicbrainz.converter.CdConverter;

@Component
public class CdQuery {
	public static Disc getDiscInfo(String discID) {
		Object resultFromMBapi = null;//= use the api
		CdConverter cdConverter = new CdConverter();
		return cdConverter.convert(resultFromMBapi);
	}
}
