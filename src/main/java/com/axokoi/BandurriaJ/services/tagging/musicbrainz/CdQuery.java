package com.axokoi.BandurriaJ.services.tagging.musicbrainz;

import java.util.List;

import org.musicbrainz.controller.ReleaseGroup;
import org.musicbrainz.model.entity.ReleaseWs2;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Disc;
import com.axokoi.BandurriaJ.services.tagging.musicbrainz.converter.CdConverter;

@Component
public class CdQuery {
	public static Disc getDiscInfo(String discID) {
		ReleaseWs2 resultFromMBapi = (ReleaseWs2) callMB(discID);//= use the api
		CdConverter cdConverter = new CdConverter();
		return cdConverter.convert(resultFromMBapi);
	}

	private static ReleaseWs2 callMB(String discID) {
		ReleaseGroup releaseGroup = new ReleaseGroup();
		releaseGroup.search(discID);
		List<ReleaseWs2> results = releaseGroup.getFullReleaseList();
		//todo if not empty
		return results.get(0);

	}
}
