package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

/**
 * A table to display milk production data points
 */
public class MilkTable extends TableView<MilkData> {

	private int totalMilk;

	@SuppressWarnings("unchecked")
	public MilkTable() {

		totalMilk = 0;

		TableColumn<MilkData, String> farm = new TableColumn<>("Farm");
		TableColumn<MilkData, String> milkPer = new TableColumn<>("Milk %");
		TableColumn<MilkData, String> milkWeight = new TableColumn<>("Milk Weight (lbs)");

		// describes how each column should handle milk data
		final ObservableList<MilkData> data = FXCollections.observableArrayList();
		farm.setCellValueFactory(new Callback<CellDataFeatures<MilkData, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MilkData, String> p) {
				return new SimpleStringProperty(p.getValue().getFarmName());
			}
		});

		milkPer.setCellValueFactory(new Callback<CellDataFeatures<MilkData, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MilkData, String> p) {
				return new SimpleStringProperty(Double.toString(p.getValue().getPercentOf(totalMilk) * 100) + "%");
			}
		});

		milkWeight.setCellValueFactory(new Callback<CellDataFeatures<MilkData, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MilkData, String> p) {
				return new SimpleStringProperty(Double.toString(p.getValue().getMilkWeight()));
			}
		});

		this.getColumns().addAll(farm, milkPer, milkWeight);

		this.setItems(data);
	}

	public int getTotalMilk() {
		return totalMilk;
	}

	public void addMilkWeight(double additionalMilk) {
		totalMilk += additionalMilk;
	}
	
	/**
	 * reset the table
	 */
	public void reset() {
		totalMilk = 0;
		this.getItems().removeAll(this.getItems());
	}
}
