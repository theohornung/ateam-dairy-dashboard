package application;

import java.time.Month;
import application.interfaces.IMilkList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class GetMilkDialog extends Dialog<MilkList>{
	private ObservableList<Month> months = FXCollections.observableArrayList(null,
			Month.JANUARY,
			Month.FEBRUARY,
			Month.MARCH,
			Month.APRIL,
			Month.MAY,
			Month.JUNE,
			Month.JULY,
			Month.AUGUST,
			Month.SEPTEMBER,
			Month.OCTOBER,
			Month.NOVEMBER,
			Month.DECEMBER);

	public GetMilkDialog(IMilkList milkList) {
		this.setTitle("Milk By Range");

		ButtonType retrieveButtonType = new ButtonType("Retrieve");
		this.getDialogPane().getButtonTypes().addAll(retrieveButtonType, ButtonType.CANCEL);
		this.setHeaderText("Please enter parameters to search from:");
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		//creation of boxes to return the information requested
		ComboBox<Month> startMonth = new ComboBox<>(months);
		ComboBox<Month> endMonth = new ComboBox<>(months);
		TextField startYear = new TextField();
		startYear.setPromptText("Starting Year");
		TextField endYear = new TextField();
		endYear.setPromptText("Ending Year");
		TextField farm = new TextField();
		farm.setPromptText("Farm");

		//adding boxes to grid
		grid.add(new Label("Farm Name:"), 0, 0);
		grid.add(new Label("Starting Year:"), 0, 1);
		grid.add(new Label("Ending Year:"), 0, 2);
		grid.add(new Label("Starting Month"), 0, 3);
		grid.add(new Label("Ending Month"), 0, 4);

		grid.add(farm, 1, 0);
		grid.add(startYear, 1, 1);
		grid.add(endYear, 1, 2);
		grid.add(startMonth, 1, 3);
		grid.add(endMonth, 1, 4);

		this.getDialogPane().setContent(grid);

		Node loginButton = this.getDialogPane().lookupButton(retrieveButtonType);


		loginButton.setDisable(false);


		this.setResultConverter(dialogButton -> {
			if (dialogButton.getText().equalsIgnoreCase("cancel")) return null;
			MilkList specificMilkList = null;
			
			int startingMonth;
			int endingMonth;
			int startingYear;
			int endingYear;
			String farmName;
			
			if(startMonth.getValue() == null) {
				startingMonth = 0;
			}else {
				startingMonth = startMonth.getValue().getValue();
			}
			if(endMonth.getValue() == null) {
				endingMonth = 12;
			}else {
				endingMonth = endMonth.getValue().getValue();
			}
			if(startYear.getText().equals("")) {
				startingYear = -1;
			}else {
				startingYear = Integer.parseInt(startYear.getText());
			}
			if(endYear.getText().equals("")) {
				endingYear = -1;
			}else {
				endingYear = Integer.parseInt(endYear.getText());
			}
			farmName = farm.getText();
			if (dialogButton == retrieveButtonType && legalRange(startingMonth, endingMonth, startingYear, endingYear)) {

				specificMilkList = new MilkList();
				//if only farmName specified
				if(startingYear == -1 && endingYear == -1 && endMonth.getValue() == null && startMonth.getValue() == null) {
					specificMilkList.addAll(milkList.getFromFarm(farmName));
				}
				//over the course of only one year
				else if(startingYear == endingYear || endingYear == -1) {
					specificMilkList.addAll(milkList.getFromMonths(startingYear, startingMonth, endingMonth).getFromFarm(farmName));
				}
				//over two simultaneous years
				else if(startingYear+1 == endingYear) {
					specificMilkList.addAll(milkList.getFromMonths(startingYear, startingMonth, 12).getFromFarm(farmName));
					specificMilkList.addAll(milkList.getFromMonths(endingYear, 0, endingMonth).getFromFarm(farmName));
				}
				// >2 years
				else {
					specificMilkList.addAll(milkList.getFromMonths(startingYear, startingMonth, 12).getFromFarm(farmName));
					specificMilkList.addAll(milkList.getFromMonths(endingYear, 0, endingMonth).getFromFarm(farmName));
					specificMilkList.addAll(milkList.getFromYears(startingYear+1, endingYear-1)
							.getFromFarm(farmName));

				}
				//creating popup of new range
				MilkStatsTable newTable = new MilkStatsTable(specificMilkList);
				newTable.updateStatistics(specificMilkList);
				Dialog<MilkList> dialog = new Dialog<>();
				dialog.getDialogPane().setContent(newTable);
				ButtonType okButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(okButtonType);
				dialog.show();
				newTable = null;
				return specificMilkList;
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Warning");
				alert.setHeaderText("Illegal Range Provided!");
				alert.setContentText("Please respecify range.");
				alert.show();	

			}
			return null;
		});
	}

	/**
	 * 
	 * @param startMonth
	 * @param endMonth
	 * @param startYear
	 * @param endYear
	 * @return true if the parameters allow for a legal retrieval of MilkData
	 */
	private boolean legalRange(int startMonth, int endMonth, int startYear, int endYear) {
		if(startYear == endYear && endMonth < startMonth) return false;
		if(endYear < startYear && endYear !=-1) return false;
		return true;
	}
}
