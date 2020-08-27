package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import application.model.Artist;
import application.model.Band;
import application.model.Disc;
import application.model.DiscRepository;
import application.model.GroupRepository;
import application.model.Track;
import application.views.DiscView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

@Component
public class StageInitializer implements ApplicationListener<MainApplication.StageReadyEvent> {

	@Autowired
	private DiscRepository discRepository;
	@Autowired
	private GroupRepository groupRepository;

	@Override
	public void onApplicationEvent(MainApplication.StageReadyEvent event) {
		Stage stage = event.getStage();
		buildEntities();

		MenuBar menuBar = getMenuBar();
		VBox mainBox = new VBox(menuBar);

		stage.setTitle("BandurriaJ");
		VBox vboxLeft = new VBox();

		FileChooser fileChooser = new FileChooser();

		Button button = new Button("Select File");
		button.setOnAction(e -> {
			File selectedFile = fileChooser.showOpenDialog(stage);
		});

		TreeView<String> treeView = getTreeView();

		vboxLeft.getChildren().add(button);
		vboxLeft.getChildren().add(treeView);

		VBox vboxRight = new DiscView(discRepository.findAll().iterator().next());
		HBox hbox = new HBox(vboxLeft, vboxRight);
		mainBox.getChildren().add(hbox);
		hbox.setPrefWidth(300);

		stage.setScene(new Scene(mainBox, 800, 500));
		stage.show();
	}

	private void buildEntities() {
		Disc testDisc = new Disc();
		testDisc.setName("test disc Name");

		Band band = new Band();
		band.setName("group name");
		band.setComment("");
		Artist artist = new Artist();
		artist.setName("this is the artist name");
		List<Artist> artists = new ArrayList<>();
		artists.add(artist);
		band.setArtists(artists);
		testDisc.setBand(band);

		Track track = new Track();
		track.setName("Track 1");
		List<Track> tracks = new ArrayList<>();
		testDisc.setTracks(tracks);
		groupRepository.save(band);
		discRepository.save(testDisc);
	}

	private MenuBar getMenuBar() {
		MenuBar menuBar = new MenuBar();
		Menu menu1 = new Menu("menu1");
		MenuItem menuItem1 = new MenuItem("Item1");
		MenuItem menuItem2 = new MenuItem("Item2");
		menu1.getItems().add(menuItem1);
		menu1.getItems().add(menuItem2);
		menuBar.getMenus().add(menu1);
		return menuBar;
	}

	private TreeView<String> getTreeView() {
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
		return treeView;
	}

}
