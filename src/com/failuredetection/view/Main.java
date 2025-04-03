package com.failuredetection.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.failuredetection.controller.TrainingController;
import com.failuredetection.model.Node;
import com.failuredetection.model.TreeNode;
import com.failuredetection.treelayout.view.SwingDemo;
import com.failuredetection.util.FileUtil;
import com.failuredetection.util.MathUtil;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	
	TrainingController trainingController = new TrainingController();
	TreeNode<Node> root = null;
	public static List<String> lines = new ArrayList<String>();
	public static Double mean = 0.0, standardDeviation = 0.0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Button trainingBtn = new Button("Training Mode");
		trainingBtn.setMinHeight(100);
		trainingBtn.setMinWidth(150);
		trainingBtn.setMaxHeight(100);
		trainingBtn.setMaxWidth(150);
		trainingBtn.setPrefHeight(100);
		trainingBtn.setPrefWidth(150);
		trainingBtn.setStyle("-fx-color: gray;");
		
		Button detectionBtn = new Button("Detection Mode");
		detectionBtn.setMinHeight(100);
		detectionBtn.setMinWidth(150);
		detectionBtn.setMaxHeight(100);
		detectionBtn.setMaxWidth(150);
		detectionBtn.setPrefHeight(100);
		detectionBtn.setPrefWidth(150);
		detectionBtn.setStyle("-fx-color: gray;");
		
		final FileChooser fileChooser = new FileChooser();
		
		trainingBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Open file chooser
				File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                	lines = FileUtil.readFile(file);
                    root = trainingController.buildTree(lines, Boolean.FALSE);
                    
                    if(TrainingController.tree != null) {
                    	SwingDemo.buildTree();
                    }
                    
                    Main.mean = MathUtil.calculateMean(root);
                    Main.standardDeviation = MathUtil.calculateStandardDeviation(root, mean);
                }
				
				System.out.println("Done");
			}
			
		});
		
		detectionBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Open file chooser
				File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                	List<String> lines1 = FileUtil.readFile(file);
                	// Match lines
                	Boolean matched = trainingController.matchTree(lines, lines1);
                	
                	// Check Mean and standard deviation
                	TreeNode<Node> root1 = trainingController.buildTree(lines1, Boolean.TRUE);
                	
                	if(Boolean.TRUE.equals(matched) && root1 != null) {
                		// Show Pass
                		
                		Button passBtn = new Button("Pass");
                		passBtn.setMinHeight(100);
                		passBtn.setMinWidth(150);
                		passBtn.setMaxHeight(100);
                		passBtn.setMaxWidth(150);
                		passBtn.setPrefHeight(100);
                		passBtn.setPrefWidth(150);
                		passBtn.setStyle("-fx-color: green;");
                		
                		VBox vBox = new VBox(50);
                		vBox.setAlignment(Pos.CENTER);
                		vBox.getChildren().addAll(passBtn);
                		
                		Scene scene = new Scene(vBox);
                		
                		Stage stage = new Stage();
                		
                		primaryStage.close();
                		
                		stage.setTitle("Result");
                		stage.setScene(scene);
                		stage.setWidth(500);
                		stage.setHeight(500);
                		stage.show();
                	} else {
                		// Show Fail

                		Button failBtn = new Button("Fail");
                		failBtn.setMinHeight(100);
                		failBtn.setMinWidth(150);
                		failBtn.setMaxHeight(100);
                		failBtn.setMaxWidth(150);
                		failBtn.setPrefHeight(100);
                		failBtn.setPrefWidth(150);
                		failBtn.setStyle("-fx-color: red;");
                		
                		VBox vBox = new VBox(50);
                		vBox.setAlignment(Pos.CENTER);
                		vBox.getChildren().addAll(failBtn);
                		
                		Scene scene = new Scene(vBox);
                		
                		Stage stage = new Stage();
                		
                		primaryStage.close();
                		
                		stage.setTitle("Result");
                		stage.setScene(scene);
                		stage.setWidth(500);
                		stage.setHeight(500);
                		stage.show();
                	}
                }
				
			}
			
		});
		
		VBox vBox = new VBox(50);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(trainingBtn, detectionBtn);
		
		Scene scene = new Scene(vBox);
		
		primaryStage.setTitle("Mode");
		primaryStage.setScene(scene);
		primaryStage.setWidth(500);
		primaryStage.setHeight(500);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
