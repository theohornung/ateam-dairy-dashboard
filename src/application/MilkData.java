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

	public MilkData(String farm, double weight, int day, Month month, int year) {
		farmName = farm;
		milkWeight = weight;
		date = LocalDate.of(year, month, day);
	}

	public String getFarmName() {
		return farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

	public double getMilkWeight() {
		return milkWeight;
	}

	public void setMilkWeight(double milkWeight) {
		this.milkWeight = milkWeight;
	}

	public void setDate(int day, Month month, int year) {
		date = LocalDate.of(year, month, day);
	}

	public LocalDate getDate() {
		return date;
	}

	public double getPercentOf(int total) {
		return milkWeight / total;
	}

}
