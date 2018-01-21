/*  Luqhasal
 *  Artificial Intelligence
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EightP {

    //initialize variable for command line params
    private static int maxnodes;
    private static String searchtype;
    private static String heuristictype;
    private static final int[] ARRAYGRID = new int [9];
    //test config 8 6 7 2 5 4 3 9 1 31 moves
    //test config 6 4 7 8 5 9 3 2 1 31 moves
    //test config 4 6 3 7 1 2 5 9 8 19 moves
    //test config 1 8 2 9 4 3 7 6 5 09 moves
    BreadthFirst bfs = null;

    public static void main(String[] args) {
        
        //command line argument parser by running through args and save into hashmap
        final Map<String, List<String>> params = new HashMap<>();
        List<String> options = null;
        
        for (int i = 0; i < args.length; i++) {
            final String a = args[i];
            
            if (a.charAt(0) == '-') {
                if (a.length() < 2) {
                    System.err.println("Error at argument " + a);
                    return;
                }
        
            options = new ArrayList<>();
            params.put(a.substring(1), options);
            }
            else if (options != null) {
                options.add(a);
            }
            else {
                System.err.println("Illegal parameter usage");
                return;
            }
        }
        
        //read 'startpositon' file after bash redirection "<" and save into array
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            for (int i = 0; i < 9; i++) {
                    int tmp = Integer.parseInt(sc.next());;
                        if (tmp == 9){
                            tmp = 0;
                        }
                    ARRAYGRID[i] = tmp;
                }
        }

        //populate values by capturing flags
        searchtype = params.get("s").get(0);
        heuristictype = params.get("h").get(0);
        maxnodes = Integer.parseInt(params.get("m").get(0));

        //conditional switching
        switch (searchtype) {

            //BFS search
            case "BreadthFirst":
                BreadthFirst bfs = new BreadthFirst(ARRAYGRID);
                bfs.setMaxnodes(maxnodes);
                if (!bfs.isSolvable()) {
                    System.out.printf("Initial puzzle is not solvable!\n", bfs.state.toString());
                    System.exit(0);
                }
                bfs.solve();
                break;

            //ASTAR search    
            case "ASTAR":
                ASTAR astar = new ASTAR(ARRAYGRID);
                if (!astar.isSolvable()) {
                    System.out.printf("Initial puzzle is not solvable!\n", astar.state.toString());
                    System.exit(0);
                }
                astar.solve();               
                break;
                
            //Exception error    
            default:
                System.out.println("Invalid searchtype name");
        }
    }
}