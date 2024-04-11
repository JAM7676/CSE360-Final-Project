package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class User
{
	public String firstName;
	public String lastName;
	public String sex;
	public String DOB;
	public String phoneNumber;
	public String email;
	public String username;
	public String insuranceNum;
	public String userID;
	public String nickName;
	public String accountType;
	
	public User(String userID) throws FileNotFoundException, IOException
	{
		String dataLocation = IOHandeler.getFile(userID, "PatientAccountInfo.txt");
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
		}
		
		this.firstName = data[0];
		this.lastName = data[1];
		this.sex = data[2];
		this.DOB = data[3];
		this.phoneNumber = data[4];
		this.email = data[5];
		this.username = data[6];
//		this.username = data[7]; PASSWORD
		this.accountType = data[8];
		this.insuranceNum = data[9];
		this.userID = userID;
		this.nickName = data[10];
	}
}
