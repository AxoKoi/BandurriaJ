package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.tagging.ProviderFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MusicBrainzFacade implements ProviderFacade {

    @Autowired
    CdQuery cdQuery;

    @Override
    public Disc getDiscInfo(String discName) {
        return cdQuery.getDiscInfo(discName);
    }

    @Override
    public Disc
    getDiscInfoFromDiscId(String id) {
        return cdQuery.getDiscInfoById(id);
    }
}
