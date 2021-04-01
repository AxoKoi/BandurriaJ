package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.tagging.ProviderFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class MusicBrainzFacade implements ProviderFacade {

    private final CdQuery cdQuery;

    public MusicBrainzFacade(CdQuery cdQuery) {
        this.cdQuery = cdQuery;
    }

    public List<Disc> getDiscInfoFromDiscId(String id) {
        log.info("Retrieving disc information with MusicBrainz. Id=" + id);
        return cdQuery.getDiscInfoById(id);
    }

    @Override
    public Optional<Disc> getFullDiscInfoFromUniqueIdentifier(String providerIdentifier) {
        return cdQuery.getFullDiscInfoFromUniqueIdentifier(providerIdentifier);
    }

    @Override
    public List<Disc> lookUpFromDiscId(String discId) {
        return cdQuery.lookUpFromDiscId(discId);
    }


}
