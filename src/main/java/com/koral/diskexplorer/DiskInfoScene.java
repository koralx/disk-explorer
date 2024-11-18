package com.koral.diskexplorer;

import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.File;
import java.nio.file.Path;

public class DiskInfoScene {
    private Scene scene;
    private File root_dir;
    public DiskInfoScene(File path) {
        root_dir = path;
    }

    Scene getScene() {

        PieChart pieChart = new PieChart();

        PieChart.Data slice1 = new PieChart.Data("Desktop", 213);
        PieChart.Data slice2 = new PieChart.Data("Phone"  , 67);
        PieChart.Data slice3 = new PieChart.Data("Tablet" , 36);

        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
        pieChart.getData().add(slice3);

        TreeView<String> treeView = new TreeView<>();
        if (root_dir != null && root_dir.isDirectory()) {
            TreeItem<String> rootItem = createDirectoryTree(root_dir);
            treeView.setRoot(rootItem);
        }

        HBox hbox = new HBox(treeView, pieChart);

        HBox.setHgrow(treeView, Priority.ALWAYS);
        HBox.setHgrow(pieChart, Priority.ALWAYS);

        scene = new Scene(hbox, 1000, 600);
        return scene;
    }

    private TreeItem<String> createDirectoryTree(File dir) {
        if (dir == null || !dir.isDirectory()) {
            return new TreeItem<>(dir != null ? dir.getName() + " (inaccessible)" : "Unknown Directory");
        }

        File[] files = dir.listFiles();
        if (files == null) {
            return new TreeItem<>(dir.getName() + " (inaccessible)");
        }

        long dirSize = calculateDirectorySize(dir);
        TreeItem<String> rootItem = new TreeItem<>(dir.getName() + " (" + formatSize(dirSize) + ")");

        for (File file : files) {
            if (file.isDirectory()) {
                rootItem.getChildren().add(createDirectoryTree(file));
            } else {
                long fileSize = file.length();
                TreeItem<String> fileItem = new TreeItem<>(file.getName() + " (" + formatSize(fileSize) + ")");
                rootItem.getChildren().add(fileItem);
            }
        }
        return rootItem;
    }

    private long calculateDirectorySize(File dir) {
        if (dir == null || !dir.isDirectory()) {
            // Handle invalid or inaccessible directory
            return 0;
        }

        File[] files = dir.listFiles();
        if (files == null) {
            // Directory is inaccessible
            return 0;
        }
        long size = 0;
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                size += calculateDirectorySize(file);
            } else {
                size += file.length();
            }
        }
        return size;
    }

    private String formatSize(long size) {
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        double sizeInUnits = size;

        while (sizeInUnits >= 1024 && unitIndex < units.length - 1) {
            sizeInUnits /= 1024;
            unitIndex++;
        }
        return String.format("%.2f %s", sizeInUnits, units[unitIndex]);
    }
    
}
