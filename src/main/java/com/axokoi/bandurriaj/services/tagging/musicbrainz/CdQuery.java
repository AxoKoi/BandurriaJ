package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import java.util.List;

import org.musicbrainz.controller.ReleaseGroup;
import org.musicbrainz.model.entity.ReleaseWs2;
import org.musicbrainz.model.searchresult.ReleaseGroupResultWs2;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.converter.CdConverter;

@Component
public class CdQuery {
	public static Disc getDiscInfo(String discName) {
		ReleaseWs2 resultFromMBapi = callMB(discName);//= use the api
		CdConverter cdConverter = new CdConverter();
		return cdConverter.convert(resultFromMBapi);
	}

	private static ReleaseWs2 callMB(String discID) {
		ReleaseGroup releaseGroup = new ReleaseGroup();
		releaseGroup.search(discID);
		List<ReleaseGroupResultWs2> apiResult= releaseGroup.getFullSearchResultList();
		List<ReleaseWs2> results = apiResult.get(0).getReleaseGroup().getReleases();
		//todo if not empty
		return results.get(0);

	}
}
