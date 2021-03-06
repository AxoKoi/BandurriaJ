package com.axokoi.bandurriaj.services.cdreader;


import com.axokoi.discid.JMBDiscId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
@Profile("!noCD")
class CdReaderImpl implements CdReader {


   private final String pathToLib;
   private final JMBDiscId jmbDiscId;

   public CdReaderImpl(@Value("${PathToLib}") String pathToLib) {
      this.pathToLib = pathToLib;
      jmbDiscId = getJmbDiscId();
   }

   public String readId(String driverPath) {
      return jmbDiscId.getDiscId(driverPath);
   }

   @Override
   public String readToC(String driverPath) {
      return jmbDiscId.getToC(driverPath);
   }

   private JMBDiscId getJmbDiscId() {
      JMBDiscId discId = new JMBDiscId();
      String relativePath = (new File("")).getAbsolutePath();
      log.info("Initiating library at :[" + relativePath + pathToLib + "]");
      String extension = SystemUtils.IS_OS_WINDOWS ? ".dll" : ".so";
      discId.init(relativePath + pathToLib + extension);
      return discId;
   }




}
