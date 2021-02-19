//Created by Anthony Siu and Benjamin Lee

package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SongLibController {

	@FXML private ListView<String> musicList;
	@FXML private TextField textFieldName, textFieldArtist, textFieldAlbum, textFieldYear;
	@FXML private Button buttonEdit, buttonAdd, buttonDelete;
	@FXML private Text textDetailName, textDetailArtist, textDetailAlbum, textDetailYear, 
			textInputName, textInputArtist, textInputAlbum, textInputYear, 
			textInstruct, textError;

	private ObservableList<String> obsList; 
	private int buttonNumber = 1;
	private Button prevButton = buttonEdit;

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

	}
	
	
	
	
	public void buttonPress(ActionEvent e) {
		
		Button b = (Button)e.getSource();
		
		//If the second button pressed is incorrect, we state an error, and do not pass the action
		if (buttonNumber == 2 && b != prevButton) {
			textError.setText("Error: Incorrect button press; Action reset.");
			resetFields();
			textInstruct.setText("");
			return;
		}
		textError.setText("");
		
		//Implementing Button functions
		
		if (b == buttonEdit) {
			
			
			
			
			//User will press Edit twice, once to initiate text fields, and the second time to submit them
			
			if (buttonNumber == 1) {
				textInstruct.setText("Please edit fields, then press Edit again.");
				textInputName.setText("Name: ");
				textInputArtist.setText("Artist: ");
				textInputAlbum.setText("Album: ");
				textInputYear.setText("Year: ");
				prevButton = buttonEdit;
				buttonNumber = 2;
			} else {
				String editName = textFieldName.getText();
				String editArtist = textFieldArtist.getText();
				String editAlbum = textFieldAlbum.getText();
				String editYear = textFieldYear.getText();
				resetFields();
				textInstruct.setText("Edit Successful.");
			}
			
		}
		
		//User will press Add twice, once to initiate text fields, and the second time to submit them
			
		else if (b == buttonAdd) {
			if (buttonNumber == 1) {
				textInstruct.setText("Please edit fields, then press Add again.");
				textInputName.setText("Name: ");
				textInputArtist.setText("Artist: ");
				textInputAlbum.setText("Album: ");
				textInputYear.setText("Year: ");
				prevButton = buttonAdd;
				buttonNumber = 2;
			} else {
				String addName = textFieldName.getText();
				String addArtist = textFieldArtist.getText();
				String addAlbum = textFieldAlbum.getText();
				String addYear = textFieldYear.getText();
				
				if (addName == "" || addArtist == "") {
					textError.setText("Error: To add a song, you must include name AND artist, at the least.");
					textInstruct.setText("");
					resetFields();
					return;
				}
				
				resetFields();
				textInstruct.setText("Add Successful.");
			}
			
		}
		
		else {
			textInstruct.setText("Delete Successful.");
		}
	}
	
	public void resetFields(){ 
		textFieldName.setText("");
		textFieldArtist.setText("");
		textFieldAlbum.setText("");
		textFieldYear.setText("");
		buttonNumber = 1;
		textInputName.setText("");
		textInputArtist.setText("");
		textInputAlbum.setText("");
		textInputYear.setText("");
	}

}
