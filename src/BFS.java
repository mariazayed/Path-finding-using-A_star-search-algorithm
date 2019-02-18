import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class BFS {
	public static int discoverdNode=0;
	public static int visitedNode=0;
	public ArrayList<NodeB> bFSAlgorithem(DirectedGraph graph, int source, int goal){
		ArrayList<NodeB> discoverd = new ArrayList<NodeB>();
		ArrayList<NodeB> path = new ArrayList<NodeB>();
		Queue<NodeB> queue = new LinkedList<NodeB>();
		Iterable<DirectedEdge> adjacent;
		NodeB current = new NodeB(source,0,null);
		queue.offer(current);
		discoverd.add(current);
		discoverdNode++;
		visitedNode++;
		while(current.getNodeB()!=goal && !queue.isEmpty()){
			current=queue.poll();
			adjacent = graph.adjacency(current.getNodeB());
			for (DirectedEdge d : adjacent) {
				NodeB check = new NodeB(d.to());
				check.setParent(current);
				check.setPathCost(current.getPathCost()+d.weight());
				if(discoverd.contains(check) == false){
					discoverd.add(check);
					queue.offer(check);
					discoverdNode++;
				}
			}
			visitedNode++;
		}
		if(current.getNodeB() == goal){
			path.add(0,current);
			current=current.parent;
		}
		else
			path=null;
		
		return path;
	}
	public class NodeB{
	int NodeB;
	double pathCost;
	NodeB parent;
	public NodeB(){
		this.NodeB=-1;
		this.pathCost=0;
		this.parent=null;
	}
	public NodeB(int NodeB){
		this.NodeB=NodeB;
		this.pathCost=0;
		this.parent=null;
	}
	public NodeB(int NodeB,double pathCost,NodeB parent){
		this.NodeB=NodeB;
		this.pathCost=pathCost;
		this.parent=parent;
	}
	public int getNodeB() {
		return NodeB;
	}
	public void setNodeB(int NodeB) {
		this.NodeB = NodeB;
	}
	public double getPathCost() {
		return pathCost;
	}
	public void setPathCost(double pathCost) {
		this.pathCost = pathCost;
	}
	public NodeB getParent() {
		return parent;
	}
	public void setParent(NodeB parent) {
		this.parent = parent;
	}
	@Override
	public boolean equals(Object obj) {
		return this.NodeB == ((NodeB) obj).getNodeB();
	}
	
	
	}

}
