/**
 * This is the CS526 finall project
 * <p>
 * Design and Implement two Heuristic Algorithms
 * Author: Yin Deascentis
 * <p>
 * Code Reference:
 * https://www.ibm.com/developerworks/library/j-ai-search/
 * https://www.cs.princeton.edu/~rs/AlgsDS07/15ShortestPaths.pdf
 * https://github.com/alesiaaa/CS566/tree/master/HeuristicPathFinder
 * https://github.com/kizzlebot/Computer-Science-II/blob/master/assignment/agn1a/Graph.java
 */

import java.util.ArrayList;
import java.util.HashMap;

public class AlgorithmTwo extends adjacencyGraph {
    /**
     * Constructor for AlgorithmTwo.
     * Input: Take user input vertex, hashmap distance and inputMap
     * Output: None.
     */
    public AlgorithmTwo(String vertex, HashMap<String, Integer> distance,
                        HashMap<String, Integer> inputMap) {
        this.vertex = vertex;
        this.distance = distance;
        this.inputMap = inputMap;
        
        System.out.println("=========== Algorithm 2 ===========");
    }
    
    /**
     * String of the vertex that is adjacent to specified input vertex with the
     * shortest distance (from direct distance file only).
     * eg:A: w(J, A) + dd(A) = 151 + 380 = 531; C: w(J, C) + dd(C) = 140 + 366 = 506
     * I: w(J, I) + dd(I) = 80 + 193 = 273; K: w(J, K) + dd(K) = 99 + 176 = 275
     *  then I is selected
     */
    private String getShortestSetAlgTwo(String vertex) {
        //this adjacent list is the one that get from the getAdjacentSet() method
        ArrayList<String> adjacent = getAdjacentSet(vertex);
        
        String shortestSet = null; //the shortest set is empty to start
        int maxValue = 9999999;
        char c2[] = null;
        
        // find shortest path from just reading the "direct_distance.txt" file
        for (String s : adjacent) {
            
            c2 = s.toCharArray();
            
            // Check to find the smallest distance
            if ((distance.get(Character.toString(c2[1])) + inputMap.get(s)) < maxValue
                    && !this.pathUsedList.contains(Character.toString(c2[1]))
                    ) {
                maxValue = distance.get(Character.toString(c2[1])) + inputMap.get(s);
                shortestSet = s;
            }
            
            
        }
        
        return shortestSet;
    }
    
    /**
     * Retrieve the distance of next vertex until vertex "Z" is reached.
     * Used recursively until final path is found.
     *
     * eg: Shortest path: J → K
     */
    public void getDistance(String vertex) {
        //this set stores the shortest distance of the vertex
        String vertexSet = getShortestSetAlgTwo(vertex);
        
        // vertex requested was "Z"
        if (vertex.equals("Z")) {
            
            this.lastVertexToAdd = vertex;
            
            //update paths
            pathWithBackTrack.add(lastVertexToAdd);
            pathNoBackTrack.add(lastVertexToAdd);
            
        } //end of if
        
        // handle track back
        //Implements backtracking as a way to avoid dead paths.
        // if returned set is null (dead end) back track
        else if (vertexSet == null && !vertex.equals("Z")) {
            
            // remove last added node from the final path
            this.pathWithBackTrack.remove(lastVertexToAdd);
            
            // update last added node
            this.lastVertexToAdd = pathWithBackTrack.get(pathWithBackTrack.size() - 1);
            
            // update the complete sequence path
            this.pathNoBackTrack.add(lastVertexToAdd);
            
            // subtract last added node from distance
            shortestDistance -= inputMap.get(finalVertexSet);
            
        }//end of else if
        
        else {
            
            // record the complete set
            finalVertexSet = vertexSet;
            
            // record only the last node
            char currentSet[] = vertexSet.toCharArray();
            lastVertexToAdd = Character.toString(currentSet[1]);
            
            
            // add visited nodes to the banned list
            // this serves as a check for future search
            for (char c : currentSet) {
                if (!this.pathUsedList.contains(Character.toString(c))) {
                    this.pathUsedList.add(Character.toString(c));
                }
            }
            
            //also add to final path sequence
            if (!pathWithBackTrack.isEmpty()) {
                pathWithBackTrack.add(lastVertexToAdd);
            } else {
                pathWithBackTrack.add(vertex);
                pathWithBackTrack.add(lastVertexToAdd);
            }
            
            //and add to complete path sequence of visited nodes
            if (!pathNoBackTrack.isEmpty()) {
                pathNoBackTrack.add(lastVertexToAdd);
            } else {
                pathNoBackTrack.add(vertex);
                pathNoBackTrack.add(lastVertexToAdd);
            }
            
            
            // add new value to the final distance
            shortestDistance += inputMap.get(vertexSet);
            
        }//end of else
        
        // Check to see if we are done
        if (lastVertexToAdd.equals("Z")) {
            
            System.out.println("Sequence of all nodes: " + printPathNodes(this.pathNoBackTrack));
            System.out.println("Shortest path: " + printPathNodes(this.pathWithBackTrack));
            System.out.println("Shortest path length: " + printPathDistance());
            System.out.println(" ");
        }
        
        // Not done
        else {
            // Continue to look for path recursively
            getDistance(lastVertexToAdd);
            
        }
        
    }
    
    /**
     * Input: None.
     * Output: None.
     * Use: Publicly facing function to start
     * Algorithm1 work.
     */
    public void getShortestDistance() {
        // Initial call for the getDistance function
        getDistance(this.vertex);
    }
    
}//end of AlgorithmTwo class
