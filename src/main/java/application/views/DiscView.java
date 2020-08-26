package application.views;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import application.model.Disc;

public class DiscView extends VBox {

	private final Disc discToDisplay;

	public DiscView(Disc discToDisplay) {
		this.discToDisplay = discToDisplay;
		this.build();
	}

	private void build() {
		this.getChildren().add(new Label("Displaying disc:" + discToDisplay.getName()));
	}

}
