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
    StringBuilder data = null;
    List<State<BoardIF>> list = new ArrayList<>();
    BoardIF board;
    PieceFactory pieceFactory = new PieceFactory();
    int rank;
    int file;
    GameColor color;
    ChessPieceType pieceType;

    boolean bType = false;
    boolean bColor = false;


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
