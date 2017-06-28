package de.daniel.dengler.URLChecker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	


}
