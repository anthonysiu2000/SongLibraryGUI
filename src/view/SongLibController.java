//Created by Anthony Siu and Benjamin Lee

package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	
	//Stores strings used in the Observable List aka. "name by artist"
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
		
		
		
		// List listening function
		musicList.getSelectionModel().selectedIndexProperty().addListener(
				(obs, oldVal, newVal) -> showDetail(mainStage));
		
		// select the first item
		musicList.getSelectionModel().select(0);
	}
	
	
	
	//Method called when button is pressed
	public void buttonPress(ActionEvent e) {
		int index = musicList.getSelectionModel().getSelectedIndex();
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
		
		//User will press Edit twice, once to initiate text fields, and the second time to submit them
		if (b == buttonEdit) {
			
			//If Empty List, output error
			if (songData.size() == 0) {
				textError.setText("Error: no songs to edit. Please add a song.");
				textInstruct.setText("");
				resetFields();
				return;
			}
			
			//First Button Press
			if (buttonNumber == 1) {
				//Instructs user
				textInstruct.setText("Please edit fields, then press Edit again.");
				textInputName.setText("Name: ");
				textInputArtist.setText("Artist: ");
				textInputAlbum.setText("Album: ");
				textInputYear.setText("Year: ");
				prevButton = buttonEdit;
				buttonNumber = 2;
				
				//Sets text fields to current song's details
				textFieldName.setText(songData.get(index)[0]);
				textFieldArtist.setText(songData.get(index)[1]);
				textFieldAlbum.setText(songData.get(index)[2]);
				textFieldYear.setText(songData.get(index)[3]);
				
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
				if (checkDuplicate(editName, editArtist, index) == true) {
					textError.setText("Error: name and artist cannot match existing song.");
					textInstruct.setText("");
					resetFields();
					return;
				}
				
				//Otherwise, song's fields are updated and selected
				songData.remove(index);
				resetAlphaOrder(editName, editArtist, editAlbum, editYear);
				
				resetFields();
				textInstruct.setText("Edit Successful.");
			}
		}
		
		
		
		
		//User will press Add twice, once to initiate text fields, and the second time to submit them
		else if (b == buttonAdd) {
			
			//First Button Press
			if (buttonNumber == 1) {
				//Instructs User
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
				if (checkDuplicate(addName, addArtist, -1) == true) {
					textError.setText("Error: name and artist cannot match existing song.");
					textInstruct.setText("");
					resetFields();
					return;
				}
				
				//Otherwise, song is added and selected
				resetAlphaOrder(addName, addArtist, addAlbum, addYear);
				
				resetFields();
				textInstruct.setText("Add Successful.");
			}
		}
		
		
		
		//Delete Button will only need one button press; no confirmation window
		else {
			
			//If empty list, output error
			if (songData.size() == 0) {
				textError.setText("Error: no songs to delete. Please add a song.");
				textInstruct.setText("");
				resetFields();
				return;
			}
			
			//Deletes song from Song Library
			prevButton = buttonDelete;
			textDetailName.setText("");
			textDetailArtist.setText("");
			textDetailAlbum.setText("");
			textDetailYear.setText("");
			songData.remove(index);
			songArrayList = new ArrayList<String>();
			for (int i = 0; i < songData.size(); i++) {
				songArrayList.add(songData.get(i)[0] + " by " + songData.get(i)[1]);
			}
			obsList = FXCollections.observableArrayList(songArrayList); 
			musicList.setItems(obsList); 
			//Selects the next song
			if (songData.size() == 0) {
				musicList.getSelectionModel().select(0);
			}
			else if (index < songData.size()) {
				musicList.getSelectionModel().select(index);
			}
			else {
				musicList.getSelectionModel().select(index - 1);
			}
			
			textInstruct.setText("Delete Successful.");
		}
	}
	
	

	
	
	//Method called when selecting an item from this list
	public void showDetail(Stage mainStage) {
		int index = musicList.getSelectionModel().getSelectedIndex();
		if (index < 0) {
			return;
		}
		textDetailName.setText("Name: " + songData.get(index)[0]);
		textDetailArtist.setText("Artist: " + songData.get(index)[1]);
		textDetailAlbum.setText("Album: " + songData.get(index)[2]);
		textDetailYear.setText("Year: " + songData.get(index)[3]);
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
	
	//Method called to check if input name and artist already exist within the data
	//accepts song name, artist, and index of edited song, if editing
	public boolean checkDuplicate(String inputName, String inputArtist, int index) {
		
		//parses through song data to see if there is a match with both name and artist
		for (int i = 0; i < songData.size(); i++) {
			if (i == index) {
				continue;
			}
			if (songData.get(i)[0].equals(inputName) && songData.get(i)[1].equals(inputArtist)) {
				return true;
			}
		}
		return false;
		
	}
	
	//Method called to reset songData, songArrayList, and obsList for a new/edited addition to the ArrayList
	//Accepts String fields as inputs of the added/edited song
	public void resetAlphaOrder(String inputName, String inputArtist, String inputAlbum, String inputYear) {
		String[] input = {inputName, inputArtist, inputAlbum, inputYear};
		int newIndex = songData.size() - 1;
		if (songData.size() == 0) {
			newIndex = 0;
		}
		for (int j = 0; j < songData.size(); j++) {
			if (inputName.compareTo(songData.get(j)[0]) == 0) {
				if (inputArtist.compareTo(songData.get(j)[0]) > 0) {
					newIndex = j;
					break;
				}
			} else
			if (inputName.compareTo(songData.get(j)[0]) > 0) {
				newIndex = j;
				break;
			}
		}
		
		songData.add(newIndex, input);
		songArrayList = new ArrayList<String>();
		for (int i = 0; i < songData.size(); i++) {
			songArrayList.add(songData.get(i)[0] + " by " + songData.get(i)[1]);
		}
		obsList = FXCollections.observableArrayList(songArrayList); 
		musicList.setItems(obsList); 
		musicList.getSelectionModel().select(newIndex);
	}
	
	
	
	
	//Method called when save button is selected
	public void saveButton() throws IOException {
		//writes to a file
		FileWriter txtWriter = new FileWriter("src/view/songList.txt");
		for (int i = 0; i < songData.size(); i++) {
		    txtWriter.append(songData.get(i)[0] + "\t" + songData.get(i)[1] + "\t" + songData.get(i)[2] + "\t" + songData.get(i)[3]);
		    txtWriter.append("\n");
		}
		txtWriter.flush();
		txtWriter.close();
		
		textInstruct.setText("Library Saved. You may now close the application.");
	}

}
