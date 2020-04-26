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
 * A dialog that takes in data to create new milk data
 */
public class FarmRepDialog extends Dialog<MilkList> {

	@SuppressWarnings("unchecked")
	public FarmRepDialog(IMilkList mainList) {
		this.setTitle("Farm Report");
		this.setHeaderText("Please put in a farm name and year");

		ButtonType displayButtonType = new ButtonType("Display", ButtonData.OK_DONE);
		this.getDialogPane().getButtonTypes().addAll(displayButtonType, ButtonType.CANCEL);

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

		Node removeButton = this.getDialogPane().lookupButton(displayButtonType);
		// can change to true to add reqs to report
		removeButton.setDisable(false);

		this.getDialogPane().setContent(grid);
		
		this.setResultConverter(dialogButton -> {
			if (dialogButton == displayButtonType) {
				int curYear = Integer.parseInt(year.getText());
				String farmId = farmName.getText();
				MilkList yearMilk = new MilkList();
				for (MilkData data : mainList) {
					if (data.getDate().getYear() == curYear) {
						yearMilk.add(data);
					}
				}
				MilkList farmList = new MilkList();
				for (MilkData data : yearMilk) {
					if (data.getFarmName().equals(farmId)) {
						farmList.add(data);
					}
				}
				Collections.sort(farmList, new SortByDate());
				MilkList comp = new MilkList();
				for (int j = 1; j < 13; j++) {
					int monthWeight = monthComposite(farmList, j);
					MilkData toAdd = new MilkData(farmId, monthWeight, 1, Month.of(j), curYear);
					comp.add(toAdd);
				}
				
				
				
				MilkStatsTable newTable = new MilkStatsTable(comp);
				MilkTable opTable = newTable.getTable();
				opTable.getColumns().remove(1); //removes the default percent column
				opTable.getColumns().remove(2); //removes the default date column
				
				TableColumn<MilkData, String> milkPer = new TableColumn<>("Milk % of Month Total");
				TableColumn<MilkData, String> month = new TableColumn<>("Month");
				milkPer.setCellValueFactory(new Callback<CellDataFeatures<MilkData, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<MilkData, String> p) {
						return new SimpleStringProperty(Double.toString(Math.floor(1000*p.getValue().getPercentOf(
								monthComposite(yearMilk, p.getValue().getDate().getMonthValue())) * 100)/1000) + "%");
					}
				});
				month.setCellValueFactory(new Callback<CellDataFeatures<MilkData, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<MilkData, String> p) {
						return new SimpleStringProperty(p.getValue().getDate().getMonth().toString());
					}
				});
				opTable.getColumns().addAll(milkPer, month);
				
				newTable.monthStatistics(comp);
				Dialog<MilkList> dialog = new Dialog<>();
				dialog.getDialogPane().setContent(newTable);
				ButtonType okButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(okButtonType);
				dialog.show();
			}
			return null;
		});
	}

	private int monthComposite(MilkList compList, int month) {
		int monthWeight = 0;
		for (MilkData data : compList) {
			if (data.getDate().getMonthValue() == month) {
				monthWeight += data.getMilkWeight();
			}
		}
		return monthWeight;
	}

	public class SortByDate implements Comparator<MilkData> {

		@Override
		public int compare(MilkData o1, MilkData o2) {
			// TODO Auto-generated method stub
			return o1.getDate().compareTo(o2.getDate());
		}
	}

}