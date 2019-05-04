package UI_GUI;

import colorama.ColorChooser;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Interfaces.ScreenChangeHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Handler.SettingsObserver;
import Handler.SubjectIF;

import java.util.ArrayList;

/**
 * The settings screen of ChessMeister that can be accessed at the main screen
 * or the game screen.
 *
 * @author Jacob Ginn (100%)
 * @version 05/03/2019
 */
public class SettingsScreen implements SubjectIF, EventHandler<ActionEvent> {

    /** The root of the Scene. */
    private BorderPane root;

    /** the singleton instance of this class */
    private static SettingsScreen instance;

    /** a singleton instance of the stage */
    private Stage colorama;

    /** The arrayList of our observers */
    private ArrayList<SettingsObserver> settingsObservers;

    /** ScreenChangeHandler object */
    ScreenChangeHandler handler;

    /**the instance of color chooser */
    private Scene colorChooser;

    /** The Pane that shows the white square color */
    Pane wcolorB;

    /** The Pane that shows the black square color */
    Pane bcolorB;

    /** the white square color background */
    String whiteColor;

    /** the black square color background */
    String blackColor;

    /**
     * the constructor for SettingsScreen
     */
    private SettingsScreen(){
        root = new BorderPane();
        settingsObservers = new ArrayList<>();
        colorChooser = new Scene(ColorChooser.getInstance().getRoot(), 400,600);
        wcolorB = new Pane();
        bcolorB = new Pane();
        settingsObservers.add(GameScreen.getInstance());
        whiteColor = "ffffff";
        blackColor = "000000";
    }


    /**
     * gets the singleton instance of this class
     * @return - the instance of this class
     */
    public static SettingsScreen getInstance(){
        if(instance == null) instance = new SettingsScreen();
        return instance;
    }

    /**
     * Gets the root of the scene
     * @return - the root of the scene
     */
    public BorderPane getRoot(){
        return this.root;
    }


    /**
     * sets the Scene with the necessary objects
     */
    public void settingScene(){
        root.setMinSize(500.0,280.0);
        setTop();
        setCenterRight();
    }


    /**
     * sets the top of the borderPane
     */
    public void setTop(){
        Label topLabel = new Label("Settings");
        topLabel.getStyleClass().add("mainLabel");
        root.setTop(topLabel);
        BorderPane.setAlignment(topLabel, Pos.CENTER);
    }


    /**
     * sets the center and the right of the borderPane
     */
    public void setCenterRight(){
        VBox colorSet = new VBox(); //color vbox
        VBox showSet = new VBox(); //show moves and Exit

        HBox selectColor = new HBox();// select color hbox
        HBox squareColor = new HBox();//Square Colors


        //Setting up the Button
        Button userColor = new Button();
        Button exit = new Button();
        userColor.setText("Pick Color");
        exit.setText("Exit");

        exit.setOnAction(this);
        userColor.setOnAction(this);


        //Setting up CheckBoxes
        CheckBox enableUndo = new CheckBox("Enabled Undo");
        CheckBox showBox = new CheckBox("Show Moves");
        CheckBox unlimBox = new CheckBox("Unlimited Undo");

        //Sets what the button does
        enableUndo.setOnAction(this);
        showBox.setOnAction(this);
        unlimBox.setOnAction(this);

        //Sets up the Labels that are used
        Label color = new Label("Color");
        Label boardColor = new Label("Choose Square Colors: ");
        Label wcolor = new Label("White Color: ");
        Label bcolor = new Label("   Black Color: ");
        Label undo = new Label("Undo");



        //Sets up the TextFeild for the max undo
        TextField maxUndo = new TextField();
        maxUndo.setPromptText("Set Max Undos");

        //sets up the Checkboxes on the screen
        Label unlim = new Label("Unlimited Undo", unlimBox);
        Label enable = new Label("Enabled",enableUndo);
        Label showMoves = new Label("Show Moves", showBox);

        //setsListener on the textfield for if the user changes the amount of undos
        maxUndo.textProperty().addListener(e -> {

            try {
                maxundoNotify(Integer.parseInt(maxUndo.getText()));
            }catch (NumberFormatException n){
                maxundoNotify(0);
            }

        });


        //sets style sheets
        color.getStyleClass().add("mediumLabel");
        boardColor.getStyleClass().add("regularLabel");
        //userColor.getStyleClass().add("buttonSizeS2");
        bcolorB.getStyleClass().addAll("buttonSizeS2", "blackButton");
        wcolorB.getStyleClass().addAll("buttonSizeS2", "whiteButton");
        wcolor.getStyleClass().add("regularLabel");
        bcolor.getStyleClass().add("regularLabel");
        exit.getStyleClass().add("buttonSizeS2");
        undo.getStyleClass().add("mediumLabel");
        enableUndo.getStyleClass().add("checkBox");
        unlimBox.getStyleClass().add("checkBox");
        unlim.getStyleClass().add("regularLabel");
        enable.getStyleClass().add("regularLabel");
        maxUndo.getStyleClass().add("textField");
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
        squareColor.getChildren().add(wcolorB);
        squareColor.getChildren().add(bcolor);
        squareColor.getChildren().add(bcolorB);

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
        showSet.getChildren().add(exit);

        colorSet.setSpacing(10.0);
        showSet.setSpacing(205.0);

        root.setCenter(colorSet);
        root.setRight(showSet);

        BorderPane.setAlignment(colorSet, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(showSet, Pos.CENTER_RIGHT);
        root.setId("Screen1");

    }

    /**
     * handles the button presses on the screen
     * @param event - the event that is being looked for
     */
    @Override
    public void handle(ActionEvent event) {

        if((event.getSource().toString().contains("Exit"))
                && Board_GUI.boardSettings == 0){
            handler.switchScreen(ScreenChangeHandler.MAINSCREEN);
        }else if (event.getSource().toString().contains("Pick Color")) {
            setStage();
        } else if(event.getSource().toString().contains("Enabled Undo")){
            undoNotify(((CheckBox)event.getSource()).isSelected());
        } else if(event.getSource().toString().contains("Show Moves")){
            showNotify(((CheckBox)event.getSource()).isSelected());
        } else if(event.getSource().toString().contains("Unlimited Undo")){
            unlimNotify(((CheckBox)event.getSource()).isSelected());
        } else{
            handler.switchScreen(ScreenChangeHandler.GAMESCREEN);
        }


    }


    /**
     * gets the instance of colorama so that there is only one instance of colorama open at one time
     * @return - the Stage that colorama is being held on.
     */
    public Stage getColorama(){
        if(colorama == null) {
            colorama = new Stage();
        }
        return colorama;
    }

    /**
     * Sets the stage for the popup window for the colorama that will only open one window
     */
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


    /**
     * Set the white spaces color and display the result
     * @param colorSelection - the color that is selected
     */
    public void setWhiteColor(String colorSelection){
        System.out.println(colorSelection);
        whiteColor = colorSelection;
        wcolorB.setStyle("-fx-background-color: #" + colorSelection );
        colorNotify();
    }//nd setColor

    /**
     * Set the black space color and display the result
     * @param colorSelection - the color that is selected
     */
    public void setBlackColor(String colorSelection){
        blackColor = colorSelection;
        bcolorB.setStyle("-fx-background-color: #" + colorSelection );
        colorNotify();
    }//nd setColor



    /**
     * Method that sets the screenChangeHandler to a new screenChangeHandler
     * @param sch - new screenChangeHandler that we want to set
     */
    public void setScreenChangeHandler(ScreenChangeHandler sch){
        this.handler = sch;
    }


    /**
     * adds an observer to the setting screen
     * @param observer - the Observer to the setting screen that must implement SettingsObserver
     */
    public void addObserver(SettingsObserver observer){
        settingsObservers.add(observer);
    }

    /**
     * removes an observer from the setting screen
     * @param observer - the Observer to the setting screen that must implement SettingsObserver
     */
    public void removeObserver(SettingsObserver observer){
        settingsObservers.remove(observer);
    }

    /**
     * Show moves notify that notifies the observer
     * @param show - true to show; false to hide
     */
    public void showNotify(boolean show){
        for (SettingsObserver s: settingsObservers){
            s.moveUpdate(show);
        }
    }

    /**
     * undo notify that notifies the observer to enable undo or not
     * @param undo - true to enable; false to disable
     */
    public void undoNotify(boolean undo){
        for (SettingsObserver s: settingsObservers){
            s.undoUpdate(undo);
        }
    }

    /**
     * Notifies the max undo that the users are able to do
     * @param numUndo - the number of undo commands that the users can achieve
     */
    public void maxundoNotify(int numUndo){
        for (SettingsObserver s: settingsObservers){
            s.maxundoUpdate(numUndo);
        }
    }

    /**
     * Sets the number of undos that the users are allowed to unlimited
     * @param unlimUndo - true to enable; false to disable;
     */
    public void unlimNotify(boolean unlimUndo){
        for (SettingsObserver s: settingsObservers){
            s.unlimUpdate(unlimUndo);
        }
    }

    /**
     * notifies the color of the board to be changed to the selected color
     */
    @Override
    public void colorNotify() {
        for (SettingsObserver s: settingsObservers){
            s.colorUpdate(whiteColor, blackColor);
        }
    }
}