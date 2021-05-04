package com.axokoi.bandurriaj.gui.viewer.controllers;

import com.axokoi.bandurriaj.gui.commons.PopUpDisplayer;
import com.axokoi.bandurriaj.gui.commons.popups.ReadingDiscPopupView;
import com.axokoi.bandurriaj.gui.commons.popups.TaggingDiscPopupView;
import com.axokoi.bandurriaj.gui.viewer.views.MenuBarView;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.Track;
import com.axokoi.bandurriaj.model.UserConfiguration;
import com.axokoi.bandurriaj.services.cdreader.CdReadingFacade;
import com.axokoi.bandurriaj.services.cdreader.ToCStringToListOfTracksConverter;
import com.axokoi.bandurriaj.services.dataaccess.UserConfigurationService;
import com.axokoi.bandurriaj.services.tagging.TaggingFacade;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;


@Slf4j
@Component
public class MenuBarController {

    private final CdReadingFacade cdReadingFacade;
    private final TaggingFacade taggingFacade;
    private final LoadedCdController loadedCdController;
    private final UserConfigurationService userConfigurationService;
    private final MessagesProvider messagesProvider;
    private final PopUpDisplayer popUpDisplayer;
    private final ThreadPoolTaskExecutor executorService;
    private final ReadingDiscPopupView readingDiscPopupView;
    private final TaggingDiscPopupView taggingDiscPopupView;
    private final ToCStringToListOfTracksConverter tocConverter;
    @Autowired
    private MenuBarView menuBarView;

    public MenuBarController(CdReadingFacade cdReadingFacade, TaggingFacade taggingFacade, LoadedCdController loadedCdController, UserConfigurationService userConfigurationService, MessagesProvider messagesProvider, PopUpDisplayer popUpDisplayer, ThreadPoolTaskExecutor executorService, ReadingDiscPopupView readingDiscPopupView, TaggingDiscPopupView taggingDiscPopupView, ToCStringToListOfTracksConverter tocConverter) {
        this.cdReadingFacade = cdReadingFacade;
        this.taggingFacade = taggingFacade;
        this.loadedCdController = loadedCdController;
        this.userConfigurationService = userConfigurationService;
        this.messagesProvider = messagesProvider;
       this.popUpDisplayer = popUpDisplayer;
       this.executorService = executorService;
       this.readingDiscPopupView = readingDiscPopupView;
       this.taggingDiscPopupView = taggingDiscPopupView;
       this.tocConverter = tocConverter;
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
      //Submit the task for reading the cd in the background
       Future<?> futureDiscId = executorService.submit(() -> {

          String cdId = cdReadingFacade.readCdId(FileToCDPathConverter.convert(selectedFile));
          log.info("Read cdId: {}", cdId);
          Platform.runLater(()->((Stage)readingDiscPopupView.getScene().getWindow()).close());
          return cdId;
       });

       popUpDisplayer.displayNewPopupWithFunction(readingDiscPopupView, null, () -> null);

        //Submit the task for tagging the cd in the background
       Future<?> futureTaggedDiscs = executorService.submit(() -> {

          String computedDiscId = (String) futureDiscId.get();
          List<Disc> loadedCds = taggingFacade.lookupFromDiscId(computedDiscId);
          log.info("Cd tagged was: {}", loadedCds);
          Platform.runLater(()->((Stage)taggingDiscPopupView.getScene().getWindow()).close());
          return loadedCds;
       });

       popUpDisplayer.displayNewPopupWithFunction(taggingDiscPopupView, null, () -> null);
       //Populate the disc list with the result from the background task
       List<Disc> loadedCds = Collections.emptyList();
       try {
          loadedCds = (List<Disc>) futureTaggedDiscs.get();
          if(loadedCds.isEmpty()){
             Disc untaggedDisc = buildUntaggedDisc(selectedFile, (String) futureDiscId.get());
             loadedCds.add(untaggedDisc);
          }
       } catch (InterruptedException | ExecutionException e) {
          log.error("Error reading the cd from driver:"+selectedFile,e);
          Thread.currentThread().interrupt();
       }

       loadedCdController.refreshView(loadedCds);
    }

   private Disc buildUntaggedDisc(File selectedFile, String discID) {
      String toc = cdReadingFacade.readToc(FileToCDPathConverter.convert(selectedFile));
      List<Track> tracks = tocConverter.convert(toc);
      var untaggedDisc = new Disc();
      untaggedDisc.setDiscId(discID);
      untaggedDisc.setName("Disc");
      untaggedDisc.setTracks(new HashSet<>(tracks));
      return untaggedDisc;
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
