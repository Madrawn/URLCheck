package de.daniel.dengler.URLChecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Helper {

	public static List<String> readFile(String path) {

		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(path));
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
	


}
