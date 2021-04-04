package com.axokoi.bandurriaj.services.cdreader;


import com.axokoi.discid.JMBDiscId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("!noCD")
class CdReaderImpl implements CdReader {


    private final String pathToLib;
    public CdReaderImpl(@Value("${PathToLib}") String pathToLib) {
        this.pathToLib = pathToLib;
    }

    public String readId(String driverPath) {
        JMBDiscId discId = new JMBDiscId();

        log.info("Initiating library at :[" + pathToLib + "]");
        discId.init(pathToLib);

        return discId.getDiscId(driverPath);
    }


}
