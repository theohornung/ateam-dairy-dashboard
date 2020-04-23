package application;

import java.io.File;
import java.io.IOException;

import application.interfaces.IFileService;
import application.interfaces.IMilkList;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Handles exporting milk data
 * 
 * TODO add file service param
 */
public class ExportButton extends Button {

	public ExportButton(Stage primaryStage, IFileService fileService, IMilkList list) {
		this.setText("Export");

		// set up file chooser
		FileChooser exportChooser = new FileChooser();
		exportChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", "*.csv"));

		// creation of functionality to button
		this.setOnAction(e -> {
			File export = exportChooser.showSaveDialog(primaryStage);
			if (export != null) {
				// write to file
				try {
					fileService.writeCsv(export.getAbsolutePath(), list);
				} catch (IOException e1) {
					// TODO alert that the write failed
				}
			}
		});
	}
}
