package de.daniel.dengler.URLChecker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;

public class Controller implements Runnable {

	File selectedFile;
	private MainWindow mainWindow;
	private List<List<String>> workingTable = new LinkedList<List<String>>();
	private List<String> title = new LinkedList<String>();
	private Connector conn = new Connector();
	

	public Controller(MainWindow me) {
		this.mainWindow = me;
		// This is the first row with the titles of each column
		title.add("Start URL");
		title.add("Matches");
		title.add("End URL");
		title.add("Korrekte URL");
		workingTable.add(title);
	}

	public void run() {
		checkTheFile();
	}

	public void setFile(File selectedFile) {
		this.selectedFile = selectedFile;


	}

	public void checkTheFile() {

		int relevantColumn = mainWindow.getRelevantColumn();
		
		// get the lines from the file
		List<String> lines = Helper.readFile(selectedFile.getAbsolutePath());
		
		// disable the check button again
		mainWindow.setCheckButtonEnabled(false);
		
		
		Helper.processLines(this, relevantColumn, lines, conn);
	}

	
	protected List<List<String>> getWorkingTable() {
		return workingTable;
	}

	void setProgressBarValue(int i) {
		mainWindow.getProgressBar().setValue(i);
	}

	protected void setProgressMaximum(int size) {
		mainWindow.getProgressBar().setMaximum(size);
	}

	protected void append(String string) {
		this.mainWindow.getTextArea().append(string);
	}

	protected List<String> getTitle() {
		return title;
	}

	public void export() {

		JFileChooser jfc = new JFileChooser();
		int result = jfc.showSaveDialog(mainWindow.getJPanel());
		if (result == JFileChooser.APPROVE_OPTION) {

			String path = jfc.getSelectedFile().getAbsolutePath() + ".csv";

			// Turn the list into a array
			String[][] table = new String[workingTable.size()][];
			int index = 0;
			for (List<String> l : workingTable) {
				table[index] = l.toArray(new String[0]);
				index++;
			}
			// write the file.
			try {

				CsvWrapper.writeCSV(Paths.get(path), table);
				mainWindow.getTextArea().append(
						"\n-------- Export fertig! --------");

				// everything went fine we can disable the export button
				mainWindow.setExportButtonEnabled(false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void setExportButtonEnabled(boolean b) {
		mainWindow.setExportButtonEnabled(b);
	}



}
