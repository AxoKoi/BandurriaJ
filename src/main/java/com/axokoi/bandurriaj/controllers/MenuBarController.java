package com.axokoi.bandurriaj.controllers;

import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.UserConfiguration;
import com.axokoi.bandurriaj.services.cdreader.CdReadingFacade;
import com.axokoi.bandurriaj.services.dataaccess.UserConfigurationService;
import com.axokoi.bandurriaj.services.tagging.TaggingFacade;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Component
public class MenuBarController {

    private final CdReadingFacade cdReadingFacade;
    private final TaggingFacade taggingFacade;
    private final LoadedCdController loadedCdController;
    private final UserConfigurationService userConfigurationService;
   private final MessagesProvider messagesProvider;

    public MenuBarController(CdReadingFacade cdReadingFacade, TaggingFacade taggingFacade, LoadedCdController loadedCdController, UserConfigurationService userConfigurationService, MessagesProvider messagesProvider) {
        this.cdReadingFacade = cdReadingFacade;
        this.taggingFacade = taggingFacade;
        this.loadedCdController = loadedCdController;
        this.userConfigurationService = userConfigurationService;
        this.messagesProvider = messagesProvider;
    }

   public void changeLocale(String language) {
      displayRebootPopup();
       Locale.Builder localeBuilder = new Locale.Builder().setRegion(Locale.getDefault().getCountry());
       switch (language){
          case "EN":
             localeBuilder.setLanguage("en");
             break;

          case "FR":
             localeBuilder.setLanguage("fr");
             break;

          case "ES":
             localeBuilder.setLanguage("es");
             break;

          default:
             throw new IllegalArgumentException("Not Supported language");
       }

      Locale locale = localeBuilder.build();
      userConfigurationService.saveLocale(locale.toString());
    }

   private void displayRebootPopup() {

      Stage popUpStage = new Stage();

      Label infoLabel = new Label(messagesProvider.getMessageFrom("menubar.controller.language.reboot"));
      Button okButton = new Button("OK");
      okButton.setOnMouseClicked(e -> ((Stage) okButton.getScene().getWindow()).close());

      VBox vbox = new VBox(infoLabel, okButton);
      vbox.setAlignment(Pos.CENTER);
      Scene popUpScene = new Scene(vbox, 300, 100);
      popUpStage.setScene(popUpScene);
      popUpStage.show();
   }

    public void handleReadCd(File selectedFile) {
        String cdId = cdReadingFacade.readCdId(FileToCDPathConverter.convert(selectedFile));
        log.info("Read cdId: {}", cdId);
        List<Disc> loadedCds = taggingFacade.lookupFromDiscId(cdId);
        log.info("Cd tagged was: {}", loadedCds);
        loadedCdController.refreshView(loadedCds);
    }

   public Optional<String> getUserPreferredPath() {
      return userConfigurationService.findValueByKey(UserConfiguration.Keys.PREFERRED_CD_DRIVER_PATH);
    }

   public void savePathToPreferredDriver(String absolutePath) {
         userConfigurationService.saveConfiguration(UserConfiguration.Keys.PREFERRED_CD_DRIVER_PATH,absolutePath);
    }


   static class FileToCDPathConverter {

        private FileToCDPathConverter(){}

        static String convert(File file) {
            if (SystemUtils.IS_OS_WINDOWS) {
                return file.getAbsolutePath();
            } else if (SystemUtils.IS_OS_LINUX) {
                return "/dev/cdrom";
            } else {
                throw new UnsupportedOperationException("Other SO not yet supported");
            }
        }
    }
}
