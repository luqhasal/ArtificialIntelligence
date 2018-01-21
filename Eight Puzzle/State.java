/*  Luqhasal
 *  Artificial Intelligence
 */

import java.util.Arrays;

public class State {

    /* initialize variables
     * g is the path cost
     * h is the heuristic cost
     */
    int[] array = new int[9];
    int blankIndex;
    int g;
    String action;
    int h;
    State previous;

    //constructor
    public State(int[] input) {
        this.array = input;
        this.blankIndex = getIndex(input, 0);
        this.g = 0;
        this.action = "";
        this.previous = null;
        this.h = ASTAR.getHeuristic(this.array);
    }

    //copy state
    public State(State previous, int blankIndex, String action) {
        this.array = Arrays.copyOf(previous.array, previous.array.length);
        this.array[previous.blankIndex] = this.array[blankIndex];
        this.array[blankIndex] = 0;
        this.blankIndex = blankIndex;
        this.g = previous.g + 1;
        this.action = action;
        this.h = ASTAR.getHeuristic(this.array);
        this.previous = previous;
    }

    //get blank index position
    public static int getIndex(int[] array, int value) {
        for (int i = 0; i < array.length; i++)
            if (array[i] == value) return i;
        return -1;
    }

    //Goal state and return true if found, false if not 
    public boolean isSolved() {
        int[] p = this.array;
        for (int i = 1; i < p.length - 1; i++)
            if(p[i-1] > p[i]) return false;
    return (p[0] == 1);
    }

    //print state
    public String toString() {
        int[] state = this.array;
        String s = "\n\n";
        for(int i = 0; i < state.length; i++) {
            if(i % 3 == 0 && i != 0) s += "\n";
            s += (state[i] != 0) ? String.format("%d ", state[i]) : "9 ";
        }
        return s;
    }

    //print the path from root to the goal
    public String allSteps() {
        StringBuilder sb = new StringBuilder();
        if (this.previous != null) 
            sb.append(previous.allSteps());
            sb.append(this.toString());
        return sb.toString();
    }

    //print the solution 
    public String solutionMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.allSteps());
        sb.append("\n\n" + this.g + " moves\n");
        return sb.toString();
    }

    //get path cost
    public int g() {
        return this.g;
    }

    //get heuristic cost
    public int h() {
        return this.h;
    }

    //get the estimated total cost
    public int f() {
        return g() + h();
    }

    //previous state
    public State getPrevious() {
        return this.previous;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;
        return Arrays.equals(array, state.array);
    }

    //for hashset mapping - optimization
    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }   
}