package application;

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
import javafx.stage.Stage;

public class PatientLandingController implements Initializable
{
	private Scene scene;
	private Parent root;
	private Stage stage;
	
	@FXML
	private Label welcomeLabel;
	
	public void switchToLogin(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToVisitHistory(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("VisitationHistory.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchToMessagePortal(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("PatientMessagePortal.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchToContactInfo(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("ContactInfo.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		welcomeLabel.setText(welcomeLabel.getText() + Main.currUser.lastName);
	}
}
