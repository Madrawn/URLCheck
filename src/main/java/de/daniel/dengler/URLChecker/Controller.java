package de.daniel.dengler.URLChecker;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class Controller implements Runnable{

	File selectedFile;
	private MainWindow mainWindow;
	private List<List<String>> workingTable = new LinkedList<List<String>>();
	private List<String> title = new LinkedList<String>();

	public Controller(MainWindow me) {
		this.mainWindow = me;
	}

	public void run() {
		checkTheFile();
	}

	public void setFile(File selectedFile) {
		this.selectedFile = selectedFile;
		
		//This is the first row with the titles of each column
		title.add("Start URL");
		title.add("Matches");
		title.add("End URL");
		title.add("Korrekte URL");
		workingTable.add(title);

	}

	public void checkTheFile() {
		//disable the check button again
		mainWindow.setCheckButtonEnabled(false);
		
		JTextArea jta = mainWindow.getTextArea();
		//get the lines from the file
		List<String> lines = Helper.readFile(selectedFile.getAbsolutePath());
		if (!lines.isEmpty()) {
			String[][] table = CsvWrapper.readCsv(lines);

			jta.append("\n .csv-Datei erfolgreich eingelesen mit "
					+ lines.size() + " Zeilen");
			JProgressBar pb = mainWindow.getProgressBar();
			pb.setMaximum(lines.size());
			
			
			//begin checking
			jta.append("\n \n Checking URLs");
			for (int i = 0; i < table.length; i++) {
				//check every url and generate the export table
				String e = table[i][mainWindow.getRelevantColumn()];
				UrlChecker urlChecker;
				try {
					urlChecker = new UrlChecker(new URL(e));
					workingTable.add(addLine(urlChecker.getStartURL(),
							urlChecker.getMatches(), urlChecker.getNewUrl(),
							urlChecker.getGuessedCorrectUrl()));
					jta.append("\n" + e);
					pb.setValue(i+1);
				} catch (MalformedURLException e1) {
					jta.append("\n"+e+" ist keine korrekte URL");
					e1.printStackTrace();
				} catch (IOException e1) {
					jta.append("\n"+e+" <-> URL <-> IOException");
					
					e1.printStackTrace();
				}

			}
			//we have checked all the URLs enable the export button
			
			jta.append("\n \n FINISHED! Sie können das Ergebniss jetzt exportieren.");
			mainWindow.setExportButtonEnabled(true);

		} else {
			jta.append("\n Datei nicht richtig einlesen können. Richtige Datei gewählt?");

		}
	}

	public void export() {
		
		
		JFileChooser jfc = new JFileChooser();
		int result = jfc.showSaveDialog(mainWindow.getJPanel());
		if(result == JFileChooser.APPROVE_OPTION){
			
			
			String path = jfc.getSelectedFile().getAbsolutePath() +".csv";
			
			//Turn the list into a array
			String[][] table = new String[workingTable.size()][];
			int index = 0;
			for(List<String> l : workingTable){
				table[index] = l.toArray(new String[0]);
				index++;
			}
			//write the file.
			try {
				
				CsvWrapper.writeCSV(Paths.get(path), table);
				mainWindow.getTextArea().append("\n-------- Export fertig! --------");

				//everything went fine we can disable the export button
				mainWindow.setExportButtonEnabled(false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	//helper method for generating a line as a list
	private List<String> addLine(String startURL, boolean matches,
			String newUrl, String guessedCorrectUrl) {
		List<String> line = new LinkedList<String>();
		line.add(startURL);
		line.add("" + matches);
		line.add(newUrl);
		line.add(guessedCorrectUrl);
		return line;
	}



}
