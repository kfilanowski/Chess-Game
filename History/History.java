package History;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Interfaces.BoardIF;
import Sax_Parser.HistoryHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * The history class holds each state of an entity class. In this case, a board.
 * It allows the board to change states, effectively functioning as an undo/redo.
 *
 * @author Kevin Filanowski 100%
 * @version April 6, 2019
 */
public class History<T extends BoardIF> {
    /** A static reference to this class for the Singleton pattern. */
    private static History<BoardIF> instance;
    /** Keeps track of the undo stages. */
    private int undoIndex;
    /** Keeps track of the redo stages. */
    private int redoIndex;
    /** A List of previous state of the project. */
    private List<State<BoardIF>> list;

    /**
     * Private Constructor defining index's and a history list.
     * Using the Singleton pattern.
     */
    private History() {
        undoIndex = -1;
        redoIndex = 1;
        list = new ArrayList<>();
    }

    /**
     * Retrieves the instance of this class, or creates an instance if one does
     * not already exist.
     *
     * @return - An instance of the History class.
     */
    public static History<BoardIF> getInstance() {
        if (instance == null) { instance = new History<BoardIF>(); }
        return instance;
    }

    /**
     * Add a previous state to the array list of states.
     *
     * @param state - An object containing a state at some point in time.
     */
    public void add(State<BoardIF> state) {
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
     * Get a State object containing a previous state. This is effectively an undo.
     *
     * @return - An object containing a state at a previous point in time.
     * @throws ArrayIndexOutOfBoundsException - Thrown when the indexing of the
     *                                        history is malfunctioning. This should
     *                                        not occur, but is here for safety
     *                                        reasons if it does.
     * @throws NullPointerException           - This exception is thrown when the
     *                                        user tries to undo or redo to a state
     *                                        that does not exist.
     */
    public State<BoardIF> undo(BoardIF board) throws ArrayIndexOutOfBoundsException,
                                                     NullPointerException {
        if (undoIndex < 0) { return null; }
        addUndo(board);
        redoIndex--;
        // Cloning is to prevent certain specific cases of cross referencing.
        State<BoardIF> state = new State<BoardIF>(list.get(undoIndex--).getState().clone());
        if (board.equals(state.getState())) {
            state = new State<BoardIF>(list.get(undoIndex--).getState().clone());
            redoIndex--;
        }
        return state;
    }

    /**
     * This method adds the current state if an undo operation is called,
     * only if the undo operation involves removing the current state and that
     * state was never tracked in recent history.
     * It effectively ensures that the last movement is still recorded.
     * 
     * @param board - The current board.
     */
    private void addUndo(BoardIF board) {
        State<BoardIF> curr = board.saveState();
        // Get the current state and compare with the previous state.
        // TODO: test throughly to ensure it works, then remove the excess
        // TODO: commented condition.
        if (/*!curr.equals(list.get(undoIndex)) 
            && */list.size() - (undoIndex + 1) == 0) {
            list.add(curr);
            undoIndex++;
            redoIndex++;
        }
    }

    /**
     * Get a State object containing a forward state. This is effectively a redo.
     *
     * @return - An object containing a state at forwarded point in time.
     * @throws ArrayIndexOutOfBoundsException - Thrown when the indexing of the
     *                                        history is malfunctioning. This should
     *                                        not occur, but is here for safety
     *                                        reasons if it does.
     * @throws NullPointerException           - This exception is thrown when the
     *                                        user tries to undo or redo to a state
     *                                        that does not exist.
     */
    public State<BoardIF> redo() throws ArrayIndexOutOfBoundsException,
                                        NullPointerException {
        if (redoIndex >= list.size()) { return null; }
        undoIndex++;
        // Cloning is to prevent certain specific cases of cross referencing.
        return new State<BoardIF>(list.get(redoIndex++).getState().clone());
    }

    /**
     * 
     * 
     * @param undoIndex
     */
    public void setUndoIndex(int undoIndex) {
        this.undoIndex = undoIndex;
    }

    /**
     * 
     * 
     * 
     * @param redoIndex
     */
    public void setRedoIndex(int redoIndex) {
        this.redoIndex = redoIndex;
    }

    /**
     * 
     * 
     * @param list
     */
    public void setList(List<State<BoardIF>> list) {
        this.list = list;
    }

    /**
     * Method that returns a list of the history.
     *
     * @return - The history list.
     */
    public List<State<BoardIF>> getList() {
        return list;
    }


    /**
     *
     * @param file the xml file to load in
     * @return the latest board state in history
     */
    public State<BoardIF> loadHistory(File file){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try{
            SAXParser parser = factory.newSAXParser();
            HistoryHandler historyHandler = new HistoryHandler();
            parser.parse(file, historyHandler);
        }catch (SAXException saxe){
            System.out.println("caught sax exception");
        }catch (ParserConfigurationException pce){
            System.out.println("caught parser confix except");
        }catch(IOException ioe){
            System.out.println("caught ioe exception");
        }

        return list.get(list.size() - 1);
    }

    /**
     * Converts this object into an xml string for saving/loading
     * @return an xml string representing this object
     */
    public String toXML(BoardIF board){
        StringBuilder builder = new StringBuilder();
        builder.append("<history undoIndex= \"" + undoIndex +"\" redoIndex= \"" + redoIndex + "\" >\n");

        // list index tag
        builder.append("\t<list>\n");
        for(State<BoardIF> states : this.list){
            builder.append(states.getState().toXML());
        }
        builder.append(board.toXML());
        builder.append("\t</list>\n");

        builder.append("</history>");

        return builder.toString();
    }

}