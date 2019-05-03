package colorama;

import UI_GUI.*;
import UI_GUI.Board_GUI.Screens;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
/**Defined a schene which shows a color**/
public class ColorScene extends GridPane {

    /**The area on to which the color is shown**/
    StackPane color;

    /**The selected color**/
    String selectedColor;

    /**The button for when a color is selected**/
    Button buttonColor;

    private static ColorScene instance;

    /**Create a color screene with black as the default color**/
    private ColorScene(){
        this("000000");
    }

    /**Create a color scene with a defined color**/
    private ColorScene(String colorSelection){

        selectedColor =  colorSelection;

        RowConstraints row0 = new RowConstraints();
        row0.setPercentHeight(50);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);

        //grid constraints for columns
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(100);


        //Apply constraints
        this.getRowConstraints().addAll(row0, row1);
        this.getColumnConstraints().addAll(col0);

        color = new StackPane();
        color.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        buttonColor = new Button("Choose Color");
        GridPane.setHalignment(buttonColor, HPos.CENTER);

        this.add(color, 0, 0,1,1);
        this.add(buttonColor,0, 1, 1, 1);

        setColor(colorSelection);

        //Wire the button to a handler.
        buttonColor.setOnAction(buttonHandler);
    }//end constructor.

    /**
     * Set a color and display the result
     * @param colorSelection
     */
    public void setColor(String colorSelection){
        selectedColor = colorSelection;
        color.setStyle("-fx-background-color: #" + colorSelection );
    }//nd setColor


    /**An event handler as a field*/
    EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            //This is tightly coupled, not loosely coupled way to do this.
            //Easier but not optimal for reuse.
            Board_GUI.getInstance().switchUI(Screens.ColorChooser);
        }
    };//end buttonHandler

    /**Get a singleton instance of this class**/
    public static ColorScene getInstance(){
        if(instance == null)
            instance = new ColorScene();

        return instance;
    }//end getInstance.

}//end class
