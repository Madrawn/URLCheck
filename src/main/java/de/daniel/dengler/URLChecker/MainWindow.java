package de.daniel.dengler.URLChecker;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DefaultCaret;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class MainWindow {
	
	private MainWindow me = this;
	private JFrame frmUrlRedirectCheck;
	private JButton btnCheck;
	private JLabel lblDateipfad;
	private Controller myController = new Controller(me);
	private JTextArea txtrAnleitungcsv;
	private JSpinner spinner;
	private JProgressBar progressBar;
	private JButton btnExport;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmUrlRedirectCheck.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUrlRedirectCheck = new JFrame();
		frmUrlRedirectCheck.setTitle("URL redirect check");
		frmUrlRedirectCheck.setBounds(100, 100, 613, 414);
		frmUrlRedirectCheck.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUrlRedirectCheck.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.MIN_ROWSPEC,}));
		
		JButton btnDurchsuchen = new JButton("Durchsuchen");
		btnDurchsuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Helper.selectFile(me);
			}
		});
		frmUrlRedirectCheck.getContentPane().add(btnDurchsuchen, "2, 2");
		
		lblDateipfad = new JLabel("Dateipfad");
		frmUrlRedirectCheck.getContentPane().add(lblDateipfad, "6, 2");
		
		btnCheck = new JButton("Check");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(myController).start();
			}
		});
		
		JLabel lblRelevanteSpalte = new JLabel("Relevante Spalte");
		frmUrlRedirectCheck.getContentPane().add(lblRelevanteSpalte, "2, 4");
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		frmUrlRedirectCheck.getContentPane().add(spinner, "4, 4");
		btnCheck.setEnabled(false);
		frmUrlRedirectCheck.getContentPane().add(btnCheck, "2, 6");
		
		progressBar = new JProgressBar();
		frmUrlRedirectCheck.getContentPane().add(progressBar, "6, 6, fill, default");
		
		btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myController.export();
			}
		});
		btnExport.setEnabled(false);
		frmUrlRedirectCheck.getContentPane().add(btnExport, "2, 8");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frmUrlRedirectCheck.getContentPane().add(scrollPane, "6, 8, fill, fill");
		
		txtrAnleitungcsv = new JTextArea();
		txtrAnleitungcsv.setLineWrap(true);
		txtrAnleitungcsv.setEditable(false);
		DefaultCaret caret = (DefaultCaret)txtrAnleitungcsv.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		txtrAnleitungcsv.setText("Anleitung:\r\n1. *.csv Datei auswählen\r\n2. Die relevante Spalte mit den zu überprüfenden URLs angeben (0 ist die erste Spalte)\r\n3. Check-Button klicken\r\n4. Wenn alle URLs überprüft wurden über Export das Ergebniss als *.csv exportieren.");
		
		scrollPane.setViewportView(txtrAnleitungcsv);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		frmUrlRedirectCheck.getContentPane().add(rigidArea_1, "7, 8");
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		frmUrlRedirectCheck.getContentPane().add(rigidArea, "6, 9");
	}

	public Component getJPanel() {
		return frmUrlRedirectCheck;
	}

	public void setCheckButtonEnabled(boolean b) {
		btnCheck.setEnabled(b);
		
	}

	public void setFilePathLabel(String absolutePath) {
		lblDateipfad.setText(absolutePath);
	}

	public Controller getController() {
		return myController ;
	}

	public JTextArea getTextArea() {
		return txtrAnleitungcsv;
	}

	public int getRelevantColumn() {
		return (Integer) spinner.getValue();
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void setExportButtonEnabled(boolean b) {
		btnExport.setEnabled(b);
		
	}

}
