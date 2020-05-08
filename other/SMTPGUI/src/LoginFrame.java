import javax.swing.*;

import java.awt.event.*;

public class LoginFrame extends JFrame {

    public static void main(String[] args) {
        LoginFrame frameTabel = new LoginFrame();
    }

    JButton btnRequest = new JButton("Request");
    JPanel panel = new JPanel();
    JLabel lbIntroduction = new JLabel("Welcome to the SMTP Server");
    JLabel lbUser = new JLabel("User Name");
    JLabel lbServer = new JLabel("Server Name");

    JTextField txuser = new JTextField(15);
    JTextField txserver = new JTextField(15);


    LoginFrame() {
        super("SMTP-Server");
        setSize(600, 400);
        setLocation(500, 280);
        panel.setLayout(null);

        lbIntroduction.setBounds(200, 0, 300, 30);


        //First Part
        lbUser.setBounds(150, 70, 150, 20);
        txuser.setBounds(250, 70, 150, 20);

        //Second part

        lbServer.setBounds(150, 125, 150, 20);
        txserver.setBounds(250, 125, 150, 20);


        btnRequest.setBounds(230, 200, 100, 20);

        panel.add(lbIntroduction);
        panel.add(lbUser);
        panel.add(lbServer);
        panel.add(btnRequest);
        panel.add(txuser);
        panel.add(txserver);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        actionlogin();
    }

    public void actionlogin() {
        btnRequest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String puname = txuser.getText();
                String pServer = txserver.getText();

                 /*  if(function checker with puname and pserver){

                    go to our server

                 } else if {
                 function to relay

                }else{
                error
                }
                */
                //if all fields are complete
                if (puname.equals("test") && pServer.equals("12345")) {
                    //newframe regFace =new newframe();
                    EmailFrame.SendEmail regFace = new EmailFrame.SendEmail();

                    regFace.setVisible(true);

                    dispose();
                    JOptionPane.showMessageDialog(null, "Perfect");
                }
                //error
                else {

                    JOptionPane.showMessageDialog(null, "Wrong Username/Server");
                    txuser.setText("");
                    txserver.setText("");
                    txuser.requestFocus();
                }

            }
        });
    }
}


/*
public String serverChecker(String a) {


    String r;
    r = a.substring(a.indexOf("E") + 4);

    return r;
*/