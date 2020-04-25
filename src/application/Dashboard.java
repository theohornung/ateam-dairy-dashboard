package application;

import java.util.Optional;

import application.interfaces.IFileService;
import application.interfaces.IMilkList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/*
 * The application's main dashboard that facilitates the functionality of all other components.
 * This dashboard organizes the layout of these children components.
 */
public class Dashboard extends BorderPane {

	private MilkList masterList; // the list of all currently loaded milk data
	private IFileService fileService;
	
	private Stage primaryStage;
	private ScrollPane sPane;
	private MilkTable farmTable;

	public Dashboard(Stage primaryStage) {
		super();
		
		masterList = new MilkList();
		fileService = new FileService();
		
		sPane = new ScrollPane();
		farmTable = new MilkTable();
		this.primaryStage = primaryStage;
		init();
	}

	/**
	 * Helper method to organize the initialization of the dashboard.
	 * This methods calls other helper methods that serve to initialize different
	 * panels of the dashboard.
	 */
	private void init() {
		
		initRight();
		initTop();
		initBottom();
		initCenter();
	}


	/*
	 * Initialize the right panel.
	 * This panel contains all data manip. controls.
	 */
	private void initRight() {
		VBox buttons = new VBox();
		Button addMilk = new Button("Add Milk");

		AddMilkDialog milkTaker = new AddMilkDialog();

		// set control to add milk data entered into the dialog
		addMilk.setOnMouseClicked(e -> {
			Optional<MilkData> data = milkTaker.showAndWait();
			if (data.isPresent()) {
				ObservableList<MilkData> tableData = farmTable.getItems();
				MilkData milk = data.get();
				tableData.add(milk);
				masterList.add(milk);
				farmTable.addMilkWeight(milk.getMilkWeight());
				farmTable.setItems(tableData);
			}
		});
		
		// TODO implement remove button
		Button removeMilk = new Button("Remove milk");

		
		
		GetMilkDialog getMilk = new GetMilkDialog(masterList);
		Button milkByRange = new Button("Milk By Range");
		milkByRange.setOnMouseClicked(e -> {
			Optional<MilkList> data = getMilk.showAndWait();
			if (data.isPresent()) {
				
			}
		});
		
		buttons.getChildren().addAll(addMilk, removeMilk, milkByRange);
		this.setRight(buttons);
		
		
	}

	/*
	 * Initialize the top panel.
	 * This panel contains user help controls.
	 */
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

	/**
	 * Initialize bottom panel.
	 * This panel contains all controls related to file r/w ops.
	 */
	private void initBottom() {

		// Creation of import and export button
		Button importButton = new ImportButton(primaryStage, fileService, masterList, farmTable);
		Button exportButton = new ExportButton(primaryStage, fileService, masterList);

		// adding to dashboard
		HBox importExport = new HBox(importButton, exportButton);
		this.setBottom(importExport);
	}

	/**
	 * Initialize the center panel.
	 * This panel displays the main milk data table.
	 */
	private void initCenter() {
		sPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sPane.setContent(farmTable);
		this.setCenter(sPane);
	}
}
