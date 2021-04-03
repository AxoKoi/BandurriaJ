package com.axokoi.bandurriaj.model;

import javax.persistence.*;

@Entity
public class UserConfiguration {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @Column(unique = true)
   String key;

   String value;

   public enum Keys{
      LOCALE("user.language");

      private String value;
      Keys(String value) {
         this.value = value;
      }

      public String getValue(){
         return this.value;
      }
   }

}
