package com.axokoi.bandurriaj.gui.viewer.controllers;

import com.axokoi.bandurriaj.gui.commons.PopUpDisplayer;
import com.axokoi.bandurriaj.gui.editor.controllers.ArtistEditorController;
import com.axokoi.bandurriaj.gui.editor.views.ArtistEditorView;
import com.axokoi.bandurriaj.gui.viewer.views.ArtistView;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.dataaccess.ArtistService;
import com.axokoi.bandurriaj.services.dataaccess.DiscService;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArtistController extends ViewerController<Artist> {

   @Autowired
   private ArtistService artistService;

   @Autowired
   private DiscService discService;

   @Autowired
   private ArtistView artistView;

   @Autowired
   private DiscController discController;
   @Autowired
   private  ArtistEditorController artistEditorController;
   private final PopUpDisplayer popUpDisplayer;

	public ArtistController(PopUpDisplayer popUpDisplayer) {
		this.popUpDisplayer = popUpDisplayer;
	}

	@Override
   public void refreshView(Artist artist) {
      artistView.refresh(artistService.findById(artist.getId()));
   }

   @Override
   protected Node getView() {
      return this.artistView;
   }

   public List<Disc> findAllDiscByArtist(Artist artist) {
      return discService.findAllDiscByArtist(artist);
   }

   public void replaceCenterWithDisc(Disc selectedItem) {
      discController.displayViewCenter(selectedItem);
   }

   public void displayEditorPopup(ActionEvent event, Artist artist) {

      artistEditorController.refreshView(artist);
      ArtistEditorView view = artistEditorController.getView();
      popUpDisplayer.displayNewPopup(view,null);
   }
}
