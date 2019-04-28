package UI_GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.*;

public class SettingsRoundTwo {
    /** */
    private BorderPane root;

    /** */
    private AnchorPane ap;

    /** */
    private HBox hbox1;

    /** */
    private HBox hbox2;

    private VBox vbox1;

    private VBox vbox2;


    public SettingsRoundTwo(){
        root = new BorderPane();
        ap = new AnchorPane();
        hbox1 = new HBox();
        hbox2 = new HBox();
        vbox1 = new VBox();
        vbox2 = new VBox();
    }


    public void settingSetup(){
        root.setMinSize(500.0,280.0);
        Label lbl = new Label("Settings");
        root.setTop(lbl);
        lbl.getStyleClass().add("mainLabel");
        BorderPane.setAlignment(lbl, Pos.CENTER);

        color();

        root.setCenter(vbox1);
        root.setRight(vbox2);
        BorderPane.setAlignment(vbox1, Pos.CENTER);
        BorderPane.setAlignment(vbox2,Pos.CENTER_RIGHT);

    }

    public BorderPane getRoot(){
        return this.root;
    }

    private void color(){
        Label color = new Label("Color");
        Label whiteColor = new Label("White Squares:\t");
        Label blackColor = new Label("Black Squares:\t");
        Button bcolor = new Button();
        Button wcolor = new Button();
        Button save = new Button();
        save.setText("Save");
        Button exit = new Button("Exit");
        exit.setText("Exit");
        Label undo = new Label("Undo");

        CheckBox cb1 = new CheckBox();
        Label unlim = new Label("Unlimited Undo", cb1);

        CheckBox cb2 = new CheckBox();
        Label enable = new Label("Enabled",cb2);

        TextField tf = new TextField();
        tf.setText("Max Undo");

        //sets style sheets
        color.getStyleClass().add("mediumLabel");
        whiteColor.getStyleClass().add("regularLabel");
        blackColor.getStyleClass().add("regularLabel");
        bcolor.getStyleClass().add("buttonSizeS");
        wcolor.getStyleClass().add("buttonSizeS");
        exit.getStyleClass().add("buttonSizeS");
        save.getStyleClass().add("buttonSizeS");
        undo.getStyleClass().add("mediumLabel");
        cb1.getStyleClass().add("checkBox");
        cb2.getStyleClass().add("checkBox");
        unlim.getStyleClass().add("regularLabel");
        enable.getStyleClass().add("regularLabel");
        tf.getStyleClass().add("textField");
        save.getStyleClass().add("buttonSizeS");
        exit.getStyleClass().add("buttonSizeS");





        //adds the all the nodes
        hbox1.getChildren().add(whiteColor);
        hbox1.getChildren().add(wcolor);

        hbox2.getChildren().add(blackColor);
        hbox2.getChildren().add(bcolor);

        //sets up vbox1
        vbox1.getChildren().add(color);
        vbox1.getChildren().add(hbox1);
        vbox1.getChildren().add(hbox2);
        vbox1.getChildren().add(undo);
        vbox1.getChildren().add(enable);
        vbox1.getChildren().add(unlim);
        vbox1.getChildren().add(tf);

        vbox2.getChildren().add(save);
        vbox2.getChildren().add(exit);

    }

}
