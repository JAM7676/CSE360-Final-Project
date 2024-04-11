package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VisitationHistoryController implements Initializable{
	private Scene scene;
	private Parent root;
	private Stage stage;
	// First index is visit number, second index is line number
	private String visits[][] = new String[6][5];
	
	@FXML
	Button visitOne;
	
	@FXML
	Button visitTwo;
	
	@FXML
	Button visitThree;
	
	@FXML
	Button visitFour;
	
	@FXML
	Button visitFive;
	
	@FXML
	Button visitSix;
	
	@FXML
	TextField visitDescription;
	
	@FXML
	TextField doctorNotes;
	
	public void switchToMainMenu(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("PatientLanding.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void buttonOnePress(ActionEvent e) throws IOException
	{
		visitDescription.setText(visits[0][3]);
		doctorNotes.setText(visits[0][4]);
	}
	
	public void buttonTwoPress(ActionEvent e) throws IOException
	{
		visitDescription.setText(visits[1][3]);
		doctorNotes.setText(visits[1][4]);
	}
	
	public void buttonThreePress(ActionEvent e) throws IOException
	{
		visitDescription.setText(visits[2][3]);
		doctorNotes.setText(visits[2][4]);
	}
	
	public void buttonFourPress(ActionEvent e) throws IOException
	{
		visitDescription.setText(visits[3][3]);
		doctorNotes.setText(visits[3][4]);
	}
	
	public void buttonFivePress(ActionEvent e) throws IOException
	{
		visitDescription.setText(visits[4][3]);
		doctorNotes.setText(visits[4][4]);
	}
	
	public void buttonSixPress(ActionEvent e) throws IOException
	{
		visitDescription.setText(visits[5][3]);
		doctorNotes.setText(visits[5][4]);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		visitOne.setVisible(false);
		visitTwo.setVisible(false);
		visitThree.setVisible(false);
		visitFour.setVisible(false);
		visitFive.setVisible(false);
		visitSix.setVisible(false);
		
		String folderLoc = IOHandeler.getDirectory() + "\\" + Main.hashString(Main.currUser.username);
		try {
			File visitHistory = new File(folderLoc, "visitHistory.txt");
			Scanner reader = new Scanner(visitHistory);
			int i = 0;
			while(reader.hasNextLine()) {
				visits[i][0] = reader.nextLine();
				visits[i][1] = reader.nextLine();
				visits[i][2] = reader.nextLine();
				visits[i][3] = reader.nextLine();
				visits[i][4] = reader.nextLine();
				i++;
			}
			for (int j = 0; j < i; j++) {
				switch(j) {
				case 0:
					visitOne.setVisible(true);
					visitOne.setText("Doctor: " + visits[j][0] + 
							"\nDate: " + visits[j][1] + 
							"\nReason: " + visits[j][2]);
					break;
				case 1:
					visitTwo.setVisible(true);
					visitTwo.setText("Doctor: " + visits[j][0] + 
							"\nDate: " + visits[j][1] + 
							"\nReason: " + visits[j][2]);
					break;

				case 2:
					visitThree.setVisible(true);
					visitThree.setText("Doctor: " + visits[j][0] + 
							"\nDate: " + visits[j][1] + 
							"\nReason: " + visits[j][2]);
					break;

				case 3:
					visitFour.setVisible(true);
					visitFour.setText("Doctor: " + visits[j][0] + 
							"\nDate: " + visits[j][1] + 
							"\nReason: " + visits[j][2]);
					break;

				case 4:
					visitFive.setVisible(true);
					visitFive.setText("Doctor: " + visits[j][0] + 
							"\nDate: " + visits[j][1] + 
							"\nReason: " + visits[j][2]);
					break;

				case 5:
					visitSix.setVisible(true);
					visitSix.setText("Doctor: " + visits[j][0] + 
							"\nDate: " + visits[j][1] + 
							"\nReason: " + visits[j][2]);
					break;
				default:
					break;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist");
			e.printStackTrace();
		}
	}
	
}
