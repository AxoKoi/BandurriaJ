package com.axokoi.bandurriaj.services.cdreader;


import org.musicbrainz.discid.JMBDiscId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class CdReader {


    private final String pathToLib;
    public CdReader(@Value("${PathToLib}") String pathToLib) {
        this.pathToLib = pathToLib;
    }

    String readId(String driverPath) {
        JMBDiscId discId = new JMBDiscId();
        discId.init(pathToLib);
        return discId.getDiscId(driverPath);

    }


}
