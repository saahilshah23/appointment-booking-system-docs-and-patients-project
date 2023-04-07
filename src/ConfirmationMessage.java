/**
 * This class creates a small window showing a message and a single OK button that closes the window
 * @author Fernando Ramírez de Aguilar Centeno
 *
 */

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmationMessage {
	private String message;
	
	/**
	 * Class constructor
	 * @param message A string that contains the message to be shown
	 * @param title The title of the window
	 */
	public ConfirmationMessage(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
