//package History;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * The history class holds each state of an entity class. In this case, a board.
// * It allows the board to change states, effectively functioning as an undo/redo.
// *
// * @author Kevin Filanowski
// * @version April 2, 2019
// */
//public class History<T> {
//    /** Index to begin with that keeps track of the current state. */
//    int indexStamp;
//
//    /** A list of previous state of the project. */
//    private List<State<T>> stateList = null;
//
//    /**
//     * Constructor defining an index and mementoList.
//     */
//    public History() {
//        indexStamp = -1;
//        stateList = new ArrayList<State<T>>();
//    }
//
//    /**
//     * Add a previous state to the array list of states.
//     *
//     * @param state - A memento containing a previous state.
//     **/
//    public void add(Memento<T> state) {
//        for (int i = indexStamp + 1; i < mementoList.size(); i++) {
//            mementoList.remove(i);
//        }
//        mementoList.add(state);// push on to memento
//        indexStamp++;
//    }
//
//    /**
//     * Get a mememnto object containing a previous state.
//     *
//     * @param index The index of the state to return.
//     * @return The memento object containing a previous state.
//     */
//    public Memento<T> get(int index) {
//        indexStamp = index;
//        return mementoList.get(index);// pop off memento
//    }
//
//    /**
//     * Get a mememnto object containing a previous state. This is effectively an
//     * undo.
//     *
//     * @return The memento object containing the previous state.
//     */
//    public Memento<T> undo() {
//        return mementoList.get(--indexStamp);// pop off memento
//    }
//
//    /**
//     * Get a mememnto object containing a forward state. This is effectively a redo.
//     *
//     * @return The memento object containing the forward state.
//     */
//    public Memento<T> redo() {
//        return mementoList.get(++indexStamp);
//    }
//
//}// end class.