//made by Anthony Siu and Benjamin Lee

package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.SongLibController;



public class SongLib extends Application {

	public void start(Stage primaryStage)
	throws Exception {
		
		//Code for Unanchored List GUI
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/SongLib.fxml"));
		
		
		AnchorPane root = (AnchorPane)loader.load();
		
		SongLibController songLibController =
				loader.getController();
		songLibController.start();
		
		
		Scene scene = new Scene(root, 200, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	
	public static void main(String[] args) {
		launch(args);

	}
}