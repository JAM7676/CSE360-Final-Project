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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	private Scene scene;
	private Parent root;
	private Stage stage;
	
	@FXML
	private TextField usernameBox;
	
	@FXML
	private TextField passwordBox;
	
	@FXML
	private Label errorLabel;
	
	@FXML
	private Button testButton;
	
	public void attemptLogin(ActionEvent e) throws IOException
	{
		String UNIn = usernameBox.getText();
		String PWIn = passwordBox.getText();
		
		if(UNIn.equals("")|| PWIn.equals(""))
		{
			errorLabel.setText("Username or Password empty");
			return;
		}
		else
		{
//			String temp = UNIn + PWIn;
//			String userID = Main.hashString(temp);
			
			String userID = Main.hashString(UNIn);
			
			String dirPath = Main.folderLoc + userID;
			File dir = new File(dirPath);
			
			if(dir.exists())
			{
	            System.out.println("Logging in...");
	            Main.currUser = new User(userID);
//	            Main.setUser(new User(userID));
	            
	            switch(Main.currUser.accountType)
	    		{
	    		case "Patient":
	    		{
	    			switchToPatientMainMenu(e);
	    			break;
	    		}
	    		case "Doctor":
	    		{
	    			switchToDoctorMainMenu(e);
	    			break;
	    		}
	    		case "Nurse":
	    		{
	    			switchToNurseMainMenu(e);
	    			break;
	    		}
	    		default:
	    			System.out.println("Account type cannot be resolved");
	    		}
	        }
			else
	        {
				errorLabel.setText("Incorrect Username or Password");
				return;
	        }
		}
	}
	
	public void switchToCreateAccount(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToPatientMainMenu(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("PatientLanding.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToDoctorMainMenu(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("DoctorLanding.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToNurseMainMenu(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("NurseLanding.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	
	// ----------------------------------------------------------------------------
	public void TestUser(ActionEvent e) throws IOException
	{
//		String temp = "TestUsername";
		String userID = Main.hashString("TestUsername");
		
		String newDirPath = Main.folderLoc + userID;
		File newDir = new File(newDirPath);
		
		boolean accountMade = newDir.mkdir();

        if(accountMade)
        {
        	File file = new File(newDirPath, "PatientAccountInfo.txt");
        	
        	try {
                // Create the file, and return true if the file was successfully created
                boolean fileCreated = file.createNewFile();
                
                if (fileCreated)
                {
                    System.out.println("File created: " + file.getAbsolutePath());
                    
                    String content = "TestFirstName" + "\n" + "TestLastName" + "\n" + "Male" + "\n" + "2024-04-05" + "\n" + "1234567890" + "\n" + "Test@test.com" + "\n" + "TestUsername" + "\n" + "TestPassword" + "\n" + "Patient" + "\n" + "TestIDValue";
                    
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
                    {
                        writer.write(content);
                        System.out.println("Successfully wrote to the file: " + file.getAbsolutePath());
                        
                    }
                    catch (IOException ex)
                    {
                        System.err.println("An error occurred while writing to the file: " + file.getAbsolutePath());
                        ex.printStackTrace();
                    }
                }
                else
                {
                    System.out.println("File already exists: " + file.getAbsolutePath());
                }
            }
        	catch (IOException ex)
        	{
                System.err.println("Cannot create the file: " + file.getAbsolutePath());
                ex.printStackTrace();
            }
        	
            System.out.println("Account was created successfully.");
        }
        else
        {
            System.out.println("Failed to create Account.");
        }
        
//		switchToLogin(e); THIS IS WHERE THE FILES ARE FINISHED CREATING
		
		String dirPath = Main.folderLoc + userID;
		File dir = new File(dirPath);
		
		if(dir.exists())
		{
            System.out.println("Logging in...");
            Main.currUser = new User(userID);
            
            switch(Main.currUser.accountType)
    		{
    		case "Patient":
    		{
    			switchToPatientMainMenu(e);
    			break;
    		}
    		case "Doctor":
    		{
    			switchToDoctorMainMenu(e);
    			break;
    		}
    		case "Nurse":
    		{
    			switchToNurseMainMenu(e);
    			break;
    		}
    		default:
    			System.out.println("Account type cannot be resolved");
    		}
        }
	}
}
