package application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test case for FileService.java
 */
class FileServiceTest {

	IFileService underTest;

	@BeforeEach
	void setUp() throws Exception {
		underTest = new FileService();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Assert that a 'small' csv file can be accurately read
	 */
	@Test
	void test01_readCsv_Reads_Data_For_One_Small_Month() {
		// arrange
		List<MilkData> data = new ArrayList<MilkData>();

		// act
		try {
			data = underTest.readCsv("test-files/2019-1.csv");
			MilkData item0 = data.get(0);
			MilkData item50 = data.get(50);

			// assert
			assertEquals(93, data.size());
			assertEquals("Farm 0", item0.getFarmName());
			assertEquals(6760, item0.getMilkWeight(), 0.000001);
			assertEquals(1, item0.getDate().getDayOfMonth());
			assertEquals(Month.JANUARY, item0.getDate().getMonth());
			assertEquals(2019, item0.getDate().getYear());

			assertEquals("Farm 2", item50.getFarmName());
			assertEquals(3558, item50.getMilkWeight(), 0.000001);
			assertEquals(17, item50.getDate().getDayOfMonth());
			assertEquals(Month.JANUARY, item50.getDate().getMonth());
			assertEquals(2019, item50.getDate().getYear());

		} catch (FileNotFoundException e) {
			fail("test01: unexpected exception " + e.getMessage());
		}
	}
	
	/**
	 * Assert that a 'large' csv file can be accurately read
	 */
	@Test
	void test02_readCsv_Reads_Data_For_One_Large_Month() {
		// arrange
		List<MilkData> data = new ArrayList<MilkData>();

		// act
		try {
			data = underTest.readCsv("test-files/2019-12.csv");
			MilkData item0 = data.get(0);
			MilkData item50 = data.get(50);

			// assert
			assertEquals(2844, data.size());
			assertEquals("Farm 18", item0.getFarmName());
			assertEquals(2318, item0.getMilkWeight(), 0.000001);
			assertEquals(1, item0.getDate().getDayOfMonth());
			assertEquals(Month.DECEMBER, item0.getDate().getMonth());
			assertEquals(2019, item0.getDate().getYear());

			assertEquals("Farm 75", item50.getFarmName());
			assertEquals(7656, item50.getMilkWeight(), 0.000001);
			assertEquals(1, item50.getDate().getDayOfMonth());
			assertEquals(Month.DECEMBER, item50.getDate().getMonth());
			assertEquals(2019, item50.getDate().getYear());

		} catch (FileNotFoundException e) {
			fail("test01: unexpected exception " + e.getMessage());
		}
	}
	
	/**
	 * Assert that an exception is thrown when a poorly formatted file is given
	 */
	@Test
	void test03_readCsv_Throws_When_Reading_Missing() {
		// act
		try {
			underTest.readCsv("test-files/2019-4-missing.csv");

			// assert
			fail("test03: failed to throw exception when parsing invalid data");

		} catch (FileNotFoundException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
			// pass
		}
	}
	
	/**
	 * Assert that an exception is thrown when a file with poorly formatted data is given
	 */
	@Test
	void test04_readCsv_Throws_When_Reading_Error() {
		// act
		try {
			underTest.readCsv("test-files/2019-8-error.csv");

			// assert
			fail("test04: failed to throw exception when parsing invalid data");

		} catch (FileNotFoundException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
			// pass
		}
	}
	
	/**
	 * Assert that data is correctly written to a file
	 */
	@Test
	void test05_write_Csv() {
		// arrange
		List<MilkData> data = new ArrayList<MilkData>();
		data.add(new MilkData("test05-1", 1234, 1, Month.JANUARY, 2000));
		data.add(new MilkData("test05-2", 2345, 2, Month.JANUARY, 2001));
		data.add(new MilkData("test05-3", 3456, 3, Month.JANUARY, 2002));
		data.add(new MilkData("test05-4", 4567, 4, Month.JANUARY, 2003));
		data.add(new MilkData("test05-6", 5678, 5, Month.JANUARY, 2004));
		
		String path = System.getProperty("user.dir") + "\\test-files\\out\\test05.csv";
		
		// act
		try {
			underTest.writeCsv(path, data);
		}
		// assert
		catch (Exception e) {
			e.printStackTrace();
			fail("test05: unexpected exception " + e.getMessage());
		}
		// assert -> read from file, check that the contents are correct
		Scanner sc = null;
		try {
			sc = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			fail("test05: unexpected exception when creating Scanner");
		}
		int i = 0;
		while (sc.hasNextLine()) {
			String[] testData = sc.nextLine().split(",");
			String[] date = testData[0].split("-");
			assertEquals(Integer.parseInt(date[0]), data.get(i).getDate().getYear());
			assertEquals(Integer.parseInt(date[1]), data.get(i).getDate().getMonth().ordinal() + 1);
			assertEquals(Integer.parseInt(date[2]), data.get(i).getDate().getDayOfMonth());
			
			assertEquals(testData[1], data.get(i).getFarmName());
			assertEquals(Double.parseDouble(testData[2]), data.get(i).getMilkWeight(), 0.0000001);
			i++;
		}
	}

}
