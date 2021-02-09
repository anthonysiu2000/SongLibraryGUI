//made by Anthony Siu and Benjamin Lee

package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SongLib extends Application {

	public void start(Stage primaryStage)
	throws Exception {
		
		//Code for Unanchored List GUI
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				getClass().getResource("/view/list.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		ListController listController =
				loader.getController();
		listController.start();
		Scene scene = new Scene(root, 200, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
		// TODO Auto-generated method stub

	}