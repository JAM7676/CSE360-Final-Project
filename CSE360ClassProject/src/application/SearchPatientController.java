package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchPatientController implements Initializable
{
	private Scene scene;
	private Parent root;
	private Stage stage;
	
	@FXML
	private TextField UsernameInBox;
	
	@FXML
	private TextArea AllergyBox;
	
	@FXML
	private TextArea MedicationBox;
	
	@FXML
	private TextArea BasicInfoBox;
	
	@FXML
	private TextArea HealthConcernBox;
	
	public void switchToMainMenu(ActionEvent e) throws IOException
	{
		if(Main.currUser.accountType.equals("Nurse"))
			root = FXMLLoader.load(getClass().getResource("NurseLanding.fxml"));
		else
			root = FXMLLoader.load(getClass().getResource("DoctorLanding.fxml"));
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void searchPatientRecord() throws FileNotFoundException, IOException
	{
		if(UsernameInBox.getText().equals(""))
			return;
		else
		{
			String folderLoc = IOHandeler.getDirectory();
			
			String userID = Main.hashString(UsernameInBox.getText());
			
			String newDirPath = folderLoc + userID;
			File newDir = new File(newDirPath);
			
			if(newDir.exists())
			{
	            System.out.println("Account exists.");
	            
	            String BasicInfoString;
	            String HealthConcernsString;
	            String AllergiesString;
	            String MedicationString;
	            
	            
	          // The following extracts data from the PatientAccountInfo file 
	          //-------------------------------------------------------------------------------
	            File file = new File(newDirPath, "PatientAccountInfo.txt");
	            String[] data = new String[11];
	    		// Try-with-resources to ensure that resources are closed
	    		try (BufferedReader reader = new BufferedReader(new FileReader(file)))
	    		{
	    			String line;
	    			// Read line by line
	    			int idx = 0;
	    			while ((line = reader.readLine()) != null)
	    			{
	    				data[idx] = line;
	    				idx++;
	    			}
	    			
	    			BasicInfoString = "First Name: " + data[0] + "\nLast Name: " + data[1] + "\nDOB: " + data[3] + "\nSex: " + data[2] + "\nInsuranceID: " + data[9];
	    			BasicInfoBox.setText(BasicInfoString);
	    		}
	    		
	    		// The next portion is for getting the contents of the patientIntakeInfo file
	    		//-------------------------------------------------------------------------------
	    		file = new File(newDirPath, "PatientIntakeInfo.txt");
	    		
	    		if(file.exists())
	    		{
	    			data = new String[6];
		    		// Try-with-resources to ensure that resources are closed
		    		try (BufferedReader reader = new BufferedReader(new FileReader(file)))
		    		{
		    			String line;
		    			// Read line by line
		    			int idx = 0;
		    			while ((line = reader.readLine()) != null)
		    			{
		    				data[idx] = line;
		    				idx++;
		    			}
		    			HealthConcernsString = "Health Concerns: \n" + data[3];
			            AllergiesString = "Known Allergies: \n" + data[5];
			            MedicationString = "Medications on file: \n" + data[4];
			            
			            AllergyBox.setText(AllergiesString);
			            MedicationBox.setText(MedicationString);
			            HealthConcernBox.setText(HealthConcernsString);
		    			
		    		}
	    		}
	    		else
	    		{
	    			AllergyBox.setText("Known Allergies: \nNot On File");
		            MedicationBox.setText("Known Allergies: \nNot On File");
		            HealthConcernBox.setText("Health Concerns: \nNot On File");
	    		}
	        }
			else
	        {
	            System.out.println("User does not exist.");
	        }
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		BasicInfoBox.setEditable(false);
		AllergyBox.setEditable(false);
		HealthConcernBox.setEditable(false);
		MedicationBox.setEditable(false);
	}
}
