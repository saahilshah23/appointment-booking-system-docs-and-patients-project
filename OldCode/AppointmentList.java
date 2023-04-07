import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AppointmentList {
	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;
	private static DefaultTableModel tableModel = new DefaultTableModel();
	
	public static void main(String[] args) {
		createGUI();
		connectDB();
	}
	
	private static void createGUI() {
		//create the JFrame
			JFrame frame = new JFrame("Appointments List");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(400, 400);
	        
		        //Table at the
		        //DefaultTableModel tableModel = new DefaultTableModel();
		        JTable table = new JTable(tableModel);
		        tableModel.addColumn("Doctor");
		        tableModel.addColumn("Patient");
		        tableModel.addColumn("Date & Time");
		        
		        //Add rows to table
		        
		        //Create the ScrollPane
		        JScrollPane scroll = new JScrollPane(table);
		        
		        //Add components to the frame
		        frame.getContentPane().add(BorderLayout.CENTER, scroll);
		        frame.setVisible(true);
	}

	private static void connectDB() {
		try {
			//Declare the SQL Connector
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Establish connection to Database
			connection = DriverManager.getConnection("jdbc:mysql://localhost/co559?user=wifif&password=wififdb");
			
			//Create Query
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT doctors.name AS doc, patients.name AS patient, appointments.date AS Date FROM doctors left JOIN appointments ON doctorID = appointments.doctor LEFT JOIN patients ON appointments.patient = patients.patientID;");
			
			//Results
			while(resultSet.next()) {
				//Print too console
				System.out.println(resultSet.getString("doc") + " - " + resultSet.getString("patient") + " - " + resultSet.getString("Date"));
				
				//Add to table
				tableModel.addRow(new Object[] {resultSet.getString("doc"), resultSet.getString("patient"), resultSet.getString("Date")});
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
