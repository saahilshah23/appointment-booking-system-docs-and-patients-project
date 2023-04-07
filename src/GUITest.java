import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GUITest 
{
	JTextField username;
	JTextField password;
	
	@BeforeEach
	public void setUp() throws Exception 
	{
		username =  new JTextField("bh330@kent.ac.uk");	
		password =  new JTextField("bh330"); 
	}



	@Test
	@DisplayName("Check username and password")
	//Checks the username and password is valid
	public void testDoctorID() 
	{
		username = GUI.userText;
		password = GUI.passwordText;
		assertEquals(username,GUI.userText );
		assertEquals(password,GUI.passwordText);
		
		
	}




}
