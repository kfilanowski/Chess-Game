package UI_GUI;

import Interfaces.ScreenChangeHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * This class models the ChessRules screen for the GUI
 * @author Matthew Lutz 100%
 */
public class ChessRules implements EventHandler<ActionEvent> {
    private BorderPane root;
    private ScreenChangeHandler handler;
    private AnchorPane anchorPane;
    private Button exit;

    /**
     * Contructor for the ChessRules class
     */
    public ChessRules(){
        root = new BorderPane();
        anchorPane = new AnchorPane();
        exit = new Button("Exit");
        AnchorPane.setRightAnchor(exit, 10.0);
        AnchorPane.setBottomAnchor(exit, 7.0);
        anchorPane.getChildren().addAll(exit);
        Label label = new Label("Chess Rules Coming Soon!");
        label.getStyleClass().add("screenTitle");
        root.setCenter(label);
        root.setBottom(anchorPane);
        exit.getStyleClass().add("buttonSizeS");
        exit.setOnAction(this);
        root.setId("Screen1");
    }

    /**
     * Method that gets the root
     * @return - BorderPane that represents the root pane
     */
    public Pane getRoot(){
        return root;
    }

    /**
     * Method that sets the screenChangeHandler to a new screenChangeHandler
     * @param sch - new screenChangeHandler that we want to set
     */
    public void setScreenChangeHandler(ScreenChangeHandler sch){
        this.handler = sch;
    }

    /**
     * Handle method that handles the screen changing
     * @param event - Action event that represents the button press
     */
    @Override
    public void handle(ActionEvent event){
        handler.switchScreen(ScreenChangeHandler.MAINSCREEN);
    }
}
