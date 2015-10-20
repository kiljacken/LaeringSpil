package dk.kiljacken.laeringsspil.graph;

public class Edge {
	private Node nodeA;
	private Node nodeB;
	
	public boolean linksTo(Node node) {
		return node == nodeA || node == nodeB;
	}
	
	public Node getOther(Node node) {
		if (getNodeA() == node)
			return getNodeB();
		else
			return getNodeA();
	}
	
	public Node getNodeA() {
		return nodeA;
	}
	
	public void setNodeA(Node nodeA) {
		if (this.nodeA != null)
			this.nodeA.deattach(this);
		
		this.nodeA = nodeA;
		this.nodeA.attach(this);
	}
	
	public Node getNodeB() {
		return nodeB;
	}
	
	public void setNodeB(Node nodeB) {
		if (this.nodeB != null)
			this.nodeB.deattach(this);
		
		this.nodeB = nodeB;
		this.nodeB.attach(this);
	}
}
