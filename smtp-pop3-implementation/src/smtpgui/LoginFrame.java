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

public class LoginFrame extends JFrame {

	JButton btnRequest = new JButton("Connect");
	JPanel panel = new JPanel();
	JLabel lbIntroduction = new JLabel("Welcome to the SMTP Client GUI");

	JLabel lbUser = new JLabel("Host Name");
	JLabel lbServer = new JLabel("Server Name");
	JLabel lbPort = new JLabel("Port");

	JTextField txuser = new JTextField(15);
	JTextField txserver = new JTextField(15);
	JTextField txPort = new JTextField(15);

	LoginFrame() {
		super("SMTP Client GUI");
		setSize(600, 400);
		setLocation(500, 280);
		panel.setLayout(null);

		lbIntroduction.setBounds(200, 0, 300, 30);

		// First Part
		lbUser.setBounds(150, 70, 150, 20);
		txuser.setBounds(250, 70, 150, 20);

		// Second part
		lbServer.setBounds(150, 125, 150, 20);
		txserver.setBounds(250, 125, 150, 20);

		// Third part
		lbPort.setBounds(150, 180, 150, 20);
		txPort.setBounds(250, 180, 150, 20);

		btnRequest.setBounds(230, 220, 100, 20);

		txuser.setText("Client");
		txserver.setText("127.0.0.1");
		txPort.setText("25");

		panel.add(lbIntroduction);
		panel.add(lbUser);
		panel.add(lbServer);
		panel.add(lbPort);
		panel.add(btnRequest);
		panel.add(txuser);
		panel.add(txserver);
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
				String hostName = txuser.getText();
				String serverName = txserver.getText();
				String port = txPort.getText();

				// Check if all the fields are complete
				if (hostName.equals("") || serverName.equals("") || port.equals("")) { // error
					JOptionPane.showMessageDialog(null, "Please complete all the fields.");
				} else {
					
					boolean gui = true;
					Main.smtpClient = new SmtpClient();
					Main.smtpClient.start(gui);
					
					
					while(Main.smtpClient.GUI_HAS_CONNECTED == false) {
						Utils.sleep(500);
					}
					
					SendEmail newFrame = new SendEmail();
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
