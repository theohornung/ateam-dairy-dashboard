package application;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import application.interfaces.IMilkList;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Dialog component to display a milk production report for a year
 */
public class AnnualRepDialog extends Dialog<MilkList> {

	/**
	 * Constructor to create a new AnnualRepDialog object
	 * 
	 * @param mainList the list of milk data produced in a year
	 */
	public AnnualRepDialog(IMilkList mainList) {
		this.setTitle("Milk by Year");
		this.setHeaderText("Please input a year for which to display milk totals");

		// set up display button
		ButtonType displayButtonType = new ButtonType("Display", ButtonData.OK_DONE);
		this.getDialogPane().getButtonTypes().addAll(displayButtonType, ButtonType.CANCEL);

		// set up grid pane to contain data fields
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
				
		TextField year = new TextField(); // year field input
		year.setPromptText("Year");

		grid.add(new Label("Year:"), 0, 0); // add year field component to grid
		grid.add(year, 1, 0);

		// keep the display button enabled
		this.getDialogPane().lookupButton(displayButtonType).setDisable(false);

		// place the grid into the dialog
		this.getDialogPane().setContent(grid);

		// set the dialog to return year after submitting valid data
		this.setResultConverter(dialogButton -> {
			if (dialogButton == displayButtonType) {
				try {
					int curYear = Integer.parseInt(year.getText());
					
					MilkList yearMilk = new MilkList();
					//gather all milk data for year into list
					for (MilkData data : mainList) {
						if (data.getDate().getYear() == curYear) {
							yearMilk.add(data);
						}
					}
					
					//collect all unique farm names into list
					ArrayList<String> names = new ArrayList<String>();
					for (MilkData data : yearMilk) {
						if(!names.contains(data.getFarmName())) {
							names.add(data.getFarmName());
						}
					}
					
					MilkList nameComp = nameComposite(names, mainList, curYear);
					Collections.sort(nameComp, new SortByName());
					
					MilkStatsTable newTable = new MilkStatsTable(nameComp);
					MilkTable opTable = newTable.getTable();
					opTable.getColumns().remove(3); //removes date since year is only thing needed
					
					newTable.yearStatistics(nameComp);
					opTable.setPrefWidth(260);
					
					Dialog<MilkList> dialog = new Dialog<>();
					BorderPane pane = new BorderPane();
					newTable.autosize();
					pane.setCenter(newTable);
					HBox buttonBox = new HBox();
					
					// set up button to sort by name
					Button byFarm = new Button("Sort by Name");
					byFarm.setOnMouseClicked(e -> {
						Collections.sort(nameComp, new SortByName());
						newTable.yearStatistics(nameComp);
					});
					
					// set up button to sort by ascending qt of milk produced
					Button ascend = new Button("Sort in Ascending Milk Order");
					ascend.setOnMouseClicked(e -> {
						Collections.sort(nameComp, new SortAscend());
						newTable.yearStatistics(nameComp);
					});
					
					// set up button to sort by descending qt of milk produced
					Button descend = new Button("Sort in Descending Milk Order");
					descend.setOnMouseClicked(e -> {
						Collections.sort(nameComp, new SortDescend());
						newTable.yearStatistics(nameComp);
					});
					
					// add all buttons to pane
					buttonBox.getChildren().addAll(byFarm, ascend, descend);
					pane.setBottom(buttonBox);
					
					// add all components to dialog pane
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
	private MilkList nameComposite(ArrayList<String> names, IMilkList mainList, int year) {
		MilkList comp = new MilkList();
		for (String name : names) {
			int milkWeight = 0;
			for (MilkData data: mainList) {
				if (name.equals(data.getFarmName())) {
					milkWeight += data.getMilkWeight();
				}
			}
			MilkData newData = new MilkData(name, milkWeight, 1, Month.JANUARY, year);
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