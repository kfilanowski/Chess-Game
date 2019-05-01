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

public class HistoryHandler extends DefaultHandler {
    private StringBuilder data = null;
    private List<State<BoardIF>> list = new ArrayList<>();
    private BoardIF board;
    private PieceFactory pieceFactory = new PieceFactory();
    private int rank;
    private int file;
    private GameColor color;
    private ChessPieceType pieceType;

    boolean bType = false;
    boolean bColor = false;


    @Override
    public void startElement(String uri, String localName, String qname, Attributes attributes){
        if(qname.equals("history")){
            History.getInstance().setUndoIndex(Integer.parseInt(attributes.getValue("undoIndex")));
            History.getInstance().setRedoIndex(Integer.parseInt(attributes.getValue("redoIndex")));
        }else if(qname.equals("board")){
            //TODO: few things that may be causing it to go wrong: the board.setDrawStrategy (tightly coupled anyways
            // probably should change it) below when i return it in History as a State, or in
            // Game_Controller_GUI when I call restore state or Could be the move is not working and tied to the load.
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

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start,length));
    }


}
