package com.koral.diskexplorer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DiskUsageAnalyzerApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Disk Usage Analyzer");

        File[] roots = File.listRoots();
        Button[] buttons = new Button[roots.length];
        int i = 0;
        for (File root : roots) {
            Button button = new Button(root.toString());
            buttons[i++] = button;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    DiskInfoScene diskInfoScene = new DiskInfoScene(root);
                    primaryStage.setScene(diskInfoScene.getScene());
                }
            });
        }

        VBox vbox = new VBox(buttons);

        vbox.setAlignment(Pos.CENTER);

        Scene firstScene = new Scene(vbox, 1000, 600);
        primaryStage.setScene(firstScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
