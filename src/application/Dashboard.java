package application;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class Dashboard extends BorderPane {

	private Stage primaryStage;
	private ScrollPane sPane = new ScrollPane();
	private TableView byFarm = new TableView();
	private int milkTotal = 0;
	
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
		VBox buttons = new VBox();
		Button addMilk = new Button("Add Milk");
		
		Dialog<String[]> milkTaker = new Dialog<String[]>();
		milkTaker.setTitle("Add Milk");
		milkTaker.setHeaderText("Please put in the following milk values");
		
		ButtonType insertButtonType = new ButtonType("Insert", ButtonData.OK_DONE);
		milkTaker.getDialogPane().getButtonTypes().addAll(insertButtonType, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField farmName = new TextField();
		farmName.setPromptText("Farm Name");
		TextField milkWeight = new TextField();
		milkWeight.setPromptText("Milk Weight");
		TextField day = new TextField();
		day.setPromptText("Day");
		TextField month = new TextField();
		month.setPromptText("Month");
		TextField year = new TextField();
		year.setPromptText("Year");
		
		grid.add(new Label("Farm Name:"), 0, 0);
		grid.add(farmName, 1, 0);
		grid.add(new Label("Milk Weight:"), 0, 1);
		grid.add(milkWeight, 1, 1);
		grid.add(new Label("Day:"), 0, 2);
		grid.add(day, 1, 2);
		grid.add(new Label("Month:"), 0, 3);
		grid.add(month, 1, 3);
		grid.add(new Label("Year:"), 0, 4);
		grid.add(year, 1, 4);
		
		Node loginButton = milkTaker.getDialogPane().lookupButton(insertButtonType);
		//can change to true to add reqs to inserting
		loginButton.setDisable(false);
		
		milkTaker.getDialogPane().setContent(grid);
		
		Platform.runLater(() -> farmName.requestFocus());
		
		milkTaker.setResultConverter(dialogButton -> {
		    if (dialogButton == insertButtonType) {
		        String[] data = new String[5];
		        data[0] = farmName.getText();
		        data[1] = milkWeight.getText();
		        data[2] = day.getText();
		        data[3] = month.getText();
		        data[4] = year.getText();
		        return data;
		    }
		    return null;
		});
		
		addMilk.setOnAction(new EventHandler<ActionEvent>() { //closes window on click
			@Override
			public void handle(ActionEvent arg0) {
				Optional<String[]> data = milkTaker.showAndWait();
				if (data.isPresent()) {
					ObservableList prior = byFarm.getItems();
					milkTotal += Integer.parseInt(data.get()[1]);
					String[] byFarmData = new String[3];
					byFarmData[0] = data.get()[0];
					byFarmData[1] = Double.toString(
							(double)Integer.parseInt(data.get()[1])/milkTotal * 100) + "%";
					byFarmData[2] = data.get()[1];
					prior.add(byFarmData);
					byFarm.setItems(prior);
				}
			}
			
		});
		
		buttons.getChildren().add(addMilk);
		this.setRight(buttons);
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
		
		sPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		
		TableColumn<String[], String> farm = new TableColumn<String[], String>("Farm");
		TableColumn<String[], String> milkPer = new TableColumn<String[], String>("Milk %");
		TableColumn<String[], String> milkWeight = new TableColumn<String[], String>("Milk Weight (lbs)");
		
		//example data
		String[][] farmData = {{"Aidan_Farm", "25%", "1000"}, 
				{"Ethan_Farm", "50%", "2000"}, {"Theo_Farm", "25%", "1000"}};
		milkTotal = 4000;
		final ObservableList<String[]> data = FXCollections.observableArrayList();
		data.addAll(Arrays.asList(farmData));
		farm.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                return new SimpleStringProperty((p.getValue()[0]));
            }
        });
		
		milkPer.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                return new SimpleStringProperty((p.getValue()[1]));
            }
        });
		
		milkWeight.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                return new SimpleStringProperty((p.getValue()[2]));
            }
        });
		
		byFarm.getColumns().addAll(farm, milkPer, milkWeight);
		byFarm.setItems(data);
		
		
		sPane.setContent(byFarm);
		this.setCenter(sPane);
	}
}
