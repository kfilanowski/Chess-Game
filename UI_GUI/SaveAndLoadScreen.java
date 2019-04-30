package UI_GUI;

import java.io.*;

import History.History;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SaveAndLoadScreen extends Application {
    private FileChooser fileChooser;

    @Override
    public void start(Stage primaryStage){

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));

        Button save = new Button("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    try {
                        FileWriter fileWriter = new FileWriter(file);
                        PrintWriter writer = new PrintWriter(fileWriter);
                        writer.print(History.getInstance().toXML());
                        writer.close();
                    }catch (IOException ioe) {
                        System.out.println("caught ioe exception");
                    }
                }
            }
        });


        Button load = new Button("Load");
        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    try {
                        FileWriter fileWriter = new FileWriter(file);
                        PrintWriter writer = new PrintWriter(fileWriter);
                        writer.print(History.getInstance().toXML());
                        writer.close();
                    }catch (IOException ioe) {
                        System.out.println("caught ioe exception");
                    }
                }
            }
        });


        HBox root = new HBox();
        root.getChildren().add(save);
        root.getChildren().add(load);
        Scene scene = new Scene(root, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}