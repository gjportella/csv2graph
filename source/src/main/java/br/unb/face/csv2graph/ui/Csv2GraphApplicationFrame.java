package br.unb.face.csv2graph.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.unb.face.csv2graph.graph.Csv2Graph;

public class Csv2GraphApplicationFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JMenuBar jMenuBar;
	private JMenu jMenuFile;
	private JMenu jMenuHelp;
	private JMenuItem jMenuItemExit;
	private JMenuItem jMenuItemAbout;

	private JTextField jTextFieldCsvPath;
	private JFileChooser jFileChooserCsv;
	private JButton jButtonCsvFile;
	private JButton jButtonRun;
	private JButton jButtonClear;

	private JTabbedPane jTabbedPane;
	private JScrollPane scrollPaneConsole;
	private JTextArea textAreaConsole;

	private JScrollPane scrollPaneGraphviz;
	private JTextArea textAreaGraphviz;

	public Csv2GraphApplicationFrame() {

		initMenu();
		initLayout();
		initEvents();
	}

	private void initMenu() {

		jMenuBar = new JMenuBar();
		setJMenuBar(jMenuBar);

		jMenuFile = new JMenu("File");
		jMenuBar.add(jMenuFile);

		jMenuItemExit = new JMenuItem("Exit");
		jMenuFile.add(jMenuItemExit);

		/* ----------------------------------------------------------------- */

		jMenuHelp = new JMenu("Help");
		jMenuBar.add(jMenuHelp);

		jMenuItemAbout = new JMenuItem("About");
		jMenuHelp.add(jMenuItemAbout);
	}

	private void initLayout() {

		int width = 800, height = 600;
		int initX = 10, initY = 10, gapX = 10, gapY = 10;
		int x = initX, y = initY;

		getContentPane().setLayout(null);
		setTitle("Csv2Graph");
		setSize(new Dimension(width, height));
		setResizable(false);

		/* ----------------------------------------------------------------- */

		JLabel jLabelTitulo = new JLabel();
		jLabelTitulo.setText("csv2graph - CSV to graph parsing tool");
		jLabelTitulo.setBounds(x, y, 700, 20);
		getContentPane().add(jLabelTitulo);
		x = initX;
		y += jLabelTitulo.getHeight() + gapY;

		/* ----------------------------------------------------------------- */

		JLabel jLabelCsvFile = new JLabel();
		jLabelCsvFile.setText("CSV File:");
		jLabelCsvFile.setBounds(x, y, 80, 20);
		getContentPane().add(jLabelCsvFile);
		x += jLabelCsvFile.getWidth() + gapX;

		jTextFieldCsvPath = new JTextField();
		jTextFieldCsvPath.setText("");
		jTextFieldCsvPath.setBounds(x, y, 480, 20);
		getContentPane().add(jTextFieldCsvPath);
		x += jTextFieldCsvPath.getWidth() + gapX;

		jFileChooserCsv = new JFileChooser();
		jButtonCsvFile = new JButton("Abrir");
		jButtonCsvFile.setBounds(x, y, 100, 20);
		getContentPane().add(jButtonCsvFile);
		x = initX;
		y += jTextFieldCsvPath.getHeight() + gapY;

		/* ----------------------------------------------------------------- */

		jButtonRun = new JButton("Run");
		jButtonRun.setBounds(x, y, 100, 20);
		getContentPane().add(jButtonRun);
		x += jButtonRun.getWidth() + gapX;

		jButtonClear = new JButton("Clear");
		jButtonClear.setBounds(x, y, 100, 20);
		getContentPane().add(jButtonClear);
		x = initX;
		y += jButtonClear.getHeight() + gapY;

		/* ----------------------------------------------------------------- */

		textAreaConsole = new JTextArea("Please choose the CSV file and click on \"Run\".\n");
		textAreaConsole.setFont(new Font("monospaced", Font.PLAIN, 12));
		textAreaConsole.setLineWrap(true);
		textAreaConsole.setWrapStyleWord(true);
		textAreaConsole.setEditable(false);

		scrollPaneConsole = new JScrollPane(textAreaConsole);
		scrollPaneConsole.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		textAreaGraphviz = new JTextArea("");
		textAreaGraphviz.setFont(new Font("monospaced", Font.PLAIN, 12));
		textAreaGraphviz.setLineWrap(true);
		textAreaGraphviz.setWrapStyleWord(true);
		textAreaGraphviz.setEditable(false);

		scrollPaneGraphviz = new JScrollPane(textAreaGraphviz);
		scrollPaneGraphviz.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		jTabbedPane = new JTabbedPane();
		jTabbedPane.setBounds(x, y, width - 20, height - 190);
		jTabbedPane.addTab("Console", scrollPaneConsole);
		jTabbedPane.addTab("Graphviz", scrollPaneGraphviz);
		getContentPane().add(jTabbedPane);
	}

	private void initEvents() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jMenuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(NORMAL);
			}
		});

		jMenuItemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getContentPane(),
						"csv2graph\n" + "http://github.com/gjportella/csv2graph/wiki\n" + "September, 2017.");
			}
		});

		jButtonCsvFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int returnVal = jFileChooserCsv.showOpenDialog(Csv2GraphApplicationFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = jFileChooserCsv.getSelectedFile();
					jTextFieldCsvPath.setText(file.getAbsolutePath());
				}
			}
		});

		jButtonClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jTextFieldCsvPath.setText("");
				textAreaConsole.setText("Please choose the CSV file and click on \"Run\".\n");
				textAreaGraphviz.setText("");
			}
		});

		jButtonRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonRunActionPerformed(evt);
			}
		});
	}

	private void jButtonRunActionPerformed(ActionEvent evt) {

		Csv2Graph csv2Graph = new Csv2Graph(jTextFieldCsvPath.getText());
		try {
			csv2Graph.doConversion();
			textAreaConsole.setText(csv2Graph.getLogBuilder().toString());
			textAreaGraphviz.setText(csv2Graph.getGraphvizBuilder().toString());

		} catch (Exception ex) {
			textAreaConsole.setText("Error processing the CSV file (line= " + Integer.toString(csv2Graph.getRow())
					+ ", column= " + Integer.toString(csv2Graph.getColumn()) + ").\n" + ex.toString());
		}
	}
}
