package com.axokoi.bandurriaj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
@Component
public final class BackupDB implements ApplicationListener<MainApplication.FXApplicationClosedEvent> {


   public static final String BACKUP_TO_BACKUP_ZIP = "BACKUP TO 'backup/backup.zip'";
   private final String username;
   private final String password;
private final String datasourceUrl;

   public BackupDB(@Value("${spring.datasource.username}") String username,
                   @Value("${spring.datasource.password}") String password,
                   @Value("${spring.datasource.url}") String datasourceUrl) {
      this.username = username;
      this.password = password;
      this.datasourceUrl = datasourceUrl;
   }

   @Override
   public void onApplicationEvent(MainApplication.FXApplicationClosedEvent event) {

      log.info("Creating backup");
      try (Connection con = DriverManager.getConnection(datasourceUrl, username, password)) {
         try (var statement = con.prepareStatement(BACKUP_TO_BACKUP_ZIP)) {
            statement.executeUpdate();
         }
      } catch (Exception ex) {
         log.error("Error when creating backup for DB", ex);
      }
   }
}
