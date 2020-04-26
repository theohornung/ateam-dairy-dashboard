package application;

import java.util.Optional;

import application.interfaces.IFileService;
import application.interfaces.IMilkList;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/*
 * The application's main dashboard that facilitates the functionality of all other components.
 * This dashboard organizes the layout of these children components.
 */
public class Dashboard extends BorderPane {

	private IMilkList masterList; // the list of all currently loaded milk data
	private IFileService fileService;
	
	private Stage primaryStage;
	private MilkStatsTable farmTable;

	public Dashboard(Stage primaryStage) {
		super();
		
		masterList = new MilkList();
		fileService = new FileService();
		farmTable = new MilkStatsTable(masterList);
		this.primaryStage = primaryStage;
		init();
	}

	/**
	 * Helper method to organize the initialization of the dashboard.
	 * This methods calls other helper methods that serve to initialize different
	 * panels of the dashboard.
	 */
	private void init() {
		
		initTop();
		initCenter();
	}

	/*
	 * Initialize the top panel.
	 * This panel contains user help controls.
	 */
	private void initTop() {
		HBox buttons = new HBox();
		// Creation of dialog box
		Alert help = new Alert(AlertType.INFORMATION);
		help.setTitle("Help / FAQs");
		help.setHeaderText(null);
		help.setContentText("Welcome to the dairy dashboard!\nContent\n\nMore Content");

		// Creation of button
		Button helpButton = new Button("Help");
		// creation of functionality of help button
		helpButton.setOnAction(e -> help.showAndWait());
		
		Button addMilk = new Button("Add Milk");
		AddMilkDialog milkTaker = new AddMilkDialog();
		// set control to add milk data entered into the dialog
		addMilk.setOnMouseClicked(e -> {
			Optional<MilkData> data = milkTaker.showAndWait();
			if (data.isPresent()) {
				MilkData milk = data.get();
				masterList.add(milk);
				farmTable.updateStatistics(masterList);
			}
		});
		
		// Create remove button
		Button removeMilk = new Button("Remove milk");
		RemoveMilkDialog milkRemover = new RemoveMilkDialog();
		//sets remove button operation
		removeMilk.setOnMouseClicked(e -> {
			Optional<MilkData> data = milkRemover.showAndWait();
			if (data.isPresent()) {
				MilkData milk = data.get();
				masterList.remove(milk.getFarmName(), milk.getDate());
				farmTable.updateStatistics(masterList);
			}
		});
		

		
		//button to display farm report table
		Button farmReport = new Button("Farm Report");
		FarmRepDialog farmDia = new FarmRepDialog(masterList);
		farmReport.setOnMouseClicked(e -> farmDia.showAndWait());
			
		
		Button annReport = new Button("Annual Report");
		
		GetMilkDialog getMilk = new GetMilkDialog(masterList);
		Button milkByRange = new Button("Milk By Range");
		milkByRange.setOnMouseClicked(e -> getMilk.showAndWait());
		
		// Creation of import and export button
		Button importButton = new ImportButton(primaryStage, fileService, masterList, farmTable);
		Button exportButton = new ExportButton(primaryStage, fileService, masterList);
		
		buttons.getChildren().addAll(importButton, exportButton, addMilk, removeMilk, farmReport, milkByRange, helpButton);
		this.setTop(buttons);
	}

	/**
	 * Initialize the center panel.
	 * This panel displays the main milk data table.
	 */
	private void initCenter() {
		this.setCenter(farmTable);
	}
}
