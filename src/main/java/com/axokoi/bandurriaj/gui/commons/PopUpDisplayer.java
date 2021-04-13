package com.axokoi.bandurriaj.gui.commons;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class PopUpDisplayer {

   private final Stage popUpStage = new Stage();
   private  Scene popupScene;
   private final Pane dummyNode = new Pane();

   public PopUpDisplayer() {
      popupScene = new Scene(dummyNode,500,300);
      popUpStage.setScene(popupScene);
   }

   public void displayNewPopup(Parent toDisplay, Node toHide) {


      popupScene.setRoot(toDisplay);
      //toHide.setDisable(true);
      popUpStage.showAndWait();
      popupScene.setRoot(dummyNode);
     // toHide.setDisable(false);

   }

}
