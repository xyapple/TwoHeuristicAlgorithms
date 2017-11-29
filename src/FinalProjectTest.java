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


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FinalProjectTest {
    //user input
    private static Scanner input = new Scanner(System.in);
    //arraylist to store user input
    private static ArrayList<String> inputVertex = new ArrayList<>();
    private static HashMap<String, Integer> distance = new HashMap<>();
    private static HashMap<String, Integer> inputMap = new HashMap<>();
    private static String[][] matrix = null;
    
    public static void main(String[] args) {
        
        // Load and process necessary data
        processFileData();
        
        //display the user menu
        displayUserMenu();
        
        
    }//end of main method
    
    //Display menu for user to choose
    public static void displayUserMenu() {
        
        // user choice for menu
        int userChoice = 0;
        System.out.println("1. Please enter a starting node: ");
        System.out.println("2. Exit");
        
        
        do {
            if (input.hasNextInt()) {
                userChoice = input.nextInt();
                // In order for user not to skip the next line!
                input.nextLine();
                
            } else {
                System.out.println("Number not entered, try again");
                // discarded bad input
                input.next();
            }
            // check valid selection
            if (userChoice < 1 || userChoice > 2) {
                System.out.println("Please enter number 1 or 2.");
            }
            
            switch (userChoice) {
                case 1:
                    System.out.println("Enter Vertex that you wanted to start: ");
                    String inputNode = input.nextLine();
                    // Add a key
                    if (inputVertex.contains(inputNode.toUpperCase())) {
                        // process user input
                        processUserInput(inputNode.toUpperCase());
                        
                    } else {
                        
                        System.out.println("\nThe node you requested is not a valid start node or"
                                + " was not found. Please try again.\n");
                        
                    }
                    displayUserMenu();
                    break;
                
                case 2:
                    System.out.println("GoodBye!");
                    System.exit(0);
                    break;
            }
            
        } while (userChoice != 2);
        
    }
    
    /**
     *The method will process the user input
     */
    public static void processUserInput(String input) {
        
        //initialize Algorithm1
        AlgorithmOne algorithm1 = new AlgorithmOne(input, distance, inputMap);
        algorithm1.getShortestDistance();
        
        //initialize Algorithm2
        AlgorithmTwo algorithm2 = new AlgorithmTwo(input, distance, inputMap);
        algorithm2.getShortestDistance();
        
        
    }
    
    /**This is a helper function
     */
    
    public static void processFileData() {
        //input file names
        String file1 = "direct_distance.txt";
        String file2 = "graph_input.txt";
        //file reader
        FileReader fr1 = null;
        FileReader fr2 = null;
        
        try {
            fr1 = new FileReader(file1);
            fr2 = new FileReader(file2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //use buffer reader
        BufferedReader br1 = new BufferedReader(fr1);
        BufferedReader br2 = new BufferedReader(fr2);
        try {
            String line;
            // read one line at a time until end of file
            // store direct distances in a hashmap
            while ((line = br1.readLine()) != null) {
                
                StringBuilder num = new StringBuilder();
                StringBuilder str = new StringBuilder();
                
                for (char c : line.toCharArray()) {
                    //find the distance
                    if (Character.isDigit(c)) {
                        num.append(c);
                    }
                    //find the associated letter
                    else if (Character.isLetter(c)) {
                        str.append(c);
                    }
                }
                
                // add values into hashmap
                distance.put(str.toString(), Integer.parseInt(num.toString()));
                
            }
            
            int row = 0;
            int column = -1;
            int x = 0; // keeps track of line to add
            int y = 1; // y is the number from that '151   0 140   0   0   0   0   0  80   0  99   0   0   0   0   0   0   0   0   0   0'
            while ((line = br2.readLine()) != null) {
                String[] values = line.split("\\s+");
                
                //instantiate matrix
                if (matrix == null) {
                    
                    matrix = new String[column = values.length]
                            [row = values.length];
                }
                
                // add values into the matrix
                for (int i = 0; i < values.length; i++) {
                    
                    matrix[i][x] = values[i];
                    
                }
                
                x++; // next line
                
                
            }
            while (y < row) {
                
                inputVertex.add(matrix[0][y]);
                
                for (int i = 1; i < column; i++) {
                    
                    StringBuilder str = new StringBuilder();
                    str.append(matrix[0][y]);
                    str.append(matrix[i][0]);
                    
                    int inputValue = Integer.parseInt(matrix[i][y]);
                    
                    if (inputValue > 0) {
                        inputMap.put(str.toString(), inputValue);
                    }
                    
                }
                
                y++;
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Years in Office must be a number. ");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr1 != null && fr2 != null) {
                    fr1.close();
                    fr2.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        
    }
    
}
