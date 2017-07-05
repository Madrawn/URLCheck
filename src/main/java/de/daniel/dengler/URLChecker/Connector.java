package de.daniel.dengler.URLChecker;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Connector {

	private HttpURLConnection con;

	// this class handles connection to the internet, so I can get rid of
	// network code in other classes
	//its just a HTTPURLConnection wrapper
	public Connector() {
	}

	
	public void openConnection(URL toCheck) throws IOException {
		con = (HttpURLConnection) toCheck.openConnection();
	}

	public void connect() throws IOException {
		con.connect();
	}

	public int getResponseCode() throws IOException {
		return con.getResponseCode();
	}

	public URL getURL() {
		return con.getURL();
	}

	public Map<String, List<String>> getHeaderFields() {
		// TODO Auto-generated method stub
		return con.getHeaderFields();
	}

	public void setInstanceFollowRedirects(boolean b) {
		con.setInstanceFollowRedirects(b);
	}

	public void disconnect() {
		if (con != null)
			con.disconnect();
	}

}
