package br.unb.face.csv2graph.graph.structure;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private List<String> keyList;
	private Map<String, GraphNode> roots;

	public Graph() {
		this(null);
	}

	public Graph(String name) {
		this.name = name;
		this.keyList = new LinkedList<String>();
		this.roots = new HashMap<String, GraphNode>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getKeyList() {
		return keyList;
	}

	public Map<String, GraphNode> getRoots() {
		return roots;
	}

	public void addRootNode(String name) {
		this.keyList.add(name);
		this.roots.put(name, new GraphNode(name));
	}
}
