package de.daniel.dengler.URLChecker;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HelperTest {

	static Controller c;
	static List<String> correctLines;
	static Connector conn = new ConnectorMock();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		correctLines = Arrays.asList(new String[] { "Title",
				"https://www.brief-huellen.de/briefumschlaege__150-x-150-mm_1",
				"https://www.brief-huellen.de/briefumschlaege__150-x-150-mm" });
	}

	@Before
	public void setUp() throws Exception {
		c = new ControllerMock(new MainWindowMockUp());
	}

	@Test()
	public void processCorruptLinesTest() {
		int relevantColumn = 0;

		List<String> corruptLines = Arrays.asList(new String[] { "Title",
				"httefumschlaege__150-x-150-mm_1",
				"htaps://www/briefumschlaege__150-x-150-mm" });
		
		Helper.processLines(c, relevantColumn, corruptLines, conn);
		//there shouldn't be more than the title
		assertTrue("Should be only the Titles but more rows: "+c.getWorkingTable().size(),c.getWorkingTable().size() == 1);
		assertTrue("Should be 5 but is "+c.getTitle().size(), c.getTitle().size() == 5);
	}
	@Test()
	public void wrongColumnTest() {
		int relevantColumn = 24;
		
		List<String> corruptLines = Arrays.asList(new String[] { "Title",
				"httefumschlaege__150-x-150-mm_1",
		"htaps://www/briefumschlaege__150-x-150-mm" });
		
		Helper.processLines(c, relevantColumn, corruptLines, conn);
		//there shouldn't be more than the title
		assertTrue("Should be only the Titles but more rows: "+c.getWorkingTable().size(),c.getWorkingTable().size() == 1);
		assertTrue("Should be 5 but is "+c.getTitle().size(), c.getTitle().size() == 5);
	}
	
	@Test()
	public void doubleURLTest() {
		int relevantColumn = 0;
		
		List<String> doubleLines = Arrays.asList(new String[] { "Title",
				"https://www.brief-huellen.de/briefumschlaege__150-x-150-mm_1",
				"https://www.brief-huellen.de/briefumschlaege__150-x-150-mm",
				"https://www.brief-huellen.de/briefumschlaege__150-x-150-mm"});
		
		Helper.processLines(c, relevantColumn, doubleLines, conn);
		//this should just work
		assertTrue("Should be 4: "+c.getWorkingTable().size(),c.getWorkingTable().size() == 4);
		assertTrue("Should be 5 but is "+c.getTitle().size(), c.getTitle().size() == 5);
		assertTrue(c.getWorkingTable().get(3).get(0).equals(doubleLines.get(3)));
	}
	
	
	@Test()
	public void linesNullTest() {
		int relevantColumn = 0;
		
		List<String> lines = null;
		
		Helper.processLines(c, relevantColumn, lines, conn);
		//there shouldn't be more than the title
		assertTrue("Should be 1: "+c.getWorkingTable().size(),c.getWorkingTable().size() == 1);
		assertTrue("Should be 4 but is "+c.getTitle().size(), c.getTitle().size() == 4);
	}

}
