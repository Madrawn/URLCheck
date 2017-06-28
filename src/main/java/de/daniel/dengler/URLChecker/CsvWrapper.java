package de.daniel.dengler.URLChecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CsvWrapper {
	
	//read file in, split by newline then by comma and create a table[][]
	public static String[][] readCsv(List<String> lines){
		String[] row = lines.toArray(new String[0]);
		String[][] table = new String[row.length][];
		
		for (int i = 0; i < row.length; i++) {
			String[] cells = row[i].split(","); 
			table[i] = cells;
		}
		
		
		
		
		return table;
		
	}

	public static void writeCSV(Path path, String[][] table) throws IOException {
		List<String> lines = new LinkedList<String>();
		for (int i = 0; i < table.length; i++) {
			String[] row = table[i];
			lines.add(String.join(",", row));
		}
		Files.write(path, lines);
		
	}

}
