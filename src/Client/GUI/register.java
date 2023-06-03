package Client.GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;

import Client.GUI.login;

public class register extends JFrame {

    private JPanel contentPane;
    private JTextField txtUserName;
    private JPasswordField passwordField;
    private JTextField txtNumber;
    private JTextField txtEmail;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    register frame = new register();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public register() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 681, 317);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Register");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setForeground(SystemColor.activeCaption);
        lblNewLabel.setBounds(525, 21, 90, 25);
        contentPane.add(lblNewLabel);

        txtUserName = new JTextField();
        txtUserName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(txtUserName.getText().equals("User name"))
                {
                    txtUserName.setText("");
                    txtUserName.setForeground(new Color(153,153,153));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(txtUserName.getText().equals(""))
                {
                    txtUserName.setText("User name");
                    txtUserName.setForeground(new Color(153,153,153));
                }
            }
        });
        txtUserName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtUserName.setForeground(SystemColor.activeCaptionBorder);
        txtUserName.setText("User name");
        txtUserName.setBounds(475, 57, 182, 31);
        contentPane.add(txtUserName);
        txtUserName.setColumns(10);





        passwordField = new JPasswordField();
        passwordField.setBounds(475, 98, 182, 31);
        contentPane.add(passwordField);

        JButton btnLogin = new JButton("Login");
        btnLogin.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openLogin();
            }
        });
//        btnNewButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                new login();
//            }
//        });
        btnLogin.setBackground(SystemColor.textHighlightText);
        btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnLogin.setBounds(475, 230, 90, 25);
        btnLogin.setBorder(new LineBorder(SystemColor.activeCaption, 2));
        contentPane.add(btnLogin);

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnRegister.setBackground(SystemColor.textHighlightText);
        btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnRegister.setBounds(567, 230, 90, 25);
        btnRegister.setBorder(new LineBorder(SystemColor.activeCaption, 2));
        contentPane.add(btnRegister);




        JLabel label = new JLabel("");
        label.setBounds(23, 21, 415, 234);
        label.setIcon(new ImageIcon("C:\\Users\\TGDD\\IdeaProjects\\AppChatJavaSwing\\src\\icon\\hinhanh.png.png"));
        contentPane.add(label);

        txtNumber = new JTextField();
        txtNumber.setForeground(SystemColor.activeCaptionBorder);
        txtNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtNumber.setText("Number");
        txtNumber.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(txtNumber.getText().equals("Number"))
                {
                    txtNumber.setText("");
                    txtNumber.setForeground(new Color(153,153,153));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(txtNumber.getText().equals(""))
                {
                    txtNumber.setText("Number");
                    txtNumber.setForeground(new Color(153,153,153));
                }
            }
        });
        txtNumber.setBounds(475, 139, 182, 31);
        contentPane.add(txtNumber);
        txtNumber.setColumns(10);

        txtEmail = new JTextField();
        txtEmail.setText("Email");
        txtEmail.setForeground(SystemColor.activeCaptionBorder);
        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(txtEmail.getText().equals("Email"))
                {
                    txtEmail.setText("");
                    txtEmail.setForeground(new Color(153,153,153));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(txtEmail.getText().equals(""))
                {
                    txtEmail.setText("Email");
                    txtEmail.setForeground(new Color(153,153,153));
                }
            }
        });
        txtEmail.setBounds(475, 179, 182, 31);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);
    }

    private void closeRegister() {
        setVisible(false);
        dispose();
    }
    private void openLogin() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                closeRegister();
                login login = new login();
                login.setVisible(true);

            }
        });
    }

}
