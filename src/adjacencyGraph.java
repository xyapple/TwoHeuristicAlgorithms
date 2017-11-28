import java.util.ArrayList;
import java.util.HashMap;

/** This is the CS526 finall project
 * Design and Implement two Heuristic Algorithms
 * 
 * Reference:
 * https://www.ibm.com/developerworks/library/j-ai-search/
 * https://www.cs.princeton.edu/~rs/AlgsDS07/15ShortestPaths.pdf
 */

/**
 * @author Yin Deascentis
 *
 */
public class adjacencyGraph {
	/*
	 * set iniital instances basic on the project.pdf
	 */
	
	public String vertex; // vertex of the graph
	public HashMap <String, Integer> distance; //this is the distance which read from the "direct_distance.txt"
	public HashMap <String, Integer> inputMap; //this is the weight which read from the "graph_input.txt"
	
	public String finalVertexSet; //this store all the vertices (vertex along the path) in the Set structure
	public String lastVertexToAdd; //this is the last vertex to be added
	
	public ArrayList <String> pathWithBackTrack = new ArrayList <>(); //Path that has dead end and duplicate vertices
	public ArrayList <String> pathNoBackTrack = new ArrayList <>(); //path that without duplicate vertices
	public ArrayList <String> pathUsedList = new ArrayList <>(); //path that had been used and should not be using again!
	public int shortestDistance = 0; //this is the calculation of the path
	
	/*
	 */
	public adjacencyGraph(){
	   
    }
    
	
	
    /** setter
     * Input: Starting node.
     * Output: None.
     * Use: Setter for the starting node.
     */
    public void setStartingNode(String vertex){
        this.vertex = vertex;
    }
    
    
    
	/**
	 * ArrayList of vertices adjacent to the input node. 
	 * 
	 * eg: Adjacent nodes: A, C, I, K
	 */
	
	public ArrayList<String> getAdjacentSet(String vertex) {
		//key are the "A, B, C.." 
		ArrayList <String> keys = new ArrayList<>();
		//store the keys into the Arraylist
		keys.addAll(inputMap.keySet());
		
		ArrayList <String> adjacent = new ArrayList<>();
		
		// Go through all the vertices on the map		
		for (String key: keys){
			
			char c[] = key.toCharArray(); // find the ones that are adjacent to the input vertex
			String vertex1 =  Character.toString(c[0]);			
			if (vertex1.equals(vertex)){
				adjacent.add(key);
			}
		}
		
		return adjacent;
	}
 
	
	
	/**This is going to print out what the Path node
	 * Input: ArrayList of a nodes.
	 * Output: Formated string of a path sequence.
	 * Use: Retrieve string representation of
	 * a path sequence.
	 */
	public String printPathNodes (ArrayList <String> sequence){
		
		StringBuilder sb = new StringBuilder();
		int finalElement = sequence.size()-1;
		
		
		for (int i = 0; i < sequence.size(); i++){
			
			if (i == finalElement){
				sb.append(sequence.get(i));
			} else {
				sb.append(sequence.get(i) + " -> ");
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Input: None.
	 * Output: Integer of total distance
	 * for the path taken.
	 * Use: Getter for the final distance.
	 */
	public int printPathDistance(){
		return shortestDistance;
	}
 
	
}//end of the adjacenyGraph
