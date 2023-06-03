package Client.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;

public class inforUser {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    inforUser window = new inforUser();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public inforUser() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(186, 225, 247));
        frame.setBounds(100, 100, 388, 270);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblAvatar = new JLabel("");
        lblAvatar.setIcon(new ImageIcon("C:\\Users\\TGDD\\Downloads\\Graphicloads-Flat-Finance-Person.64.png"));
        lblAvatar.setBounds(153, 10, 69, 64);
        frame.getContentPane().add(lblAvatar);

        JLabel lblNameUser = new JLabel("User'name");
        lblNameUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNameUser.setBounds(101, 86, 173, 21);
        lblNameUser.setHorizontalAlignment(JLabel.CENTER);
        frame.getContentPane().add(lblNameUser);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblEmail.setBounds(101, 124, 173, 21);
        lblEmail.setHorizontalAlignment(JLabel.CENTER);
        frame.getContentPane().add(lblEmail);
    }

}
