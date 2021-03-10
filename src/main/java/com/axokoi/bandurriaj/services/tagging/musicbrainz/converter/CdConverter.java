package com.axokoi.bandurriaj.services.tagging.musicbrainz.converter;

import com.axokoi.bandurriaj.model.Band;
import com.axokoi.bandurriaj.model.Disc;
import org.musicbrainz.controller.Release;
import org.musicbrainz.model.TrackWs2;
import org.musicbrainz.model.entity.ReleaseGroupWs2;
import org.musicbrainz.model.entity.ReleaseWs2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CdConverter implements Converter<ReleaseWs2, Disc> {

    private final TrackConverter trackConverter;

    public CdConverter(TrackConverter trackConverter) {
        this.trackConverter = trackConverter;
    }

    @Override
    public Disc convert(ReleaseWs2 release) {
        Disc disc = new Disc();
        disc.setName(release.getTitle());
        disc.setBand(new Band());
        disc.getBand().setName(release.getArtistCreditString());
        disc.setComment(release.getYear());

        List<TrackWs2> tracks = release.getMediumList().getCompleteTrackList();
        disc.setTracks(tracks.stream().map(trackConverter::convert).collect(Collectors.toList()));

        return disc;
    }
}
