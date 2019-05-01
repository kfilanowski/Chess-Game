package UI_GUI;

import Interfaces.*;
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
public class MainMenu  {

    private static MainMenu instance;
    /** ScreenChangeHandler object */
    private ScreenChangeHandler handler;
    private Button pvp;
    private Button playerVsComp;
    private Button onlinePlay;
    private Button chessRules;
    private Button tutorial;
    private Button settings;
    private Button exit;
    private BorderPane root;
    private VBox vbox;
    private HBox hbox;
    private AnchorPane anchorPane;

    EventHandler<ActionEvent> buttonHandlerB = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            handler.switchScreen(ScreenChangeHandler.SCREENB);
        }//end handle
    };

    EventHandler<ActionEvent> buttonHandlerC = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            handler.switchScreen(ScreenChangeHandler.SCREENC);
        }//end handle
    };

    EventHandler<ActionEvent> exitHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
           Board_GUI.getPrimaryStage().close();
        }//end handle
    };

    public MainMenu(){
        super();
        root = new BorderPane();
        vbox = new VBox();

    }

    public void setup(){
        vbox.setAlignment(Pos.CENTER);//SET the alignment of the  layout.
        vbox.setSpacing(20);


        pvp = new Button("Player vs Player");
        //pvp.getStyleClass().add("buttonStyleA");
        pvp.getStyleClass().add("buttonSizeL");
        pvp.setOnAction(buttonHandlerB);
        vbox.getChildren().add(pvp);

        playerVsComp = new Button("Player vs Computer");
        //playerVsComp.getStyleClass().add("buttonStyleA");
        playerVsComp.getStyleClass().add("buttonSizeL");
        vbox.getChildren().add(playerVsComp);

        onlinePlay = new Button("Online Play");
        //onlinePlay.getStyleClass().add("buttonStyleA");
        onlinePlay.getStyleClass().add("buttonSizeL");
        vbox.getChildren().add(onlinePlay);

        chessRules = new Button("Rules of Chess");
        //chessRules.getStyleClass().add("buttonStyleA");
        chessRules.getStyleClass().add("buttonSizeL");
        vbox.getChildren().add(chessRules);

        tutorial = new Button("Tutorial");
        //tutorial.getStyleClass().add("buttonStyleA");
        tutorial.getStyleClass().add("buttonSizeL");
        vbox.getChildren().add(tutorial);

        settings = new Button("Settings");
        //settings.getStyleClass().add("buttonStyleA");
        settings.getStyleClass().add("buttonSizeS");
        settings.setOnAction(buttonHandlerC);


        exit = new Button("exit");
        //exit.getStyleClass().add("buttonStyleA");
        exit.getStyleClass().add("buttonSizeS");
        exit.setOnAction(exitHandler);

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
        BorderPane.setAlignment(king, Pos.CENTER_RIGHT);
        root.setId("Screen1");
        //root.getStyleClass().add("Screen1");

        root.setMinSize(900.0, 700.0);
    }

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
