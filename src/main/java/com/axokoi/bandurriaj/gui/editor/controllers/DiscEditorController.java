package com.axokoi.bandurriaj.gui.editor.controllers;

import com.axokoi.bandurriaj.gui.commons.PopUpDisplayer;
import com.axokoi.bandurriaj.gui.editor.views.DiscEditorView;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.Track;
import com.axokoi.bandurriaj.services.dataaccess.DiscService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscEditorController extends EditorController<Disc> {

   @Autowired
   private DiscEditorView discEditorView;
   private final DiscService discService;
   private final PopUpDisplayer popUpDisplayer;
   @Autowired
   private ArtistEditorController artistEditorController;
   @Autowired
   private TrackEditorController trackEditorController;

   public DiscEditorController(DiscService discService, PopUpDisplayer popUpDisplayer) {
      this.discService = discService;
      this.popUpDisplayer = popUpDisplayer;
   }

   @Override
   public DiscEditorView getView() {
      return discEditorView;
   }

   @Override
   public void refreshView(Disc disc) {
      discEditorView.refresh(disc);
   }

   @Override
   public void onCancel(ActionEvent event) {

   }

   @Override
   public void onSave(ActionEvent event) {
      discService.save(discEditorView.getEntityToEdit());
   }

   public void displayArtistEditor(Event event) {
      Artist artistToEdit = ((ListView<Artist>) event.getSource()).getSelectionModel().getSelectedItem();
      artistEditorController.refreshView(artistToEdit);
      popUpDisplayer.displayNewPopupWithFunction(artistEditorController.getView(), null,
              () -> {
                 this.refreshView(this.getView().getEntityToEdit());
                 return null;
              }
      );
   }

   public void displayTrackEditor(Event event) {
      Track trackToEdit = ((ListView<Track>) event.getSource()).getSelectionModel().getSelectedItem();
      trackEditorController.refreshView(trackToEdit);
      popUpDisplayer.displayNewPopupWithFunction(trackEditorController.getView(), null,
              () -> {
                 this.refreshView(this.getView().getEntityToEdit());
                 return null;
              }
      );

   }
}
