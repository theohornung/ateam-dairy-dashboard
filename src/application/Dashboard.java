package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Dashboard extends BorderPane {

	private Stage primaryStage;
	private class ImportExportHandler implements EventHandler<ActionEvent>{
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
		FileInputStream input = null;
		try {
			input = new FileInputStream("questionIcon.jpg");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = new Image(input);
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(30);
		imageView.setFitWidth(25);
		
		Button button = new Button("Help", imageView);
		this.setTop(button);
	}
	
	private void initBottom() {
		//Creating the HBox to hold the import and export buttons
		FileChooser importer = new FileChooser();
		
		//Creation of import and export button
		Button importButton = new Button("import");
		Button exportButton = new Button("export");	
		
		//Creating filechoosers for import and export
		FileChooser importChooser = new FileChooser();
		FileChooser exportChooser = new FileChooser();
		importChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", ".csv"));
		exportChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", ".csv"));
		
		//creation of functionality to button
		ImportExportHandler impHandler = new ImportExportHandler(importButton, importChooser);
		ImportExportHandler expHandler = new ImportExportHandler(exportButton, exportChooser);
		importButton.setOnAction(impHandler);
		exportButton.setOnAction(expHandler);
		
		//adding to dashboard
		HBox importExport = new HBox(importButton, exportButton);	
		this.setBottom(importExport);
	}
	
	private void initCenter() {
		Label testCenter = new Label("Test center");
		this.setCenter(testCenter);
	}
}
