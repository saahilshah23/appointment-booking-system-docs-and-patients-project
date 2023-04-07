//A view of a doctor's patients
//@authors Fernando Ramrez & Devika Vasisht

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PatientsList {
	private  Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private  ResultSet resultSet;
	private  ResultSet doctorInfo;
	private  DefaultTableModel tableModel = new DefaultTableModel();
	private String doctorID;
	
	
	//Class constructor
	PatientsList(String doctorID) {
		this.doctorID = doctorID;
		init();
	}
	
	private void init() {
		connectDB(); //get the patients list
		createGUI();
		fillTable(resultSet);
	}
	
	private void createGUI() {
		try {
		//create the JFrame
			JFrame frame = new JFrame("Patients List");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(900, 600);
	        
	        	//Create the label at top
	        	JLabel lblTitle = new JLabel("View Patients for " + doctorInfo.getString("name"), SwingConstants.CENTER);
	        	lblTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
	        
		        //Table at the
		        //DefaultTableModel tableModel = new DefaultTableModel();
		        JTable table = new JTable(tableModel);
		        //tableModel.addColumn("Doctor");
		        tableModel.addColumn("Name");
		        tableModel.addColumn("Last Name");
		        tableModel.addColumn("Age");
		        tableModel.addColumn("Mail");
		        tableModel.addColumn("Phone Number");

		        
		        //Back Button
		        JButton back = new JButton("Back");
		        back.addActionListener (new ActionListener () {
				    public void actionPerformed(ActionEvent e) {
				        Dashboard dashboard = new Dashboard(doctorID);
				        frame.setVisible(false);
				    }
				});
		        
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
	private void connectDB() {
		try {
			//Declare the SQL Connector
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Establish connection to Database
			connect = DriverManager.getConnection("jdbc:mysql://localhost/co559?user=wifif&password=wififdb");
			
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

	//Fill the table with the result from the query with the doctor's patients
	//@param results The ResultSet originating from the query
	private void fillTable(ResultSet results) {
		try {
			while(results.next()) {
				//Print to console
				//System.out.println(resultSet.getString("doc") + " - " + resultSet.getString("patient") + " - " + resultSet.getString("Date"));
				
				//Add to table
				tableModel.addRow(new Object[] {resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getString("age"), resultSet.getString("email"), resultSet.getString("phoneNumber")});
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
