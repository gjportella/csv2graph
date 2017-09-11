package br.unb.face.csv2graph.graph;

import java.io.IOException;

import br.unb.cic.conversion.csv.CsvWrapperReader;
import br.unb.face.csv2graph.graph.structure.Graph;
import br.unb.face.csv2graph.graph.structure.GraphNode;

public class Csv2Graph {

	private static final String DEFAULT_CSV_DELIMITER = ";";

	private String csvPath;
	private String csvDelimiter;
	private StringBuilder logBuilder;
	private StringBuilder graphvizBuilder;
	private Graph graph;
	int row;
	int column;

	public Csv2Graph(String csvPath) {
		this(csvPath, DEFAULT_CSV_DELIMITER);
	}

	public Csv2Graph(String csvPath, String csvDelimiter) {
		this.csvPath = csvPath;
		this.csvDelimiter = csvDelimiter;
		this.logBuilder = new StringBuilder();
		this.graphvizBuilder = new StringBuilder();
		this.graph = new Graph();
	}

	public void doConversion() throws Exception {
		step01_ReadData();
		step02_BuildGraphviz();
	}

	public void step01_ReadData() throws Exception {

		CsvWrapperReader csvReader = null;
		try {

			csvReader = new CsvWrapperReader(csvPath, csvDelimiter);
			csvReader.openFile();

			row = 0;
			String[] auxObj;
			while ((auxObj = csvReader.readLine()) != null) {

				if (row == 0) {

					column = 0;
					graph.setName(auxObj[0]);

					for (int i = 1; i < auxObj.length; i++) {
						graph.addRootNode(auxObj[i]);
						column = i;
					}

				} else {

					column = 0;
					GraphNode target = graph.getRoots().get(auxObj[0]);
					for (int i = 1; i < auxObj.length; i++) {
						if ("1".equals(auxObj[i])) {
							String nodeName = graph.getKeyList().get(i - 1);
							GraphNode source = graph.getRoots().get(nodeName);
							source.addTarget(target);
						}
						column++;
					}

				}
				row++;
			}

		} catch (IOException ioEx) {
			throw new Exception("Runtime error processing CSV file.", ioEx);

		} finally {
			if (csvReader != null) {
				csvReader.closeFile();
			}
		}
	}

	public void step02_BuildGraphviz() throws Exception {

		graphvizBuilder.append("digraph csv_graph {\n");
		graphvizBuilder.append("\tlabel=\"" + graph.getName() + "\";\n");
		for (String key : graph.getKeyList()) {
			GraphNode source = graph.getRoots().get(key);
			graphvizBuilder.append("\t{node [label=\"" + source.getName() + "\"] node_" + source.getName() + "}\n");
		}

		for (String key : graph.getKeyList()) {
			GraphNode source = graph.getRoots().get(key);
			for (GraphNode target : source.getTargets()) {
				graphvizBuilder.append("\tnode_" + source.getName() + "->node_" + target.getName()
						+ " [label=\"\" color=\"#000000\"]\n");
			}
		}
		graphvizBuilder.append("}\n");
		logBuilder.append("Grafo gerado com sucesso.");
	}

	public String getCsvPath() {
		return csvPath;
	}

	public String getCsvDelimiter() {
		return csvDelimiter;
	}

	public StringBuilder getLogBuilder() {
		return logBuilder;
	}

	public StringBuilder getGraphvizBuilder() {
		return graphvizBuilder;
	}

	public Graph getGraph() {
		return graph;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
