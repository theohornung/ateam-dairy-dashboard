package application;

import java.time.Month;
import java.util.Calendar;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

/**
 * A dialog that takes in data to create new milk data
 */
public class AddMilkDialog extends Dialog<MilkData> {

	/**
	 * Empty constructor. Creates a new dialog with fields to create new milk data
	 */
	public AddMilkDialog() {
		this.setTitle("Add Milk");
		this.setHeaderText("Please put in the following milk values");

		// set up insert button
		ButtonType insertButtonType = new ButtonType("Insert", ButtonData.OK_DONE);
		this.getDialogPane().getButtonTypes().addAll(insertButtonType, ButtonType.CANCEL);

		// set up grid pane to contain data fields
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField farmName = new TextField(); // farm name field input
		farmName.setPromptText("Farm Name");
		TextField milkWeight = new TextField(); // milk weight field input
		milkWeight.setPromptText("Milk Weight");
		
		// List of days 1-31, inclusive-inclusive
		ObservableList<Integer> days = FXCollections.observableArrayList(
				IntStream.rangeClosed(1, 31).boxed().collect(Collectors.toList()));
		ComboBox<Integer> day = new ComboBox<>(days);
		
		// List of months
		ObservableList<Month> months = FXCollections.observableArrayList(
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
		ComboBox<Month> month = new ComboBox<>(months); // month select drop down field
				
		TextField year = new TextField(); // year field input
		year.setPromptText("Year");
		
		// add all field components to the grid pane
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

		// keep the insert button enabled
		this.getDialogPane().lookupButton(insertButtonType).setDisable(false);

		// place the grid into the dialog
		this.getDialogPane().setContent(grid);

		// set the dialog to return a MilkData obj after submitting valid data
		this.setResultConverter(dialogButton -> {
			if (dialogButton == insertButtonType) {
				try {
					// check for invalid date
					if (isValidDate(day.getValue(), month.getValue(), Integer.parseInt(year.getText()))) {
						MilkData data = new MilkData(
								farmName.getText(),
								Double.parseDouble(milkWeight.getText()),
								day.getValue(),
								month.getValue(),
								Integer.parseInt(year.getText()));
						return data;
					}
				}
				catch (Exception e) {
					// alert to invalid input
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid field(s) provided!");
					alert.showAndWait();
				}
			}
			return null;
		});
	}

	/**
	 * Helper method to check for invalid date
	 * 
	 * @param day a day
	 * @param month a month
	 * @param year a year
	 * @return true if the given date is valid
	 */
	private boolean isValidDate(int day, Month month, int year) {
		Calendar.Builder builder = new Calendar.Builder();
		builder.setLenient(false);
		try {
			builder.setDate(year, month.ordinal(), day);
			builder.build();
		}
		// build() throws when the given date is invalid
		catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
}
