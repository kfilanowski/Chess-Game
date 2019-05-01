package UI_GUI;
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
    VBox screen;


    /**
     * Creates an input name screen object
     */
    private InputNameScreen(){
        super();

        // create screen
        screen = new VBox();
        screen.setAlignment(Pos.CENTER);
        screen.setId("InputNameScreen");
        screen.setSpacing(20);

        //create labels and text field inputs
        Label p1 = new Label("Player 1 Name");
        Label p2 = new Label("Player 2 Name");

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

        // set up event handlers for the buttons
        play.setOnAction(this);
        exit.setOnAction(this);

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
            if(getPlayer1Name().equals("")){
                this.tf1.setText("White");
            }
            if(getPlayer2Name().equals("")){
                this.tf2.setText("Black");
            }
            //TODO: call switch screens somewhere
        }else if(event.getSource() == exit){
            //TODO: call switch screens somewhere
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
     * Gets the second player's name
     * @return the second player's inputted name
     */
    public String getPlayer2Name(){
        return this.tf2.getText();
    }

}