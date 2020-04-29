package application;

import java.io.File;

import application.interfaces.IFileService;
import application.interfaces.IMilkList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Handles exporting milk data
 */
public class ExportButton extends Button {

	/**
	 * Constructor to create button to handle exporting data
	 * 
	 * @param primaryStage the stage that the button belongs to
	 * @param fileService a file service instance to perform file write operations
	 * @param list the list of data to write
	 */
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
				} catch (Exception e1) {
					// alert that the write failed
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Failed to write " + export.getName());
					alert.showAndWait();
				}
			}
		});
	}
}
