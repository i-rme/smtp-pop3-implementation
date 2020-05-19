package smtpgui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendEmail extends JFrame {

    JButton btnSend = new JButton("Send");
    JPanel panel = new JPanel();
    //JLabel lbIntroduction = new JLabel("Email Creation");

    JLabel lbFrom = new JLabel("From:");
    JLabel lbTo = new JLabel("To:");
    JLabel lbSubject = new JLabel("Subject:");

    JTextField txFrom = new JTextField(15);
    JTextField txTo = new JTextField(15);
    JTextField txSubject= new JTextField(15);


    JTextArea Body= new JTextArea(40,40);

    SendEmail() {
        super("Send-Email");
        setSize(700, 500);
        setLocation(500, 280);
        panel.setLayout(null);


        btnSend.setBounds(10, 10, 150, 150);
        btnSend.setIcon(new ImageIcon("message.png"));


        //First Part
        lbFrom.setBounds(170, 30, 50, 20);
        txFrom.setBounds(230, 30, 430, 20);

        //Second part

        lbTo.setBounds(170, 80, 50, 20);
        txTo.setBounds(230, 80, 430, 20);

        //Third part

        lbSubject.setBounds(170, 130, 50, 20);
        txSubject.setBounds(230, 130, 430, 20);

        Body.setBounds(10,170,660,280);


        Body.setText("Write here the message");

/*
        txuser.setText("Client");
        txserver.setText("127.0.0.1");
        txPort.setText("25");
*/
        //panel.add(lbIntroduction);
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
                String subject=txSubject.getText();
                //if all fields are not complete
                if (from.equals("") | to.equals("")| subject.equals("")) {
                    
                    //dispose();
                    JOptionPane.showMessageDialog(null, "Please complete the fields");
                }
                //Send Message
                else {
                    //Create Mail to send
                    //Mail(subject, from, to, Body)

                    JOptionPane.showMessageDialog(null, "Message sent");
                    txFrom.setText("");
                    txTo.setText("");
                    txSubject.setText("");

                }

            }
        });
    }


}
