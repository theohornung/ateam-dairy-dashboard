package application;

import java.io.File;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * Handles importing milk data
 * 
 * TODO add file service param
 */
public class ImportButton extends Button {

	public ImportButton(Stage primaryStage) {
		this.setText("Import");

		// set up the file chooser
		FileChooser importChooser = new FileChooser();
		importChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", ".csv"));

		// creation of functionality to button
		this.setOnAction(e -> {
			File importFile = importChooser.showOpenDialog(primaryStage);
			if (importFile != null) {
				// read file TODO
			}
		});
	}
}
