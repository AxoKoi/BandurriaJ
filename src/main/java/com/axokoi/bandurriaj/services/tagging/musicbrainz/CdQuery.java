package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import java.util.List;

import org.musicbrainz.controller.ReleaseGroup;
import org.musicbrainz.model.entity.ReleaseGroupWs2;
import org.musicbrainz.model.entity.ReleaseWs2;
import org.musicbrainz.model.searchresult.ReleaseGroupResultWs2;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.converter.CdConverter;

@Component
public class CdQuery {
	public static Disc getDiscInfo(String discName) {
		ReleaseGroupWs2 resultFromMBapi = callMB(discName);//= use the api
		CdConverter cdConverter = new CdConverter();
		return cdConverter.convert(resultFromMBapi);
	}

	private static ReleaseGroupWs2 callMB(String discID) {
		ReleaseGroup releaseGroup = new ReleaseGroup();
		releaseGroup.search(discID);
		List<ReleaseGroupResultWs2> apiResult= releaseGroup.getFullSearchResultList();
		return  apiResult.get(0).getReleaseGroup();


	}
}
