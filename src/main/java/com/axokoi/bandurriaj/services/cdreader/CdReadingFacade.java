package com.axokoi.bandurriaj.services.cdreader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CdReadingFacade {

    @Autowired
    CdReader reader;

    public String readCdId(String driverPath) {
        return reader.readId(driverPath);
    }
}
