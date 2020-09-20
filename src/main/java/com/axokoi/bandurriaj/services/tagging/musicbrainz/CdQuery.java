package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.converter.CdConverter;
import lombok.extern.slf4j.Slf4j;
import org.musicbrainz.MBWS2Exception;
import org.musicbrainz.controller.ReleaseGroup;
import org.musicbrainz.model.entity.DiscWs2;
import org.musicbrainz.model.entity.ReleaseGroupWs2;
import org.musicbrainz.model.searchresult.ReleaseGroupResultWs2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CdQuery {
    @Autowired
    CdConverter cdConverter;

    public Disc getDiscInfo(String discName) {
        ReleaseGroupWs2 resultFromMBapi = callMB(discName);//= use the api
        return cdConverter.convert(resultFromMBapi);
    }

    private ReleaseGroupWs2 callMB(String discID) {
        ReleaseGroup releaseGroup = new ReleaseGroup();
        releaseGroup.search(discID);
        List<ReleaseGroupResultWs2> apiResult = releaseGroup.getFullSearchResultList();
        return apiResult.get(0).getReleaseGroup();
    }

    //todo actually this can return a list of Disc!
    public Disc getDiscInfoById(String id) {
        org.musicbrainz.controller.Disc controller = new org.musicbrainz.controller.Disc();
        try {
            DiscWs2 disc = controller.lookUp(id, null);
            return cdConverter.convert(disc.getReleases().get(0).getReleaseGroup());
        } catch (MBWS2Exception e) {
            log.info("Error when looking for disc id: {}", id, e);
        }
        return null;
    }
}
