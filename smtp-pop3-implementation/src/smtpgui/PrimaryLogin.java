package smtpgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import smtpclient.SmtpClient;
import utils.Utils;

public class PrimaryLogin extends JFrame {

	JButton btnRequest = new JButton("Connect");
	JPanel panel = new JPanel();
	JLabel lbIntroduction = new JLabel("Welcome to the SMTP Client GUI");

	JLabel lbHostname = new JLabel("Hostname");
	JLabel lbServer = new JLabel("Server");
	JLabel lbPort = new JLabel("Port");

	JTextField txHostname = new JTextField(15);
	JTextField txServer = new JTextField(15);
	JTextField txPort = new JTextField(15);

	PrimaryLogin() {
		super("SMTP Client GUI");
		setSize(600, 400);
		setLocation(500, 280);
		panel.setLayout(null);

		lbIntroduction.setBounds(200, 0, 300, 30);

		// First Part
		lbHostname.setBounds(150, 70, 150, 20);
		txHostname.setBounds(250, 70, 150, 20);

		// Second part
		lbServer.setBounds(150, 125, 150, 20);
		txServer.setBounds(250, 125, 150, 20);

		// Third part
		lbPort.setBounds(150, 180, 150, 20);
		txPort.setBounds(250, 180, 150, 20);

		btnRequest.setBounds(230, 220, 100, 20);

		txHostname.setText("SMTPGUI.local");
		txServer.setText("127.0.0.1");
		txPort.setText("25");

		panel.add(lbIntroduction);
		panel.add(lbHostname);
		panel.add(lbServer);
		panel.add(lbPort);
		panel.add(btnRequest);
		panel.add(txHostname);
		panel.add(txServer);
		panel.add(txPort);

		getContentPane().add(panel);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
		actionlogin();
	}

	public void actionlogin() {
		btnRequest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String hostname = txHostname.getText();
				String server = txServer.getText();
				String port = txPort.getText();

				// Check if all the fields are complete
				if (hostname.equals("") || server.equals("") || port.equals("")) { // error
					JOptionPane.showMessageDialog(null, "Please complete all the fields.");
				} else {

					Main.smtpClient = new SmtpClient();
					Main.smtpClient.start(hostname, server, Integer.parseInt(port));
					// Main.smtpClient.start(serverName, hostName, 25);

					while (Main.smtpClient.GUI_HAS_CONNECTED == false) {
						Utils.sleep(500);
					}

					SecondaryForm newFrame = new SecondaryForm();
					newFrame.setVisible(true);
					dispose();
					JOptionPane.showMessageDialog(null, "Connection was successful.");

				}

			}
		});
	}
}

/*
 * public String serverChecker(String a) {
 * 
 * 
 * String r; r = a.substring(a.indexOf("E") + 4);
 * 
 * return r;
 */

//Crear otro textf para el puerto   25
// Servidor 127.0.0.1

//reformar GuI para borrar el texto
//mirar lo de al cerrar al QUIT
