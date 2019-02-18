/**
 * class description :this class implements the graph of the cities
 */

public class DirectedGraph {

	private static final String NEWLINE = System.getProperty("line.separator");
	private final int numberOfCities;
	private int numberOfEdges;
	// adj[c] = adjacency list for city c
	private AdjacencyList<DirectedEdge>[] adjacency;

	// Default Constructor
	@SuppressWarnings("unchecked")
	public DirectedGraph() {
		this.numberOfCities = 19; // default value
		this.numberOfEdges = 0; // initial value
		// create the adjacency array
		adjacency = (AdjacencyList<DirectedEdge>[]) new AdjacencyList[numberOfCities];
		for (int i = 0; i < numberOfCities; i++)
			adjacency[i] = new AdjacencyList<DirectedEdge>();
	}

	// Constructor with a specific value for numberOfCities
	@SuppressWarnings("unchecked")
	public DirectedGraph(int numberOfCities) {
		if (numberOfCities < 0)
			throw new IllegalArgumentException("Number of Cities must be nonnegative");
		this.numberOfCities = numberOfCities;
		this.numberOfEdges = 0; // initial value
		// create the adjacency array
		adjacency = (AdjacencyList<DirectedEdge>[]) new AdjacencyList[numberOfCities];
		for (int i = 0; i < numberOfCities; i++)
			adjacency[numberOfCities] = new AdjacencyList<DirectedEdge>();
	}

	// getter method
	public int getNumberOfCities() {
		return numberOfCities;
	}

	// getter method
	public int getNumberOfEdges() {
		return numberOfEdges;
	}

	public void addCityToGraph(DirectedEdge city) {
		int source = city.from();
		adjacency[source].add(city);
		numberOfEdges++;
	}

	// returns the directed edges incident from city(vertex) V
	public Iterable<DirectedEdge> adjacency(int v) {
		return adjacency[v];
	}

	// returns all DirectedEdges in this EdgeWeightedDirectedGraph
	public Iterable<DirectedEdge> edges() {
		AdjacencyList<DirectedEdge> list = new AdjacencyList<DirectedEdge>();
		for (int i = 0; i < numberOfCities; i++) {
			for (DirectedEdge e : adjacency(i)) {
				list.add(e);
			}
		}
		return list;
	}

	// returns a string representation of this EdgeWeightedDirectedGraph
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Number Of Cities : " + numberOfCities + " -- Number Of Edges : " + numberOfEdges + NEWLINE);
		for (int i = 0; i < numberOfCities; i++) {
			// s.append(i + ") ");
			for (DirectedEdge e : adjacency[i]) {
				s.append(e + "\n");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}
}
