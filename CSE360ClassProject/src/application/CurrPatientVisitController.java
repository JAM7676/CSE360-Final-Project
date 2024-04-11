package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CurrPatientVisitController implements Initializable
{
	private Scene scene;
	private Parent root;
	private Stage stage;
	
	@FXML
	private TextArea InfoBox;
	
	@FXML
	private TextField DescriptionBox;
	
	@FXML
	private TextField ReasonBox;
	
	@FXML
	private TextField NotesBox;
	
	@FXML
	private TextField DateBox;
	
	@FXML
	private Button FinishVisitButton;
	
	public void switchToMainMenu(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("DoctorLanding.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void finalizeVisit(ActionEvent e) throws IOException
	{
		
		if(DescriptionBox.getText().equals("") || ReasonBox.getText().equals("") || NotesBox.getText().equals("") || DateBox.getText().equals(""))
		{
			return;
		}
		else
		{
			String UID = Main.queue.poll().userID;
			
			String folderLoc = IOHandeler.getDirectory();
			
			String newDirPath = folderLoc + UID;
			
			File file = new File(newDirPath, "visitHistory.txt");
			
			if(file.exists())
			{
				String filePath = newDirPath + "/visitHistory.txt";
			        String textToAppend = Main.currUser.firstName + " " + Main.currUser.lastName + "\n" + DateBox.getText() + "\n" + ReasonBox.getText() + "\n" + DescriptionBox.getText() + "\n" + NotesBox.getText();

			        // Set the second parameter to true to enable appending mode
			        try (FileWriter fileWriter = new FileWriter(filePath, true);
			             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter))
			        {
			            
			            // New line to separate appended text from existing text
			            bufferedWriter.newLine();
			            // Writing text to file
			            bufferedWriter.write(textToAppend);
			            // Always flush to ensure all data is written to the file
			            bufferedWriter.flush();
			        }
			        catch (IOException ex)
			        {
			            System.err.println("An error occurred while writing to the file: " + ex.getMessage());
			        }
			}
			else
			{
				String filePath = newDirPath + "/visitHistory.txt";
				String content = Main.currUser.firstName + " " + Main.currUser.lastName + "\n" + DateBox.getText() + "\n" + ReasonBox.getText() + "\n" + DescriptionBox.getText() + "\n" + NotesBox.getText();

		        // FileWriter defaults to non-append mode, which creates a new file if it doesn't exist
		        try (FileWriter fileWriter = new FileWriter(filePath);
		             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
		            
		            // Writing text to file
		            bufferedWriter.write(content);
		            // Flush ensures all data is written to the file
		            bufferedWriter.flush();
		        } catch (IOException ex) {
		            System.err.println("An error occurred while writing to the file: " + ex.getMessage());
		        }
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		if(Main.queue.peek() == null)
		{
			InfoBox.setText("No patients in queue");
		}
		else
		{
			User curr = Main.queue.peek();
			String InfoBoxString = "First Name: " + curr.firstName + "\nLast Name: " + curr.lastName + "\nDOB: " + curr.DOB + "\nSex: " + curr.sex;
			InfoBox.setText(InfoBoxString);
		}
	}
	
}
