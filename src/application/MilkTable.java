package application;

import application.interfaces.IMilkList;
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
	/**
	 * Creates a new MilkTable to display milk production data points
	 * @param milkList the list of milk production data points to display
	 */
	@SuppressWarnings("unchecked")
	public MilkTable(IMilkList milkList) {
		this.setPrefWidth(450);
		TableColumn<MilkData, String> farm = new TableColumn<>("Farm");
		TableColumn<MilkData, String> milkPer = new TableColumn<>("Milk %");
		TableColumn<MilkData, String> milkWeight = new TableColumn<>("Milk Weight (lbs)");
		milkWeight.setMinWidth(120);
		TableColumn<MilkData, String> date = new TableColumn<>("Date");
		final ObservableList<MilkData> data = FXCollections.observableArrayList(milkList);
		
		// describes how each column should handle milk data
		date.setCellValueFactory(new Callback<CellDataFeatures<MilkData, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MilkData, String> p) {
				return new SimpleStringProperty(p.getValue().getFormattedDate());
			}
		});
		
		farm.setCellValueFactory(new Callback<CellDataFeatures<MilkData, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MilkData, String> p) {
				return new SimpleStringProperty(p.getValue().getFarmName());
			}
		});

		milkPer.setCellValueFactory(new Callback<CellDataFeatures<MilkData, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MilkData, String> p) {
				return new SimpleStringProperty(Double.toString(Math.floor(1000*p.getValue().getPercentOf((int)milkList.getSum()) * 100)/1000) + "%");
			}
		});

		milkWeight.setCellValueFactory(new Callback<CellDataFeatures<MilkData, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MilkData, String> p) {
				return new SimpleStringProperty(Integer.toString((int) p.getValue().getMilkWeight()));
			}
		});

		this.getColumns().addAll(farm, milkPer, milkWeight, date);

		this.setItems(data);
	}

	
	/**
	 * reset the table
	 */
	public void reset() {
		this.getItems().removeAll(this.getItems());
	}
}
