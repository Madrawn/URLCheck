package de.daniel.dengler.URLChecker;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * This class is instantiated with an URL it then checks instantly, containing the results after the constructor finishes
 * @author Madrawn
 *
 */
public class UrlChecker implements Comparable<UrlChecker>{
	
	private URL toCheck;
	private String responseCode = "not set";
	private String newURL = "not set";
	private String guessedURL = "not set / start and finish url match";
	private boolean redirected;
	private Connector c;
	
	
	/**
	 * Tries to connect to the given URL, then determines if we have been redirected,
	 * if so it tries to guess the correct URL by adding "_<number 1 to 9>" at the end and checking them
	 * If we are not redirected we got a correct URL existing in the webshop.
	 * Results are collected which consist of:
	 * The response code of the initial connection,
	 * the URL we end up after trying to connect to the given URL, might be the same or a different one,
	 * if we could find one a URL which leads to the item the given URL should have led to
	 * a boolean signaling if the given URL was working or not, decided by if we have been redirected.
	 * @param toCheck the URL we want checked
	 * @param c the Connector enabling us to talk to the internet
	 * @throws IOException
	 */
	public UrlChecker(URL toCheck, Connector c) throws IOException{
		this.toCheck = toCheck;
		this.c = c;
		checkIt();		
	}

	
	private void checkIt() throws IOException {
		c.openConnection(toCheck);
		//connection automatically redirects
		//con.setInstanceFollowRedirects(false);
		c.connect();
		responseCode = String.valueOf(c.getResponseCode());
		
		newURL = c.getURL().toString();
		System.out.println(c.getHeaderFields().toString());
		redirected = !newURL.equals(toCheck.toString());
		if(redirected){
			
			//figure out if URL already ends in _#
			String baseURL = toCheck.toString();
			if(baseURL.matches("/.*_\\d$")){
				//remove the trailing _#
				baseURL = baseURL.substring(0, baseURL.length()-2);
			}
			
			//now check the base url with _1 up to _9
			 for(int i = 1; i < 10; i++){
				 String urlToTry = baseURL + "_" + i;
				 c.openConnection(new URL(urlToTry));
				 //don't follow redirects
				 c.setInstanceFollowRedirects(false);
				 c.connect();
				 int response = c.getResponseCode();
				 System.out.println();
				 
				 //if we get ok this should be our working URL
				 if(response == 200){
					 guessedURL = urlToTry;
				 }
				 
			 }
		}
		c.disconnect();
	}

	public String getStatus() {
		return responseCode;
	}

	public String getNewUrl() {
		return newURL;
	}

	public String getContent() {
		// TODO I don't think I need this after all
		return null;
	}

	public String getStartURL() {
		return toCheck.toString();
	}

	public String getGuessedCorrectUrl() {
		return guessedURL;
	}

	public boolean isRedirected() {
		return redirected;
	}

	public boolean getMatches() {
		return !isRedirected();
	}

	public int compareTo(UrlChecker o) {
		
		return this.getStartURL().compareTo(o.getStartURL());
	}

}
