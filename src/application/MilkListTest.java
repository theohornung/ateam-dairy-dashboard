package application;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MilkListTest {

	MilkList milkList;
	@BeforeEach
	void setUp() throws Exception {
		milkList = new MilkList();
		milkList.add(new MilkData("Farm 1", 1000, 26, Month.MARCH, 2015));
		milkList.add(new MilkData("Farm 1", 100, 5, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 10, 15, Month.APRIL, 2015));
		milkList.add(new MilkData("Farm 2", 600, 5, Month.DECEMBER, 2017));
		milkList.add(new MilkData("Farm 3", 10000, 5, Month.JULY, 2019));
		milkList.add(new MilkData("Farm 1", 0, 5, Month.AUGUST, 2020));
		milkList.add(new MilkData("Farm 3", 999, 5, Month.MARCH, 9999));
	}

	@AfterEach
	void tearDown() throws Exception {
		milkList = null;
	}

	@Test
	void test001_Add_Milk_Singular() {
		fail("Not yet implemented");
	}
	
	@Test
	void test002_Add_Milk_Multiple() {
		fail("Not yet implemented");
	}

	@Test
	void test003_Add_Milk() {
		fail("Not yet implemented");
	}
}
