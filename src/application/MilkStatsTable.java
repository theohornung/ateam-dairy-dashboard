package application;

import java.time.Month;

import application.interfaces.IMilkList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Custom UI element to display milk data in a table and statistics about
 * the milk data.
 */
public class MilkStatsTable extends HBox{

	private MilkTable table; // table display for data
	VBox vbox;
	
	/**
	 * Constructor to create new MilkStatsTable instance
	 * @param milkList the list of milk data to display
	 */
	public MilkStatsTable(IMilkList milkList) {

		super(10);
		//making table and filling it with data from milkList
		table = new MilkTable(milkList);

		//creating data analysis
		this.getChildren().addAll(table);
	}
	
	
	/**
	 * Updates the statistics displayed
	 * @param milkList the list of data to calculate statistics from
	 */
	public void updateStatistics(IMilkList milkList) {
		final ObservableList<MilkData> data = FXCollections.observableArrayList(milkList);
		table.reset();
		table.setItems(data);
		//creating data analysis
		String mean = null;
		if (Double.isNaN(milkList.getMean())) {
			mean = "0";
		}
		else {
			mean = String.format("%.1f", milkList.getMean());
		}
		Label max = new Label("Most Milk:\n" + milkList.getMax().getFarmName() + " on " + milkList.getMax().getFormattedDate() + " with " + (int) milkList.getMax().getMilkWeight() + " pounds.");
		Label min = new Label("Least Milk:\n" + milkList.getMin().getFarmName() + " on " + milkList.getMin().getFormattedDate() + " with " + (int) milkList.getMin().getMilkWeight() + " pounds.");
		Label average = new Label("Average milk on entry is " + mean + " pounds.");
		Label total = new Label("Total milk produced is " + String.format("%.1f", milkList.getSum())  + " pounds.");
		vbox = new VBox(10);
		vbox.setPrefWidth(400);
		vbox.getChildren().addAll(max, min, average, total);
		this.getChildren().clear();
		this.getChildren().addAll(table, vbox);
	}
	
	/**
	 * Calculates and displays statistics about milk data by month for a single farm
	 * @param milkList the list of data to calculate statistics from
	 */
	public void monthStatistics(IMilkList milkList) {
		final ObservableList<MilkData> data = FXCollections.observableArrayList(milkList);
		table.reset();
		table.setItems(data);
		//creating data analysis
		String farmName = milkList.getMax().getFarmName();
		String mean = null;
		if (Double.isNaN(milkList.getMean())) {
			mean = "0";
		}
		else {
			mean = String.format("%.1f", milkList.getMean());
		}
		Label max = new Label("Most Milk:\n" + farmName + " in " + milkList.getMax().getDate().getMonth().toString() + " with " + (int) milkList.getMax().getMilkWeight() + " pounds.");
		Label min = new Label("Least Milk:\n" + farmName + " in " + milkList.getMin().getDate().getMonth().toString() + " with " + (int) milkList.getMin().getMilkWeight() + " pounds.");
		Label average = new Label("Average milk produced by " + farmName + " in each month is "+ mean + " pounds.");
		Label total = new Label("Total milk produced in " + milkList.getMax().getDate().getYear() + " by " + farmName + " is " + String.format("%.1f", milkList.getSum()) + " pounds.");
		vbox = new VBox(10);
		vbox.setPrefWidth(400);
		vbox.getChildren().addAll(max, min, average, total);
		this.getChildren().clear();
		this.getChildren().addAll(table, vbox);
	}
	
	/**
	 * Calculates and displays statistics about milk data by year
	 * @param milkList the list of data to calculate statistics from
	 */
	public void yearStatistics(IMilkList milkList, int curYear) {
		final ObservableList<MilkData> data = FXCollections.observableArrayList(milkList);
		table.reset();
		table.setItems(data);
		//creating data analysis
		String year = Integer.toString(curYear);
		String mean = null;
		if (Double.isNaN(milkList.getMean())) {
			mean = "0";
		}
		else {
			mean = String.format("%.1f", milkList.getMean());
		}
		Label max = new Label("Most Milk in " + year + ":\n" + milkList.getMax().getFarmName() + " with " + (int) milkList.getMax().getMilkWeight() + " pounds.");
		Label min = new Label("Least Milk in " + year + ":\n" + milkList.getMin().getFarmName()  + " with " + (int) milkList.getMin().getMilkWeight() + " pounds.");
		Label average = new Label("Average milk produced by farms in " + year + " is "+ mean + " pounds.");
		Label total = new Label("Total milk produced in " + year + " is " + String.format("%.1f", milkList.getSum()) + " pounds.");
		vbox = new VBox(10);
		vbox.setPrefWidth(400);
		vbox.getChildren().addAll(max, min, average, total);
		this.getChildren().clear();
		this.getChildren().addAll(table, vbox);
	}
	
	/**
	 * Calculates and displays statistics about milk produced by a single farm for a month report
	 * @param milkList
	 */
	public void monthreStatistics(IMilkList milkList, Month curMonth, int curYear) {
		final ObservableList<MilkData> data = FXCollections.observableArrayList(milkList);
		table.reset();
		table.setItems(data);
		//creating data analysis
		String month = curMonth.toString();
		String year = Integer.toString(curYear);
		String mean = null;
		if (Double.isNaN(milkList.getMean())) {
			mean = "0";
		}
		else {
			mean = String.format("%.1f", milkList.getMean());
		}
		Label years = new Label("Stats for year " + year + " in month " + month);
		Label max = new Label("Most Milk:\n" + milkList.getMax().getFarmName() + " in " + month + " with " + (int) milkList.getMax().getMilkWeight() + " pounds.");
		Label min = new Label("Least Milk:\n" + milkList.getMin().getFarmName() + " in " + month + " with " + (int) milkList.getMin().getMilkWeight() + " pounds.");
		Label average = new Label("Average milk produced in " + month + " is " + mean + " pounds.");
		Label total = new Label("Total milk produced in " + month + " is " + String.format("%.1f", milkList.getSum()) + " pounds.");
		vbox = new VBox(10);
		vbox.setPrefWidth(400);
		vbox.getChildren().addAll(years, max, min, average, total);
		this.getChildren().clear();
		this.getChildren().addAll(table, vbox);
	}
	
	/**
	 * Accessor method to get the MilkTable contained in this obj
	 * @return the MilkTable instance contained by this obj
	 */
	public MilkTable getTable(){
		return table;
	}
}
