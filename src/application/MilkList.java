package application;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import application.interfaces.IMilkList;

public class MilkList extends ArrayList<MilkData> implements IMilkList {

	/**
	 * We don't use this. This is only here to appease an eclipse warning.
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * creates an ArrayList with all instances inclusively between the two years entered 
	 * @param begin - starting year
	 * @param end - ending year
	 * @return - MilkList of all MilkData between the starting and ending year
	 */
	public MilkList getFromYears(int begin, int end){
		MilkList yearMilk = new MilkList();
		for( MilkData data : this) {
			if(data.getDate().getYear() >= begin && data.getDate().getYear() <= end) {
				yearMilk.add(data);
			}
		}
		return yearMilk;
	}
	
	/**
	 * creates an ArrayList with all instances within the year provided
	 * @param year
	 * @return - MilkList of all MilkData during the year
	 */
	public MilkList getFromYear(int year) {
		return getFromYears(year, year);
	}
	
	/**
	 * Returns all milk values over a span of months
	 * @param year
	 * @param begin
	 * @param end
	 * @return MilkList
	 */
	public MilkList getFromMonths(int year, int begin, int end){
		MilkList monthMilk = new MilkList();
		for( MilkData data : this.getFromYear(year)) {
			if(data.getDate().getMonthValue() >= begin && data.getDate().getMonthValue() <= end) {
				monthMilk.add(data);
			}
		}
		return monthMilk;		
	}
	
	/**
	 * gets all milk values from a specific month
	 * @param year
	 * @param month
	 * @return - MilkList
	 */
	public MilkList getFromMonth(int year, int month){
		return getFromMonths(year, month, month);
	}
	
	/**
	 * gets all milk values from a specific farm
	 * @param farmID
	 * @return - MilkList
	 */
	public MilkList getFromFarm(String farmID){
		if(farmID.equals("")) return this;
		 MilkList farmMilk = new MilkList();
		for(MilkData data : this) {
			if(data.getFarmName().equals(farmID)) {
				farmMilk.add(data);
			}
		}
		return farmMilk;
	}
	/**
	 * Removes all farm milk that has matching farm name and date
	 * @param name
	 * @param date
	 */
	public void remove(String name, LocalDate date) {
		ArrayList<MilkData> removal = new ArrayList<MilkData>(); //avoid concurrent remove error
		for (MilkData data : this) {
			if (data.getFarmName().equals(name) && data.getDate().equals(date)) {
				removal.add(data); //gets all matching data to remove
			}
		}
		for (MilkData data : removal) {
			this.remove(data); //goes back and removes data
		}
	}
	/**
	 * finds the MilkData with the minimum milk weight in the list
	 * @return - MilkData
	 */
	public MilkData getMin() {
		if(this.size() == 0) return new MilkData("Empty", 0, 1, Month.JANUARY, 0);
		MilkData min = null;
		for( MilkData m : this) {
			if(min == null) {
				min = m;
			}
			if(min.getMilkWeight() > m.getMilkWeight()) {
				min = m;
			}
		}
		return min;
	}
	
	/**
	 * finds the MilkData with the maximum milk weight in the list
	 * @return - MilkData
	 */
	public MilkData getMax() {
		if(this.size() == 0) return new MilkData("Empty", 0, 1, Month.JANUARY, 0);
		MilkData max = null;
		for( MilkData m : this) {
			if(max == null) {
				max = m;
			}
			if(max.getMilkWeight() < m.getMilkWeight()) {
				max = m;
			}
		}
		return max;
	}
	
	/**
	 * find the mean value of milk weight in a MilkList
	 * @return - mean value
	 */
	public double getMean() {
		double mean = 0;
		for(MilkData data : this) {
			mean += data.getMilkWeight();
		}
		return mean / this.size(); 
	}


	/**
	 * return sum of milkWeights
	 */
	@Override
	public double getSum() {
		double sum = 0;
		for(MilkData data : this) {
			sum += data.getMilkWeight();
		}
		return sum;
	}
}