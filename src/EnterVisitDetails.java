/**
 * Enter the visit details from an appointment
 * @author Fernando Ramírez de Aguilar Centeno
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;


public class EnterVisitDetails {
	private String doctorID;
	private JComboBox<String> patientDropDown;
	private JComboBox<String> day;
	private JComboBox<String> month;
	private JComboBox<String> hour;
	private JComboBox<String> minute;
	private JComboBox<String> year;
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
	public EnterVisitDetails(String doctorID) {
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
        
        JLabel dateLabel = new JLabel("Day");
        dateLabel.setBounds(325, 15, 200, 25);
        day = new JComboBox<String>();
        day.setBounds(325, 40, 75, 25);
        JLabel monthLabel = new JLabel("Month");
        monthLabel.setBounds(415, 15, 200, 25);
        month = new JComboBox<String>();
        month.setBounds(415, 40, 75, 25);
        JLabel yearLabel = new JLabel("Year");
        yearLabel.setBounds(510, 15, 200, 25);
        year = new JComboBox<String>();
        year.setBounds(510, 40, 85, 25);
        
        JLabel hourLabel = new JLabel("Hour");
        hourLabel.setBounds(635, 15, 200, 25);
        hour = new JComboBox<String>();
        hour.setBounds(635, 40, 75,25);
        JLabel minuteLabel = new JLabel("Minute");
        minuteLabel.setBounds(720, 15, 200, 25);
        minute = new JComboBox<String>();
        minute.setBounds(720, 40, 75,25);
        
        JLabel symptomsLabel = new JLabel("Symptoms");
        symptomsLabel.setBounds(25, 120, 200, 25);
        symptoms = new JTextArea();
        symptoms.setBounds(25, 150, 200, 300);
        symptoms.setLineWrap(true);
        JScrollPane sympScroll = new JScrollPane(symptoms);
        
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
        panel.add(dateLabel);
        panel.add(symptomsLabel);
        panel.add(prescriptionsLabel);
        panel.add(commentsLabel);
        panel.add(patientDropDown);
        panel.add(year);
        panel.add(symptoms);
        panel.add(prescriptions);
        panel.add(comments);
        panel.add(back);
        panel.add(save);
        panel.add(day);
        panel.add(month);
        panel.add(monthLabel);
        panel.add(yearLabel);
        panel.add(hour);
        panel.add(minute);
        panel.add(hourLabel);
        panel.add(minuteLabel);
        
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
		
		//Fill day dropdown
		for(Integer i = 1; i <= 31; i++) {
			if(i < 10) {
				day.addItem("0" + i.toString());
			}
			else {
				day.addItem(i.toString());
			}
		}
		//fill Month dropdown
		for(Integer i = 1; i <= 12; i++) {
			if(i < 10) {
				month.addItem("0" + i.toString());
			}
			else {
				month.addItem(i.toString());
			}
		}
		//Fill hour dropdown
		for(Integer i = 0; i < 24; i++) {
			if(i < 10) {
				hour.addItem("0" + i.toString());
			}
			else {
				hour.addItem(i.toString());
			}
		}
		//Fill minute dropdown
		for(Integer i = 0; i < 60; i = i + 15) {
			if(i < 10) {
				minute.addItem("0" + i.toString());
			}
			else {
				minute.addItem(i.toString());
			}
		}
		//fill year dropdown
		for(Integer i = 2021; i <= 2050; i++) {
			year.addItem(i.toString());
		}
	}

	private String formatDateTime() {
		String res = "";
		res = year.getSelectedItem().toString() + "-" + month.getSelectedItem().toString() + "-"+ day.getSelectedItem().toString() + " " + hour.getSelectedItem().toString() + ":" + minute.getSelectedItem().toString() + ":" + "00";
		return res;
	}

	private void submit() {
		//Create Query for updating patient's doctor
				try {
					preparedStatement = connect.prepareStatement("INSERT INTO appointments VALUES (?, ?, ?, ?, ?, ?);");
					preparedStatement.setString(1, doctorID);
					preparedStatement.setString(2, patientID.get(patientDropDown.getSelectedItem().toString()));
					preparedStatement.setString(3, formatDateTime());
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
			msg = new ConfirmationMessage("The visit details have been logged succesfully", "Success");
		}
		else {
			msg = new ConfirmationMessage("An error occured, check that the patient doesn't have a registered appointment at the same time", "Error");
		}
	}
}
