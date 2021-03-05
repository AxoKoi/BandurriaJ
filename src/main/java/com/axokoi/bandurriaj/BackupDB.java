package com.axokoi.bandurriaj;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Component
public final class BackupDB implements ApplicationListener<MainApplication.FXApplicationClosedEvent> {


   @Override
   public void onApplicationEvent(MainApplication.FXApplicationClosedEvent event) {
//IRO obtain the values from properties
         try{
            System.out.println("Creating backup");
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection("jdbc:h2:C:/data/sampledata", "sa", "" );
            Statement stmt = con.createStatement();
            con.prepareStatement("BACKUP TO 'backup.zip'").executeUpdate();

         }catch(Exception ex){
            System.out.println(ex);
         }

   }
}
