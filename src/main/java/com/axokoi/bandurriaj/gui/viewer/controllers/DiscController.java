package com.axokoi.bandurriaj.gui.viewer.controllers;

import com.axokoi.bandurriaj.gui.commons.PopUpDisplayer;
import com.axokoi.bandurriaj.gui.commons.popups.DeleteDiscWarningPopupController;
import com.axokoi.bandurriaj.gui.editor.controllers.DiscEditorController;
import com.axokoi.bandurriaj.gui.viewer.views.DiscView;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.dataaccess.ArtistService;
import com.axokoi.bandurriaj.services.dataaccess.DiscService;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DiscController extends ViewerController<Disc> {

   @Autowired
   private DiscView discView;
   @Autowired
   private DiscService discService;
   @Autowired
   private ArtistService artistService;
   @Autowired
   private ArtistController artistController;
   @Autowired
   private CatalogueController catalogueController;
   @Autowired
   private DeleteDiscWarningPopupController deleteDiscWarningPopupController;
   @Autowired
   private DiscEditorController discEditorController;

   private final MessagesProvider messagesProvider;
   private final PopUpDisplayer popUpDisplayer;

   public DiscController(MessagesProvider messagesProvider, PopUpDisplayer popUpDisplayer) {
      this.messagesProvider = messagesProvider;
      this.popUpDisplayer = popUpDisplayer;
   }

   @Override
   protected Node getView() {
      return this.discView;
   }

   @Override
   public void refreshView(Disc disc) {
      discView.refresh(disc);
   }

   @Transactional
   public Disc fetchDiscToDisplay(Disc discToDisplay) {
      return discService.findByBusinessIdentifier(discToDisplay.getBusinessIdentifier()).orElseThrow(() -> new RuntimeException("Impossible to retrieve disc from DB:" + discToDisplay.getBusinessIdentifier()));
   }

   public Artist fetchArtistToDisplay(Artist artist) {
      return artistService.findById(artist.getId());
   }

   public void replaceCenterWithArtist(Artist selectedArtist) {
      artistController.displayViewCenter(selectedArtist);

   }

   public void displayEditorPopup(ActionEvent event, Disc disc) {

      discEditorController.refreshView(disc);
      popUpDisplayer.displayNewPopupWithFunction(discEditorController.getView(), this.getView(),
              () -> {
                 discView.refresh(disc);
                 return null;
              });
   }

   public void displayDeletePopup(ActionEvent event, Disc disc) {
      deleteDiscWarningPopupController.refreshView(disc);
      popUpDisplayer.displayNewPopupWithFunction(deleteDiscWarningPopupController.getView(), this.getView(), () -> {
         catalogueController.refreshView();
         var discDeletedLabel = new Label(messagesProvider.getMessageFrom("disc.controller.disc.deleted.successfully", disc.getName()));
         discDeletedLabel.setFont(new Font(discDeletedLabel.getFont().getFamily(),15));
         viewDispatcher.replaceCenterWith(discDeletedLabel);
         return null;
      });
   }
}
