package com.axokoi.bandurriaj.booting;

import com.axokoi.bandurriaj.model.UserConfiguration;
import com.axokoi.bandurriaj.services.dataaccess.UserConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Slf4j
@Component
public class UserConfigurationLoader {

   private final UserConfigurationService service;

   public UserConfigurationLoader(UserConfigurationService service) {
      this.service = service;
   }

   @EventListener
   public void load(ApplicationStartedEvent event) {
      Optional<String> locale = service.findValueByKey(UserConfiguration.Keys.LOCALE);
      locale.ifPresent(l -> Locale.setDefault(LocaleUtils.toLocale(l)));
      locale.ifPresent(l->log.info("User Language configuration:"+l));
   }
}
