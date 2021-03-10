package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.tagging.ProviderFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MusicBrainzFacade implements ProviderFacade {

    private final CdQuery cdQuery;

    public MusicBrainzFacade(CdQuery cdQuery) {
        this.cdQuery = cdQuery;
    }

    @Override
    public Disc getDiscInfo(String discName) {
        return cdQuery.getDiscInfo(discName);
    }

    @Override
    public List<Disc> getDiscInfoFromDiscId(String id) {
        log.info("Retrieving disc information with MusicBrainz. Id=" + id);
        return cdQuery.getDiscInfoById(id);
    }
}
