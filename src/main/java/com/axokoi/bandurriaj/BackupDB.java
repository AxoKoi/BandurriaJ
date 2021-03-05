package com.axokoi.bandurriaj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public final class BackupDB implements ApplicationListener<MainApplication.FXApplicationClosedEvent> {


   public static final String BACKUP_TO_BACKUP_ZIP = "BACKUP TO 'backup.zip'";
   private final String username;
   private final String password;

   public BackupDB(@Value("${spring.datasource.username}") String username,
                   @Value("${spring.datasource.password}")String password) {
      this.username = username;
      this.password = password;
   }

   @Override
   public void onApplicationEvent(MainApplication.FXApplicationClosedEvent event) {

      System.out.println("Creating backup");
      try (Connection con = DriverManager.getConnection("jdbc:h2:C:/data/sampledata", username, password)) {
         try (var statement = con.prepareStatement(BACKUP_TO_BACKUP_ZIP)) {
            statement.executeUpdate();
         }
      } catch (Exception ex) {
         System.out.println(ex);
      }
   }
}
