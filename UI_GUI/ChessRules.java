package UI_GUI;

import Interfaces.ScreenChangeHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ChessRules implements EventHandler<ActionEvent> {
    private BorderPane root;
    private ScreenChangeHandler handler;
    private AnchorPane anchorPane;
    private Button exit;

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

    @Override
    public void handle(ActionEvent event){
        handler.switchScreen(ScreenChangeHandler.SCREENA);
    }
}
