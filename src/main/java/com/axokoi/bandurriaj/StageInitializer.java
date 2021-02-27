package com.axokoi.bandurriaj;

import com.axokoi.bandurriaj.model.DiscRepository;
import com.axokoi.bandurriaj.views.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<MainApplication.StageReadyEvent> {

    @Autowired
    private DiscRepository discRepository;

    @Autowired
    private CatalogueView catalogueView;

    @Autowired
    private DiscView discView;
    @Autowired
    private ArtistView artistView;

    @Autowired
    BandView bandView;

    @Autowired
    private SmartSearchView smartSearchView;

    @Autowired
    private DBCreation dbCreation;

    @Autowired
    ViewDispatcher viewDispatcher;

    @Autowired
    MenuBarView menuBarView;

    @Override
    public void onApplicationEvent(MainApplication.StageReadyEvent event) {

        dbCreation.init();
        catalogueView.refresh();

        Stage stage = event.getStage();
        BorderPane mainPane = new BorderPane();
        menuBarView.build(stage);

        VBox center = new VBox();
        center.setPadding(new Insets(5));
        mainPane.setTop(menuBarView);
        mainPane.setLeft(catalogueView);
        mainPane.setCenter(center);
        mainPane.setRight(smartSearchView);
        mainPane.getStyleClass().add("root");

        HBox footerView = new HBox();
        footerView.getChildren().add(new Text("BandurriaJ by Axokoi"));
        mainPane.setBottom(footerView);

        stage.setTitle("BandurriaJ");
        stage.setScene(new Scene(mainPane, 1000, 350));
        stage.sizeToScene();
        new JMetro(stage.getScene(), Style.LIGHT);
        viewDispatcher.setBorderPane(mainPane);
        stage.show();

    }

}
