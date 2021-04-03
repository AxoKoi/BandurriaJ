package com.axokoi.bandurriaj.controllers;

import com.axokoi.bandurriaj.model.Disc;
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

@Slf4j
@Component
public class MenuBarController {

    private final CdReadingFacade cdReadingFacade;
    private final TaggingFacade taggingFacade;
    private final LoadedCdController loadedCdController;
    private final UserConfigurationService userConfigurationService;

    public MenuBarController(CdReadingFacade cdReadingFacade, TaggingFacade taggingFacade, LoadedCdController loadedCdController, UserConfigurationService userConfigurationService) {
        this.cdReadingFacade = cdReadingFacade;
        this.taggingFacade = taggingFacade;
        this.loadedCdController = loadedCdController;
       this.userConfigurationService = userConfigurationService;
    }

   public void changeLocale(String language) {
       Locale.Builder localeBuilder = new Locale.Builder().setRegion(Locale.getDefault().getCountry());
       switch (language){
          case "EN":
             Locale.setDefault(localeBuilder.setLanguage("en").build());
             break;

          case "FR":
             Locale.setDefault(localeBuilder.setLanguage("fr").build());
             break;

          default:
             throw new IllegalArgumentException("Not Supported language");
       }
       //todo display popup.
      displayRebootPopup();
      userConfigurationService.saveLocale(Locale.getDefault().toString());
    }

   private void displayRebootPopup() {

      Stage popUpStage = new Stage();

      Label infoLabel = new Label("A reboot of BandurriaJ is needed to apply your changes.");
      Button okButton = new Button("Ok");
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



   static class FileToCDPathConverter {

        private FileToCDPathConverter(){}

        static String convert(File file) {
            String rawPath = file.getPath();
            //todo we need to figure it out how can we better handle this.
            //maybe take the last sr0?for linux
            if (SystemUtils.IS_OS_WINDOWS) {
                return "";
                //todo we have the spring profiles,
                // todo Maybe add a detection if profile is isolated not throw new UnsupportedOperationException("Windows not yet supported");

            } else if (SystemUtils.IS_OS_LINUX) {
                return "/dev/cdrom";
            } else {
                throw new UnsupportedOperationException("Other SO not yet supported");
            }
        }
    }
}
