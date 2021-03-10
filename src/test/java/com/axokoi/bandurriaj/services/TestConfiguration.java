package com.axokoi.bandurriaj.services;

import com.axokoi.bandurriaj.services.tagging.musicbrainz.CdQueryImpl;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.converter.CdConverter;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.converter.TrackConverter;
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
    CdConverter cdConverter(TrackConverter trackConverter) {
        return new CdConverter(trackConverter);
    }
}
