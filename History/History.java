package History;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import Interfaces.BoardIF;
import Model.Board;

/**
 * The history class holds each state of an entity class. In this case, a board.
 * It allows the board to change states, effectively functioning as an undo/redo.
 * 
 * @author Kevin Filanowski
 * @version April 2, 2019
 */
public class History {
    /*  **/
    int indexStamp;

    /** A Deque of previous state of the project. */
    private Deque<State<Board>> undoList;

    /** A Deque of forwarded state of the project. */
    private Deque<State<Board>> redoList;

    List<State<Board>> list = new ArrayList<>();

    /**
     * Constructor defining an index and mementoList.
     */
    public History() {
        indexStamp = -1;
        undoList = new ArrayDeque<State<Board>>();
        redoList = new ArrayDeque<State<Board>>();
    }

    /**
     * Add a previous state to the array list of states.
     * 
     * @param state - An object containing a state at some point in time.
     **/
    public void add(State<Board> state) {
        undoList.add(state);
        redoList.clear();
    }

    public void addList(State<Board> state, BoardIF current) {
        for (int i = indexStamp + 1; i < list.size(); i++) {
            list.remove(i);
        }
        if (indexStamp < 0 || !state.getState().equals(list.get(indexStamp).getState())) {
            list.add(state);
            indexStamp++;
            System.out.println("===added this state below===");
            state.getState().draw();
            System.out.println("----------------------------");
        } else {
            System.out.println("Duplicate detected, did not add.");
        }
    }

    // /**
    //  * Get a state object containing a previous state.
    //  * 
    //  * @param index - The index of the state to return.
    //  * @return - The state object containing a previous state.
    //  */
    // public State<T> get(int index) {
    //     indexStamp = index;
    //     return stateList.get(index);
    // }

    /**
     * Get a mememnto object containing a previous state. 
     * This is effectively an undo.
     * 
     * @return - An object containing a state at a previous point in time.
     */
    public State<Board> undo(BoardIF current) throws NullPointerException {
        if (undoList.peek() == null) { return null; }
        redoList.push(undoList.peekLast());
        State<Board> state = undoList.pollLast();
        if (undoList.size() >= 1) {
            while (state.getState().equals(current)) {
                //redoList.push(undoList.peekLast());
                state = undoList.pollLast();
            }
        }
        return state; // TODO: /move /move /move /undo /undo /redo /undo /redo breaks it.
    }

    public State<Board> undoList(BoardIF current) throws ArrayIndexOutOfBoundsException {
        if (indexStamp < 0) { return null; }
        State<Board> state = list.get(indexStamp--);
        if (list.size() >= 1) {
            while (state.getState().equals(current)) {
                // redoList.push(undoList.peekLast());
                state = list.get(indexStamp--);
                state = new State<Board>(state.getState().clone());
            }
        }
        // System.out.println("======got this state /undo=====");
        // state.getState().draw();
        // System.out.println("-------------------------------");
        return state;// pop off memento
    }

    public State<Board> redoList(BoardIF current) throws ArrayIndexOutOfBoundsException {
        if (indexStamp + 1 >= list.size()) { return null; }
        State<Board> state = list.get(++indexStamp);
        if (list.size() >= 1) {
            while (state.getState().equals(current)) {
                // redoList.push(undoList.peekLast());
                if (indexStamp + 1 >= list.size()) {
                    --indexStamp;
                    return null;
                }
                state = list.get(++indexStamp);
                state = new State<Board>(state.getState().clone());

            }
        }
        // System.out.println("======got this state /redo=====");
        // state.getState().draw();
        // System.out.println("-------------------------------");
        return state;
    }

    /**
     * Get a state object containing a forward state. 
     * This is effectively a redo.
     * 
     * @return - An object containing a state at forwarded point in time.
     */
    public State<Board> redo(BoardIF current) throws NullPointerException {
        if (redoList.peek() == null) { return null; }
        undoList.add(redoList.peek());
        State<Board> state = redoList.poll();
        if (redoList.size() >= 1) {
            while (state.getState().equals(current)) {
                //undoList.add(redoList.peek());
                state = redoList.poll();
            }
        }
        return state;
    }
}// end class.