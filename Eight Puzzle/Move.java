/*  Luqhasal
 *  Artificial Intelligence
 */

/* L D U R possible move class
 * Check for the wall which blank tile not supposed to move
 */
public class Move {

  //LEFT
  public static State left(State state) {
    state.action = "L";
    if (state.blankIndex % 3 > 0)
      return new State(state, state.blankIndex - 1, state.action);
    return null;
  }

  //DOWN
  public static State down(State state) {
    state.action = "D";
    if (state.blankIndex < 6)
      return new State(state, state.blankIndex + 3, state.action);
    return null;
  }

  //UP
  public static State up(State state) {
    state.action = "U";
    if (state.blankIndex > 2)
      return new State(state, state.blankIndex - 3, state.action);
    return null;
  }

  //RIGHT
  public static State right(State state) {
    state.action = "R";
    if (state.blankIndex % 3 < 2)
      return new State(state, state.blankIndex + 1, state.action);
    return null;
  }

}