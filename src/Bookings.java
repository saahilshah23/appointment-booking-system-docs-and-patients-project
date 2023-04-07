import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Bookings {
    
	//private  Connection connection;
	private static  Connection connect = null;
	private static PreparedStatement preparedStatement = null;
	//private  Statement statement;
	private static ResultSet resultSet;
	private static ResultSet doctorInfo;
	private static DefaultTableModel tableModel = new DefaultTableModel();
	private static String doctorID;
	
	
	//Class constructor
	Bookings(String doctorName) {
		this.doctorID = doctorName;
		init();
	}
	
	static private void init() {
		connectDB(); //get the patients list
		createGUI();
		fillTable(resultSet);
	}
	
	static private void createGUI() {
		try {
		//create the JFrame
			JFrame frame = new JFrame("Bookings");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(900, 600);
	        
	        	//Create the label at top
	        	JLabel lblTitle = new JLabel("View Bookings for " + doctorInfo.getString("name"), SwingConstants.CENTER);
	        	lblTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
	        
		        //Table at the
		        //DefaultTableModel tableModel = new DefaultTableModel();
		        JTable table = new JTable(tableModel);
		        //tableModel.addColumn("Doctor");
		        tableModel.addColumn("Date & Time");
		        tableModel.addColumn("Name");
		        tableModel.addColumn("Last Name");
		        
		        
				 JButton back = new JButton("Back");
		        back.addActionListener (new ActionListener () {
				    public void actionPerformed(ActionEvent e) {
				        Dashboard dashboard = new Dashboard(doctorID);
				        frame.setVisible(false);
				    }
				});
		        
		        //Add rows to table
		        
		        //Create the ScrollPane
		        JScrollPane scroll = new JScrollPane(table);
		        
		        //Add components to the frame
		        frame.getContentPane().add(BorderLayout.CENTER, scroll);
		        frame.getContentPane().add(BorderLayout.NORTH, lblTitle);
				frame.getContentPane().add(BorderLayout.SOUTH, back);
		        frame.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	//make a query to the database to return the patients list
	static private void connectDB() {
		try {
			//Declare the SQL Connector
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Establish connection to Database
			connect = DriverManager.getConnection("jdbc:mysql://localhost/co559?user=wifif&password=wififdb");
			
			//Create Query for patients list
			//statement = connection.createStatement();
			//resultSet = statement.executeQuery("SELECT * FROM patients WHERE doctor = ?");
			preparedStatement = connect.prepareStatement("SELECT appointments.date, patients.name, patients.lastName FROM appointments JOIN patients WHERE appointments.patient = patients.patientID AND appointments.doctor = ? "); 
			preparedStatement.setString(1, doctorID);//
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

	//Fill the table with the result from the query with the doctor's patients
	//@param results The ResultSet originating from the query
	static private void fillTable(ResultSet results) {
		try {
			while(results.next()) {
				//Print to console
				//System.out.println(resultSet.getString("doc") + " - " + resultSet.getString("patient") + " - " + resultSet.getString("Date"));
				
				//Add to table
				tableModel.addRow(new Object[] {resultSet.getString("date"), resultSet.getString("name"),resultSet.getString("lastName") });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Return the doctor ID
	public String getDoctorID() {
		return doctorID;
	}
	
	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}
	
	public ResultSet getDoctorResultSet() {
		return doctorInfo;
	}
	
	public ResultSet getPatientResultSet() {
		return resultSet;
	}





}

