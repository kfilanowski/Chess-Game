package UI_GUI;

import Interfaces.ScreenChangeHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Handler.SettingsObserver;
import Handler.SubjectIF;
import Interfaces.ScreenChangeHandler;
import colorama.ColorChooser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SettingsRoundTwo implements SubjectIF {
    /**The root of the project */
    private BorderPane root;

    /**hold the white color label and the button */
    private HBox selectColor;

    /**hold the black color label and the button */
    private HBox squareColor;

    /** Holds all the info on the left side of the screen */
    private VBox colorSet;

    /** holds all the info on the right side of the screen */
    private VBox showSet;

    /** Holds the save and exit button*/
    private HBox saveExit;

    /** ScreenChangeHandler object */
    ScreenChangeHandler handler;

    /** the white square color */
    private Button wcolor;

    /** the black square button */
    private Button bcolor;

    /** the button that controls the colorama. */
    private Button userColor;

    /**the instance of color chooser */
    private Scene colorChooser;

    /** the singleton instance of this class */
    private static SettingsRoundTwo instance;

    /** a singleton instance of the stage */
    private Stage colorama;

    /** The arrayList of our observers */
    private ArrayList<SettingsObserver> settingsObservers;



    /**
     * the constructor for the settings screen
     */
    private SettingsRoundTwo(){
        super();
        root = new BorderPane();
        selectColor = new HBox(15);
        squareColor = new HBox(15);
        saveExit = new HBox(15);
        saveExit.setPadding(new Insets(0,10,0,0));
        colorSet = new VBox();
        colorSet.setPadding(new Insets(0,0,10,10));
        showSet = new VBox();
        bcolor = new Button();
        wcolor = new Button();
        userColor = new Button();
        userColor.setOnAction(buttonHandler2);
        colorChooser = new Scene(ColorChooser.getInstance().getRoot(),400,600);
        colorama = null;
        settingsObservers = new ArrayList<>();
        settingsObservers.add(GameScreen.getInstance());
    }

    /**
     * gets the singleton instance of this class
     * @return - the instance of this class
     */
    public static SettingsRoundTwo getInstance(){
        if(instance == null) instance = new SettingsRoundTwo();
        return instance;
    }



    public void settingSetup(){
        root.setMinSize(500.0,280.0);
        Label lbl = new Label("Settings");
        root.setTop(lbl);
        root.setLeft(new Pane());
        lbl.getStyleClass().add("mainLabel");
        BorderPane.setAlignment(lbl, Pos.CENTER);

        color();

        root.setCenter(colorSet);
        root.setRight(showSet);
        colorSet.setSpacing(10.0);
        showSet.setSpacing(205.0);
        BorderPane.setAlignment(colorSet, Pos.CENTER);
        BorderPane.setAlignment(showSet,Pos.CENTER_RIGHT);
        root.setId("Screen1");
    }

    public BorderPane getRoot(){
        return this.root;
    }



    /**
     * Sets up the labels and the buttons that are on the board
     */
    private void color(){
        
        //Sets up the Labels that are used
        Label color = new Label("Color");
        Label boardColor = new Label("Choose Square Colors: ");
        Label wcolor = new Label("White Color:");
        Label bcolor = new Label("Black Color:");
        Label undo = new Label("Undo");
        
        //Sets up the buttons
        Button save = new Button();
        save.setText("Save");
        save.setOnAction(saveAction);
        Button exit = new Button("Exit");
        exit.setOnAction(exitAction);
        exit.setText("Exit");
        

        //sets up the Checkboxes on the screen
        CheckBox enableUndo = new CheckBox();
        Label unlim = new Label("Unlimited Undo", enableUndo);

        CheckBox unlimBox = new CheckBox();
        Label enable = new Label("Enabled",unlimBox);

        CheckBox showBox = new CheckBox();
        Label showMoves = new Label("Show Moves", showBox);

        //sets the text field
        TextField maxUndo = new TextField();

        //maxUndo.setText("Max Undo");
        maxUndo.setPromptText("Set Max Undos");

        //sets style sheets
        color.getStyleClass().add("mediumLabel");
        boardColor.getStyleClass().add("regularLabel");
        userColor.getStyleClass().add("buttonSizeS2");
        this.bcolor.getStyleClass().addAll("buttonSizeS2", "blackButton");
        this.wcolor.getStyleClass().addAll("buttonSizeS2", "whiteButton");
        wcolor.getStyleClass().add("regularLabel");
        bcolor.getStyleClass().add("regularLabel");
        exit.getStyleClass().add("buttonSizeS2");
        save.getStyleClass().add("buttonSizeS2");
        undo.getStyleClass().add("mediumLabel");
        enableUndo.getStyleClass().add("checkBox");
        unlimBox.getStyleClass().add("checkBox");
        unlim.getStyleClass().add("regularLabel");
        enable.getStyleClass().add("regularLabel");
        maxUndo.getStyleClass().add("textField");
        save.getStyleClass().add("buttonSizeS2");
        exit.getStyleClass().add("buttonSizeS2");
        showMoves.getStyleClass().add("regularLabel");
        showBox.getStyleClass().add("checkBox");

        //scales check boxes
        enableUndo.setScaleX(.75);
        enableUndo.setScaleY(.75);
        unlimBox.setScaleX(.75);
        unlimBox.setScaleY(.75);
        showBox.setScaleX(.75);
        showBox.setScaleY(.75);



        //adds the nodes to selectColor
        selectColor.getChildren().add(boardColor);
        selectColor.getChildren().add(userColor);

        //adds the nodes to squareColor
        squareColor.getChildren().add(wcolor);
        squareColor.getChildren().add(this.wcolor);
        squareColor.getChildren().add(bcolor);
        squareColor.getChildren().add(this.bcolor);

        //adds the nodes to saveExit
        saveExit.getChildren().add(save);
        saveExit.getChildren().add(exit);

        //sets up colorSet
        colorSet.getChildren().add(color);
        colorSet.getChildren().add(selectColor);
        colorSet.getChildren().add(squareColor);
        colorSet.getChildren().add(undo);
        colorSet.getChildren().add(enable);
        colorSet.getChildren().add(unlim);
        colorSet.getChildren().add(maxUndo);

        //sets up showSet
        showSet.getChildren().add(showMoves);
        showSet.getChildren().add(saveExit);

    }
    
    /**
     * Method that sets the screenChangeHandler to a new screenChangeHandler
     * @param sch - new screenChangeHandler that we want to set
     */
    public void setScreenChangeHandler(ScreenChangeHandler sch){
        this.handler = sch;
    }

    /**
     * Set the white spaces color and display the result
     * @param colorSelection - the color that is selected
     */
    public void setWhiteColor(String colorSelection){
        wcolor.setStyle("-fx-background-color: #" + colorSelection );
    }//nd setColor

    /**
     * Set the black space color and display the result
     * @param colorSelection - the color that is selected
     */
    public void setBlackColor(String colorSelection){
        bcolor.setStyle("-fx-background-color: #" + colorSelection );
    }//nd setColor


    public void addObserver(SettingsObserver observer){
        settingsObservers.add(observer);
    }

    public void removeObserver(SettingsObserver observer){
        settingsObservers.remove(observer);
    }

    public void notifyGame(){
        for(SettingsObserver sb: settingsObservers){
            sb.update();
        }
    }

    public Stage getColorama(){
        if(colorama == null) {
            colorama = new Stage();
        }
        return colorama;
    }

    public void setStage(){
        if(colorama == null){
            colorama = new Stage();
        } else {
            colorChooser.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            colorama.setScene(colorChooser);
            colorama.setMinHeight(colorChooser.getHeight());
            colorama.setMinWidth(colorChooser.getWidth());
            colorama.show();
        }

    }

    EventHandler<ActionEvent> saveAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(Board_GUI.boardSettings == 0){
                handler.switchScreen(ScreenChangeHandler.SCREENA);
                notifyGame();
            }else{
                handler.switchScreen(ScreenChangeHandler.SCREENB);
                notifyGame();
            }
        }
    };


    EventHandler<ActionEvent> exitAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(Board_GUI.boardSettings == 0){
                handler.switchScreen(ScreenChangeHandler.SCREENA);
            }else{
                handler.switchScreen(ScreenChangeHandler.SCREENB);
            }
        }
    };
    

    EventHandler<ActionEvent> buttonHandler2 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            setStage();
        }//end handle
    };
}
