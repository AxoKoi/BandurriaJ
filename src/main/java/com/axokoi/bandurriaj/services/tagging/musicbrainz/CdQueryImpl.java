package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.converter.CdConverter;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.converter.TrackConverter;
import lombok.extern.slf4j.Slf4j;
import org.musicbrainz.MBWS2Exception;
import org.musicbrainz.model.entity.DiscWs2;
import org.musicbrainz.model.entity.ReleaseWs2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Profile("!noInternet")
@Component
public class CdQueryImpl implements CdQuery {
    @Autowired
    CdConverter cdConverter;
    @Autowired
    private TrackConverter trackConverter;

    public Disc getDiscInfo(String discName) {
        throw new UnsupportedOperationException("Not possible to search a disc by its name yet");
    }

    public List<Disc> getDiscInfoById(String id) {
        org.musicbrainz.controller.Disc controller = new org.musicbrainz.controller.Disc();
        try {
            controller.getIncludes().setRecordings(true);
            controller.getIncludes().setArtistCredits(true);

            DiscWs2 disc = controller.lookUp(id, null);

            return disc.getReleases().stream()
                    .map(x -> cdConverter.convert(x))
                    .collect(Collectors.toList());

        } catch (MBWS2Exception e) {
            log.error("Error when looking for disc id: {}", id, e);
        }
        log.error("No cd was found on MusicBrainz");
        return new ArrayList<>();
    }

    @Override
    public List<Disc> lookUpFromDiscId(String discId) {
        org.musicbrainz.controller.Disc controller = new org.musicbrainz.controller.Disc();
        try {
            DiscWs2 disc = controller.lookUp(discId, null);

            return disc.getReleases().stream()
                    .map(x -> cdConverter.convertSimple(x))
                    .collect(Collectors.toList());

        } catch (MBWS2Exception e) {
            log.error("Error when looking for disc id: {}", discId, e);
        }
        log.error("No cd was found on MusicBrainz");
        return new ArrayList<>();
    }

    @Override
    public Optional<Disc> getFullDiscInfoFromUniqueIdentifier(String uniqueId) {
        org.musicbrainz.controller.Release releaseController = new org.musicbrainz.controller.Release();
        releaseController.getIncludes().setRecordings(true);
        releaseController.getIncludes().setArtistCredits(true);

        try {
            ReleaseWs2 complete = releaseController.getComplete(uniqueId);
            return Optional.ofNullable(cdConverter.convert(complete));
        } catch (MBWS2Exception mbws2Exception) {
            log.error("Error when trying to read the release from MusicBrainz. Id={}",uniqueId);
        }
        return Optional.empty();
    }

}
