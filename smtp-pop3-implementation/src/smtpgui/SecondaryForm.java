package smtpgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import data.Mail;
import utils.Utils;

public class SecondaryForm extends JFrame {

	JButton btnSend = new JButton("Send");
	JPanel panel = new JPanel();

	JLabel lbFrom = new JLabel("From:");
	JLabel lbTo = new JLabel("To:");
	JLabel lbSubject = new JLabel("Subject:");

	JTextField txFrom = new JTextField(15);
	JTextField txTo = new JTextField(15);
	JTextField txSubject = new JTextField(15);

	JTextArea Body = new JTextArea(40, 40);

	SecondaryForm() {
		super("Send-Email");
		setSize(700, 500);
		setLocation(500, 280);
		panel.setLayout(null);

		btnSend.setBounds(10, 10, 150, 150);
		btnSend.setIcon(new ImageIcon("./src/smtpgui/message.png"));

		// First Part
		lbFrom.setBounds(170, 30, 50, 20);
		txFrom.setBounds(230, 30, 430, 20);

		// Second part
		lbTo.setBounds(170, 80, 50, 20);
		txTo.setBounds(230, 80, 430, 20);

		// Third part
		lbSubject.setBounds(170, 130, 50, 20);
		txSubject.setBounds(230, 130, 430, 20);
		
		txFrom.setText("rocio@SMTPSERVER.local");
		txTo.setText("paco@SMTPSERVER.local");
		txSubject.setText("Subject");

		Body.setBounds(10, 170, 660, 280);
		Body.setText("Write the body here.");

		panel.add(lbFrom);
		panel.add(lbTo);
		panel.add(lbSubject);
		panel.add(btnSend);
		validate();
		panel.add(txFrom);
		panel.add(txTo);
		panel.add(txSubject);
		panel.add(Body);

		getContentPane().add(panel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		sendAction();
	}

	public void sendAction() {
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				String from = txFrom.getText();
				String to = txTo.getText();
				String subject = txSubject.getText();
				String body = Body.getText();
				
				// if all fields are not complete
				if (from.equals("") | to.equals("") | subject.equals("")) {
					
					JOptionPane.showMessageDialog(null, "Please complete the fields");
					
				}else{
					
					// Create Mail
					Main.smtpClient.GUI_MAIL_FROM 	= from;
					Main.smtpClient.GUI_MAIL_TO 	= to;
					Main.smtpClient.GUI_MAIL		= new Mail(subject, from, to, body);

					while (Main.smtpClient.GUI_HAS_SENT == false) {
						//System.out.println("Waiting GUI_HAS_SENT");
						Utils.sleep(250);
					}

					JOptionPane.showMessageDialog(null, "Message sent sucessfully.");
					txFrom.setText("rocio@SMTPSERVER.local");
					txTo.setText("paco@SMTPSERVER.local");
					txSubject.setText("Subject");
					Body.setText("Write the body here.");

				}

			}
		});
	}

}
