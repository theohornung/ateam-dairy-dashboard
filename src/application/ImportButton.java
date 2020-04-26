package application;

import java.io.File;

import application.interfaces.IFileService;
import application.interfaces.IMilkList;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * Handles importing milk data
 */
public class ImportButton extends Button {

	public ImportButton(Stage primaryStage, IFileService fileService, IMilkList milkList, MilkStatsTable statsTable) {
		IMilkList backup = milkList;
		this.setText("Import");

		// set up the file chooser
		FileChooser importChooser = new FileChooser();
		importChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", "*.csv"));

		// creation of functionality to button
		this.setOnAction(e -> {
			// get the file
			File importFile = importChooser.showOpenDialog(primaryStage);
			if (importFile != null) {
				milkList.clear();
				
				// try to read the new data
				try {
					// add all new data to the list, then table
					milkList.addAll(fileService.readCsv(importFile.getAbsolutePath()));
					statsTable.updateStatistics(milkList);
					
				} catch (Exception e1) {
					// restore data if a bad file is chosen
					
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText(importFile.getName() + " is invalid.");
					alert.showAndWait();
					
					milkList.addAll(backup);
				}
			}
		});
	}
}
