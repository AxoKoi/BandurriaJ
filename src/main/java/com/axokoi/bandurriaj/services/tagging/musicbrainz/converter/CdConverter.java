package com.axokoi.bandurriaj.services.tagging.musicbrainz.converter;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Band;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.Track;
import org.musicbrainz.model.TrackWs2;
import org.musicbrainz.model.entity.ReleaseWs2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        Disc disc = buildDisc(release);

        disc.setTracks(getTrackList(release));

        return disc;
    }

    private Disc buildDisc(ReleaseWs2 release) {
        Disc disc = new Disc();
        disc.setName(release.getTitle());
        disc.setBand(new Band());
        disc.getBand().setName(release.getArtistCreditString());
        disc.setComment(release.getYear());
        return disc;
    }

    private List<Track> getTrackList(ReleaseWs2 release) {
        List<TrackWs2> tracks = release.getMediumList().getCompleteTrackList();
        return tracks.stream().map(trackConverter::convert).collect(Collectors.toList());
    }


}
