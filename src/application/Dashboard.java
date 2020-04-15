package application;

<<<<<<< HEAD

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
=======
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

>>>>>>> ethanCommit01
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Dashboard extends BorderPane {

<<<<<<< HEAD
	private Stage primaryStage;
	/**
	 * ImportExportHandler - handles import export requests from clicked buttons
	 * @author Ethan Simonen (2020)
	 */
	private class ImportExportHandler implements EventHandler<ActionEvent> {
		private Button button;
		private FileChooser fileChooser;
		ImportExportHandler(Button button, FileChooser fileChooser){
			this.button = button;
			this.fileChooser = fileChooser;}
		@Override
		public void handle(ActionEvent e) {
			if(button.getText().equals("import")) {
				fileChooser.showOpenDialog(primaryStage);
			}else if(button.getText().equals("export")) {
				fileChooser.showSaveDialog(primaryStage);
				
			} 
		}
		
	}
	
	/**
	 * HelpHandler - handles dialog request from clicked button
	 * @author Ethan Simonen (2020)
	 */
	private class HelpHandler implements EventHandler<ActionEvent> {
		private Alert help;
		HelpHandler(Alert help){
			this.help = help;}
		@Override
		public void handle(ActionEvent e) {
			help.showAndWait(); 
		}
		
	}

	
=======
	private Stage primaryStage;	
>>>>>>> ethanCommit01
	
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

		//Creation of button
		Button button = new Button("Help");
		this.setTop(button);
		
		//Creation of dialog box
		Alert help = new Alert(AlertType.INFORMATION);
		help.setTitle("Help / FAQs");
		help.setHeaderText(null);
		help.setContentText("Welcome to the dairy dashboard!\nContent\n\nMore Content");
		
		//creation of functionality of help button
		HelpHandler helpHandler = new HelpHandler(help);
		button.setOnAction(helpHandler);

	}
	
	private void initBottom() {
		
		//Creation of import and export button
		Button importButton = new Button("import");
		Button exportButton = new Button("export");	
		
		
		//Creating filechoosers for import and export
		FileChooser importChooser = new FileChooser();
		FileChooser exportChooser = new FileChooser();
		importChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", ".csv"));
		exportChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", ".csv"));
		
		//creation of functionality to button		
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
		
		//adding to dashboard
		HBox importExport = new HBox(importButton, exportButton);	
		this.setBottom(importExport);
	}
	
	private void initCenter() {
		Label testCenter = new Label("Test center");
		this.setCenter(testCenter);
	}
}
