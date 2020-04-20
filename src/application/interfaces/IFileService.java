package application.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import application.MilkData;

/**
 * Interface to define file r/w functionality for milk data
 */
public interface IFileService {

	/**
	 * Defines file read functionality
	 * @param filePath
	 * @return a list of milk data points
	 * @throws FileNotFoundException 
	 */
	public List<MilkData> readCsv(String filePath) throws FileNotFoundException;
	
	/**
	 * Define file write functionality
	 * @param fileName
	 * @param data
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public void writeCsv(String fileName, List<MilkData> data) throws IOException;
}
