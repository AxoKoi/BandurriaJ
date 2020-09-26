package com.axokoi.bandurriaj.views;

import com.axokoi.bandurriaj.controllers.MenuBarController;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class MenuBarView extends MenuBar {

    @Autowired
    private MenuBarController menuBarController;

    public void build(Stage stage) {
        Menu menu1 = new Menu("File");
        MenuItem menuItem1 = new MenuItem("save");
        menu1.getItems().add(menuItem1);

        Menu menu2 = new Menu("Import");
        MenuItem menu2Item1 = new MenuItem("from CD");


        DirectoryChooser directoryChooser = new DirectoryChooser();

        menu2Item1.setOnAction(e -> {
            File selectedFile = directoryChooser.showDialog(stage);
            menuBarController.handleReadCd(selectedFile);

        });


        menu2.getItems().add(menu2Item1);

        this.getMenus().add(menu1);
        this.getMenus().add(menu2);
    }
}