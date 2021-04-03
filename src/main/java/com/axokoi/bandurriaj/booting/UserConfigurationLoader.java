package com.axokoi.bandurriaj.booting;

import com.axokoi.bandurriaj.model.UserConfiguration;
import com.axokoi.bandurriaj.model.UserConfigurationRepository;
import com.axokoi.bandurriaj.services.dataaccess.UserConfigurationService;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.util.Locale;
import java.util.Optional;

public class UserConfigurationLoader {

   private final UserConfigurationService service;

   public UserConfigurationLoader(UserConfigurationService service) {
      this.service = service;
   }

   @EventListener
   public void load(ApplicationStartedEvent event) {
      Optional<String> locale = service.findValueByKey(UserConfiguration.Keys.LOCALE);
      locale.ifPresent(l -> Locale.setDefault(LocaleUtils.toLocale(l)));
   }
}
