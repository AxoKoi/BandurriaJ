package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.converter.CdConverter;
import lombok.extern.slf4j.Slf4j;
import org.musicbrainz.MBWS2Exception;
import org.musicbrainz.controller.ReleaseGroup;
import org.musicbrainz.model.entity.DiscWs2;
import org.musicbrainz.model.entity.ReleaseGroupWs2;
import org.musicbrainz.model.entity.ReleaseWs2;
import org.musicbrainz.model.searchresult.ReleaseGroupResultWs2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Profile("prod")
@Component
public class CdQueryImpl implements CdQuery {
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

    public List<Disc> getDiscInfoById(String id) {
        org.musicbrainz.controller.Disc controller = new org.musicbrainz.controller.Disc();
        try {
            DiscWs2 disc = controller.lookUp(id, null);
            return disc.getReleases().stream()
                    .map(ReleaseWs2::getReleaseGroup)
                    .map(x -> cdConverter.convert(x))
                    .collect(Collectors.toList());

        } catch (MBWS2Exception e) {
            log.info("Error when looking for disc id: {}", id, e);
        }
        return new ArrayList<>();
    }
}
