package application;

import java.util.List;

/**
 * Interface to define file r/w functionality for milk data
 */
public interface IFileService {

	/**
	 * Defines file read functionality
	 * @param filePath
	 * @return a list of milk data points
	 */
	public List<MilkData> readCsv(String filePath);
	
	/**
	 * Define file write functionality
	 * @param fileName
	 * @param data
	 */
	public void writeCsv(String fileName, List<MilkData> data);
}
