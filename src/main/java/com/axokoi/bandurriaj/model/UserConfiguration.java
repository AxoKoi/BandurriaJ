package com.axokoi.bandurriaj.model;

import javax.persistence.*;

@Entity
public class UserConfiguration {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long id;

   @Column(unique = true)
   private String key;

   public UserConfiguration() {
      //For JPA
   }


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getKey() {
      return key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   private String value;


   public UserConfiguration(Keys key, String value) {
      this.key = key.getValue();
      this.value = value;
   }

   public enum Keys{
      LOCALE("user.language"),
      PREFERRED_CD_DRIVER_PATH("user.preferred.driver.path");

      private final String value;
      Keys(String value) {
         this.value = value;
      }

      public String getValue(){
         return this.value;
      }
   }

}
