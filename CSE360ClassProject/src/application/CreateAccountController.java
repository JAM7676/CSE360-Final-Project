package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateAccountController implements Initializable
{
	private Scene scene;
	private Parent root;
	private Stage stage;
	
	@FXML
	private TextField firstNameBox;
	
	@FXML
	private TextField lastNameBox;
	
	@FXML
	private ChoiceBox<String> sexDropDown;
	private String[] sexes = {"Male", "Female"};
	
	@FXML
	private DatePicker DOBSelector;
	
	@FXML
	private TextField phoneNumberBox;
	
	@FXML
	private TextField emailBox;
	
	@FXML
	private TextField usernameBox;
	
	@FXML
	private TextField passwordBox;
	
	@FXML
	private ChoiceBox<String> accountTypeDropDown;
	private String[] accountTypes = {"Patient", "Doctor", "Nurse"};
	
	@FXML
	private TextField insIDBox;
	
	@FXML
	private Label errorLabel;
	
	public void switchToLogin(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	public void createAccount(ActionEvent e) throws IOException
	{
		if(firstNameBox.getText().equals("") || lastNameBox.getText().equals("") || sexDropDown.getValue() == null || DOBSelector.getValue().equals("") || phoneNumberBox.getText().equals("") || emailBox.getText().equals("") || usernameBox.getText().equals("") || passwordBox.getText().equals("") || accountTypeDropDown.getValue() == null || insIDBox.getText().equals(""))
		{
			errorLabel.setText("One or more Fields are empty");
			return;
		}
		else
		{
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
			String passwordRegex = "^(?=(?:[^0-9]*[0-9]){4})[a-zA-Z0-9]{8,}$";
			
			Pattern emailPattern = Pattern.compile(emailRegex);
			Matcher emailMatcher = emailPattern.matcher(emailBox.getText());
			
			Pattern passwordPattern = Pattern.compile(passwordRegex);
			Matcher passwordMatcher = passwordPattern.matcher(passwordBox.getText());
			
			if (!passwordMatcher.matches())
			{
	            errorLabel.setText("Invalid Password");
	            return;
	        }
			
			if (!emailMatcher.matches())
			{
	            errorLabel.setText("Invalid Email");
	            return;
	        }
			
			
			
			
			String folderLoc = IOHandeler.getDirectory();
			
			String userID = Main.hashString(usernameBox.getText());
			
			String newDirPath = folderLoc + userID;
			File newDir = new File(newDirPath);
			
			if(newDir.exists())
			{
	            System.out.println("Username already exists.");
	        }
			else
	        {
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
	                        
	                        String content = firstNameBox.getText() + "\n" + lastNameBox.getText() + "\n" + sexDropDown.getValue() + "\n" + DOBSelector.getValue() + "\n" + phoneNumberBox.getText() + "\n" + emailBox.getText() + "\n" + usernameBox.getText() + "\n" + passwordBox.getText() + "\n" + accountTypeDropDown.getValue() + "\n" + insIDBox.getText() + "\n" + firstNameBox.getText();
	                        
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
	        }
			
			switchToLogin(e);
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		sexDropDown.getItems().addAll(sexes);
		accountTypeDropDown.getItems().addAll(accountTypes);
	}
}
