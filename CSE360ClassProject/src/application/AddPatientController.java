package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPatientController {
	private Scene scene;
	private Parent root;
	private Stage stage;
	
	@FXML
	private Label statusLabel;
	
	@FXML
	private TextField usernameBox;
	
	@FXML
	private TextField heightBox;
	
	@FXML
	private TextField weightBox;
	
	@FXML
	private TextField BPBox;
	
	@FXML
	private TextField healthConcernBox;
	
	@FXML
	private TextField medicationBox;
	
	@FXML
	private TextField allergyBox;
	
	public void switchToMainMenu(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("NurseLanding.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void AddPatientInfo(ActionEvent e) throws IOException
	{
		if(usernameBox.getText() == "" || heightBox.getText() == "" || weightBox.getText() == "" || BPBox.getText() == "" || healthConcernBox.getText() == "" || medicationBox.getText() == "" || allergyBox.getText() == "")
		{
//			statusLabel.setText("One or more Fields are empty");
//			return;
		}
		else
		{
			String folderLoc = IOHandeler.getDirectory();
			
			String userID = Main.hashString(usernameBox.getText());
			
			String newDirPath = folderLoc + userID;
			File newDir = new File(newDirPath);
			
			if(newDir.exists())
			{
	            System.out.println("Account already exists.");
	            
	            File file = new File(newDirPath, "PatientIntakeInfo.txt");
            	
            	System.out.println("File created: " + file.getAbsolutePath());
				
				String content = heightBox.getText() + "\n" + weightBox.getText() + "\n" + BPBox.getText() + "\n" + healthConcernBox.getText() + "\n" + medicationBox.getText() + "\n" + allergyBox.getText();
				
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
				{
				    writer.write(content);
				    System.out.println("Successfully wrote to the file: " + file.getAbsolutePath());
				    Main.queue.add(new User(Main.hashString(this.usernameBox.getText())));
				    switchToMainMenu(e);
				}
				catch (IOException ex)
				{
				    System.err.println("An error occurred while writing to the file: " + file.getAbsolutePath());
				    ex.printStackTrace();
				}
	        }
			else
	        {
	            System.out.println("User does not exist.");
	        }
		}
	}
	
	private void clearBoxes()
	{
		this.allergyBox.setText("");
	}
}
