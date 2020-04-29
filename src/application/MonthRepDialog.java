package application;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import application.interfaces.IMilkList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * A custom dialog to generate a report about milk production 
 * for a given month in a given year.
 */
public class MonthRepDialog extends Dialog<MilkData> {

	/**
	 * Constructor to create a new dialog with a milk production report
	 * @param mainList the list of data to filter and generate the report from
	 */
	public MonthRepDialog(IMilkList mainList) {
		this.setTitle("Milk by Month");
		this.setHeaderText("Please put in a month and year to get a report of");

		// set up display button
		ButtonType displayButtonType = new ButtonType("Display", ButtonData.OK_DONE);
		this.getDialogPane().getButtonTypes().addAll(displayButtonType, ButtonType.CANCEL);

		// set up grid pane to display components
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		// list of months to choose from
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
		ComboBox<Month> month = new ComboBox<>(months); // month drop down field input
				
		TextField year = new TextField(); // year field input
		year.setPromptText("Year");

		grid.add(new Label("Month:"), 0, 0); // organize grid pane
		grid.add(month, 1, 0);
		grid.add(new Label("Year:"), 0, 1);
		grid.add(year, 1, 1);

		// keep the display button from being disabled
		this.getDialogPane().lookupButton(displayButtonType).setDisable(false);

		this.getDialogPane().setContent(grid);

		// set the dialog to return a MilkData obj after submitting valid data
		this.setResultConverter(dialogButton -> {
			
			if (dialogButton == displayButtonType) {
				try {
					// get data from input fields
					int curYear = Integer.parseInt(year.getText());
					Month curMonth = month.getValue();
					MilkList monthMilk = new MilkList();
					
					for (MilkData data : mainList) {
						if (data.getDate().getYear() == curYear && data.getDate().getMonth().equals(curMonth)) {
							monthMilk.add(data);
						}
					}
					
					//collect all unique farm names into list
					ArrayList<String> names = new ArrayList<String>();
					for (MilkData data : monthMilk) {
						if(!names.contains(data.getFarmName())) {
							names.add(data.getFarmName());
						}
					}
					
					// set up display table data and table
					MilkList nameComp = nameComposite(names, mainList, curYear, curMonth);
					Collections.sort(nameComp, new SortByName());
					
					MilkStatsTable newTable = new MilkStatsTable(nameComp);
					MilkTable opTable = newTable.getTable();
					opTable.getColumns().remove(3); //removes date since day is irrelevant
					
					newTable.monthreStatistics(nameComp, curMonth, curYear);
					opTable.setPrefWidth(280);
					
					Dialog<MilkList> dialog = new Dialog<>();
					BorderPane pane = new BorderPane();
					newTable.autosize();
					pane.setCenter(newTable);
					HBox buttonBox = new HBox();
					
					// sort by farm name button
					Button byFarm = new Button("Sort by Name");
					byFarm.setOnMouseClicked(e -> {
						Collections.sort(nameComp, new SortByName());
						newTable.monthreStatistics(nameComp, curMonth, curYear);
					});
					
					// sort by ascending milk weight button
					Button ascend = new Button("Sort in Ascending Milk Order");
					ascend.setOnMouseClicked(e -> {
						Collections.sort(nameComp, new SortAscend());
						newTable.monthreStatistics(nameComp, curMonth, curYear);
					});
					
					// sort by descending milk weight button
					Button descend = new Button("Sort in Descending Milk Order");
					descend.setOnMouseClicked(e -> {
						Collections.sort(nameComp, new SortDescend());
						newTable.monthreStatistics(nameComp, curMonth, curYear);
					});
					
					buttonBox.getChildren().addAll(byFarm, ascend, descend);
					pane.setBottom(buttonBox);
					
					dialog.getDialogPane().setContent(pane);
					ButtonType okButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
					dialog.getDialogPane().getButtonTypes().addAll(okButtonType);
					dialog.show();
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
	 * Creates a milk list such that there is one element for each farm, and 
	 * each farm element contains its total milk produced
	 * 
	 * @param names the names of farms
	 * @param mainList the list of milk data
	 * @param year the year to get milk data from
	 * @return
	 */
	private MilkList nameComposite(ArrayList<String> names, IMilkList mainList, int year, Month curMonth) {
		MilkList comp = new MilkList();
		for (String name : names) {
			int milkWeight = 0;
			for (MilkData data: mainList) {
				if (name.equals(data.getFarmName())) {
					milkWeight += data.getMilkWeight();
				}
			}
			MilkData newData = new MilkData(name, milkWeight, 1, curMonth, year);
			comp.add(newData);
		}
		return comp;
	}

	/**
	 * Comparator class to sort MilkData by farm name
	 */
	public class SortByName implements Comparator<MilkData> {

		@Override
		public int compare(MilkData o1, MilkData o2) {
			return o1.getFarmName().compareTo(o2.getFarmName());
		}
	}
	
	/**
	 * Comparator class to sort MilkData by ascending milk weight
	 */
	public class SortAscend implements Comparator<MilkData> {

		@Override
		public int compare(MilkData o1, MilkData o2) {
			if (o1.getMilkWeight()<o2.getMilkWeight()) {
				return -1;
			}
			return 1;
		}
	}
	
	/**
	 * Comparator class to sort MilkData by descending milk weight
	 */
	public class SortDescend implements Comparator<MilkData> {

		@Override
		public int compare(MilkData o1, MilkData o2) {
			if (o1.getMilkWeight()>o2.getMilkWeight()) {
				return -1;
			}
			return 1;
		}
	}
	
}