package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.tagging.ProviderFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MusicBrainzFacade implements ProviderFacade {

    @Autowired
    CdQueryImpl cdQueryImpl;

    @Override
    public Disc getDiscInfo(String discName) {
        return cdQueryImpl.getDiscInfo(discName);
    }

    @Override
    public List<Disc> getDiscInfoFromDiscId(String id) {
        return cdQueryImpl.getDiscInfoById(id);
    }
}
