package com.axokoi.bandurriaj.services;

import com.axokoi.bandurriaj.services.tagging.musicbrainz.CdQuery;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.converter.CdConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@org.springframework.context.annotation.Configuration
@PropertySource("application.properties")
public class TestConfiguration {

    @Bean
    CdQuery cdQuery() {
        return new CdQuery();
    }

    @Bean
    CdConverter cdConverter() {
        return new CdConverter();
    }
}
