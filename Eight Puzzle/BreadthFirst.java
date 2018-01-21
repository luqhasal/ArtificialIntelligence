/*  Luqhasal
 *  Artificial Intelligence
 */

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirst {

    //initialize variables
    private int m_nodes;
    public State initialState;
    public State state;

    //Queue and Hashset provide efficient data structure for storing states
    public final Queue<State> queue = new LinkedList<>();
    public final HashSet<State> visited = new HashSet<>();

    //constructor
    public BreadthFirst(int[] puzzleInput) {
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

    //add successors to the queue with repeated state detected not followed again
    private void addToQueue(State nextState) {
        if(nextState != null && !visited.contains(nextState)) 
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
    *  When it is not empty, remove the state from the fringe
    *  and check if it is a goal state. If yes, it will print zero 
    *  number of moves and program stop. If no, it will increase the counter 
    *  by 1 and save into visited list. The state will be expanded into possible
    *  moves in order L D U R then inserted into the fringe.
    */
    public void solve() {
        int nNodes = 0;
        queue.clear();
        queue.add(initialState);

        while(!queue.isEmpty()) {
            state = queue.remove();
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
            visited.add(state);
            addToQueue(Move.left(state));
            addToQueue(Move.down(state));
            addToQueue(Move.up(state));
            addToQueue(Move.right(state));
        }
    }
}