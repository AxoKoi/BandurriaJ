package com.axokoi.bandurriaj.services.cdreader;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;


@Profile("noCD")
@Component
public class CdReaderMock implements CdReader {

   private static final Random random = new Random();

   /*private final String[] cdIds = {"lmxR.w18EnF0ZiGVY5dDxXMsCpw-",
   "lwHl8fGzJyLXQR33ug60E8jhf4k-",
           "XzPS7vW.HPHsYemQh0HBUGr8vuU-",
           "lEBJIODhbtLhFfWY02.SIfXi4DU-",
           "yVf8PUpOvtUHGeGvop.qXSsTVUQ-",
           "dDZwsvSVNy4YC5Or5htTU5z6NZA-"};
   */private final String[] cdIds = {
           "yVf8PUpOvtUHGeGvop.qXSsTVUQ-"};

   @Override
   public String readId(String driverPath) {
      return cdIds[random.nextInt(cdIds.length)];
   }

   @Override
   public String readToC(String driverPath) {
      return "1 7 164900 150 22460 50197 80614 100828 133318 144712";
   }

}
