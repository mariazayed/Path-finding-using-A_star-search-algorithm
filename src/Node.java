public class Node implements Comparable<Node> {
	private int node;
	private Node parent = null;
	private boolean isExpanded = false; // initially the node is NOT expanded
	private boolean isVisited = false; // initially the noode is NODE visited
	private double pathCost;    //cost from root to this node
	private double totalCost;   //path cost + heuristic
	public static int numOfExpandedNode = 0;
	public static int numOfVisitedNode = 0;

	// constructor
	public Node(int node) {
		this.node = node;
	}

	// getter
	public int getNode() {
		return node;
	}

	// get parent node
	public Node getParent() {
		return parent;
	}

	// set parent node
	public void setParent(Node parent) {
		this.parent = parent;
	}

	// update the number of expanded nodes
	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
		if (isExpanded)
			numOfExpandedNode++;
	}
	
	// getter
	public boolean isExpanded() {
		return isExpanded;
	}

	// update the number of visited nodes
	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
		if (isVisited)
			numOfVisitedNode++;
	}
	
	// getter
	public boolean isVisited() {
		return isVisited;
	}

	// getter
	public double getPathCost() {
		return pathCost;
	}

	// setter
	public void setPathCost(double pathCost) {
		this.pathCost = pathCost;
	}

	// getter
	public double getTotalCost() {
		return totalCost;
	}

	// setter
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	@Override
	public int compareTo(Node o) {
		if (this.getTotalCost() > o.getTotalCost())
			return 1;
		if (this.getTotalCost() < o.getTotalCost())
			return -1;
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		return this.node == ((Node) obj).getNode();
	}

	@Override
	public String toString() {
		return "Node [node=" + node + ", parent=" + parent + ", isExpanded=" + isExpanded + ", isVisited=" + isVisited
				+ ", pathCost=" + pathCost + ", totalCost=" + totalCost + "]";
	}

	public String changeToName() {
		String s = null;
		if (node == 0)
			s = "Safad";
		else if (node == 1)
			s = "Tabarya";
		else if (node == 2)
			s = "Bisan";
		else if (node == 3)
			s = "Al-Nasrah";
		else if (node == 4)
			s = "Jenin";
		else if (node == 5)
			s = "Nablus";
		else if (node == 6)
			s = "Tulkarem";
		else if (node == 7)
			s = "Jericho";
		else if (node == 8)
			s = "Ramallah";
		else if (node == 9)
			s = "Jerusalem";
		else if (node == 10)
			s = "Bethlehem";
		else if (node == 11)
			s = "Hebron";
		else if (node == 12)
			s = "Akka";
		else if (node == 13)
			s = "Heifa";
		else if (node == 14)
			s = "Qalqilya";
		else if (node == 15)
			s = "Yaffa";
		else if (node == 16)
			s = "Al-Lid";
		else if (node == 17)
			s = "Al-Ramleh";
		else if (node == 18)
			s = "Gaza";
		return s;
	}

}
