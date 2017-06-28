package de.daniel.dengler.URLChecker;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlChecker {
	
	private URL toCheck;
	private String responseCode = "not set";
	private String newURL = "not set";
	private String guessedURL = "not set / start and finish url match";
	private boolean redirected;
	public UrlChecker(URL toCheck) throws IOException{
		this.toCheck = toCheck;
		checkIt();		
	}

	private void checkIt() throws IOException {
		HttpURLConnection con = (HttpURLConnection) toCheck.openConnection();
		
		//connection automatically redirects
		//con.setInstanceFollowRedirects(false);
		con.connect();
		responseCode = String.valueOf(con.getResponseCode());
		
		newURL = con.getURL().toString();
		System.out.println(con.getHeaderFields().toString());
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
				 HttpURLConnection c = (HttpURLConnection) new URL(urlToTry).openConnection();
				 //don't follow redirects
				 c.setInstanceFollowRedirects(false);
				 c.connect();
				 int response = c.getResponseCode();
				 System.out.println();
				 
				 //if we get ok this should be our working URL
				 if(response == 200){
					 guessedURL = urlToTry;
				 }
				 c.disconnect();
				 
			 }
		}
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

}
