package colorama;

import Interfaces.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**Defines a simple slider pane with an input field, slider and title.**/
public class SliderPane extends VBox {

    /**The label of the control**/
    private Label title;

    /**The title above the value text field**/
    private Label valueTitle;

    /**The value of the control**/
    private TextField value;

    /**The slider control**/
    private Slider slider;

    /**The minimum slider value**/
    private int min;

    /**The maximum slider value**/
    private int max;

    /** The slideChangeListener */
    private SliderChangeListener scl;

    /** The color we want */
    private int color;


    /**
     * Construct a slider
     * @param titleText The title of the slider.
     * @param initVal The initial value of the slider.
     * @param min The minimum value on the slider.
     * @param max The maximum value on  the slider
     */
    public SliderPane(String titleText, int initVal, int min , int max){

        this.min = min;
        this.max = max;

        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(10.0);

        //Create child components
        title = new Label(titleText);
        valueTitle = new Label("value");
        value = new TextField(initVal+"");
        slider = new Slider();
        slider.setOrientation(Orientation.VERTICAL);

        //Add them to the layout
        this.getChildren().add(title);
        this.getChildren().add(valueTitle);
        this.getChildren().add(value);
        this.getChildren().add(slider);

        //Apply CSS Styles
        title.getStyleClass().add("slider_title");
        valueTitle.getStyleClass().add("value_title");
        value.getStyleClass().add("text_input");

        //Set up slider
        slider.setMin(min);
        slider.setMax(max);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(max);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);

        slider.valueProperty().addListener(valueChangeListener);
        value.textProperty().addListener(textValueChangeListener);
    }//end constructor


    /**Set the value of the slider**/
    public void set(int newValue){
        value.setText(newValue+"");
        slider.setValue(newValue);

        this.color = newValue;
        notifyObserver();


    }//end set

    /**Get the selected value on the slider**/
    public int getValue(){
        return (int) slider.getValue();
    }//end getValue

    /**Change listender for when the slider is changed**/
    ChangeListener<Number> valueChangeListener = new ChangeListener<Number>(){

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if(!oldValue.equals(newValue))
                set(newValue.intValue());

        }//end changed

    };//end valueChangeListener

    /**The textView needs another listener because its value type is String.**/
    ChangeListener<String> textValueChangeListener = new ChangeListener<String>(){

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            // TODO Auto-generated method stub
            if(!oldValue.equals(newValue)){
                try{
                    int n = Integer.parseInt(newValue);
                    set(n);
                }
                catch(NumberFormatException ex){
                    //do nothing.
                }

            }//end if

        }
    };


    public void setSliderChangeListener(SliderChangeListener scl){
        this.scl = scl;
    }

    public void notifyObserver(){
        scl.notifyChangeListener(this, this.color);
    }

}//end SliderPanel
