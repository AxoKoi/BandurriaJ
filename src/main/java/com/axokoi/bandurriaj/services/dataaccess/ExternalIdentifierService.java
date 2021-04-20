package com.axokoi.bandurriaj.services.dataaccess;

import com.axokoi.bandurriaj.model.ExternalIdentifier;
import com.axokoi.bandurriaj.model.ExternalIdentifierRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ExternalIdentifierService {

   private final ExternalIdentifierRepository externalIdentifierRepository;

   public ExternalIdentifierService(ExternalIdentifierRepository externalIdentifierRepository) {
      this.externalIdentifierRepository = externalIdentifierRepository;
   }

   public List<ExternalIdentifier> getIdentifiersBy(ExternalIdentifier.Type type) {
      throw new UnsupportedOperationException();
   }

   public String getNextUserIdentifier() {
      List<ExternalIdentifier> allByType = externalIdentifierRepository.findAllByType(ExternalIdentifier.Type.USER);
      if (allByType.isEmpty()) {
         return "1";
      }
      allByType.sort(Comparator.comparing(ExternalIdentifier::getIdentifier));
      ExternalIdentifier externalIdentifier = allByType.get(allByType.size() - 1);
      //assumes it's a number!
      return String.valueOf(Integer.parseInt(externalIdentifier.getIdentifier()) + 1);
   }

   public Optional<ExternalIdentifier> findByExternalIdentifier(ExternalIdentifier externalIdentifier) {

      return externalIdentifierRepository.findByTypeAndIdentifier(externalIdentifier.getType(), externalIdentifier.getIdentifier());

   }
}
