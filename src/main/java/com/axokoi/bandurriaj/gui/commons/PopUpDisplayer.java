package com.axokoi.bandurriaj.gui.commons;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class PopUpDisplayer {

   /**
    * A component to handle all the popups needed in the application.
    *
    * @param toDisplay The Parent view that will be displayed in the popup
    * @param toHide    The Node that needs to be disabled. By default it will get its corresponding scene and disable the root
    * @param function  a void function to be called when the popup is closed
    */
   public void displayNewPopupWithFunction(Parent toDisplay, Node toHide, Supplier<Void> function) {
      final Stage popUpStage = new Stage();
      Scene popupScene;
      Pane dummyNode = new Pane();

      popupScene = new Scene(dummyNode, 250 * 3, 250 * 3);

      popUpStage.setScene(popupScene);
      popupScene.setRoot(toDisplay);

      if (toHide != null) {
         toHide.getScene().getRoot().setDisable(true);
      }

      popUpStage.showAndWait();

      if (toHide != null) {
         toHide.getScene().getRoot().setDisable(false);
      }
      popupScene.setRoot(dummyNode);
      function.get();
   }

}
