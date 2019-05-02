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

import java.util.regex.Pattern;

public class SettingsRoundTwo implements SubjectIF, EventHandler<ActionEvent> {
    /**The root of the project */
    private BorderPane root;

    /**hold the white color label and the button */
    private HBox hbox1;

    /**hold the black color label and the button */
    private HBox hbox2;

    /** Holds all the info on the left side of the screen */
    private VBox vbox1;

    /** holds all the info on the right side of the screen */
    private VBox vbox2;

    /** Holds the save and exit button*/
    private HBox hbox3;

    /** ScreenChangeHandler object */
    ScreenChangeHandler handler;

    /**
     * Method that sets the screenChangeHandler to a new screenChangeHandler
     * @param sch - new screenChangeHandler that we want to set
     */
    public void setScreenChangeHandler(ScreenChangeHandler sch){
        this.handler = sch;
    }

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

    private Button save;

    private Button exit;


    /**
     * the constructor for the settings screen
     */
    private SettingsRoundTwo(){
        super();
        root = new BorderPane();
        hbox1 = new HBox(15);
        hbox2 = new HBox(15);
        hbox3 = new HBox(15);
        hbox3.setPadding(new Insets(0,10,0,0));
        vbox1 = new VBox();
        vbox1.setPadding(new Insets(0,0,10,10));
        vbox2 = new VBox();
        bcolor = new Button();
        wcolor = new Button();
        save = new Button();
        exit = new Button();
        userColor = new Button();
        userColor.setOnAction(this);
        colorChooser = new Scene(ColorChooser.getInstance().getRoot(),400,600);
        colorama = null;
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

        root.setCenter(vbox1);
        root.setRight(vbox2);
        vbox1.setSpacing(10.0);
        vbox2.setSpacing(205.0);
        BorderPane.setAlignment(vbox1, Pos.CENTER);
        BorderPane.setAlignment(vbox2,Pos.CENTER_RIGHT);
        root.setId("Screen1");
    }

    public BorderPane getRoot(){
        return this.root;
    }


        @Override
        public void handle(ActionEvent event) {
            if((event.getSource() == save || event.getSource() == exit)
                    && Board_GUI.boardSettings == 0){
                handler.switchScreen(ScreenChangeHandler.SCREENA);
            }else if (event.getSource() == userColor){
                setStage();
            }else{
                handler.switchScreen(ScreenChangeHandler.SCREENB);
            }


        }

    /**
     * Sets up the labels and the buttons that are on the board
     */
    private void color(){
        Label color = new Label("Color");
        Label boardColor = new Label("Choose Square Colors: ");
        Label wcolor = new Label("White Color:");
        Label bcolor = new Label("Black Color:");

        save.setText("Save");
        save.setOnAction(this);
        exit.setText("Exit");
        exit.setOnAction(this);

        Label undo = new Label("Undo");

        //sets up the Checkboxes on the screen
        CheckBox cb1 = new CheckBox();
        Label unlim = new Label("Unlimited Undo", cb1);

        CheckBox cb2 = new CheckBox();
        Label enable = new Label("Enabled",cb2);

        CheckBox cb3 = new CheckBox();
        Label showMoves = new Label("Show Moves", cb3);

        TextField tf = new TextField();

        //tf.setText("Max Undo");
        tf.setPromptText("Set Max Undos");

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
        cb1.getStyleClass().add("checkBox");
        cb2.getStyleClass().add("checkBox");
        unlim.getStyleClass().add("regularLabel");
        enable.getStyleClass().add("regularLabel");
        tf.getStyleClass().add("textField");
        save.getStyleClass().add("buttonSizeS2");
        exit.getStyleClass().add("buttonSizeS2");
        showMoves.getStyleClass().add("regularLabel");
        cb3.getStyleClass().add("checkBox");

        //scales check boxes
        cb1.setScaleX(.75);
        cb1.setScaleY(.75);
        cb2.setScaleX(.75);
        cb2.setScaleY(.75);
        cb3.setScaleX(.75);
        cb3.setScaleY(.75);



        //adds the all the nodes
        hbox1.getChildren().add(boardColor);
        hbox1.getChildren().add(userColor);

        hbox2.getChildren().add(wcolor);
        hbox2.getChildren().add(this.wcolor);
        hbox2.getChildren().add(bcolor);
        hbox2.getChildren().add(this.bcolor);

        hbox3.getChildren().add(save);
        hbox3.getChildren().add(exit);

        //sets up vbox1
        vbox1.getChildren().add(color);
        vbox1.getChildren().add(hbox1);
        vbox1.getChildren().add(hbox2);
        vbox1.getChildren().add(undo);
        vbox1.getChildren().add(enable);
        vbox1.getChildren().add(unlim);
        vbox1.getChildren().add(tf);

        vbox2.getChildren().add(showMoves);
        vbox2.getChildren().add(hbox3);

    }

    EventHandler<ActionEvent> buttonHandler2 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

        }//end handle
    };

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

    }

    public void removeObserver(SettingsObserver observer){

    }

    public void notifyGame(){

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
}
