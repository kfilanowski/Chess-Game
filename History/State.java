package History;

/**
 * The State class encapsulates a single state of an entity object. In this
 * case, a state of the board.
 * 
 * @author Kevin Filanowski 100%
 * @version April 2, 2019
 */
public class State<T> {
    /** The state of the object at some point in time. **/
    private T state;

    /**
     * Construct a state containing a state of the object to store.
     * 
     * @param state - An object containing a state at some point in time.
     */
    public State(T state) {
        this.state = state;
    }

    /**
     * Retrieve the state of this state object.
     * 
     * @return The state of this object.
     */
    public T getState() {
        return state;
    }

    /**
     * Compares an object with this State object.
     * 
     * @param obj - An object to compare with this State object.
     * @return - True if the two objects are deeply equal, false otherwise.
     */
    public boolean equals(State<T> obj) {
        return obj.state.equals(state);
    }
}