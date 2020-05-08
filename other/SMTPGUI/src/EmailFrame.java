import javax.swing.*;
import java.awt.*;


public class EmailFrame extends JFrame {

    public static void main(String[] args) {
        EmailFrame frameTabel = new EmailFrame();
    }

    JLabel welcome = new JLabel("Welcome to a New Frame");
    JPanel panel = new JPanel();

    EmailFrame(){
        super("Welcome");
        setSize(300,200);
        setLocation(500,280);
        panel.setLayout (null);

        welcome.setBounds(70,50,150,60);

        panel.add(welcome);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    static class SendEmail extends JFrame {
        private JTextField fromField = new JTextField();
        private JTextField toField = new JTextField();
        private JTextField subjectField = new JTextField();
        private JTextArea contentTextArea = new JTextArea(10,20);


        public SendEmail() {
            InitializeUI();
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    SendEmail client = new SendEmail();
                    client.setVisible(true);
                }
            });
        }

        private void InitializeUI() {
            setTitle("Send E-mail");
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setSize(new Dimension(500, 280));

            getContentPane().setLayout(new BorderLayout());

            // Header Panel
            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new GridLayout(3, 2));
            headerPanel.add(new JLabel("From:"));
            headerPanel.add(fromField);

            headerPanel.add(new JLabel("To:"));
            headerPanel.add(toField);

            headerPanel.add(new JLabel("Subject:"));
            headerPanel.add(subjectField);



            // Body Panel
            JPanel bodyPanel = new JPanel();
            bodyPanel.setLayout(new BorderLayout());
            bodyPanel.add(new JLabel("Message:"), BorderLayout.NORTH);
            contentTextArea.setMargin(new Insets(20,20,20,20));

            bodyPanel.add(contentTextArea, BorderLayout.CENTER);

            JPanel footerPanel = new JPanel();
            footerPanel.setLayout(new BorderLayout());
            JButton sendMailButton = new JButton("Send E-mail");


            footerPanel.add(sendMailButton, BorderLayout.SOUTH);

            getContentPane().add(headerPanel, BorderLayout.NORTH);
            getContentPane().add(bodyPanel, BorderLayout.CENTER);
            getContentPane().add(footerPanel, BorderLayout.SOUTH);
            setLocation(500,280);


        /*
            //ButtonListener
            sendMailButton.addActionListener() {

                String from = fromField.getText();
                String subject= toSubject.getText();
                String to = toField.getText();
                String message = contentTextArea.getText();


                       if(function checker of all parameters correctly){
                         Mail mail= new Mail(String subject, String sender, String recipient, String body, int id);
                        create mail and send to our db

                     } else if(not user from ) {
                     function to comeback

                    }else{
                    error
                    }


                }




        }



        /*private class SendEmailActionListener implements ActionListener {
            SendEmailActionListener() {
            }


            }*/

            }
            }
}

