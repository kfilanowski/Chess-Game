package Sax_Parser;

import Enums.ChessPieceType;
import Enums.GameColor;
import Factory.PieceFactory;
import History.History;
import History.State;
import Interfaces.BoardIF;
import Model.Board;
import UI_GUI.Board_GUI;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * The history handler class that parses the xml document
 * @author Jeriah Caplinger
 * @version 5/3/2019
 */
public class HistoryHandler extends DefaultHandler {
    /** the string builder that holds the data from the xml object */
    private StringBuilder data = null;
    /** A list of board states that the History class will use */
    private List<State<BoardIF>> list = new ArrayList<>();
    /** the chess board */
    private BoardIF board;
    /** the piece factory class to build our chess pieces */
    private PieceFactory pieceFactory = new PieceFactory();
    /** a rank index to build a piece */
    private int rank;
    /** a file index to build a piece */
    private int file;
    /** a game color to build a piece */
    private GameColor color;
    /** a chess piece type to build our piece */
    private ChessPieceType pieceType;
    /** a boolean to tell us to read in chess piece type data */
    boolean bType = false;
    /** a boolean to tell us to read in color type data */
    boolean bColor = false;


    /**
     * Reads in the first xml tag
     * @param uri the file location of the xml doc
     * @param localName the local xml tag name
     * @param qname the tag name
     * @param attributes attributes of the xml
     */
    @Override
    public void startElement(String uri, String localName, String qname, Attributes attributes){
        if(qname.equals("history")){
            History.getInstance().setUndoIndex(Integer.parseInt(attributes.getValue("undoIndex")));
            History.getInstance().setRedoIndex(Integer.parseInt(attributes.getValue("redoIndex")));
        }else if(qname.equals("board")){
            board = new Board();
            board.init_board();
            board.setDrawStrategy(new Board_GUI());
        }else if(qname.equals("piece")){
            rank = Integer.parseInt(attributes.getValue("rankIndex"));
            file = Integer.parseInt(attributes.getValue("fileIndex"));
        }else if(qname.equals("pieceType")){
            bType = true;
        }else if(qname.equals("color")){
            bColor = true;
        }
        //creates data container
        data = new StringBuilder();
    }

    /**
     * Reads the last xml tag
     * @param uri the file location of the xml doc
     * @param localName the local xml tag name
     * @param qname the tag name
     */
    @Override
    public void endElement(String uri, String localName, String qname){

        if(bType){
            pieceType = ChessPieceType.getChessPieceTypeFromString(data.toString());
            bType = false;
        }else if(bColor){
            color = GameColor.getColorFromString(data.toString());
            bColor = false;
            pieceFactory.setPieceIF(color, pieceType, board, rank, file);
        }

        if(qname.equals("board")){
            list.add(board.saveState());
        }else if(qname.equalsIgnoreCase("piece")){
            color = null;
            pieceType = null;
            rank = -1;
            file = -1;
        }else if(qname.equalsIgnoreCase("history")){
            History.getInstance().setList(list);
        }

    }


    /**
     * Appends anymore characters that were left out.
     * @param ch the character array
     * @param start index to start from
     * @param length the length to start from
     * @throws SAXException if a Saxecption is thrown
     */
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start,length));
    }


}
