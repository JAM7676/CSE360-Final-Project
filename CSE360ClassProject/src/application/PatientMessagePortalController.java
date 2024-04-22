package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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

public class PatientMessagePortalController implements Initializable
{
	private Scene scene;
	private Parent root;
	private Stage stage;
	private int messageNum;
	private String messageNames[] = new String[5];
	private File messageFiles[] = new File[5];
	// First index is index of message, second is index of line (To: From: Msg:)
	String messages[][] = new String[5][3];
	
	@FXML
	private TextField messageRecipient;
	
	@FXML
	private TextField newMessageField;
	
	@FXML
	private TextField messageReply;
	
	@FXML
	private Button messageOne;
	
	@FXML
	private Button messageTwo;
	
	@FXML
	private Button messageThree;
	
	@FXML
	private Button messageFour;
	
	@FXML
	private Button messageFive;
	
	@FXML
	private TextField messageView;
	
	public void switchToLandingPage(ActionEvent e) throws IOException
	{
		// Needs to check which type of user this is and send them to their
		// respective landing page
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
		/*
		root = FXMLLoader.load(getClass().getResource("PatientLanding.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		*/
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
	
	public void newMessageSend(ActionEvent e) throws IOException 
	{
		/* 1. Check for valid recipient by seeing if their folder exists
		 * 2. Generate a text file with message using PatientName + Doctor/NurseName hash for file name
		 * in the recipients and senders folder
		 * 3. Update both the recipients and senders MessageInfo.txt files
		 */
		String folderLoc = IOHandeler.getDirectory();
		String recipient = messageRecipient.getText();
		String recDirPath = folderLoc + Main.hashString(recipient);
		String sendDirPath = folderLoc + Main.hashString(Main.currUser.username);
		File recDir = new File(recDirPath);
		File sendDir = new File(sendDirPath);
		File recMessageInfo = new File(recDirPath, "MessageInfo.txt");
		File sendMessageInfo = new File(sendDirPath, "MessageInfo.txt");
		if(!recDir.exists())
		{
			System.out.println("Invalid recipient\n");
			return;
		}
		if (!sendDir.exists())
		{
			System.out.println("Sender does not exist\n");
			return;
		}
		String fileName = Main.hashString(Main.currUser.username + recipient) + ".txt";
		File recMessage = new File(recDirPath, fileName);
		File sendMessage = new File(sendDirPath, fileName);
		String content = Main.currUser.username + "\n" + recipient +
				"\nContent: " + newMessageField.getText();
		
		// Create the message file and write to it in the recipient folder
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(recMessage)))
		{
		    writer.write(content);
		    System.out.println("Successfully wrote to the file: " + recMessage.getAbsolutePath());
		}
		catch (IOException ex)
		{
		    System.err.println("An error occurred while writing to the file: " + recMessage.getAbsolutePath());
		    ex.printStackTrace();
		}
		
		// Create the message file and write to it in the sender folder
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(sendMessage)))
		{
		    writer.write(content);
		    System.out.println("Successfully wrote to the file: " + sendMessage.getAbsolutePath());
		}
		catch (IOException ex)
		{
		    System.err.println("An error occurred while writing to the file: " + sendMessage.getAbsolutePath());
		    ex.printStackTrace();
		}
		
		// Append the name of the message.txt file to the senders messageInfo.txt
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(sendMessageInfo, true)))
		{
		    writer.write(fileName + "\n");
		    System.out.println("Successfully wrote to the file: " + sendMessageInfo.getAbsolutePath());
		}
		catch (IOException ex)
		{
		    System.err.println("An error occurred while writing to the file: " + sendMessageInfo.getAbsolutePath());
		    ex.printStackTrace();
		}
		
		// Append the name of the message.txt file to the recipients messageInfo.txt
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(recMessageInfo, true)))
		{
		    writer.write(fileName + "\n");
		    System.out.println("Successfully wrote to the file: " + recMessageInfo.getAbsolutePath());
		}
		catch (IOException ex)
		{
		    System.err.println("An error occurred while writing to the file: " + recMessageInfo.getAbsolutePath());
		    ex.printStackTrace();
		}
		
	}
	
	public void messageReply(ActionEvent e) throws IOException
	{
		String folderLoc = IOHandeler.getDirectory() + "\\" + Main.hashString(Main.currUser.username);
		File messageFile = new File(folderLoc, messageNames[messageNum - 1]);
		String message = messages[messageNum - 1][1] + "\n" + messages[messageNum - 1][0] + "\n" + messages[messageNum - 1][2] + "    Reply: " + messageReply.getText();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(messageFile)))
		{
		    writer.write(message);
		    System.out.println("Successfully wrote to the file: " + messageFile.getAbsolutePath());
		}
		catch (IOException ex)
		{
		    System.err.println("An error occurred while writing to the file: " + messageFile.getAbsolutePath());
		    ex.printStackTrace();
		}
		
		folderLoc = IOHandeler.getDirectory() + "\\" + Main.hashString(messages[messageNum - 1][0]);
		messageFile = new File(folderLoc, messageNames[messageNum - 1]);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(messageFile)))
		{
		    writer.write(message);
		    System.out.println("Successfully wrote to the file: " + messageFile.getAbsolutePath());
		}
		catch (IOException ex)
		{
		    System.err.println("An error occurred while writing to the file: " + messageFile.getAbsolutePath());
		    ex.printStackTrace();
		}
		
	}
	
	public void messageOne(ActionEvent e) throws IOException
	{
		System.out.println("Message One Pressed\n");
		messageNum = 1;
		messageView.setText(messages[0][2]);
	}
	
	public void messageTwo(ActionEvent e) throws IOException
	{
		System.out.println("Message Two Pressed\n");
		messageNum = 2;
		messageView.setText(messages[1][2]);
	}

	public void messageThree(ActionEvent e) throws IOException
	{
		System.out.println("Message Three Pressed\n");
		messageNum = 3;
		messageView.setText(messages[2][2]);
	}

	public void messageFour(ActionEvent e) throws IOException
	{
		System.out.println("Message Four Pressed\n");
		messageNum = 4;
		messageView.setText(messages[3][2]);
	}

	public void messageFive(ActionEvent e) throws IOException
	{
		System.out.println("Message Five Pressed\n");
		messageNum = 5;
		messageView.setText(messages[4][2]);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		
		messageOne.setVisible(false);
		messageTwo.setVisible(false);
		messageThree.setVisible(false);
		messageFour.setVisible(false);
		messageFive.setVisible(false);
		
		String folderLoc = IOHandeler.getDirectory() + "\\" + Main.hashString(Main.currUser.username);
		try
		{
			File messageInfo = new File(folderLoc, "MessageInfo.txt");
			Scanner reader = new Scanner(messageInfo);
			int i = 0;
			while(reader.hasNextLine())
			{
				messageNames[i] = reader.nextLine();
				System.out.println("Message " + i + " name: " + messageNames[i]);
				i++;
			}
			for (int j = 0; j < i; j++)
			{
				messageFiles[j] = new File(folderLoc, messageNames[j]);
				reader = new Scanner(messageFiles[j]);
				messages[j][0] = reader.nextLine();
				messages[j][1] = reader.nextLine();
				messages[j][2] = "";
				while(reader.hasNextLine())
				{
					messages[j][2] = messages[j][2] + "\n" + reader.nextLine();
				}
				switch (j)
				{
				case 0:
					messageOne.setText("From: " + messages[j][0] + "\nTo: " + messages[j][1]);
					messageOne.setVisible(true);
					break;
				case 1:
					messageTwo.setText("From: " + messages[j][0] + "\nTo: " + messages[j][1]);
					messageTwo.setVisible(true);
					break;
				case 2:
					messageThree.setText("From: " + messages[j][0] + "\nTo: " + messages[j][1]);
					messageThree.setVisible(true);
					break;
				case 3:
					messageFour.setText("From: " + messages[j][0] + "\nTo: " + messages[j][1]);
					messageFour.setVisible(true);
					break;
				case 4:
					messageFive.setText("From: " + messages[j][0] + "\nTo: " + messages[j][1]);
					messageFive.setVisible(true);
					break;
				default:
					System.out.println("You shouldn't be here\n");
					break;
				}
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File does not exist");
			e.printStackTrace();
		}
		
		
	}
}
