package dk.kiljacken.laeringsspil.graph;

import java.util.LinkedList;

public class Node {
	private float x, y;
	private LinkedList<Edge> edges;
	
	public Node(float x, float y) {
		this.x = x;
		this.y = y;
		this.edges = new LinkedList<>();
	}
	
	public void attach(Edge edge) {
		edges.add(edge);
	}
	
	public void deattach(Edge edge) {
		edges.remove(edge);
	}
	
	public float distance(Node node) {
		float dx = Math.abs(getX() - node.getX());
		float dy = Math.abs(getY() - node.getY());
		
		return (float)Math.sqrt(dx*dx + dy*dy);
	}
	
	public boolean isConnectedTo(Node node) {
		for (Edge edge: getEdges()) {
			if (edge.linksTo(node))
				return true;
			else
				continue;
		}
		
		return false;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public LinkedList<Edge> getEdges() {
		return edges;
	}
}
