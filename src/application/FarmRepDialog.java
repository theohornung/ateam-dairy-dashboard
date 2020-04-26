package application;

import java.time.Month;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import application.interfaces.IMilkList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 * A dialog that takes in data to display a farm report
 */
public class FarmRepDialog extends Dialog<MilkList> {

	@SuppressWarnings("unchecked")
	/**
	 * Creates the dialog to take in data and uses that to make output dialog
	 * @param mainList
	 */
	public FarmRepDialog(IMilkList mainList) {
		this.setTitle("Farm Report");
		this.setHeaderText("Please put in a farm name and year");
		
		//sets up button to display dialog
		ButtonType displayButtonType = new ButtonType("Display", ButtonData.OK_DONE);
		//exit button
		this.getDialogPane().getButtonTypes().addAll(displayButtonType, ButtonType.CANCEL);

		//grid for text options
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField farmName = new TextField();
		farmName.setPromptText("Farm Name");		
		TextField year = new TextField();
		year.setPromptText("Year");
		
		grid.add(new Label("Farm Name:"), 0, 0);
		grid.add(farmName, 1, 0);
		grid.add(new Label("Year:"), 0, 1);
		grid.add(year, 1, 1);

		Node dispButton = this.getDialogPane().lookupButton(displayButtonType);
		// can change to true to add reqs to report
		dispButton.setDisable(false);
		//puts in display pane
		this.getDialogPane().setContent(grid);
		
		//dictates what occurs when display pressed
		this.setResultConverter(dialogButton -> {
			if (dialogButton == displayButtonType) {
				//get year and name
				int curYear = Integer.parseInt(year.getText());
				String farmId = farmName.getText();
				MilkList yearMilk = new MilkList();
				//gather all milk data for year into list
				for (MilkData data : mainList) {
					if (data.getDate().getYear() == curYear) {
						yearMilk.add(data);
					}
				}
				//gather the farm data for that year into diff list
				MilkList farmList = new MilkList();
				for (MilkData data : yearMilk) {
					if (data.getFarmName().equals(farmId)) {
						farmList.add(data);
					}
				}
				//sort list by months
				Collections.sort(farmList, new SortByDate());
				MilkList comp = new MilkList();
				//now makes new data that composites all of the farms contributions in a month together
				for (int j = 1; j < 13; j++) {
					int monthWeight = monthComposite(farmList, j); //weight contributed in month
					MilkData toAdd = new MilkData(farmId, monthWeight, 1, Month.of(j), curYear); //1 is filler day
					comp.add(toAdd);
				}
				
				//new table to display
				MilkStatsTable newTable = new MilkStatsTable(comp);
				MilkTable opTable = newTable.getTable();
				opTable.getColumns().remove(1); //removes the default percent column
				opTable.getColumns().remove(2); //removes the default date column
				
				TableColumn<MilkData, String> milkPer = new TableColumn<>("Milk % of Month Total");
				TableColumn<MilkData, String> month = new TableColumn<>("Month");
				//will calculate the total milk in a month by all farms that year and use that for percents
				milkPer.setCellValueFactory(new Callback<CellDataFeatures<MilkData, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<MilkData, String> p) {
						return new SimpleStringProperty(Double.toString(Math.floor(1000*p.getValue().getPercentOf(
								monthComposite(yearMilk, p.getValue().getDate().getMonthValue())) * 100)/1000) + "%");
					}
				});
				//will exclusively display month
				month.setCellValueFactory(new Callback<CellDataFeatures<MilkData, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<MilkData, String> p) {
						return new SimpleStringProperty(p.getValue().getDate().getMonth().toString());
					}
				});
				opTable.getColumns().addAll(milkPer, month);
				
				//display stats customized for farm report
				newTable.monthStatistics(comp);
				//displays dialog of results
				Dialog<MilkList> dialog = new Dialog<>();
				dialog.getDialogPane().setContent(newTable);
				ButtonType okButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(okButtonType);
				dialog.show();
			}
			return null;
		});
	}

	/**
	 * Finds how much milk total was contributed in a month 
	 * @param compList
	 * @param month
	 * @return monthWeight total weight of milk contributed in list in that month
	 */
	private int monthComposite(MilkList compList, int month) {
		int monthWeight = 0;
		for (MilkData data : compList) {
			if (data.getDate().getMonthValue() == month) {
				monthWeight += data.getMilkWeight();
			}
		}
		return monthWeight;
	}

	/**
	 * Comparator to allow for easy sorting of data by date
	 * @author AJDER
	 *
	 */
	public class SortByDate implements Comparator<MilkData> {

		@Override
		/**
		 * Overrides compare method to make milk data sort by date
		 */
		public int compare(MilkData o1, MilkData o2) {
			return o1.getDate().compareTo(o2.getDate());
		}
	}

}