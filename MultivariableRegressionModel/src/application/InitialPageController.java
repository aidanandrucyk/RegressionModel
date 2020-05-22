package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InitialPageController {
	@FXML
	private Label title;
	
	@FXML
	private Label subtitle;
		
	public void closeApp (ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}
	
	public void helpScreen(ActionEvent event) {
		// Platform.exit();
		Stage newStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/HelpScreen.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("helpScreen.css").toExternalForm());
			newStage.setTitle("Help Screen");
			newStage.setScene(scene);
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void newFile(ActionEvent event) {
		// Platform.exit();
		Stage newStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/InitialPage.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			newStage.setTitle("Aidan Andrucyk's Linear Algebra Toolkit");
			newStage.setScene(scene);
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Regression(ActionEvent event) {
		Stage newStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/RegressionModel.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			newStage.setTitle("Multivariable Regression Model");
			newStage.setScene(scene);
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void LinearAlgebra(ActionEvent event) {
		Stage newStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/LinearAlgebraFunctions.fxml"));
			Scene scene = new Scene(root);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			newStage.setTitle("Linear Algebra Functions");
			newStage.setScene(scene);
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ExpressionEvaluator(ActionEvent event) {
		Stage newStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/ExpressionEvaluator.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("ExpressionEvaluator.css").toExternalForm());
			newStage.setTitle("Essential Calculator");
			newStage.setScene(scene);
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}