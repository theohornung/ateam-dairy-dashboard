package application;


import application.interfaces.IMilkList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class MilkStatsTable extends HBox{

	private MilkTable table;
	VBox vbox;
	public MilkStatsTable(IMilkList milkList) {

		super(10);
		//making table and filling it with data from milkList
		table = new MilkTable(milkList);

		//creating data analysis
		this.getChildren().addAll(table);
	}
	
	
	public void updateStatistics(IMilkList milkList) {
		final ObservableList<MilkData> data = FXCollections.observableArrayList(milkList);
		table.reset();
		table.setItems(data);
		//creating data analysis
		Label max = new Label("Most Milk:\n" + milkList.getMax().getFarmName() + " on " + milkList.getMax().getFormattedDate() + " with " + (int) milkList.getMax().getMilkWeight() + " pounds.");
		Label min = new Label("Least Milk:\n" + milkList.getMin().getFarmName() + " on " + milkList.getMin().getFormattedDate() + " with " + (int) milkList.getMin().getMilkWeight() + " pounds.");
		Label average = new Label("Average milk on entry is " + (int) milkList.getMean() + " pounds.");
		Label total = new Label("Total milk produced is " + (int) milkList.getSum() + " pounds.");
		vbox = new VBox(10);
		vbox.setPrefWidth(400);
		vbox.getChildren().addAll(max, min, average, total);
		this.getChildren().clear();
		this.getChildren().addAll(table, vbox);
	}
	
	public MilkTable getTable(){
		return table;
	}
}
