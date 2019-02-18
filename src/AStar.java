import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;

public class AStar {
	private int numOfCities = 19; // number of cities in graph
	/*
	 *  numberOfCities--> # of rows 
	 *  2 --> # of columns
	 *  every city has X-coordinate and Y-coordinate
	 */
	private int[][] coordinates = new int[numOfCities][2]; 
	private double[][] distance = new double[numOfCities][numOfCities]; 
	private double[][] time = new double[numOfCities][numOfCities]; 
	private double[][] cost = new double[numOfCities][numOfCities]; 

	public ArrayList<Node> bestSolution(DirectedGraph graph, int source, int goal, int optimal) {
		ArrayList<Node> expanded = new ArrayList<Node>(); // array list of type Node , used to store the expanded nodes
		ArrayList<Node> path = new ArrayList<Node>(); // array list of type Node , used to store the path nodes
		readFile(); // coordinates
		// printArray(distence);
		// printArray(time);
		// printArray(cost);
		Iterable<DirectedEdge> adjacent; // 
		// double finalCost;
		double pathCost = 0;
		Node.numOfExpandedNode = 0;
		Node.numOfVisitedNode = 0;
		Node current = new Node(source);
		current.setExpanded(true); // increment the number of expanded nodes
		current.setVisited(true); // increment the number of visited nodes
		current.setPathCost(0);
		current.setTotalCost(Heuristic(current, goal, optimal));
		current.setParent(null);
		expanded.add(current);
		while (current.getNode() != goal) {
			adjacent = graph.adjacency(current.getNode());
			for (DirectedEdge d : adjacent) {
				pathCost = d.weight() + current.getPathCost();
				d.setPathCost(pathCost);
				Node check = new Node(d.to());
				check.setParent(current);
				check.setPathCost(pathCost);
				check.setExpanded(true);
				check.setTotalCost(pathCost + Heuristic(check, goal, optimal));
				if (expanded.contains(check) == false)
					expanded.add(check);
				else { // the node is exist
					Node exist = expanded.get(expanded.indexOf(check));
					if (!exist.isVisited() && exist.isExpanded()) {
						if (exist.getTotalCost() > check.getTotalCost()) {
							expanded.remove(exist);
							expanded.add(check);
						}
					} else if (exist.isVisited() && exist.getNode() != source) {
						// if the node isVisited , it have a children
						// we must re-calculate the cost in the graph
						if (exist.getTotalCost() > check.getTotalCost()) {
							for (int k = 0; k < expanded.size(); k++) {
								if (expanded.get(k).getNode() != source && expanded.get(k).getParent().equals(exist)) {
									expanded.get(k).setParent(check);
									switch (optimal) {
									case 1:
										expanded.get(k).setPathCost(findPathCost(expanded.get(k), distance));
										break;
									case 2:
										expanded.get(k).setPathCost(findPathCost(expanded.get(k), time));
										break;
									case 3:
										expanded.get(k).setPathCost(findPathCost(expanded.get(k), cost));
										break;
									}
								} else {//if exist has cheldren
									Node c = expanded.get(k);
									while (c != null) {
										if (c.equals(exist)) {
											switch (optimal) {
											case 1:
												expanded.get(k).setPathCost(findPathCost(expanded.get(k), distance));
												break;
											case 2:
												expanded.get(k).setPathCost(findPathCost(expanded.get(k), time));
												break;
											case 3:
												expanded.get(k).setPathCost(findPathCost(expanded.get(k), cost));
												break;
											}
											break;
										}
										c = c.getParent();
									}

								}
							}
							expanded.remove(exist);
							expanded.add(check);
						}
					}
				}
			}
			boolean flag = false;
			// when all the nodes in expanded is visited , there is
			// no path from source to goal, - no solution -
			for (int i = 0; i < expanded.size(); i++) {
				if (!expanded.get(i).isVisited()) {
					flag = true;
					break;
				}

			}
			if (!flag)
				return null;  //no solution
			Collections.sort(expanded);
			// find the node with least path cost and not visited
			for (int i = 0; i < expanded.size(); i++) {
				if (expanded.get(i).isVisited() == false) {
					current = expanded.get(i);
					expanded.get(i).setVisited(true);
					break;
				}

			}

		}
		//end AStar
		// finalCost = current.getPathCost();
		while (current != null) {
			path.add(0, current);
			current = current.getParent();
		}

		return path;

	}

	private double Heuristic(Node from, int goal, int optimal) {
		double h;
		switch (optimal) {
		case 1:
			h = dHeuristic(from.getNode(), goal);
			break;
		case 2:
			h = tHeuristic(from, goal);
			break;
		case 3:
			h = cHeuristic(from, goal);
			break;
		default:
			h = 0;
		}

		return h;
	}

	private double dHeuristic(int from, int goal) {
		double dx, dy, h;
		dx = Math.pow((coordinates[from][0] - coordinates[goal][0]), 2);
		dy = Math.pow((coordinates[from][1] - coordinates[goal][1]), 2);
		h = Math.sqrt(dx + dy) * 9;
		return h;
	}

	private double tHeuristic(Node from, int goal) {
		double dh, dCost, tCost, speed, h;
		dh = dHeuristic(from.getNode(), goal);
		dCost = findPathCost(from, distance);
		tCost = findPathCost(from, time);
		if (tCost == 0)
			speed = 2.0; // 120 km/h
		else
			speed = dCost / tCost;
		speed = (speed / (speed + 0.5)) + 1;  //60km/h < speed < 120km/h
		h = dh / speed;
		return h;
	}

	private double cHeuristic(Node from, int goal) {
		double dh, dCost, tCost, h, numL, f;
		dh = dHeuristic(from.getNode(), goal);
		dCost = findPathCost(from, distance);
		tCost = findPathCost(from, time);
		numL = dh / 10.0;   // 10km --> 1 L 
		if (tCost == 0)
			f = 2.0 / 3.0;
		else
			f = (2.0) / (3.0) / (dCost / tCost);
		f = f / (2 * f + 0.1);
		h = numL * f * 7;
		return h;
	}

	public boolean testHeuristic(int optimal, DirectedGraph dg) {
		int source = 0, from = 0, goal = 0;
		boolean badHeuristic = false;
		while (distance[from][goal] == 0) {
			source = (int) (Math.random() * 19);
			from = (int) (Math.random() * 19);
			goal = (int) (Math.random() * 19);
		}
		ArrayList<Node> aStar = bestSolution(dg, source, from, optimal);
		double h = Heuristic(aStar.get(aStar.size() - 1), goal, optimal);
		switch (optimal) {
		case 1:
			if (h >= distance[from][goal])
				badHeuristic = true;
			break;
		case 2:
			if (h >= time[from][goal])
				badHeuristic = true;
			break;
		case 3:
			if (h >= cost[from][goal])
				badHeuristic = true;
			break;
		}

		return badHeuristic;
	}

	public boolean testAlgorithem(int optimal, DirectedGraph graph) {
		int source, goal;
		source = (int) (Math.random() * 19);
		goal = (int) (Math.random() * 19);
		ArrayList<Node> path1 = bestSolution(graph, source, goal, optimal);
		BFS bfs = new BFS();
		ArrayList<BFS.NodeB> path2 = bfs.bFSAlgorithem(graph, source, goal);
		double cost1, cost2;
		cost1 = path1.get(path1.size() - 1).getPathCost();
		cost2 = path2.get(path2.size() - 1).getPathCost();
//		System.out.println("expanded node");
//		System.out.println("Alg1  "+Node.numOfExpandedNode + "  Alg2  "+BFS.discoverdNode); 
//		System.out.println("visited node");
//		System.out.println("Alg1  "+Node.numOfVisitedNode + "  Alg2  "+BFS.visitedNode);
		return cost1 > cost2;  //return true if the algorithm is bad
	}

	public double findPathCost(Node node, double[][] array) {
		double cost = 0;
		while (node.getParent() != null) {
			cost += array[node.getNode()][node.getParent().getNode()];
			node = node.getParent();
		}
		return cost;
	}

	public void printArray(double[][] x) {
		System.out.println();
		System.out.print("        ");
		for (int i = 0; i < x.length; i++)
			System.out.printf("%-8d", i);
		System.out.println();
		for (int i = 0; i < x.length; i++) {
			System.out.printf("%-8d", i);
			for (int j = 0; j < x[i].length; j++)
				System.out.printf("%-8.2f", x[i][j]);
			System.out.println();
		}
	}

	// read the coordinates file
	private void readFile() {
		try {
			Scanner x = new Scanner(new File("coordinates.txt"));
			for (int i = 0; i < numOfCities; i++) {
				x.next();
				coordinates[i][0] = x.nextInt();
				coordinates[i][1] = x.nextInt();
			}
			x.close();
		} catch (Exception e) {
			System.out.println(" could not find file");
		}
	}

	public double[][] getDistence() {
		return distance;
	}

	public void setDistence(double[][] distence) {
		this.distance = distence;
	}

	public double[][] getTime() {
		return time;
	}

	public void setTime(double[][] time) {
		this.time = time;
	}

	public double[][] getCost() {
		return cost;
	}

	public void setCost(double[][] cost) {
		this.cost = cost;
	}

	public void setDistence(int x, int y, double value) {
		this.distance[x][y] = value;
	}

	public void setTime(int x, int y, double value) {
		this.time[x][y] = value;
	}

}