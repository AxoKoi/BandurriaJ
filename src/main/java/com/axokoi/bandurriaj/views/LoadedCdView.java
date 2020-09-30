package com.axokoi.bandurriaj.views;

import com.axokoi.bandurriaj.controllers.LoadedCdController;
import com.axokoi.bandurriaj.model.Catalogue;
import com.axokoi.bandurriaj.model.Disc;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadedCdView extends VBox {

    /*todo continue with this. Once the cd has been loaded, we can
    put this popup to make sur that that's the one that the user want's to load.
    preferably before saving it into DB!
    Also, maybe ask to select which catalogue to save to it. :)

     */
    private static final String WARNING = "Warning! you have more than one result! please select the CD";
    private static final Label warningLabel = new Label(WARNING);
    private final Label cdName = new Label("Your Cd is:");
    private final ComboBox<Disc> cds = new ComboBox<>();
    private final Label catalogueName = new Label("Please choose the catalogue to save the CD");
    private final ComboBox<Catalogue> catalogues = new ComboBox<>();
    private final Button cancelButton = new Button("Cancel");
    private final Button saveButton = new Button("Save");
    private final ButtonBar cancelSaveBar = new ButtonBar();

    @Autowired
    LoadedCdController controller;

    public LoadedCdView() {
        super();
        warningLabel.setVisible(false);
        ButtonBar.setButtonData(cancelButton, ButtonBar.ButtonData.NO);
        ButtonBar.setButtonData(saveButton, ButtonBar.ButtonData.YES);
        cancelSaveBar.getButtons().addAll(cancelButton, saveButton);
        this.getChildren().addAll(warningLabel, cdName, cds, catalogueName, catalogues, cancelSaveBar);
        cancelButton.setOnAction(getCancelHandler());
        saveButton.setOnAction(getSaveHandler());
    }

    private EventHandler<ActionEvent> getSaveHandler() {
        return e -> {
            Disc selectedCd = cds.getSelectionModel().getSelectedItem();
            controller.saveCdOnCatalogue(selectedCd,
                    catalogues.getSelectionModel().getSelectedItem());
            controller.displayCd(selectedCd);
            controller.dispatchRefreshToCatalogue();
            ((Stage) saveButton.getScene().getWindow()).close();
        };

    }

    private EventHandler<ActionEvent> getCancelHandler() {
        return e -> ((Stage) cancelButton.getScene().getWindow()).close();
    }

    public void refresh(List<Disc> loadedCds) {

        if (loadedCds.size() > 1) {
            warningLabel.setVisible(true);
        }
        List<Catalogue> existingCatalogues = controller.getCatalogues();
        catalogues.getItems().clear();
        catalogues.getItems().addAll(existingCatalogues);
        catalogues.setCellFactory(x -> new CatalogueCell());
        //todo can we avoid or refactor these converters. They are needed to display the
        // name of the catalogue in the selected box.
        catalogues.setConverter(new StringConverter<>() {

            @Override
            public String toString(Catalogue catalogue) {
                if (catalogue == null) {
                    return "";
                }
                return catalogue.getName();
            }

            @Override
            public Catalogue fromString(String s) {
                return null;
            }
        });

        cds.getItems().clear();
        cds.getItems().addAll(loadedCds);
        cds.setCellFactory(x -> new CdsCell());
        cds.setConverter(new StringConverter<>() {

            @Override
            public String toString(Disc disc) {
                if (disc == null) {
                    return "";
                }
                return disc.getName();
            }

            @Override
            public Disc fromString(String s) {
                return null;
            }
        });
        Stage popUpStage = new Stage();
        if (this.getScene() == null) {
            new Scene(this, 500, 300);
        }
        popUpStage.setScene(this.getScene());
        popUpStage.show();

    }

    //todo we can extracts these cells into a generic one
    static class CatalogueCell extends ListCell<Catalogue> {
        @Override
        protected void updateItem(Catalogue catalogue, boolean empty) {
            super.updateItem(catalogue, empty);
            setText(catalogue == null ? "" : catalogue.getName());
        }
    }

    static class CdsCell extends ListCell<Disc> {
        @Override
        protected void updateItem(Disc disc, boolean empty) {
            super.updateItem(disc, empty);
            setText(disc == null ? "" : disc.getName());
        }
    }
}
