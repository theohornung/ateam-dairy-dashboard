package application;

import java.time.LocalDate;
import java.time.Month;

/**
 * A POJO to represent data the quantity of milk produced at a farm on a given
 * date
 */
public class MilkData {
	private String farmName;
	private double milkWeight;
	private LocalDate date;

	/**
	 * Creates a new MilkData instance
	 * 
	 * @param farm the farm name
	 * @param weight the weight of milk produced
	 * @param day the day that the milk was produced
	 * @param month the month that the milk was produced
	 * @param year the year that the milk was produced
	 */
	public MilkData(String farm, double weight, int day, Month month, int year) {
		farmName = farm;
		milkWeight = weight;
		date = LocalDate.of(year, month, day);
	}

	/**
	 * Accessor method to get the farm name
	 * @return the farm name
	 */
	public String getFarmName() {
		return farmName;
	}

	/**
	 * Accessor method to get the weight of milk produced
	 * @return the weight of milk produced
	 */
	public double getMilkWeight() {
		return milkWeight;
	}

	/**
	 * Accessor method to get the date that the milk was produced
	 * @return the date that the milk was produced
	 */
	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * Gets a formatted string that represents the date that the milk was produced
	 * @return formatted string of the date that the milk was produced
	 */
	public String getFormattedDate() {
		return date.getMonth().getValue() + "/" + date.getDayOfMonth() + "/" + date.getYear();
	}

	/**
	 * Given a total amount of milk, calculates the percent of that milk that this obj contributes 
	 * @param total the total amount of milk
	 * @return the percent of the total amount of milk contributed by this obj
	 */
	public double getPercentOf(int total) {
		return milkWeight / total;
	}

}
