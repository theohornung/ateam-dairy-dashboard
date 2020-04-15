package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
