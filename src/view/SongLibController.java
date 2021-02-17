//made by Anthony Siu and Benjamin Lee

package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class SongLibController {

	@FXML
	ListView<String> listView;
	
	private ObservableList<String> obsList;
	
	public void start() {
		// create an ObservableList
		// from an ArrayList
		obsList = FXCollections.observableArrayList(
				"Giants",
				"Patriots",
				"Jaguars");
				listView.setItems(obsList);
	}
}


