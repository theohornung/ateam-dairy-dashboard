package application;

import java.time.Month;
import java.util.Calendar;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

/**
 * A dialog that takes in data to remove milk data
 */
public class RemoveMilkDialog extends Dialog<MilkData> {
	/**
	 * Makes dialog to take in info and then return a milk value to delete based off of
	 */
	public RemoveMilkDialog() {
		this.setTitle("Remove Milk");
		this.setHeaderText("Please put in the following milk values");

		ButtonType removeButtonType = new ButtonType("Remove", ButtonData.OK_DONE);
		this.getDialogPane().getButtonTypes().addAll(removeButtonType, ButtonType.CANCEL);

		//grid for text options
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField farmName = new TextField();
		farmName.setPromptText("Farm Name");
		
		//dropdown box for days
		ObservableList<Integer> days = FXCollections.observableArrayList(
				IntStream.rangeClosed(1, 31).boxed().collect(Collectors.toList()));
		ComboBox<Integer> day = new ComboBox<>(days);
		
		//dropdown box for months
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
		ComboBox<Month> month = new ComboBox<>(months);
				
		TextField year = new TextField();
		year.setPromptText("Year");

		//ads labels to display
		grid.add(new Label("Farm Name:"), 0, 0);
		grid.add(farmName, 1, 0);
		grid.add(new Label("Day:"), 0, 1);
		grid.add(day, 1, 1);
		grid.add(new Label("Month:"), 0, 2);		
		grid.add(month, 1, 2);
		grid.add(new Label("Year:"), 0, 3);
		grid.add(year, 1, 3);

		Node removeButton = this.getDialogPane().lookupButton(removeButtonType);
		// can change to true to add reqs to inserting
		removeButton.setDisable(false);

		this.getDialogPane().setContent(grid);

		// set the dialog to return a MilkData obj after submitting valid data
		//TODO make date optional
		this.setResultConverter(dialogButton -> {
			if (dialogButton == removeButtonType) {
				try {
					// check for invalid date
					if (isValidDate(day.getValue(), month.getValue(), Integer.parseInt(year.getText()))) {
						MilkData data = new MilkData(
								farmName.getText(),
								-1, //filler milk weight
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
	 * @param day
	 * @param month
	 * @param year
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
