package UI_GUI;

import Interfaces.*;
import Model.Board;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import com.sun.javafx.css.StyleManager;

/**
 * This class models the Main Menu screen for the GUI
 * @author Matthew Lutz 100%
 */
public class MainMenu implements EventHandler<ActionEvent> {



    /** ScreenChangeHandler object */
    private ScreenChangeHandler handler;
    /** Player vs Player Button **/
    private Button pvp;
    /** Player vs Computer Button **/
    private Button playerVsComp;
    /** Online Play Button **/
    private Button onlinePlay;
    /** Rules of Chess Button **/
    private Button chessRules;
    /** Tutorial Button **/
    private Button tutorial;
    /** Settings Button **/
    private Button settings;
    /** Exit Button **/
    private Button exit;
    /** BorderPane that is the root of the Main Menu Screen**/
    private BorderPane root;
    /** Vbox that formats the buttons on the screen **/
    private VBox vbox;
    /** Anchor pane that anchors the settings button and exit button **/
    private AnchorPane anchorPane;

    /**
     *  Method that handles changing to all of
     *  the screens that this screen can change too*
     * @param event - Action event that represents the button press
     */
    @Override
    public void handle(ActionEvent event){
        if(event.getSource() == pvp && !InputNameScreen.getScreenHasChanged()){
            handler.switchScreen(ScreenChangeHandler.PLAYERNAMESCREEN);
            //Board_GUI.boardSettings = 1;
        }else{
            handler.switchScreen(ScreenChangeHandler.GAMESCREEN);
            //Board_GUI.boardSettings = 1;
        }

        if(event.getSource() == settings){
            handler.switchScreen(ScreenChangeHandler.SETTINGSSCREEN);
            Board_GUI.boardSettings = 0;
        }else if(event.getSource() == exit){
            Board_GUI.getPrimaryStage().close();
        }else if(event.getSource() == playerVsComp){
            handler.switchScreen(ScreenChangeHandler.CPU);
        }else if(event.getSource() == onlinePlay){
            handler.switchScreen(ScreenChangeHandler.ONLINE);
        }else if(event.getSource() == chessRules){
            handler.switchScreen(ScreenChangeHandler.RULES);
        }else if(event.getSource() == tutorial){
            handler.switchScreen(ScreenChangeHandler.TUTORIAL);
        }
    }


    /**
     * Contructor for the MainMenu class
     */
    public MainMenu(){
        super();
        root = new BorderPane();
        vbox = new VBox();

    }

    /**
     * Method that sets up the layout of the Main Menu screen
     */
    public void setup(){
        vbox.setAlignment(Pos.CENTER);//SET the alignment of the  layout.
        vbox.setSpacing(20);
        pvp = new Button("Player vs Player");
        //pvp.getStyleClass().add("buttonStyleA");
        pvp.getStyleClass().add("buttonSizeL");
        pvp.setOnAction(this);
        vbox.getChildren().add(pvp);

        playerVsComp = new Button("Player vs Computer");
        //playerVsComp.getStyleClass().add("buttonStyleA");
        playerVsComp.getStyleClass().add("buttonSizeL");
        playerVsComp.setOnAction(this);
        vbox.getChildren().add(playerVsComp);

        onlinePlay = new Button("Online Play");
        //onlinePlay.getStyleClass().add("buttonStyleA");
        onlinePlay.getStyleClass().add("buttonSizeL");
        onlinePlay.setOnAction(this);
        vbox.getChildren().add(onlinePlay);

        chessRules = new Button("Rules of Chess");
        //chessRules.getStyleClass().add("buttonStyleA");
        chessRules.getStyleClass().add("buttonSizeL");
        chessRules.setOnAction(this);
        vbox.getChildren().add(chessRules);

        tutorial = new Button("Tutorial");
        //tutorial.getStyleClass().add("buttonStyleA");
        tutorial.getStyleClass().add("buttonSizeL");
        tutorial.setOnAction(this);
        vbox.getChildren().add(tutorial);

        settings = new Button("Settings");
        //settings.getStyleClass().add("buttonStyleA");
        settings.getStyleClass().add("buttonSizeS");
        settings.setOnAction(this);


        exit = new Button("exit");
        //exit.getStyleClass().add("buttonStyleA");
        exit.getStyleClass().add("buttonSizeS");
        exit.setOnAction(this);

        anchorPane = new AnchorPane();
        AnchorPane.setLeftAnchor(settings,10.0);
        AnchorPane.setBottomAnchor(settings, 7.0);
        AnchorPane.setRightAnchor(exit, 10.0);
        AnchorPane.setBottomAnchor(exit, 7.0);
        anchorPane.getChildren().addAll(settings, exit);


        //A Label
        Label lab1 = new Label("Chess Meister");
        lab1.getStyleClass().add("screenTitle");
        ImageView king = new ImageView("images/chessPieces/WK.png");
        root.setTop(lab1);
        root.setBottom(anchorPane);
        root.setCenter(vbox);
        root.setRight(king);
        BorderPane.setAlignment(lab1, Pos.CENTER);
        root.setId("Screen1");
        //root.getStyleClass().add("Screen1");

    }

    /**
     * Method that gets the root
     * @return - BorderPane that represents the root pane
     */
    public Pane getRoot(){
        return this.root;
    }


    /**
     * Method that sets the screenChangeHandler to a new screenChangeHandler
     * @param sch - new screenChangeHandler that we want to set
     */
    public void setScreenChangeHandler(ScreenChangeHandler sch){
        this.handler = sch;
    }


}
