package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.MilkData;
import application.MilkList;
import application.interfaces.IMilkList;

/**
 * Test case for IMilkList.java
 */
class MilkListTest {

	IMilkList milkList;

	@BeforeEach
	void setUp() throws Exception {
		milkList = new MilkList();
	}

	@AfterEach
	void tearDown() throws Exception {
		milkList = null;
	}

	/**
	 * Tests that the {@link IMilkList#getFromYears(int, int)} method behaves
	 * correctly
	 */
	@Test
	void test001_GetFromYears() {

		milkList.add(new MilkData("Farm 1", 1000, 26, Month.MARCH, 2015));
		milkList.add(new MilkData("Farm 1", 100, 5, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 10, 15, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 600, 5, Month.DECEMBER, 2017));
		milkList.add(new MilkData("Farm 3", 10000, 5, Month.JULY, 2019));
		milkList.add(new MilkData("Farm 1", 0, 5, Month.AUGUST, 2020));
		milkList.add(new MilkData("Farm 3", 999, 5, Month.MARCH, 9999));
		assertEquals(milkList.getFromYears(2015, 2017).size(), 4);
	}

	/**
	 * Tests that the {@link IMilkList#getFromYear(int)}) method behaves correctly
	 */
	@Test
	void test002_GetFromYear() {

		milkList.add(new MilkData("Farm 1", 1000, 26, Month.MARCH, 2015));
		milkList.add(new MilkData("Farm 1", 100, 5, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 10, 15, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 600, 5, Month.DECEMBER, 2017));
		milkList.add(new MilkData("Farm 3", 10000, 5, Month.JULY, 2019));
		milkList.add(new MilkData("Farm 1", 0, 5, Month.AUGUST, 2020));
		milkList.add(new MilkData("Farm 3", 999, 5, Month.MARCH, 9999));
		assertEquals(milkList.getFromYear(2015).size(), 3);
	}

	/**
	 * Tests that the {@link IMilkList#getFromMonths(int, int, int)} method behaves
	 * correctly
	 */
	@Test
	void test003_GetFromMonths() {

		milkList.add(new MilkData("Farm 1", 1000, 26, Month.MARCH, 2015));
		milkList.add(new MilkData("Farm 1", 100, 5, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 10, 15, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 600, 5, Month.DECEMBER, 2017));
		milkList.add(new MilkData("Farm 3", 10000, 5, Month.JULY, 2019));
		milkList.add(new MilkData("Farm 1", 0, 5, Month.AUGUST, 2020));
		milkList.add(new MilkData("Farm 3", 999, 5, Month.MARCH, 9999));
		assertEquals(milkList.getFromMonths(2015, Month.MARCH.getValue(), Month.APRIL.getValue()).size(), 3);
	}

	/**
	 * Test that the {@link IMilkList#getFromMonth(int, int)} method behaves
	 * correctly
	 */
	@Test
	void test004_GetFromMonth() {

		milkList.add(new MilkData("Farm 1", 1000, 26, Month.MARCH, 2015));
		milkList.add(new MilkData("Farm 1", 100, 5, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 10, 15, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 600, 5, Month.DECEMBER, 2017));
		milkList.add(new MilkData("Farm 3", 10000, 5, Month.JULY, 2019));
		milkList.add(new MilkData("Farm 1", 0, 5, Month.AUGUST, 2020));
		milkList.add(new MilkData("Farm 3", 999, 5, Month.MARCH, 9999));
		assertEquals(milkList.getFromMonth(2015, Month.MARCH.getValue()).size(), 1);
	}

	/**
	 * Tests that the {@link IMilkList#getFromFarm(String)} method behaves correctly
	 */
	@Test
	void test005_GetFromFarm() {

		milkList.add(new MilkData("Farm 1", 1000, 26, Month.MARCH, 2015));
		milkList.add(new MilkData("Farm 1", 100, 5, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 10, 15, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 600, 5, Month.DECEMBER, 2017));
		milkList.add(new MilkData("Farm 3", 10000, 5, Month.JULY, 2019));
		milkList.add(new MilkData("Farm 1", 0, 5, Month.AUGUST, 2020));
		milkList.add(new MilkData("Farm 3", 999, 5, Month.MARCH, 9999));
		assertEquals(milkList.getFromFarm("Farm 1").size(), 3);
	}

	/**
	 * Tests that the {@link IMilkList#getMin()} method behaves correctly
	 */
	@Test
	void test006_GetMin() {

		milkList.add(new MilkData("Farm 1", 1000, 26, Month.MARCH, 2015));
		milkList.add(new MilkData("Farm 1", 100, 5, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 10, 15, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 600, 5, Month.DECEMBER, 2017));
		milkList.add(new MilkData("Farm 3", 10000, 5, Month.JULY, 2019));
		milkList.add(new MilkData("Min", 0, 5, Month.AUGUST, 2020));
		milkList.add(new MilkData("Farm 3", 999, 5, Month.MARCH, 9999));
		assertEquals(milkList.getMin().getFarmName(), "Min");
	}

	/**
	 * Tests that the {@link IMilkList#getMax()} method behaves correctly
	 */
	@Test
	void test007_GetMax() {

		milkList.add(new MilkData("Farm 1", 1000, 26, Month.MARCH, 2015));
		milkList.add(new MilkData("Farm 1", 100, 5, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 10, 15, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 600, 5, Month.DECEMBER, 2017));
		milkList.add(new MilkData("Max", 10000, 5, Month.JULY, 2019));
		milkList.add(new MilkData("Farm 1", 0, 5, Month.AUGUST, 2020));
		milkList.add(new MilkData("Farm 3", 999, 5, Month.MARCH, 9999));
		assertEquals(milkList.getMax().getFarmName(), "Max");
	}

	/**
	 * Tests that the {@link IMilkList#getMean()} method behaves correctly
	 */
	@Test
	void test008_GetMean() {

		milkList.add(new MilkData("Farm 1", 1000, 5, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 1000, 15, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 1000, 5, Month.DECEMBER, 2017));
		milkList.add(new MilkData("Max", 0, 5, Month.JULY, 2019));
		milkList.add(new MilkData("Farm 1", 0, 5, Month.AUGUST, 2020));
		milkList.add(new MilkData("Farm 3", 0, 5, Month.MARCH, 9999));
		assertEquals(milkList.getMean(), 500);
	}

	/**
	 * Tests that the {@link IMilkList#getSum()} method behaves correctly
	 */
	@Test
	void test09_GetSum() {

		milkList.add(new MilkData("Farm 1", 1000, 5, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 1000, 15, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 1000, 5, Month.DECEMBER, 2017));
		milkList.add(new MilkData("Max", 0, 5, Month.JULY, 2019));
		milkList.add(new MilkData("Farm 1", 0, 5, Month.AUGUST, 2020));
		milkList.add(new MilkData("Farm 3", 0, 5, Month.MARCH, 9999));
		assertEquals(milkList.getSum(), 3000);
	}
}
