package com.axokoi.bandurriaj.services.dataaccess;

import com.axokoi.bandurriaj.model.UserConfiguration;
import com.axokoi.bandurriaj.model.UserConfigurationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UserConfigurationService {

   private final UserConfigurationRepository repository;

   public UserConfigurationService(UserConfigurationRepository repository) {
      this.repository = repository;
   }

   public void saveConfiguration(UserConfiguration.Keys key, String value) {

      UserConfiguration configurationToPersist = repository.findByKey(key.getValue())
              .orElse(new UserConfiguration(key, value));

      repository.save(configurationToPersist);
   }

   public Optional<String> findValueByKey(UserConfiguration.Keys key) {
      Optional<UserConfiguration> entry = repository.findByKey(key.getValue());
      return entry.map(UserConfiguration::getValue);
   }

   public void saveLocale(String localeAsString) {
      this.saveConfiguration(UserConfiguration.Keys.LOCALE, localeAsString);
   }
}
