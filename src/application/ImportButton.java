package application;

import java.io.File;
import java.io.IOException;

import application.interfaces.IFileService;
import application.interfaces.IMilkList;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * Handles importing milk data
 * 
 * TODO add file service param
 */
public class ImportButton extends Button {

	public ImportButton(Stage primaryStage, IFileService fileService, IMilkList milkList, MilkTable table) {
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
					table.reset();
					table.getItems().addAll(milkList);
					
				} catch (IOException e1) {
					// restore data if a bad file is chosen
					// TODO add alert here
					milkList.addAll(backup);
				}
			}
		});
	}
}
