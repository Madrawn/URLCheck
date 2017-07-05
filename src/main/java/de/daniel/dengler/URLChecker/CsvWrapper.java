package de.daniel.dengler.URLChecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Madrawn
 * This is a class with some helper methods handling the reading and writing 
 * of a .csv file from and to an array table 
 */
public class CsvWrapper {
	
	//read file in, split by newline then by comma and create a table[][]
	/**
	 * Reads the lines and splits them using a ","
	 * @param lines The lines of a read .csv-file
	 * @return a String[][] array table representing the file
	 */
	public static String[][] readCsv(List<String> lines){
		String[] row = lines.toArray(new String[0]);
		String[][] table = new String[row.length][];
		
		for (int i = 0; i < row.length; i++) {
			String[] cells = row[i].split(","); 
			table[i] = cells;
		}
		
		
		
		
		return table;
		
	}

	/**
	 * Writes an String[][] array table as a new .csv file to the HDD
	 * @param path Where the new file should be created
	 * @param table The content of the file
	 * @throws IOException 
	 */
	public static void writeCSV(Path path, String[][] table) throws IOException {
		List<String> lines = new LinkedList<String>();
		for (int i = 0; i < table.length; i++) {
			String[] row = table[i];
			lines.add(String.join(",", row));
		}
		Files.write(path, lines);
		
	}

}
