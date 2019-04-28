package UI_GUI;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * This screen allows the user to choose the colors for the black and white squares on the board. Also permits
 * the undo and redo functionality, and to turn off the showmoves feature. The save button saves all the settings
 * you began with and the exit button does not.
 *
 * @author Jacob Ginn
 * @version April 27,2019
 */
public class SettingsScreen {
    /**Root of the Screen */
    private GridPane root;

    /**Undo is enabled in the game */
    private CheckBox cbenabled;

    /**Unlimited Checkbox for undo*/
    private CheckBox cbUnlimited;

    /**The button that shows the color of the white pieces*/
    private Button whiteB;

    /**The button that shows the color of the black pieces*/
    private Button blackB;

    /** */
    private Button save;

    /** */
    private Button exit;

    /**The checkbox that shows if you board should show the moves that are possible for a piece*/
    private CheckBox showMoves;

    public SettingsScreen(){
        //
        this.root = new GridPane();
        root.setHgap(5.0);
        root.setVgap(5.0);
        root.setMinSize(500.0,400.0);

        //sets up the buttons and the checkboxes
        this.cbenabled = new CheckBox();
        this.cbUnlimited = new CheckBox();
        this.whiteB = new Button();
        this.blackB = new Button();
        this.save = new Button("Save");
        this.exit = new Button("Exit");
        this.showMoves = new CheckBox();

        //apply styles
        whiteB.getStyleClass().add("buttonSizeS");
        blackB.getStyleClass().add("buttonSizeS");
        save.getStyleClass().add("buttonSizeS");
        exit.getStyleClass().add("buttonSizeS");
        //showMoves.getStyleClass().add("buttonSizeS");
        //cbUnlimited.getStyleClass().add("buttonSizeS");
        //cbenabled.getStyleClass().add("buttonSizeS");




        //Max Sizes to fill bounds
        cbenabled.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        cbUnlimited.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        save.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        exit.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        showMoves.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        whiteB.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        blackB.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);


    }

    public void setupGrid(){
        Label lbl1 = new Label();
        lbl1.setText("Color");
        lbl1.getStyleClass().add("regularLabel");

        Label lbl2 = new Label();
        lbl2.setText("Black Squares :");
        lbl2.getStyleClass().add("regularLabel");

        Label lbl3 = new Label();
        lbl3.setText("White Square :");
        lbl3.getStyleClass().add("regularLabel");

        Label mainlbl = new Label();
        mainlbl.setText("SETTINGS");
        mainlbl.getStyleClass().add("mainLabel");

        Label undolbl= new Label("Undo");
        undolbl.getStyleClass().add("regularLabel");

        Label enabled = new Label("Enabled");
        enabled.getStyleClass().add("regularLabel");

        Label unlimited = new Label("Unlimited Undos");
        unlimited.getStyleClass().add("regularLabel");

        Label maxundo = new Label("Max Undos");
        maxundo.getStyleClass().add("regularLabel");

        Label show = new Label("Show Moves");
        show.getStyleClass().add("regularLabel");

        //TextField text = new TextField();

        root.add(lbl1,0,1,1,1);
        root.add(mainlbl,2,0,2,1);
        root.add(lbl2,0,2,2,1);
        root.add(lbl3,0,3,2,1);

        root.add(blackB, 2,2,1,1);
        root.add(whiteB, 2,3,1,1);
        root.add(undolbl, 0,4,1,1);

        cbenabled.setText("Enabled");
        root.add(cbenabled,0,5,1,1);

        cbUnlimited.setText("Unlimited");
        root.add(cbUnlimited,0,6,1,1);

        root.add(maxundo,1,7,2,1);
        root.add(showMoves,5,2,1,1);
        root.add(show,6,2,2,1);

        root.add(save,6,6,2,1);
        root.add(exit,6,7,2,1);
        //root.add(text,0,7,1,1);
        root.setGridLinesVisible(true);
    }


    public GridPane getRoot(){
        return root;
    }


}
