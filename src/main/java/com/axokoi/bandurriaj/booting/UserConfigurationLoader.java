package com.axokoi.bandurriaj.booting;

import com.axokoi.bandurriaj.model.UserConfiguration;
import com.axokoi.bandurriaj.model.UserConfigurationRepository;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.util.Locale;
import java.util.Optional;

public class UserConfigurationLoader {

   private final UserConfigurationRepository repository;

   public UserConfigurationLoader(UserConfigurationRepository repository) {
      this.repository = repository;
   }

   @EventListener
   public void load(ApplicationStartedEvent event) {
      Optional<String> language = repository.findByKey(UserConfiguration.Keys.LOCALE.getValue());

      language.ifPresent(locale -> Locale.setDefault(LocaleUtils.toLocale(locale)));
   }
}
