import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Dashboard implements ActionListener
{
	private  Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private  ResultSet resultSet;
	private  ResultSet doctorInfo;
	private  DefaultTableModel tableModel = new DefaultTableModel();
	private String doctorID;
	private JButton viewPatients;
	private JButton viewBookings;
	private JButton assignDoctor;
	private JButton viewDetsAndPres;
	private JButton logout;
	private JLabel reminder;


	
	Dashboard(String doctorName)
	{
		this.doctorID = doctorName;
		init();
	}
	
	private void init()
	{
		connectDB();
		createGUI();
		
	}
	
	private void createGUI() {
		try {
		//create the JFrame
			JFrame frame = new JFrame("Dashboard");
			JPanel panel = new JPanel();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(900, 600);
	        frame.add(panel);
	        frame.setResizable(false);
	        panel.setLayout(null);
	        

	        
	        
	        reminder = new JLabel ("Your next appointment is at 14:00 with Ben Haras-Gummer");
	        reminder.setBounds(275, 50, 400, 25);
	        panel.add(reminder);
	        
	        viewPatients = new JButton("View your patients");
	        viewPatients.setBounds(325, 125, 200, 25);
	        panel.add(viewPatients);
	        viewPatients.addActionListener(new ActionListener()
	        {
	        	@Override
	            public void actionPerformed(ActionEvent e)
	            {
					PatientsList patientView = new PatientsList(doctorID);
	        		frame.setVisible(false);
	            }
	        }
	        );
	        
	        viewBookings = new JButton("View your bookings");
	        viewBookings.setBounds(325, 200, 200, 25);
	        panel.add(viewBookings);
	        viewBookings.addActionListener(new ActionListener()
	        {
	        	@Override
	            public void actionPerformed(ActionEvent e)
	            {
					Bookings bookingView = new Bookings(doctorID);
	        		frame.setVisible(false);
	            }
	        }
	        );
	        
	        assignDoctor = new JButton("Assign a new Doctor");
	        assignDoctor.setBounds(325,275, 200, 25);
	        panel.add(assignDoctor);
	        assignDoctor.addActionListener(new ActionListener()
	        		{
			        	@Override
			            public void actionPerformed(ActionEvent e)
			            {
			        		ChangePatient changePatientView = new ChangePatient(doctorID);
			        		frame.setVisible(false);
			            }
	        		}
	        		);
				
	  		viewDetsAndPres = new JButton("View Past Visit Details");
	        viewDetsAndPres.setBounds(325,350, 200, 25);
	        panel.add(viewDetsAndPres);
	        viewDetsAndPres.addActionListener(new ActionListener()
    		{
	        	@Override
	            public void actionPerformed(ActionEvent e)
	            {
	        		ViewVisitDetails checkdetails = new ViewVisitDetails(doctorID);
	        		frame.setVisible(false);
	            }
    		}
    		);
	        
	        JButton enterVisitDetails = new JButton("Enter Visit Details");
	        enterVisitDetails.setBounds(325,425, 200, 25);
	        panel.add(enterVisitDetails);
	        enterVisitDetails.addActionListener(new ActionListener()
    		{
	        	@Override
	            public void actionPerformed(ActionEvent e)
	            {
	        		EnterVisitDetails visitDetails = new EnterVisitDetails(doctorID);
	        		frame.setVisible(false);
	            }
    		}
    		);
	        
	        JButton editVisitDetails = new JButton("Edit Visit Details");
	        editVisitDetails.setBounds(325,500, 200, 25);
	        panel.add(editVisitDetails);
	        editVisitDetails.addActionListener(new ActionListener()
    		{
	        	@Override
	            public void actionPerformed(ActionEvent e)
	            {
	        		EditVisitDetails visitDetails = new EditVisitDetails(doctorID);
	        		frame.setVisible(false);
	            }
    		}
    		);
	        
	        logout = new JButton("Logout");
	        logout.setBounds(750, 500, 80, 25);
	        panel.add(logout);
	        logout.addActionListener(new ActionListener()
	        {
	        	@Override
	            public void actionPerformed(ActionEvent e)
	            {
	        		GUI gui = new GUI();
	        		frame.setVisible(false);
	            }
	        }
	        );
	        
		    frame.setVisible(true);
		}
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void connectDB() {
		try {
			//Declare the SQL Connector
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Establish connection to Database
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/co559?user=wifif&password=wififdb");
			
			//Create Query for patients list
			//statement = connection.createStatement();
			//resultSet = statement.executeQuery("SELECT * FROM patients WHERE doctor = ?");
			preparedStatement = connect.prepareStatement("SELECT * FROM patients WHERE doctor = ?");
			preparedStatement.setString(1, doctorID);
			resultSet = preparedStatement.executeQuery();
			
			//create query for doctor's info
			preparedStatement = connect.prepareStatement("SELECT * FROM doctors WHERE doctorID = ?");
			preparedStatement.setString(1, doctorID);
			doctorInfo = preparedStatement.executeQuery();
			doctorInfo.next(); //This 
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Return the doctor ID
	public String getDoctorID() {
		return doctorID;
	}
	
	

//	public PreparedStatement getPreparedStatement() {
		public void actionPerformed(ActionEvent e)
	    {


	    }
}

