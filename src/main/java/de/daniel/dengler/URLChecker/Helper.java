package de.daniel.dengler.URLChecker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author Madrawn
 * Provides the main functionality not provided by the URLChecker class
 */
public class Helper {

	
	public static List<String> readFile(String path) {

		List<String> lines;
		try {
			Path path2 = Paths.get(path);
			//This is better
			lines = Files.readAllLines(path2,StandardCharsets.ISO_8859_1);
			//read all bytes avoids charset problems by using system default charset and replacing invalid byte sequences
			//lines = Arrays.asList((new String(Files.readAllBytes(path2))).split("\n"));
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}
		return lines;
	}

	public static void selectFile(MainWindow me) {

		// create file chooser

		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma-separated values file", "csv", "CSV");
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(me.getJPanel());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			me.setCheckButtonEnabled(true);
			me.setFilePathLabel(fc.getSelectedFile().getAbsolutePath());
			me.getController().setFile(fc.getSelectedFile());
		}

	}
	

	/**
	 * Here most of the interesting stuff happens.
	 * We get the lines, extract the URLs we have to check, write the result table and manage most of the 
	 * text output
	 * @param c It's the controller handling the UI, it also holds the data relevant for outputting the results
	 * @param relevantColumn The index of the column which contains the URLs we want checked.
	 * @param lines The lines are rows of a table with the cells separated by a comma.
	 * @param conn the Connector handling all network traffic
	 */
	protected static void processLines(Controller c,
			int relevantColumn, List<String> lines, Connector conn) {
		
		Collection<UrlChecker> alreadyCheckedUrls = new TreeSet<UrlChecker>();
		
		if (lines != null && !lines.isEmpty()) {
			String[][] table = CsvWrapper.readCsv(lines);

			c.append("\n .csv-Datei erfolgreich eingelesen mit "
					+ lines.size() + " Zeilen");
			c.setProgressMaximum(lines.size());
			
			// We assume the file has titles in the first row. copy them to the
			// right of the new table titles
			String[] originalTitles = table[0];
			for (int i = 0; i < originalTitles.length; i++) {
				c.getTitle().add(originalTitles[i]);
			}
			//Only one column might be an error notify
			if (table[1].length == 1) {
				c.append("\n \n Die CSV hat nur eine einzige Spalte. Potenziell falscher Trenner.");
			}
			
			//This is definitely an error.
			if (table[1].length < relevantColumn +1) {
				c.append("\n \n Sie haben die "
						+ (relevantColumn +1)
						+ ". Spalte gewählt, die Tabelle"
						+ " hat jedoch nur "
						+ table[1].length
						+ " Spalte\\n\n"
						+ "Vielleich wurde die CSV mit dem falschen Trenner exportiert?(Trenner muss ein ',' (Komma) sein.)");
				return;

			}


			// begin checking
			c.append("\n \n Checking URLs");
			// skip titles, start at 1
			for (int i = 1; i < table.length; i++) {
				// check every url and generate the export table
				String e = table[i][relevantColumn];
				UrlChecker urlChecker = null;

				// if we already checked then we don't need to again saving a
				// lot of time
				boolean alreadyChecked = false;
				for (UrlChecker u : alreadyCheckedUrls) {
					if (u.getStartURL().equals(e)) {
						alreadyChecked = true;
						urlChecker = u;
					}
				}

				try {
					
					if (!alreadyChecked) {
						urlChecker = new UrlChecker(new URL(e), conn);
					}
					List<String> checkedLine = addLine(
							urlChecker.getStartURL(), urlChecker.getMatches(),
							urlChecker.getNewUrl(),
							urlChecker.getGuessedCorrectUrl(), table[i]);
					c.getWorkingTable().add(checkedLine);
					alreadyCheckedUrls.add(urlChecker);
					c.append("\n" + e);
					c.setProgressBarValue(i + 1);
				} catch (MalformedURLException e1) {
					c.append("\n" + e + " ist keine korrekte URL");
					e1.printStackTrace();
				} catch (IOException e1) {
					c.append("\n" + e + " <-> URL <-> IOException");

					e1.printStackTrace();
				}

			}
			// we have checked all the URLs enable the export button

			c.append("\n \n FINISHED! Sie können das Ergebniss jetzt exportieren.");
			c.setExportButtonEnabled(true);

		} else {
			c.append("\n Datei nicht richtig einlesen können. Richtige Datei gewählt?");

		}
	}
	
	// helper method for generating a line as a list
	private static List<String> addLine(String startURL, boolean matches,
			String newUrl, String guessedCorrectUrl, String[] rest) {
		List<String> line = new LinkedList<String>();
		line.add(startURL);
		line.add("" + matches);
		line.add(newUrl);
		line.add(guessedCorrectUrl);
		for (int i = 0; i < rest.length; i++) {
			line.add(rest[i]);
		}
		return line;
	}



}
