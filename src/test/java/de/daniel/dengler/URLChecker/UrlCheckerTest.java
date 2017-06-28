package de.daniel.dengler.URLChecker;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class UrlCheckerTest {

	
	@Before
	public void setUp(){

		//keep some space between output
		System.out.println();
		System.out.println();
		System.out.println("Connecting to: ");
	}
	

	
	@Test
	public void testCorruptBlankeUrl() throws IOException {
		//This is the currently live link
		String correctUrl = "https://www.brief-huellen.de/briefumschlaege__150-x-150-mm_1";
		
		//this is the correct, but not working link
		String start = "https://www.brief-huellen.de/briefumschlaege__150-x-150-mm";
		
		System.out.println(start);
		URL url = new URL(start); 
		
		UrlChecker checker = new UrlChecker(url); 
		collect(checker);
		
		assertTrue(checker.isRedirected() == true);
		assertTrue(checker.getGuessedCorrectUrl().equals(correctUrl));
	}
	
	@Test
	public void testBlankeUrl() throws IOException {
		//This is the currently live link
		String start = "https://www.brief-huellen.de/briefumschlaege__150-x-150-mm_1";
		
		//this is the correct, but not working link
		//String correct = "https://www.brief-huellen.de/briefumschlaege__150-x-150-mm";
		
		System.out.println(start);
		URL url = new URL(start); 
		
		UrlChecker checker = new UrlChecker(url); 
		collect(checker);
		
		assertTrue(checker.getStartURL().equals(checker.getNewUrl()));
	}

	private void collect(UrlChecker checker) {
		String statusCode = checker.getStatus();
		String newURL = checker.getNewUrl();
		String startURL = checker.getStartURL();
		String guessedCorrectURL = checker.getGuessedCorrectUrl();
		
		
		
		System.out.println(statusCode);
		System.out.println(newURL);
		System.out.println(startURL);
		System.out.println(guessedCorrectURL);
	}

}
