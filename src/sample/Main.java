package sample;

import java.io.File;

import org.apache.poi.ss.formula.functions.T;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Hello World");
		VBox vboxLeft = new VBox();

		FileChooser fileChooser = new FileChooser();

		Button button = new Button("Select File");
		button.setOnAction(e -> {
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
		});

		TreeView<String> treeView = new TreeView<>();
		TreeItem<String> rootItem = new TreeItem<>("Catalogues");

		TreeItem<String> catalogue1 = new TreeItem<>("Catalogue 1");
		catalogue1.getChildren().add(new TreeItem<>("CD1"));
		catalogue1.getChildren().add(new TreeItem<>("CD2"));

		TreeItem<String> catalogue2 = new TreeItem<>("Catalogue 2");
		catalogue2.getChildren().add(new TreeItem<>("CD1"));
		catalogue2.getChildren().add(new TreeItem<>("CD2"));

		rootItem.getChildren().addAll(catalogue1, catalogue2);
		treeView.setRoot(rootItem);

		vboxLeft.getChildren().add(button);
		vboxLeft.getChildren().add(treeView);

		VBox vboxRight = new VBox();
		vboxRight.getChildren().add(new Button("RightBox"));
		HBox hbox = new HBox(vboxLeft, vboxRight);
		hbox.setPrefWidth(300);
		primaryStage.setScene(new Scene(hbox, 800, 500));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
