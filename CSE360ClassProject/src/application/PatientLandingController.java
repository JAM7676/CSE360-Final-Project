package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PatientLandingController {
	private Scene scene;
	private Parent root;
	private Stage stage;
	
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
}
