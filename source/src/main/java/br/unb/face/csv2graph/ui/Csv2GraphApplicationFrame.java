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
	private JMenu jMenuArquivo;
	private JMenu jMenuAjuda;
	private JMenuItem jMenuItemSair;
	private JMenuItem jMenuItemSobre;

	private JTextField jTextFieldCaminho;
	private JFileChooser jFCArquivoCsv;
	private JButton jButtonArquivoCsv;
	private JButton jButtonProcessar;
	private JButton jButtonLimpar;

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

		jMenuArquivo = new JMenu("Arquivo");
		jMenuBar.add(jMenuArquivo);

		jMenuItemSair = new JMenuItem("Sair");
		jMenuArquivo.add(jMenuItemSair);

		/* ----------------------------------------------------------------- */

		jMenuAjuda = new JMenu("Ajuda");
		jMenuBar.add(jMenuAjuda);

		jMenuItemSobre = new JMenuItem("Sobre");
		jMenuAjuda.add(jMenuItemSobre);
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
		jLabelTitulo.setText("Csv2Graph - Conversao de Arquivo CSV em Grafo");
		jLabelTitulo.setBounds(x, y, 700, 20);
		getContentPane().add(jLabelTitulo);
		x = initX;
		y += jLabelTitulo.getHeight() + gapY;

		/* ----------------------------------------------------------------- */

		JLabel jLabelArquivoCsv = new JLabel();
		jLabelArquivoCsv.setText("Arquivo CSV:");
		jLabelArquivoCsv.setBounds(x, y, 80, 20);
		getContentPane().add(jLabelArquivoCsv);
		x += jLabelArquivoCsv.getWidth() + gapX;

		jTextFieldCaminho = new JTextField();
		jTextFieldCaminho.setText("");
		jTextFieldCaminho.setBounds(x, y, 480, 20);
		getContentPane().add(jTextFieldCaminho);
		x += jTextFieldCaminho.getWidth() + gapX;

		jFCArquivoCsv = new JFileChooser();
		jButtonArquivoCsv = new JButton("Abrir");
		jButtonArquivoCsv.setBounds(x, y, 100, 20);
		getContentPane().add(jButtonArquivoCsv);
		x = initX;
		y += jTextFieldCaminho.getHeight() + gapY;

		/* ----------------------------------------------------------------- */

		jButtonProcessar = new JButton("Processar");
		jButtonProcessar.setBounds(x, y, 100, 20);
		getContentPane().add(jButtonProcessar);
		x += jButtonProcessar.getWidth() + gapX;

		jButtonLimpar = new JButton("Limpar");
		jButtonLimpar.setBounds(x, y, 100, 20);
		getContentPane().add(jButtonLimpar);
		x = initX;
		y += jButtonLimpar.getHeight() + gapY;

		/* ----------------------------------------------------------------- */

		textAreaConsole = new JTextArea("Informe o arquivo CSV e clique em \"Processar\".\n");
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

		jMenuItemSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(NORMAL);
			}
		});

		jMenuItemSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getContentPane(),
						"Csv2Graph\n" + "Professor Mauricio Bugarim\n" + "FACE/UnB - Setembro de 2017");
			}
		});

		jButtonArquivoCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int returnVal = jFCArquivoCsv.showOpenDialog(Csv2GraphApplicationFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = jFCArquivoCsv.getSelectedFile();
					jTextFieldCaminho.setText(file.getAbsolutePath());
				}
			}
		});

		jButtonLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jTextFieldCaminho.setText("");
				textAreaConsole.setText("Informe o arquivo CSV e clique em \"Processar\".\n");
				textAreaGraphviz.setText("");
			}
		});

		jButtonProcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonProcessarActionPerformed(evt);
			}
		});
	}

	private void jButtonProcessarActionPerformed(ActionEvent evt) {

		Csv2Graph csv2Graph = new Csv2Graph(jTextFieldCaminho.getText());
		try {
			csv2Graph.doConversion();
			textAreaConsole.setText(csv2Graph.getLogBuilder().toString());
			textAreaGraphviz.setText(csv2Graph.getGraphvizBuilder().toString());

		} catch (Exception ex) {
			textAreaConsole.setText("Erro ao processar arquivo CSV (linha= " + Integer.toString(csv2Graph.getRow())
					+ ", coluna= " + Integer.toString(csv2Graph.getColumn()) + ").\n" + ex.toString());
		}
	}
}
