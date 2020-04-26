package application;

import java.time.Month;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

/**
 * A dialog that takes in data to create new milk data
 */
public class MonthRepDialog extends Dialog<MilkData> {

	public MonthRepDialog() {
		this.setTitle("Monthly report");
		this.setHeaderText("Please put in a month and year to get a report of");

		ButtonType removeButtonType = new ButtonType("Display", ButtonData.OK_DONE);
		this.getDialogPane().getButtonTypes().addAll(removeButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		
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

		grid.add(new Label("Month:"), 0, 0);		
		grid.add(month, 1, 0);
		grid.add(new Label("Year:"), 0, 1);
		grid.add(year, 1, 1);

		Node loginButton = this.getDialogPane().lookupButton(removeButtonType);
		// can change to true to add reqs to inserting
		loginButton.setDisable(false);

		this.getDialogPane().setContent(grid);

		// set the dialog to return a MilkData obj after submitting valid data
		this.setResultConverter(dialogButton -> {
			if (dialogButton == removeButtonType) {
				try {
					// check for invalid date
					if (isValidDate(1, month.getValue(), Integer.parseInt(year.getText()))) {
						MilkData data = new MilkData(
								"a", //filler name
								-1, //filler weight
								1, //filler day
								month.getValue(),
								Integer.parseInt(year.getText()));
						return data;
					}
				}
				catch (NumberFormatException e) {
					// don't allow invalid number fields
					// TODO (possibly) add field validation for dialogs
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