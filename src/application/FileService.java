package application;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import application.interfaces.IFileService;

/**
 * IFileService implementation to expose .csv file read and write functionality
 */
public class FileService implements IFileService {

	/**
	 * @see IFileService#readCsv(String)
	 */
	@Override
	public List<MilkData> readCsv(String filePath) throws IOException {
		return Files.lines(Paths.get(filePath))
				.filter(line -> !line.equals("date,farm_id,weight"))
				.map(line -> parseMilkData(line))
				.collect(Collectors.toList());
	}

	/**
	 * Assumed csv format: date,farm_id,weight
	 * 
	 * @param raw
	 * @return
	 */
	private MilkData parseMilkData(String raw) {

		String[] data = raw.split(",");

		String farmName = data[1];
		double weight = Double.parseDouble(data[2]);
		int year = parseYear(data[0]);
		Month month = parseMonth(data[0]);
		int day = parseDay(data[0]);

		MilkData next = new MilkData(farmName, weight, day, month, year);

		return next;
	}

	/**
	 * Helper method to parse year from csv. Assumed date format: YYYY-(M)M-dd
	 * 
	 * @param rawDate the date component of the raw csv
	 * @return the year
	 */
	private int parseYear(String rawDate) {
		return Integer.parseInt(rawDate.split("-")[0]);
	}

	/**
	 * Helper method to parse month from csv
	 * 
	 * @param rawDate the date component of the raw csv
	 * @return the month
	 */
	private Month parseMonth(String rawDate) {
		return Month.of(Integer.parseInt(rawDate.split("-")[1]));
	}

	/**
	 * Helper method to parse day from csv
	 * 
	 * @param rawDate the date component of the raw csv
	 * @return the day
	 */
	private int parseDay(String rawDate) {
		return Integer.parseInt(rawDate.split("-")[2]);
	}

	/**
	 * @see IFileService#writeCsv(String, List)
	 */
	@Override
	public void writeCsv(String fileName, List<MilkData> data) throws IOException {
		File outFile = new File(fileName);
		outFile.createNewFile();

		PrintWriter writer = new PrintWriter(fileName);

		// write all milk data
		for (MilkData milkData : data) {
			// get farm
			String farm = milkData.getFarmName();

			// get weight
			double weight = milkData.getMilkWeight();

			// get date
			LocalDate localDate = milkData.getDate();
			int year = localDate.getYear();
			int month = localDate.getMonth().ordinal() + 1;
			int day = localDate.getDayOfMonth();
			String date = year + "-" + month + "-" + day;

			// format and write data
			String out = date + "," + farm + "," + weight + "\n";
			writer.write(out);
		}

		writer.close();

	}

}
