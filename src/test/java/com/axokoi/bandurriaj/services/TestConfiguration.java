package com.axokoi.bandurriaj.services;

import com.axokoi.bandurriaj.services.tagging.musicbrainz.CdQueryImpl;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.converter.CdConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@org.springframework.context.annotation.Configuration
@PropertySource("application.properties")
public class TestConfiguration {

    @Bean
    CdQueryImpl cdQuery() {
        return new CdQueryImpl();
    }

    @Bean
    CdConverter cdConverter() {
        return new CdConverter();
    }
}
