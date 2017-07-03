package de.daniel.dengler.URLChecker;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class CsvWrapperTest {

	String fakeCSV = "Farbe,Tier,Größe\n"
			+ "Grün,Kuh,165cm\n"
			+ "Rot,Pferd,2002cm";
	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testReading() {
		
		String[][] readCSV = CsvWrapper.readCsv(Arrays.asList(fakeCSV.split("\n")));
		
		//Table should be 3x3
		assertTrue(readCSV.length == 3);
		assertTrue(readCSV[0].length == 3);
		assertTrue(readCSV[0][0].equals("Farbe"));
	}
	@Test
	public void testWriting() throws IOException {
		//write it
		String path = System.getProperty("user.dir")+"test.csv";
		String[][] table = {{"Rot","Blau"},{"Grün", "Rosa"},{"Gelb", "Magenta"}};
		CsvWrapper.writeCSV(Paths.get(path), table);
		
		//then check for existence and check for content
		
		assertTrue(Files.exists(Paths.get(path)));
		assertTrue(Files.readAllLines(Paths.get(path)).get(0).contains(("Rot,Blau")));
	}

}
