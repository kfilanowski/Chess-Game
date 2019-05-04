package UI_GUI;
import Interfaces.ScreenChangeHandler;

import Controller.GameController_GUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

/**
 * Screen that asks the users to input two names before they start a game of chess.
 * @author Jeriah Caplinger (100%)
 * @version 4/30/2019
 */
public class InputNameScreen implements EventHandler<ActionEvent> {
    /** The singleton instance of this class */
    private static InputNameScreen inputScreen = null;
    /** User input text field for player 1 entering their name */
    private TextField tf1;
    /** User input text field for player 2 entering their name */
    private TextField tf2;
    /** Once clicked, the game will start with the player's names recorded */
    private Button play;
    /** Once clicked the game will not start, and return to the main menu */
    private Button exit;
    /** the screen that contains everything for the input name screen */
    private VBox screen;
    /** Handles game logic that is detached from a GUI. */
    private GameController_GUI gc;
    /** the screen change handler for this class */
    private ScreenChangeHandler handler;
    /** boolean that tells if the screen has changed or not */
    private static boolean screenHasChanged;


    /**
     * Creates an input name screen object
     */
    private InputNameScreen(){
        super();
        screenHasChanged = false;
        // create screen
        screen = new VBox();
        screen.setAlignment(Pos.CENTER);
        screen.setId("InputNameScreen");
        screen.setSpacing(20);

        //create labels and text field inputs
        Label p1 = new Label("Player 1 Name");
        Label p2 = new Label("Player 2 Name");
        p1.getStyleClass().add("inputNameLabel");
        p2.getStyleClass().add("inputNameLabel");

        tf1 = new TextField("Player 1");
        tf1.setMaxSize(250, 20);
        tf2 = new TextField("Player 2");
        tf2.setMaxSize(250, 20);

        // add labels and text fields
        screen.getChildren().add(p1);
        screen.getChildren().add(tf1);
        screen.getChildren().add(p2);
        screen.getChildren().add(tf2);

        // create buttons
        this.play = new Button("Play");
        this.play.setPrefSize(100, 50);
        this.exit = new Button("Exit");
        this.exit.setPrefSize(100, 50);
        this.exit.getStyleClass().add("inputNameButtonExit");

        // set up event handlers for the buttons
        play.setOnAction(this);
        exit.setOnAction(this);

        play.getStyleClass().add("inputNameButtonPlay");

        // set up an hbox layout for our buttons
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(75);
        // add the butons to the hbox layout
        buttons.getChildren().add(this.play);
        buttons.getChildren().add(this.exit);

        // add the hbox to the vbox layout
        screen.getChildren().add(buttons);

        screen.setMinSize(200, 400);
        screen.setMaxSize(200, 400);
        screen.setId("Screen1");
    }

    /**
     * Gets the root for this screen
     * @return the root for this screen
     */
    public VBox getRoot() {
        return screen;
    }

    /**
     * Uses the singleton pattern to return a single instance of input name screen
     * @return the single instance of an input name screen
     */
    public static InputNameScreen getInstance(){
        if(inputScreen == null){
            inputScreen = new InputNameScreen();
        }
        return inputScreen;
    }


    /**
     * Handles the button clicks: play and exit
     * @param event the button that was clicked either play or exit
     */
    @Override
    public void handle(ActionEvent event){
        if(event.getSource() == play){
            // if the players do not enter a name, then the default names are set
            // to white and black
            handler.switchScreen(ScreenChangeHandler.GAMESCREEN);
            screenHasChanged = true;
            if(getPlayer1Name().equals("")){
                this.tf1.setText("White");
            }
            if(getPlayer2Name().equals("")){
                this.tf2.setText("Black");
            }
        }else if(event.getSource() == exit){
            handler.switchScreen(ScreenChangeHandler.MAINSCREEN);
        }
    }

    /**
     * Gets the first player's name
     * @return the first player's inputted name
     */
    public String getPlayer1Name(){
        return this.tf1.getText();
    }

    /**
     * Sets the first player's name
     * @param name the name to set the player
     */
    public void setPlayer1Name(String name){
        this.tf1.setText(name);
    }

    /**
     * Gets the second player's name
     * @return the second player's inputted name
     */
    public String getPlayer2Name() {
        return this.tf2.getText();
    }

    public static boolean getScreenHasChanged(){
        return screenHasChanged;
    }

    /**
     * Method that sets the screenChangeHandler to a new screenChangeHandler
     * @param sch - new screenChangeHandler that we want to set
     */
    public void setScreenChangeHandler(ScreenChangeHandler sch){
        this.handler = sch;
    }

}
