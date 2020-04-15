package application;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Dashboard extends BorderPane {

	private Stage primaryStage;

	public Dashboard(Stage primaryStage) {
		super();
		init();
		this.primaryStage = primaryStage;
	}

	private void init() {
		initLeft();
		initRight();
		initTop();
		initBottom();
		initCenter();
	}

	private void initLeft() {
		Label testLeft = new Label("Test left");
		this.setLeft(testLeft);
	}

	private void initRight() {
		Label testRight = new Label("Test right");
		this.setRight(testRight);
	}

	private void initTop() {

		// Creation of dialog box
		Alert help = new Alert(AlertType.INFORMATION);
		help.setTitle("Help / FAQs");
		help.setHeaderText(null);
		help.setContentText("Welcome to the dairy dashboard!\nContent\n\nMore Content");

		// Creation of button
		Button button = new Button("Help");
		this.setTop(button);

		// creation of functionality of help button
		button.setOnAction(e -> help.showAndWait());
	}

	private void initBottom() {

		// Creation of import and export button
		Button importButton = new Button("import");
		Button exportButton = new Button("export");

		// Creating filechoosers for import and export
		FileChooser importChooser = new FileChooser();
		FileChooser exportChooser = new FileChooser();
		importChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", ".csv"));
		exportChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", ".csv"));

		// creation of functionality to button
		importButton.setOnAction(e -> {
			File importFile = importChooser.showOpenDialog(primaryStage);
			if (importFile != null) {
				// read file
			}
		});
		exportButton.setOnAction(e -> {
			File export = exportChooser.showSaveDialog(primaryStage);
			if (export != null) {
				// write to file
			}
		});

		// adding to dashboard
		HBox importExport = new HBox(importButton, exportButton);
		this.setBottom(importExport);
	}

	private void initCenter() {
		Label testCenter = new Label("Test center");
		this.setCenter(testCenter);
	}
}
