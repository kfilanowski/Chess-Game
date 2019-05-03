package UI_GUI;
import Interfaces.ScreenChangeHandler;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;


public class OnlinePlay implements EventHandler<ActionEvent> {
    private BorderPane root;
    private ScreenChangeHandler handler;
    private AnchorPane anchorPane;
    private Button exit;

    public OnlinePlay(){
        root = new BorderPane();
        anchorPane = new AnchorPane();
        exit = new Button("Exit");
        AnchorPane.setRightAnchor(exit, 10.0);
        AnchorPane.setBottomAnchor(exit, 7.0);
        anchorPane.getChildren().addAll(exit);
        Label label = new Label("Online Play Coming Soon!");
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
        handler.switchScreen(ScreenChangeHandler.MAINSCREEN);
    }


}
