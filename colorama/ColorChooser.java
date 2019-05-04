package colorama;

import Interfaces.*;
import UI_GUI.Board_GUI;
import UI_GUI.SettingsScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ColorChooser extends GridPane implements EventHandler<ActionEvent> {
    /**The selected color as a hex value (RGB)**/
    String selectedColor;

    /**Red color slider panel**/
    private SliderPane red;

    /**Green color slider panel**/
    private SliderPane green;

    /**Blue color slider panel**/
    private SliderPane blue;

    /**Confirms a color choice**/
    private Button setBlack;

    /**Confirms a color choice**/
    private Button setWhite;

    /**Cancels a color choice**/
    private Button cancel;

    /**The area on to which the color is shown**/
    StackPane color;

    /**The color text label**/
    Label hexColor;

    /**Maximum color intensity**/
    private final int MAX_INTEN = 255;

    /**Minimum color intensity**/
    private final int MIN_INTEN = 0;


    /**The singleton instance of this class**/
    private static ColorChooser instance;

    /** **/
    private ScreenChangeHandler sch;




    /**Create a singleton instance of a ColorChooser**/
    public static ColorChooser getInstance(){
        if (instance == null) instance = new ColorChooser();
        return instance;
    }//end getInstance

    /**Construct a color chooser. */
    private  ColorChooser(){



        this.setVgap(20);
        //Grid contstraints for row
        RowConstraints row0 = new RowConstraints();
        row0.setPercentHeight(40);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(10);

        //grid constraints for columns
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(33);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33.5);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33.5);

        //Appy constraints
        this.getRowConstraints().addAll(row0, row1,row2);
        this.getColumnConstraints().addAll(col0,col1,col2);


        //Top panel for color
        color = new StackPane();
        color.getStyleClass().add("color");
        hexColor = new Label();
        hexColor.getStyleClass().add("color_text");
        color.getChildren().add(hexColor);
        setBackround(0, 0, 0);//set to black


        //Create Panel for sliders with centered layout
        HBox sliders = new HBox();
        sliders.setSpacing(20.0);
        sliders.setAlignment(Pos.TOP_CENTER);

        //Construct 3 sliders for RGB
        red = new SliderPane("Red",MIN_INTEN, MIN_INTEN, MAX_INTEN);
        green = new SliderPane("Green",MIN_INTEN, MIN_INTEN, MAX_INTEN);
        blue = new SliderPane("Blue",MIN_INTEN, MIN_INTEN, MAX_INTEN);

        //Add the sliders
        sliders.getChildren().add(red);
        sliders.getChildren().add(green);
        sliders.getChildren().add(blue);


        setBlack = new Button("Black Piece Color");
        setWhite = new Button("White Piece Color");
        cancel = new Button("Cancel");
        setBlack.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        setWhite.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        cancel.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        sliders.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        color.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        //col, row, colspan, rowspan
        this.add(color, 0,0,3,1);
        this.add(sliders, 0, 1,3,1);
        this.add(cancel, 0,2, 1,1);
        this.add(setBlack, 1,2,1,1);
        this.add(setWhite,2,2,1,1);


        setBlack.setOnAction(this);
        setWhite.setOnAction(this);
        cancel.setOnAction(this);


        red.setSliderChangeListener(this.scl);
        blue.setSliderChangeListener(this.scl);
        green.setSliderChangeListener(this.scl);




    }//end constructor

    /**
     * Set the background color of the  chooser
     * @param r The red intensity 0..255
     * @param g The blue intensity 0..255
     * @param b The green intensity 0..255
     */
    public void setBackround(int r, int g, int b){
        //Avoid invalid values
        if(r > 255 || g > 255 || b >255) return;

        String hr = Integer.toHexString(r);
        String hg = Integer.toHexString(g);
        String hb = Integer.toHexString(b);

        if(r+g+b/3 > 127)
            hexColor.setTextFill(Color.BLACK);
        else
            hexColor.setTextFill(Color.WHITE);

        //Add preceeding 0 if only 1 char
        if(hr.length() == 1)
            hr = 0 + hr;
        if(hg.length() == 1)
            hg = 0 + hg;
        if(hb.length() == 1)
            hb = 0 + hb;

        selectedColor = hr+hg+hb;

        color.setStyle("-fx-background-color: #" + selectedColor);

        hexColor.setText("#" + selectedColor);
        //System.out.println(col + " : " + hr + ", " + hg + ", " + hb);
    }//end setBackground

    /**The method of the button EventHandler insterface implementation that this class has**/
    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == setBlack){
            SettingsScreen.getInstance().setBlackColor(selectedColor);
            SettingsScreen.getInstance().getColorama().close();
        } else if(event.getSource() == setWhite){
            SettingsScreen.getInstance().setWhiteColor(selectedColor);
            SettingsScreen.getInstance().getColorama().close();
        } else if(event.getSource() == cancel){
            SettingsScreen.getInstance().getColorama().close();
        }
    }//end handle


    public void notifyObserver(){
        sch.switchUI(Board_GUI.Screens.ColorScreen);
    }

    private SliderChangeListener scl = new SliderChangeListener() {
        @Override
        public void notifyChangeListener(SliderPane pane, int color) {
            setBackround(red.getValue(), green.getValue(), blue.getValue());
        }

    };

    public Pane getRoot(){
        return this;
    }






    public void setScreenChangeHandler(ScreenChangeHandler sch){
        this.sch = sch;
    }

}
