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

   public PopUpDisplayer() {

   }

/*   public void displayNewPopup(Parent toDisplay, Node toHide) {


      popupScene.setRoot(toDisplay);
      //toHide.setDisable(true);
      popUpStage.showAndWait();
      popupScene.setRoot(dummyNode);
      // toHide.setDisable(false);

   }*/
//IRO The supplier solution is not the best.
   public void displayNewPopupWithFunction(Parent toDisplay, Node toHide, Supplier<Void> function) {
      final Stage popUpStage = new Stage();
      Scene popupScene;
      Pane dummyNode = new Pane();

      popupScene = new Scene(dummyNode, 250 * 3, 250 * 3);

      popUpStage.setScene(popupScene);
      popupScene.setRoot(toDisplay);
      //toHide.setDisable(true);
      popUpStage.showAndWait();
      popupScene.setRoot(dummyNode);
      function.get();
      // toHide.setDisable(false);

   }

}
