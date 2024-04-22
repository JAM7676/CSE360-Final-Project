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

public class LoginController
{
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
//		System.out.println(Main.queue);
		
		String UNIn = usernameBox.getText();
		String PWIn = passwordBox.getText();
		
		if(UNIn.equals("") || PWIn.equals(""))
		{
			errorLabel.setText("Username or Password empty");
			return;
		}
		else
		{
//			String temp = UNIn + PWIn;
//			String userID = Main.hashString(temp);
			
			String userID = Main.hashString(UNIn);
			
			String dirPath = IOHandeler.getDirectory() + userID;
			File dir = new File(dirPath);
			
			if(dir.exists())
			{
				Main.currUser = new User(userID);
				if(!Main.currUser.getPass().equals(PWIn))
				{
					Main.currUser = null;
					errorLabel.setText("Incorrect Username or Password");
					return;
				}
	            System.out.println("Logging in...");
	            
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
}
