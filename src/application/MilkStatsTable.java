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
		Label average = new Label("Average milk on entry is " + String.format("%.1f", milkList.getMean()) + " pounds.");
		Label total = new Label("Total milk produced is " + String.format("%.1f", milkList.getSum())  + " pounds.");
		vbox = new VBox(10);
		vbox.setPrefWidth(400);
		vbox.getChildren().addAll(max, min, average, total);
		this.getChildren().clear();
		this.getChildren().addAll(table, vbox);
	}
	
	public void monthStatistics(IMilkList milkList) {
		final ObservableList<MilkData> data = FXCollections.observableArrayList(milkList);
		table.reset();
		table.setItems(data);
		//creating data analysis
		String farmName = milkList.getMax().getFarmName();
		Label max = new Label("Most Milk:\n" + farmName + " in " + milkList.getMax().getDate().getMonth().toString() + " with " + (int) milkList.getMax().getMilkWeight() + " pounds.");
		Label min = new Label("Least Milk:\n" + farmName + " in " + milkList.getMin().getDate().getMonth().toString() + " with " + (int) milkList.getMin().getMilkWeight() + " pounds.");
		Label average = new Label("Average milk produced by " + farmName + " in each month is "+ String.format("%.1f", milkList.getMean()) + " pounds.");
		Label total = new Label("Total milk produced in " + milkList.getMax().getDate().getYear() + " by " + farmName + " is " + String.format("%.1f", milkList.getSum()) + " pounds.");
		vbox = new VBox(10);
		vbox.setPrefWidth(400);
		vbox.getChildren().addAll(max, min, average, total);
		this.getChildren().clear();
		this.getChildren().addAll(table, vbox);
	}
	
	public void yearStatistics(IMilkList milkList) {
		final ObservableList<MilkData> data = FXCollections.observableArrayList(milkList);
		table.reset();
		table.setItems(data);
		//creating data analysis
		String year = Integer.toString(milkList.getMax().getDate().getYear());
		Label max = new Label("Most Milk in " + year + ":\n" + milkList.getMax().getFarmName() + " with " + (int) milkList.getMax().getMilkWeight() + " pounds.");
		Label min = new Label("Least Milk in " + year + ":\n" + milkList.getMin().getFarmName()  + " with " + (int) milkList.getMin().getMilkWeight() + " pounds.");
		Label average = new Label("Average milk produced by farms in " + year + " is "+ String.format("%.1f", milkList.getMean()) + " pounds.");
		Label total = new Label("Total milk produced in " + year + " is " + String.format("%.1f", milkList.getSum()) + " pounds.");
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
