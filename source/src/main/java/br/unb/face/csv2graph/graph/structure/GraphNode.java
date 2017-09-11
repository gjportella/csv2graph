package br.unb.face.csv2graph.graph.structure;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class GraphNode implements Serializable, Comparable<GraphNode> {

	private static final long serialVersionUID = 1L;

	private String name;
	private List<GraphNode> targets;

	public GraphNode(String name) {
		this.name = name;
		this.targets = new LinkedList<GraphNode>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GraphNode> getTargets() {
		return targets;
	}

	public void addTarget(GraphNode graphNode) {
		this.targets.add(graphNode);
	}

	public int compareTo(GraphNode other) {
		if (this.name != null && other != null && this.name.equals(other.getName())) {
			return 0;
		}
		return -1;
	}
}
