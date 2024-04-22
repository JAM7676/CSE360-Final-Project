package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContactInfoController implements Initializable
{
	private Scene scene;
	private Parent root;
	private Stage stage;
	
	@FXML
	private Label phoneLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private Label nicknameLabel;
	@FXML
	private VBox editInfoVBox;
	@FXML
	private TextField newPhoneTextField;
	@FXML
	private TextField newEmailTextField;
	@FXML
	private TextField newNameTextField;
	@FXML
	private TextArea emergencyContactOneTextArea;
	@FXML
	private TextArea emergencyContactTwoTextArea;
	@FXML
	private TextArea emergencyContactThreeTextArea;
	@FXML
	private TextField newNameField;
	@FXML
	private TextField newRelationshipField;
	@FXML
	private TextField newNumberField;
	@FXML
	private VBox emergencyVBox;
	
	public void switchToMainMenu(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("PatientLanding.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void showEditingArea ()
	{
		editInfoVBox.setDisable(false);
	}
	
	public void hideEditingArea ()
	{
		editInfoVBox.setDisable(true);
	}
	
	public void saveToFile () throws FileNotFoundException, IOException
	{
		Main.currUser.phoneNumber = newPhoneTextField.getText();
		Main.currUser.email = newEmailTextField.getText();
		Main.currUser.nickName = newNameTextField.getText();
		
		// Upcoming portion currently uses the old hashing method
		
		String dirPath = IOHandeler.getDirectory() + Main.currUser.userID;
		File fileOutput = new File(dirPath, "PatientAccountInfo.txt");
		
		String dataLocation = IOHandeler.getFile(Main.currUser.userID, "PatientAccountInfo.txt");
		String[] data = new String[11];
		// Try-with-resources to ensure that resources are closed
		try (BufferedReader reader = new BufferedReader(new FileReader(dataLocation)))
		{
			String line;
			// Read line by line
			int idx = 0;
			while ((line = reader.readLine()) != null)
			{
				data[idx] = line;
				idx++;
			}
			reader.close();
		}
		
		String content = data[0] + "\n" + data[1] + "\n" + data[2] + "\n" + data[3] + "\n" + Main.currUser.phoneNumber + "\n" + Main.currUser.email + "\n" + data[6] + "\n" + data[7] + "\n" + data[8] + "\n" + data[9] + "\n" + Main.currUser.nickName;
	
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutput)))
        {
            writer.write(content);
            System.out.println("Successfully wrote to the file: " + fileOutput.getAbsolutePath());
        }
        catch (IOException ex)
        {
            System.err.println("An error occurred while writing to the file: " + fileOutput.getAbsolutePath());
            ex.printStackTrace();
        }
        
        phoneLabel.setText("Phone Number: " + Main.currUser.phoneNumber);
		emailLabel.setText("Email Address: " + Main.currUser.email);
		nicknameLabel.setText("Nickname: " + Main.currUser.nickName); 
		
		newPhoneTextField.setText("");
		newEmailTextField.setText("");
		newNameTextField.setText("");
		
		editInfoVBox.setDisable(true);
	}
	
	public void saveNewContactInfo ()
	{
		/*
		 * Will Create new file in Cx's patient directory for emergency contacts
		 * Should load into the web page and display all of them iteratively
		 * Still uses old hashing process
		 */
		
		String dirPath = IOHandeler.getDirectory() + Main.currUser.userID;
		File contactInfoFile = new File(dirPath, "PatientEmergencyContactInfo.txt");
		
		if(!contactInfoFile.exists())
		{
			try
			{
				boolean fileCreated = contactInfoFile.createNewFile();
				
				if (fileCreated)
				{
					System.out.println("File created: " + contactInfoFile.getAbsolutePath());
                    
                    String content = newNameField.getText() + "\n" + newRelationshipField.getText() + "\n" + newNumberField.getText();
                    
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(contactInfoFile)))
                    {
                        writer.write(content);
                        System.out.println("Successfully wrote to the file: " + contactInfoFile.getAbsolutePath());
                        writer.close();
                    }
                    catch (IOException ex)
                    {
                        System.err.println("An error occurred while writing to the file: " + contactInfoFile.getAbsolutePath());
                        ex.printStackTrace();
                    }
				}
				
			}
			catch (IOException ex)
			{
				System.err.println("Cannot create the file: " + contactInfoFile.getAbsolutePath());
                ex.printStackTrace();
			}
		}
		else
		{
			String content = "\n" + newNameField.getText() + "\n" + newRelationshipField.getText() + "\n" + newNumberField.getText();
			
			 try (BufferedWriter writer = new BufferedWriter(new FileWriter(contactInfoFile, true)))
             {
                 writer.write(content);
                 System.out.println("Successfully wrote to the file: " + contactInfoFile.getAbsolutePath());
                 writer.close();
             }
             catch (IOException ex)
             {
                 System.err.println("An error occurred while writing to the file: " + contactInfoFile.getAbsolutePath());
                 ex.printStackTrace();
             }
		}
		
		
		// This block just checks if the three pre-set text areas are empty
		if(emergencyContactOneTextArea.getText().equals("Contact Name:\n\nRelationship to Patient:\n\nContact Phone Number: ")) {
			emergencyContactOneTextArea.setText("Contact Name: " + newNameField.getText() + "\n\nRelationship to Patient: " + newRelationshipField.getText() + "\n\nContact Phone Number: " + newNumberField.getText());
		}
		else if (emergencyContactTwoTextArea.getText().equals("Contact Name:\n\nRelationship to Patient:\n\nContact Phone Number: ")) {
			emergencyContactTwoTextArea.setText("Contact Name: " + newNameField.getText() + "\n\nRelationship to Patient: " + newRelationshipField.getText() + "\n\nContact Phone Number: " + newNumberField.getText());
		}
		else if (emergencyContactThreeTextArea.getText().equals("Contact Name:\n\nRelationship to Patient:\n\nContact Phone Number: ")) {
			emergencyContactThreeTextArea.setText("Contact Name: " + newNameField.getText() + "\n\nRelationship to Patient: " + newRelationshipField.getText() + "\n\nContact Phone Number: " + newNumberField.getText());
		}
		else
		{
			// If the three blocks aren't empty, make a new one at the end of the VBox.
			TextArea newTextArea = new TextArea();
			newTextArea.setPrefHeight(100);
			newTextArea.setPrefWidth(200);
			newTextArea.setText("Contact Name: " + newNameField.getText() + "\n\nRelationship to Patient: " + newRelationshipField.getText() + "\n\nContact Phone Number: " + newNumberField.getText());
			emergencyVBox.getChildren().add(newTextArea);
			VBox.setMargin(newTextArea, new Insets(5, 5, 0 ,5));
		}
		
		// Clear the fields that way the Cx doesn't accidently double enter one.
		newNameField.setText("");
		newRelationshipField.setText("");
		newNumberField.setText("");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		phoneLabel.setText(phoneLabel.getText() + Main.currUser.phoneNumber);
		emailLabel.setText(emailLabel.getText() + Main.currUser.email);
		nicknameLabel.setText(nicknameLabel.getText() + Main.currUser.nickName); 
		
		// Upcoming portion currently uses the old hashing method
		
				
				String dataLocation = IOHandeler.getFile(Main.currUser.userID, "PatientEmergencyContactInfo.txt");
				ArrayList<String> data = new ArrayList<String>();
				// Try-with-resources to ensure that resources are closed
				File fileExistance = new File(dataLocation);
				if (fileExistance.exists()) {
					try (BufferedReader reader = new BufferedReader(new FileReader(dataLocation)))
					{
						String line;
						// Read line by line
						while ((line = reader.readLine()) != null)
						{
							data.add(line);
						}
						reader.close();
					
						// For loop which iterates with i + 3 for every loop
					
						for(int i=0; i<data.size(); i+=3 )
						{
							// This block just checks if the three pre-set text areas are empty
							if(emergencyContactOneTextArea.getText().equals("Contact Name:\n\nRelationship to Patient:\n\nContact Phone Number: ")) {
								emergencyContactOneTextArea.setText("Contact Name: " + data.get(i) + "\n\nRelationship to Patient: " + data.get(i+1) + "\n\nContact Phone Number: " + data.get(i+2));
							}
							else if (emergencyContactTwoTextArea.getText().equals("Contact Name:\n\nRelationship to Patient:\n\nContact Phone Number: ")) {
								emergencyContactTwoTextArea.setText("Contact Name: " + data.get(i) + "\n\nRelationship to Patient: " + data.get(i+1) + "\n\nContact Phone Number: " + data.get(i+2));
							}
							else if (emergencyContactThreeTextArea.getText().equals("Contact Name:\n\nRelationship to Patient:\n\nContact Phone Number: ")) {
								emergencyContactThreeTextArea.setText("Contact Name: " + data.get(i) + "\n\nRelationship to Patient: " + data.get(i+1) + "\n\nContact Phone Number: " + data.get(i+2));
							}
							else
							{
								// If the three blocks aren't empty, make a new one at the end of the VBox.
								TextArea newTextArea = new TextArea();
								newTextArea.setPrefHeight(100);
								newTextArea.setPrefWidth(200);
								newTextArea.setText("Contact Name: " + data.get(i) + "\n\nRelationship to Patient: " + data.get(i+1) + "\n\nContact Phone Number: " + data.get(i+2));
								emergencyVBox.getChildren().add(newTextArea);
								VBox.setMargin(newTextArea, new Insets(5, 5, 0 ,5));
							}
						}
					
					}
					catch (FileNotFoundException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}	
	}
}
