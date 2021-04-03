package com.axokoi.bandurriaj.i18n;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Component
public class MessagesProvider {

   private final MessageSource messageSource;

   public MessagesProvider(MessageSource messageSource) {
      this.messageSource = messageSource;
   }

   public String getMessageFrom(String key, Object... args) {
      return messageSource.getMessage(key, args, Locale.getDefault());
   }



}
