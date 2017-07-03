package de.daniel.dengler.URLChecker;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ConnectorMock extends Connector {

	URL url;
	
	public ConnectorMock() {
	}
	@Override
	public void connect() throws IOException {

		//do nothing
	}
	
	@Override
	public Map<String, List<String>> getHeaderFields() {
		Map<String, List<String>> map = new TreeMap<String, List<String>>();
		List<String> list = new LinkedList<String>();
		list.add("Fake Value");
		map.put("Fake Connector", list);
		return map;
	}
	
	@Override
	public int getResponseCode() throws IOException {
		return 200;
	}
	
	@Override
	public URL getURL() {
		// TODO Auto-generated method stub
		return url;
	}
	
	@Override
	public void openConnection(URL toCheck) throws IOException {
		url = toCheck;
	}
	
	
}
