/**
 * class description : this class represents the path between every two
 * countries(nodes) and the distance between them
 */

public class DirectedEdge {

	private final int from;
	private final int to;
	private final double weight;
	private double pathCost;
	private boolean isVisited = false;

	// Constructor
	public DirectedEdge(int from, int to, double weight) {
		if (from < 0)
			throw new IllegalArgumentException("Illegal Value !");
		if (to < 0)
			throw new IllegalArgumentException("Illegal Value !");
		if (Double.isNaN(weight))
			throw new IllegalArgumentException("Illegal Value !");
		this.from = from;
		this.to = to;
		this.weight = weight;
		this.setPathCost(0);
	}

	// getter method
	public int from() {
		return from;
	}

	// getter method
	public int to() {
		return to;
	}

	// getter method
	public double weight() {
		return weight;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean visited) {
		this.isVisited = visited;
	}

	public double getPathCost() {
		return pathCost;
	}

	public void setPathCost(double pathCost) {
		this.pathCost = pathCost;
	}

	public String toString() {
		return "from: " + changeToName(from) + " --> " + " to: " + changeToName(to) + " ["
				+ String.format("%5.2f", weight) + "]";
	}
	
	public String changeToName(int index) {
		String s = null;
		if (index == 0)
			s = "Safad";
		else if (index == 1)
			s = "Tabarya";
		else if (index == 2)
			s = "Bisan";
		else if (index == 3)
			s = "Al-Nasrah";
		else if (index == 4)
			s = "Jenin";
		else if (index == 5)
			s = "Nablus";
		else if (index == 6)
			s = "Tulkarem";
		else if (index == 7)
			s = "Jericho";
		else if (index == 8)
			s = "Ramallah";
		else if (index == 9)
			s = "Jerusalem";
		else if (index == 10)
			s = "Bethlehem";
		else if (index == 11)
			s = "Hebron";
		else if (index == 12)
			s = "Akka";
		else if (index == 13)
			s = "Heifa";
		else if (index == 14)
			s = "Qalqilya";
		else if (index == 15)
			s = "Yaffa";
		else if (index == 16)
			s = "Al-Lid";
		else if (index == 17)
			s = "Al-Ramleh";
		else if (index == 18)
			s = "Gaza";
		return s;
	}
}
