//@author Ben Haras-Gummer
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class GUI implements ActionListener
{
    static JLabel userLabel;
    static JTextField userText;
    static JLabel passwordLabel;
    static JPasswordField passwordText;
    static JButton loginButton;
    static JLabel success;
    static JButton clearButton;
    static JButton receptionistButton;
    static JButton patientsButton;
    static JButton doctorButton;
    static JButton backButton;
    
    static JLabel selectRole;
    
    static JPanel selectRolePanel;
    static JPanel login;
    static JPanel panel;
    static JFrame frame;
    
    private static  Connection connect = null;
	private static PreparedStatement preparedStatement = null;
	private static  ResultSet resultSet;
	private String doctorID;
	
	//Made use of card layout so I can switch between the panels once a user selects their role
	static CardLayout cardLayout = new CardLayout();
	
	public static void main(String[] args)
	{
		init();
	}
	
	GUI()
	{
		init();
	}
	
	static private void init()
	{
		//connectDB();
		createGUI();
		initialiseCardLayout();
	    initialiseSelectRole();
	    initialiseLoginPanel();
	}
	
    static private void createGUI()
    {
        // write your code here
        frame = new JFrame();
        panel = new JPanel();
        selectRolePanel = new JPanel();
        login = new JPanel();
        
        frame.setSize(900,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);    
        frame.setResizable(false);
        panel.setLayout(cardLayout);
        frame.setVisible(true);      
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {


    }
    
    //Method that adds the (select role panel) and the (login panel) to the main container panel
    private static void initialiseCardLayout()
    {
    	panel.add(selectRolePanel, "select role");
    	panel.add(login, "login");
    	
    	//this line displays the (select role panel) as the first panel to appear on the container panel
    	cardLayout.show(panel, "select role");
    }
    
    //This method simply adds all the components on to the (selectRole panel) 
    private static void initialiseSelectRole()
    {
    	 selectRolePanel.setLayout(null);
    	 selectRole = new JLabel("Please select a role");
         selectRole.setBounds(375,150,200,25);
         selectRolePanel.add(selectRole);
         
         receptionistButton = new JButton("Receptionist");
         receptionistButton.addActionListener(new ActionListener()
         {
             @Override
             public void actionPerformed(ActionEvent e)
             {
                userLabel.setText("Receptionist ID");
                cardLayout.show(panel, "login");
             }
         });
         receptionistButton.setBounds(350,175,165,25);
         selectRolePanel.add(receptionistButton);

         patientsButton = new JButton("Patient");
         patientsButton.addActionListener(new ActionListener()
         {
             @Override
             public void actionPerformed(ActionEvent e)
             {
                 userLabel.setText("Patient ID");
                 cardLayout.show(panel, "login");
             }
         });
         patientsButton.setBounds(350,215,165,25);
         selectRolePanel.add(patientsButton);

         doctorButton = new JButton("Doctor");
         doctorButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e)
             {
                 userLabel.setText("Doctor ID");
                 cardLayout.show(panel, "login");
             }
         });
         doctorButton.setBounds(350,255,165,25);
         selectRolePanel.add(doctorButton);
         selectRolePanel.setVisible(true);
    	
    }
    
    //This method adds the components to the (login panel)  
    private static void initialiseLoginPanel()
    {
    	login.setLayout(null);
        userLabel = new JLabel("User mail");
        userLabel.setBounds(350,170,200,25);
        login.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(350,200,165,25);
        login.add(userText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(350,240,80,25);
        login.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(350,270,165,25);
        login.add(passwordText);


        loginButton = new JButton("Submit");
        loginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String user = userText.getText();
                String password = passwordText.getText();
                System.out.println(user + ", " + password);

                if(tryLogin(user, password))
                {
                   // success.setText("Login Successful");
                    //userText.setText("");
                    //passwordText.setText("");
                	try {
						//PatientsList patientView = new PatientsList(resultSet.getString("id"));
                		Dashboard dash = new Dashboard(resultSet.getString("id"));
						frame.setVisible(false);
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	
                }
                else
                {
                    success.setText("Login Failed");
                }
            }
        });
        loginButton.setBounds(750,500,80,25);
        login.add(loginButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                userText.setText("");
                passwordText.setText("");
            }
        });
        clearButton.setBounds(445,300,70,25);
        login.add(clearButton);
        
        backButton = new JButton("Back");
        backButton.setBounds(50,500,80,25);
        login.add(backButton);
        backButton.addActionListener (new ActionListener () 
        {
		    public void actionPerformed(ActionEvent e) {
		        cardLayout.show(panel, "select role");
		        success.setText(null);
		        
		    }
		});
        
        success = new JLabel("");
        success.setBounds(350,290,300,25);
        login.add(success);
    }
    
    //Executes a query in the database
    //Searches for a doctor with a matching email and password
    //Returns true if such statement is true, false otherwise
    private static Boolean tryLogin(String user, String password) {
		try {
			//Declare the SQL Connector
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Establish connection to Database
			connect = DriverManager.getConnection("jdbc:mysql://localhost/co559?user=wifif&password=wififdb");
			
			//Create Query
			preparedStatement = connect.prepareStatement("SELECT doctors.password AS password, doctors.doctorID AS id FROM doctors WHERE email = ?;");
			preparedStatement.setString(1, user);
			resultSet = preparedStatement.executeQuery();
			
			//Check if there is at least one match for the doctor's mail
			if(resultSet.next()) {
				if(resultSet.getString("password").equals(password)) {
					return true;
				}
			}
			else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return false;
    }
}

