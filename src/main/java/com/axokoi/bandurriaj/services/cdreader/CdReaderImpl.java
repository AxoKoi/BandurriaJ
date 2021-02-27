package com.axokoi.bandurriaj.services.cdreader;


import com.axokoi.discid.JMBDiscId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class CdReaderImpl implements CdReader {


    private final String pathToLib;
    public CdReaderImpl(@Value("${PathToLib}") String pathToLib) {
        this.pathToLib = pathToLib;
    }

    public String readId(String driverPath) {
        JMBDiscId discId = new JMBDiscId();
        discId.init(pathToLib);
        return discId.getDiscId(driverPath);

    }


}
