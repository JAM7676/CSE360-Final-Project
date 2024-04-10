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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContactInfoController implements Initializable {
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
	
	public void switchToMainMenu(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("PatientLanding.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void showEditingArea () {
		editInfoVBox.setDisable(false);
	}
	
	public void hideEditingArea () {
		editInfoVBox.setDisable(true);
	}
	
	public void saveToFile () throws FileNotFoundException, IOException {
		Main.currUser.phoneNumber = newPhoneTextField.getText();
		Main.currUser.email = newEmailTextField.getText();
		Main.currUser.nickName = newNameTextField.getText();
		
		// Upcoming portion currently uses the old hashing method
		
		String dirPath = IOHandeler.getDirectory() + Main.currUser.userID;
		File fileOutput = new File(dirPath, "PatientAccountInfo.txt");
		
		String dataLocation = IOHandeler.getFile(Main.currUser.userID);
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
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		phoneLabel.setText(phoneLabel.getText() + Main.currUser.phoneNumber);
		emailLabel.setText(emailLabel.getText() + Main.currUser.email);
		nicknameLabel.setText(nicknameLabel.getText() + Main.currUser.nickName); 
		// Need to add a way to edit the nickname either at account creation or here.

	}
}
