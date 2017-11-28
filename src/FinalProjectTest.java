import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class FinalProjectTest {
    //user input
    private static Scanner input = new Scanner(System.in);
    //arraylist to store user input
    private static ArrayList<String> inputVertex = new ArrayList<>();
    private static HashMap <String, Integer> distance = new HashMap<> ();
    private static HashMap <String, Integer> inputMap = new HashMap<> ();
    private static String[][] matrix = null;
    
    static int lenghtOfArray = 0;
    static int widthOfArray = -1;
    
    public static void main(String[] args){

        // Load necessary data
        // All data files to be found in the 'data' folder
        processFileData();
        if (distance.isEmpty()  ||  inputMap.isEmpty()) {
        
            System.out.println("\nPlease check data files. Not enough data to complete"
                    + " calculations. Once files have been added, please restart the"
                    + " program. Thank you.");
        }
        
        
   displayUserMenu();
        
        
    
    }//end of main method
    
   
    
    /**
     * Function: menu()
     * Input: Scanner object for retrieving user input.
     * Output: None.
     * Use: Recursive function that prompts the user
     * for an input node. Checks for node validity.
     * Also serves as a secondary check for input files.
     */
    //Display user menu for user to choose
    public static void displayUserMenu(){
        
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
                System.out.println("Please enter number from 1 to 2.");
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
     * Input: Node selected by the user.
     * Output: None.
     * Use: Initializes Algorithm1 and Algorithm2.
     * Retrieves the heuristic shortest paths.
     * Prints respective output to the screen.
     */
    public static void processUserInput(String input){
        
        AlgorithmOne algorithm1 = new AlgorithmOne(input, distance, inputMap);
        // Calculates shortest path using distance file for distance metric
        // Prints the path nodes
        // Prints the distance
        algorithm1.getShortestDistance();
        
        
        AlgorithmTwo algorithm2 = new AlgorithmTwo(input, distance, inputMap);
        // Calculates shortest path using both input and distance file for distance metric
        // Prints the path nodes
        // Prints the distance
        algorithm2.getShortestDistance();
        
        
    }
    
    /**This is a helper function
     * Input: None.
     * Output: None.
     * Use: Retrieves the files hard coded in the
     * function. Stores retrieved values in hashmaps.
     */
    public static void processFileData(){
        //create a new arraylist to store files
       ArrayList<File> files = new ArrayList<>();
       
        // editable fields
        String directDistanceFile = "direct_distance.txt";
        String graphInputFile = "graph_input.txt";
        
        // end of editable fields
        File file1 = new File(directDistanceFile);
        File file2 = new File(graphInputFile);
        files.add(file1);
        files.add(file2);
        

        for (File file: files){
            try {
                // store direct distances in a hashmap
                if (file.toString().contains("distance")){
                    
                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);
                    String line;
                    
                    while ((line = br.readLine()) != null) {
                        
                        StringBuilder num = new StringBuilder();
                        StringBuilder str = new StringBuilder();
                        
                        for(char c : line.toCharArray()){
                            //find the distance
                            if(Character.isDigit(c)){
                                num.append(c);
                            }
                            //find the associated letter
                            else if(Character.isLetter(c)){
                                str.append(c);
                            }
                        }
                        
                        // add values into hashmap
                        distance.put(str.toString(), Integer.parseInt(num.toString()));
                        
                    }
                    
                    br.close(); // close the reader
                    
                }
                
                // store inputs in a
                else if (file.toString().contains("input")){
                    
                    
                    //FileReader fileReader = new FileReader(dataPath.toString() + "/" + file);
                    FileReader fileReader = new FileReader(file);
                    BufferedReader reader = new BufferedReader(fileReader);
                    
                    String line;
                    
                    int x=0; // keeps track of line to add
                    
                    
                    while ((line = reader.readLine()) != null) {
                        String[] values = line.split("\\s+");
                        
                        
                        if (matrix == null) {
                            //instantiate matrix
                            matrix = new String[widthOfArray = values.length]
                                    [lenghtOfArray = values.length];
                        }
                        
                        
                        // add values into the matrix
                        for (int i=0; i < values.length; i++){
                            
                            matrix[i][x] = values[i];
                            
                        }
                        
                        x++; // next line
                        
                        
                    }
                    
                    reader.close(); // close the reader
                    
                }
                
                // Store input combinations in a hashmap
                int y=1;
                while (y < lenghtOfArray){
                    
                    inputVertex.add(matrix[0][y]);
                    
                    for (int i=1; i < widthOfArray; i++){
                        
                        StringBuilder str = new StringBuilder();
                        str.append(matrix[0][y]);
                        str.append(matrix[i][0]);
                        
                        int inputValue = Integer.parseInt(matrix[i][y]);
                        
                        if (inputValue > 0){
                            inputMap.put(str.toString(), inputValue);
                        }
                        
                    }
                    
                    y++;
                }
                
                
            } catch (Exception e) {
                System.out.println("WARNING: Please check: "+ file.toString() + ". It was not found.");
            }
            
        }
        
    }
    
}
