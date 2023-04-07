//Change a patient's assigned doctor
//@author Fernando Ramírez

import javax.swing.*;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ChangePatient {
	JFrame frame;
	private JComboBox<String> patientDropDown;
	private JComboBox<String> doctorDropDown;
	private JTextArea details;
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet patientSet;
	private ResultSet doctorSet;
	private HashMap<String, String> patientID; //Store the patient ID related to what is displayed in the dropwdown
	private HashMap<String, String> patientDescription; //Store a description about the patient related to what is displayed in the dropdown
	private HashMap<String, String> docIDMap; //Store the doctors' ID related to how they appear in the dropdown
	private String doctorID; //Doctor's ID of the current user
	
	/**
	 * Class Constructor
	 */
	public ChangePatient (String doctorID) {
		this.doctorID = doctorID;
		patientID = new HashMap<String, String>();
		patientDescription = new HashMap<String, String>();
		docIDMap = new HashMap<String, String>();
		guiInit();
		connectDB();
		fillDropDowns();
	}
	
	/**
	 * Creates the GUI and its components
	 */
	private void guiInit() {
		//Create the JFrame
		frame = new JFrame("Change Patient's Doctor");
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
		
		doctorDropDown = new JComboBox<String>();
		doctorDropDown.setBounds(450,250,200,25);
		
		JLabel patientLabel = new JLabel("Patient");
		patientLabel.setBounds(100, 225, 200, 25);
		JLabel newDocLabel = new JLabel("New Doctor");
		newDocLabel.setBounds(450, 225, 200, 25);
		
		details = new JTextArea();
		details.setText("Details");
		details.setEditable(false);
		details.setBounds(50, 25, 625, 150);
		
		JButton submit = new JButton("Modify");
		submit.setBounds(660, 315, 100, 25);
		submit.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        updateDoc((String) patientDropDown.getSelectedItem(), (String) doctorDropDown.getSelectedItem());
		    }
		});
		
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
		panel.add(newDocLabel);
		panel.add(doctorDropDown);
		panel.add(submit);
		panel.add(details);
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
			preparedStatement = connect.prepareStatement("SELECT patients.name AS Name, patients.lastName AS LastName, patients.patientID AS pID, doctors.name AS Doctor FROM patients, doctors where doctors.doctorID = patients.doctor ORDER BY pID;");
			patientSet = preparedStatement.executeQuery();	
			
			//Query for doctors' name and ID
			preparedStatement = connect.prepareStatement("SELECT doctors.name AS Name, doctors.lastName AS LastName, doctors.doctorID AS ID FROM doctors;");
			doctorSet = preparedStatement.executeQuery();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fills the GUI dropdowns with information retrieved from the database
	 */
	private void fillDropDowns() {
		try {
			while(patientSet.next()) {	
				String name = patientSet.getString("pID") + ". " + patientSet.getString("Name") + " " + patientSet.getString("LastName");
				patientDropDown.addItem(name);
				String patientInfo = "Patient's ID and Name: \n" + name + "\n\nCurrent Doctor: \n" + patientSet.getString("Doctor");
				patientID.put(name, patientSet.getString("pID"));
				patientDescription.put(name, patientInfo);
			}
			while(doctorSet.next()) {
				String name = doctorSet.getString("Name") + " " + doctorSet.getString("LastName");
				doctorDropDown.addItem(name);
				docIDMap.put(name, doctorSet.getString("ID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateTextArea((String) patientDropDown.getSelectedItem());
	}
	
	private void updateTextArea(String patientName) {
		details.setText(patientDescription.get(patientName));
	}
	
	private void updateDoc(String patient, String doc) {
		//Create Query for updating patient's doctor
		try {
			preparedStatement = connect.prepareStatement("UPDATE patients SET patients.doctor = ? WHERE patients.patientID = ?;");
			preparedStatement.setString(1, docIDMap.get(doc));
			preparedStatement.setString(2, patientID.get(patient));
			confirmationMsg(preparedStatement.executeUpdate()); //Run the UPDATE and check that it worked
			//Refresh the GUI
			refresh();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks that the updateDoc function was able to perform the update
	 * @param state An int if 1 then the update was successful
	 */
	private void confirmationMsg(int state) {
		ConfirmationMessage msg;
		if(state == 1) {
			msg = new ConfirmationMessage("The patient's doctor has been updated", "Success");
		}
		else {
			msg = new ConfirmationMessage("An error occured", "Error");
		}
	}

	/**
	 * Refresh the GUI, comboboxes, and hashmaps
	 */
	private void refresh() {
		//remove items from dropdowns
		patientDropDown.removeAllItems();
		doctorDropDown.removeAllItems();
		//Clear the hashmaps
		patientID.clear();
		patientDescription.clear();
		docIDMap.clear();
		//Clear the resultSets
		patientSet = null;
		doctorSet = null;
		
		//Redo the queries and fill values
		connectDB();
		fillDropDowns();
		updateTextArea((String) patientDropDown.getSelectedItem());
	}
}
