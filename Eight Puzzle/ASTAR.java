/*  Luqhasal
 *  Artificial Intelligence
 */

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class ASTAR {

    //initialize variables
    private int m_nodes;
    public State initialState;
    public State state;

    //Priority Queue (with heuristic comparator function) and Hashset provide efficient data structure for storing states
    public final PriorityQueue<State> queue = new PriorityQueue<State>(1000, new Comparator<State>() {
    //compare neighbour states with F = H heuristic value + G path cost
    @Override
    public int compare(State o1, State o2) {
        int result = o1.f() - o2.f();
        if (result == 0){
            result = o2.f() - o1.f();
        }
        return result;
    }
    });
    public final HashSet<State> visited = new HashSet<>();

    //constructor
    public ASTAR(int[] puzzleInput) {
        initialState = new State(puzzleInput);
        state = initialState;
    }

    //inversion check - odd is not solvable and even is solvable
    public boolean isSolvable() {
        int inversions = 0;
        int[] p = state.array;

        for(int i = 0; i < p.length - 1; i++) {
            for(int j = i + 1; j < p.length; j++)
                if(p[i] > p[j]) inversions++;
                if(p[i] == 0 && i % 2 == 1) inversions++;
        }
        return (inversions % 2 == 0);
    }

    //number of misplaced tiles and return the total
    public static int getHeuristic(int[] array) {
    int heuristic = 0;
    int[] linearArr = {1,2,3,4,5,6,7,8,0};
    for(int i = 0; i < array.length; i++) {
        if(array[i]==0) {
            continue;
        } else if (array[i]!=linearArr[i])
            heuristic++;
    }
    return heuristic;
    }

    //add successors to the queue with repeated state NOT detected
    private void addToQueue(State nextState) {
        //if(nextState != null && !visited.contains(nextState)) 
        if(nextState != null) 
            queue.add(nextState);
    }

    //setter
    public void setMaxnodes(int n) {
        m_nodes = n;
    }

    //getter
    public int getMaxnodes() {
        return m_nodes;
    }    

    /* Clear the queue before the operation. Add the root node.
    *  When it is not empty, the PQ will poll for the lowest heuristic state
    *  and check if it is a goal state. If yes, it will print zero 
    *  number of moves and program stop. If no, it will increase the counter 
    *  by 1. The state will be expanded into possible
    *  moves in order L D U R then inserted into the fringe.
    */
    public void solve() {
        int nNodes = 0;
        queue.clear();
        queue.add(initialState);

        while(!queue.isEmpty()) {
            state = queue.poll();
            if (state.isSolved()) {
                System.out.println(state.solutionMessage());
                //System.out.println(nNodes);  For display total no. of nodes expanded
                return;
            }
            nNodes++;
            //if the expanded nodes count hit the limit, print the message
            if (nNodes == getMaxnodes()) {
                System.out.println("Not Found");
                System.exit(0);
            }
            //visited.add(state);
            addToQueue(Move.left(state));
            addToQueue(Move.down(state));
            addToQueue(Move.up(state));
            addToQueue(Move.right(state));
        }
    }

}