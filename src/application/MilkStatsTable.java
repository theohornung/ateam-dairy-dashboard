package application;

import java.text.Format;
import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class MilkStatsTable extends HBox{
	@SuppressWarnings("unchecked")
	public MilkStatsTable(MilkList milkList) {
		//making table and filling it with data from milkList
		TableView<MilkData> table = new TableView<>();
		TableColumn<MilkData, String> farm = new TableColumn<>("Farm");
		TableColumn<MilkData, String> milkPer = new TableColumn<>("Milk %");
		TableColumn<MilkData, String> milkWeight = new TableColumn<>("Milk Weight (lbs)");
		final ObservableList<MilkData> data = FXCollections.observableArrayList(milkList);
		
		farm.setCellValueFactory(new Callback<CellDataFeatures<MilkData, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MilkData, String> p) {
				return new SimpleStringProperty(p.getValue().getFarmName());
			}
		});

		milkPer.setCellValueFactory(new Callback<CellDataFeatures<MilkData, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MilkData, String> p) {
				return new SimpleStringProperty(Double.toString(p.getValue().getPercentOf((int) milkList.getSum()) * 100) + "%");
			}
		});

		milkWeight.setCellValueFactory(new Callback<CellDataFeatures<MilkData, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MilkData, String> p) {
				return new SimpleStringProperty(Double.toString(p.getValue().getMilkWeight()));
			}
		});
		table.getColumns().addAll(farm, milkPer, milkWeight);
		table.setItems(data);
		
		Format format = new SimpleDateFormat("MM/dd/yyyy");
		Label max = new Label("Most Milk in a day on" + milkList.getMax().getFormattedDate() + " with " + milkList.getMax().getMilkWeight() + " pounds.");
		Label min = new Label("Most Milk in a day on" + milkList.getMin().getFormattedDate() + " with " + milkList.getMin().getMilkWeight() + " pounds.");
		Label average = new Label("Average milk on entry is " + milkList.getMean() + " pounds.");
		Label total = new Label("Total milk produced is " + milkList.getSum() + " pounds.");
		VBox vbox = new VBox();
		vbox.getChildren().addAll(max, min, average, total);
		
		this.getChildren().addAll(table, vbox);
	}
}
