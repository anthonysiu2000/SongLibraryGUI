//Created by Anthony Siu and Benjamin Lee

package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

	//Used to read in lines
	private int lineNumber = 0;
	private String currentLine;
	
	//Stores in all song data
	private ArrayList<String[]> songData = new ArrayList<String[]>();
	
	//Stores strings used in the Observable List
	private ArrayList<String> songArrayList = new ArrayList<String>();
	
	//Initiates the Observable List
	private ObservableList<String> obsList; 
	
	//Used for button management
	private int buttonNumber = 1;
	private Button prevButton = buttonEdit;
	
	

	public void start(Stage mainStage) throws FileNotFoundException, IOException{
		
		//Reads in txt file, delimited with tabs
		BufferedReader txtReader = new BufferedReader(new FileReader("src/view/songList.txt"));
		while ((currentLine = txtReader.readLine()) != null) {
			String[] data = currentLine.split("\t");
			songData.add(data);
			songArrayList.add(songData.get(lineNumber)[0] + " by " + songData.get(lineNumber)[1]);
			lineNumber++;
		}
		txtReader.close();
		
		obsList = FXCollections.observableArrayList(songArrayList); 

		musicList.setItems(obsList); 

		// checks if the list is empty, and sends warning, not an error
		if (songData.isEmpty()) {
			textInstruct.setText("Song List is Empty. Add songs please.");
		}
		
		// select the first item
		musicList.getSelectionModel().select(0);
	}
	
	
	
	
	//Method called when button is pressed
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
			
			//If Empty List, output error
			
			//User will press Edit twice, once to initiate text fields, and the second time to submit them
			//First Button Press
			if (buttonNumber == 1) {
				textInstruct.setText("Please edit fields, then press Edit again.");
				textInputName.setText("Name: ");
				textInputArtist.setText("Artist: ");
				textInputAlbum.setText("Album: ");
				textInputYear.setText("Year: ");
				prevButton = buttonEdit;
				buttonNumber = 2;
				
				//Sets text fields to current song's details
				
				
				
				
			//Second Button Press	
			} else {
				
				//Obtains text fields
				String editName = textFieldName.getText();
				String editArtist = textFieldArtist.getText();
				String editAlbum = textFieldAlbum.getText();
				String editYear = textFieldYear.getText();
				
				//Name nor Artist may not be blank
				if (editName == "" || editArtist == "") {
					textError.setText("Error: name nor artist may be edited to blank");
					textInstruct.setText("");
					resetFields();
					return;
				}
				

				//Name and Artist cannot both match existing song
				
				
				//Otherwise, song's fields are updated and selected
				
				
				
				
				
				resetFields();
				textInstruct.setText("Edit Successful.");
			}
		}
		
		
		
		else if (b == buttonAdd) {
			
			//User will press Add twice, once to initiate text fields, and the second time to submit them
			//First Button Press
			if (buttonNumber == 1) {
				textInstruct.setText("Please edit fields, then press Add again.");
				textInputName.setText("Name: ");
				textInputArtist.setText("Artist: ");
				textInputAlbum.setText("Album: ");
				textInputYear.setText("Year: ");
				prevButton = buttonAdd;
				buttonNumber = 2;
				
			//Second Button Press
			} else {
				
				//Obtains text fields
				String addName = textFieldName.getText();
				String addArtist = textFieldArtist.getText();
				String addAlbum = textFieldAlbum.getText();
				String addYear = textFieldYear.getText();
				
				//Name and Artist must be entered at the least
				if (addName == "" || addArtist == "") {
					textError.setText("Error: To add a song, you must include name AND artist, at the least.");
					textInstruct.setText("");
					resetFields();
					return;
				}
				
				//Name and Artist cannot both match existing song
				
				
				//Otherwise, song is added and selected
				
				
				
				resetFields();
				textInstruct.setText("Add Successful.");
			}
		}
		
		
		else {
			//If empty list, output error
			
			//Deletes song from Song Library
			
			//Selects the next song, if possible
			
			
			textInstruct.setText("Delete Successful.");
		}
	}
	
	//Method called by buttonPress when operation is finished
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
