package com.axokoi.bandurriaj.services;

import com.axokoi.bandurriaj.services.tagging.musicbrainz.CdQueryImpl;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.converter.ArtistConverter;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.converter.CdConverter;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.converter.TrackConverter;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.imageQuery.ImageQueryImpl;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.imageQuery.ReleaseImageQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

//IRO do we need this?
@org.springframework.context.annotation.Configuration
@PropertySource("application.properties")
public class TestConfiguration {

    @Bean
    CdQueryImpl cdQuery() {
        return new CdQueryImpl();
    }

    @Bean
    TrackConverter trackConverter(){
        return new TrackConverter();
    }

    @Bean
    ArtistConverter artistConverter(){
        return new ArtistConverter();
    }

    @Bean
    ReleaseImageQuery releaseImageQuery(){return new ImageQueryImpl("http://coverartarchive.org/release/{identifier}/front", "/images");
    }

    @Bean
    CdConverter cdConverter(TrackConverter trackConverter, ArtistConverter artistConverter,
                            ReleaseImageQuery releaseImageQuery) {
        return new CdConverter(trackConverter, artistConverter, releaseImageQuery);
    }
}
