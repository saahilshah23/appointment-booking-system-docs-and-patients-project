/**
 * Enter the visit details from an appointment

 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;


public class EditVisitDetails {
	private String doctorID;
	private JComboBox<String> patientDropDown;
	private JComboBox<String> visit;
	private JComboBox<String> date;
	private JTextArea symptoms;
	private JTextArea prescriptions;
	private JTextArea comments;
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet patientSet;
	private HashMap<String, String> patientID; //Store the patient ID related to what is displayed in the dropwdown
	
	/**
	 * Class constructor
	 * @param doctorID The ID of the current Doctor using the system.
	 */
	public EditVisitDetails(String doctorID) {
		this.doctorID = doctorID;
		patientID = new HashMap<String, String>();
		createGUI();
		connectDB();
		fillDropDowns();
	}
	
	
	/**
	 * Initialise the window GUI and its elements
	 */
	private void createGUI() {
		//Create the JFrame and Panel
		JFrame frame = new JFrame("Enter Visit Details");
		JPanel panel = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.add(panel);
        frame.setResizable(false);
        panel.setLayout(null);
        
        //Initialise the elements
        JLabel patientLabel = new JLabel("Patient");
        patientLabel.setBounds(25, 15, 200, 25);
        patientDropDown = new JComboBox<String>();
		patientDropDown.setBounds(25,40,200,25);
        
        
        JLabel symptomsLabel = new JLabel("Symptoms");
        symptomsLabel.setBounds(25, 120, 200, 25);
        symptoms = new JTextArea();
        symptoms.setBounds(25, 150, 200, 300);
        symptoms.setLineWrap(true);
        JScrollPane sympScroll = new JScrollPane(symptoms);
        
        JLabel dateLabel = new JLabel("Date");
        dateLabel.setBounds(625, 15, 200, 50);
        date = new JComboBox<String>();
        date.setBounds(625, 40, 65,25);
        
        JLabel prescriptionsLabel = new JLabel("Prescriptions");
        prescriptionsLabel.setBounds(325, 120, 200, 25);
        prescriptions = new JTextArea();
        prescriptions.setBounds(325, 150, 200, 300);
        prescriptions.setLineWrap(true);
        
        JLabel commentsLabel = new JLabel("Comments");
        commentsLabel.setBounds(625, 120, 200, 25);
        comments = new JTextArea();
        comments.setBounds(625, 150, 200, 300);
        comments.setLineWrap(true);
        
        JButton back = new JButton("Back");
		back.setBounds(580, 500, 100, 25);
        back.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        Dashboard dashboard = new Dashboard(doctorID);
		        frame.setVisible(false);
		    }
		});
        
        JButton save = new JButton("Save");
		save.setBounds(725, 500, 100, 25);
		save.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        submit();
		    }
		});
        
        //Add elements to the panel
        panel.add(patientLabel);
        panel.add(symptomsLabel);
        panel.add(prescriptionsLabel);
        panel.add(commentsLabel);
        panel.add(patientDropDown);
        panel.add(symptoms);
        panel.add(prescriptions);
        panel.add(date);
        panel.add(comments);
        panel.add(back);
        panel.add(save);
     
        
        //Set visible
        frame.setVisible(true);
	}

	/**
	 * Connect to the database to get the patients list
	 */
	private void connectDB() {
		try {
			//Declare the SQL Connector
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Establish connection to Database
			connect = DriverManager.getConnection("jdbc:mysql://localhost/co559?user=wifif&password=wififdb");
			
			//Create Query for patients list
			preparedStatement = connect.prepareStatement("SELECT patients.name AS Name, patients.lastName AS LastName, patients.patientID AS pID FROM patients WHERE patients.doctor = ? ORDER BY pID;");
			preparedStatement.setString(1, doctorID);
			patientSet = preparedStatement.executeQuery();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fills the patient dropdown with information retrieved from the database
	 */
	private void fillDropDowns() {
		try {
			while(patientSet.next()) {	
				String name = patientSet.getString("pID") + ". " + patientSet.getString("Name") + " " + patientSet.getString("LastName");
				patientDropDown.addItem(name);
				patientID.put(name, patientSet.getString("pID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Integer i = 0; i < 24; i++) {
			if(i < 10) {
				date.addItem("0" + i.toString());
			}
			else {
				date.addItem(i.toString());
			}
		}
	}
	
	
		

	private void submit() {
		//Create Query for updating patient's doctor
				try {
					preparedStatement = connect.prepareStatement("INSERT INTO appointments VALUES (?, ?, ?, ?, ?, ?);");
					preparedStatement.setString(1, doctorID);
					preparedStatement.setString(2, patientID.get(patientDropDown.getSelectedItem().toString()));

					preparedStatement.setString(4, symptoms.getText());
					preparedStatement.setString(5, prescriptions.getText());
					preparedStatement.setString(6, comments.getText());
					confirmationMsg(preparedStatement.executeUpdate()); //Run the INSERT and check that it worked
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					confirmationMsg(0);
					e.printStackTrace();
				}
	}
	
	/**
	 * Checks that the submit function was able to perform the insert
	 * @param state An int if 1 then the update was successful
	 */
	private void confirmationMsg(int state) {
		ConfirmationMessage msg;
		if(state == 1) {
			msg = new ConfirmationMessage("The visit details have been changed succesfully", "Success");
		}
		else {
			msg = new ConfirmationMessage("An error occured, check that the patient doesn't have a registered appointment at the same time", "Error");
		}
	}
}
