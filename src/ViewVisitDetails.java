//See the patients visit details and prescriptions
//@author Fernando Ramrez

import javax.swing.*;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class ViewVisitDetails {
	JFrame frame;
	private JComboBox<String> patientDropDown;
	private JTextArea details;
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet patientSet;
	private HashMap<String, String> patientDescription; //Store a description about the patient related to what is displayed in the dropdown
	private String doctorID; //Doctor's ID of the current user
	
	/**
	 * Class Constructor
	 */
	public ViewVisitDetails (String doctorID) {
		this.doctorID = doctorID;
		patientDescription = new HashMap<String, String>();
		guiInit();
		connectDB();
		fillDropDowns();
	}
	
	/**
	 * Creates the GUI and its components
	 */
	private void guiInit() {
		//Create the JFrame
		frame = new JFrame("Visit Details and Prescriptions");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 400);
		frame.setResizable(false);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.add(panel);
		
		//Initialise the items
		patientDropDown = new JComboBox<String>();
		patientDropDown.setBounds(100,250,200,25);
		patientDropDown.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        updateTextArea((String)patientDropDown.getSelectedItem());
		    }
		});
		
		JLabel patientLabel = new JLabel("Patient");
		patientLabel.setBounds(100, 225, 200, 25);
		
		details = new JTextArea();
		details.setText("Details");
		details.setEditable(false);
		//details.setBounds(50, 25, 625, 150);
		details.setLineWrap(true);
		details.setVisible(true);
		
		JScrollPane scroll = new JScrollPane (details);
		scroll.setBounds(50, 25, 625, 200);
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JButton back = new JButton("Back");
		back.setBounds(525, 315, 100, 25);
        back.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        Dashboard dashboard = new Dashboard(doctorID);
		        frame.setVisible(false);
		    }
		});
		
		//Add items to the frame
		panel.add(patientLabel);
		panel.add(patientDropDown);
		panel.add(scroll);
		panel.add(back);
		
		//Set the frame visible
		frame.setVisible(true);
	}
	
	//make a query to the database to return the patients list
	private void connectDB() {
		try {
			//Declare the SQL Connector
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Establish connection to Database
			connect = DriverManager.getConnection("jdbc:mysql://localhost/co559?user=wifif&password=wififdb");
			
			//Create Query for patients list
			preparedStatement = connect.prepareStatement("SELECT appointments.date, appointments.symptoms, appointments.prescriptions, appointments.comments, patients.name AS pName, patients.lastName AS pLastName, doctors.name AS dName, doctors.lastName AS dLastName, patients.patientID AS pID FROM appointments JOIN patients, doctors WHERE appointments.patient = patients.patientID AND appointments.doctor = doctors.doctorID AND patients.doctor = ? ORDER BY date;");
			preparedStatement.setString(1, doctorID);
			patientSet = preparedStatement.executeQuery();	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fills the GUI dropdowns with information retrieved from the database
	 */
	private void fillDropDowns() {
		ArrayList<String> pList = new ArrayList<>();
		try {
			while(patientSet.next()) {	
				String name = patientSet.getString("pID") + ". " + patientSet.getString("pName") + " " + patientSet.getString("pLastName");
				if(!pList.contains(name)) { //Check that we dont duplicate the patients name in the dropdown
					patientDropDown.addItem(name);
					pList.add(name);
					//Create the visit string info
					String patientInfo = "Patient's ID and Name: \n" + name + "\n\nDate and Time of Visit:\n" + patientSet.getString("date") + "\n\nDoctor Assigned to the visit:\n" + patientSet.getString("dName") + " " + patientSet.getString("dLastName") + "\n\nSymptoms:\n" + patientSet.getString("symptoms") + "\n\nPrescriptions:\n" + patientSet.getString("prescriptions") + "\n\nComments:\n" + patientSet.getString("comments");
					patientDescription.put(name, patientInfo);
				}
				else {
					//update the patients visit string
					String patientInfo = patientDescription.get(name);
					patientInfo = patientInfo + "\n\n\n-----------------------------------------\nDate and Time of Visit:\n" + patientSet.getString("date") + "\n\nDoctor Assigned to the visit:\n" + patientSet.getString("dName") + " " + patientSet.getString("dLastName") + "\n\nSymptoms:\n" + patientSet.getString("symptoms") + "\n\nPrescriptions:\n" + patientSet.getString("prescriptions") + "\n\nComments:\n" + patientSet.getString("comments");
					patientDescription.replace(name, patientInfo);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateTextArea((String) patientDropDown.getSelectedItem());
	}
	
	/**
	 * Updates the text area view text
	 * @param patientName The String as it appears in the dropdown
	 */
	private void updateTextArea(String patientName) {
		details.setText(patientDescription.get(patientName));
	}
	
	public String getDoctorID() {
		return doctorID;
	}
	
	/**
	 * Get the preparedStatement
	 * @return preparedStatement
	 */
	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}
}
