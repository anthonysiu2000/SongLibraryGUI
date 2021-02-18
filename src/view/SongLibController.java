

package view;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SongLibController {

	@FXML
	ListView<String> musicList;
	TextField textFieldName, textFieldArtist, textFieldAlbum, textFieldYear;
	Button buttonEdit, buttonAdd, buttonDelete;
	Text textDetailName, textDetailArtist, textDetailAlbum, textDetailYear, 
		textInputName, textInputArtist, textInputAlbum, textInputYear, 
		textInstruct, textError;

	private ObservableList<String> obsList;              

	public void start(Stage mainStage) {                
		// create an ObservableList 
		// from an ArrayList  
		obsList = FXCollections.observableArrayList(                               
				"Giants",                               
				"Patriots",
				"49ers",
				"Rams",
				"Packers",
				"Colts",
				"Cowboys",
				"Broncos",
				"Vikings",
				"Dolphins",
				"Titans",
				"Seahawks",
				"Steelers",
				"Jaguars"); 

		musicList.setItems(obsList); 

		// select the first item
		musicList.getSelectionModel().select(0);

		// set listener for the items
		musicList
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showItemInputDialog(mainStage));

	}
	
	private void showItem(Stage mainStage) {                
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(mainStage);
		alert.setTitle("List Item");
		alert.setHeaderText(
				"Selected list item properties");

		String content = "Index: " + 
				musicList.getSelectionModel()
		.getSelectedIndex() + 
		"\nValue: " + 
		musicList.getSelectionModel()
		.getSelectedItem();

		alert.setContentText(content);
		alert.showAndWait();
	}
	
	private void showItemInputDialog(Stage mainStage) {                
		String item = musicList.getSelectionModel().getSelectedItem();
		int index = musicList.getSelectionModel().getSelectedIndex();

		TextInputDialog dialog = new TextInputDialog(item);
		dialog.initOwner(mainStage); dialog.setTitle("List Item");
		dialog.setHeaderText("Selected Item (Index: " + index + ")");
		dialog.setContentText("Enter name: ");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) { obsList.set(index, result.get()); }
	}

}
