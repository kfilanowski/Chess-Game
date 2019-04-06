package History;

import java.util.ArrayList;
import java.util.List;
import Model.Board;

/**
 * The history class holds each state of an entity class. In this case, a board.
 * It allows the board to change states, effectively functioning as an undo/redo.
 * 
 * @author Kevin Filanowski 100%
 * @version April 6, 2019
 */
public class History {
    /** Keeps track of the undo stages. */
    int undoIndex = -1;
    /** Keeps track of the redo stages. */
    int redoIndex = 1;
    /** A List of previous state of the project. */
    List<State<Board>> list;

    /**
     * Constructor defining index's and a history list.
     */
    public History() {
        undoIndex = -1;
        redoIndex = 1;
        list = new ArrayList<>();
    }

    /**
     * Add a previous state to the array list of states.
     * 
     * @param state - An object containing a state at some point in time.
     */
    public void add(State<Board> state) {
        // An integer representing if any elements had been removed.
        int removedState = 0;
        // The number of the elements in the list minus 1.
        int totalSize = list.size() - 1;

        // Removes elements after the insertion point.
        for (int i = totalSize; i >= redoIndex; i--) {
            list.remove(i);
            removedState = 1;
        }

        // Ensures that we do not double count states.
        if (list.size() <= 0 || !state.getState().equals(
                list.get(Math.max(undoIndex + removedState, 0)).getState())) {
            if (undoIndex + 2 == list.size()) {
                if (!state.getState().equals(
                        list.get(undoIndex + 1).getState())) {
                    list.add(state);
                }
            } else {
                list.add(state);
            }
        }
        undoIndex++;
        redoIndex++;
    }

    /**
     * Get a State object containing a previous state. 
     * This is effectively an undo.
     * 
     * @return - An object containing a state at a previous point in time.
     */
    public State<Board> undo() throws ArrayIndexOutOfBoundsException, NullPointerException {
        if (undoIndex < 0) { return null; }
        redoIndex--;
        // Cloning is to prevent certain specific cases of cross referencing.
        return new State<Board>(list.get(undoIndex--).getState().clone());
    }

    /**
     * Get a State object containing a forward state. This is effectively a redo.
     * 
     * @return - An object containing a state at forwarded point in time.
     */
    public State<Board> redo() throws ArrayIndexOutOfBoundsException, NullPointerException {
        if (redoIndex >= list.size()) { return null; }
        undoIndex++;
        // Cloning is to prevent certain specific cases of cross referencing.
        return new State<Board>(list.get(redoIndex++).getState().clone());
    }
}