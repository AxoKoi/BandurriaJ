package com.axokoi.bandurriaj.services.cdreader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CdReadingFacade {

    private final CdReader reader;

    public CdReadingFacade(CdReader reader) {
        this.reader = reader;
    }

    public String readCdId(String driverPath) {
        log.info("Reading from driverPath:[" + driverPath +"]");
        return reader.readId(driverPath);
    }

    public String readToc(String driverPath){
        log.info("Creating ToC from driverPath:[" + driverPath +"]");
        return reader.readToC(driverPath);
    }
}
