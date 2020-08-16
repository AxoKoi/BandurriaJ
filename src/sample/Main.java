package sample;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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



		vboxLeft.getChildren().add(button);
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
