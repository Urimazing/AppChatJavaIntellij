package Client.GUI;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;
import Client.GUI.MessengerUI;
import static Server.Server.*;
import Client.GUI.register;

public class login extends JFrame {

    private JPanel contentPane;
    private JTextField txtUserName;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    login frame = new login();
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
    public login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 681, 302);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setForeground(SystemColor.activeCaption);
        lblNewLabel.setBounds(532, 22, 73, 25);
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
        btnLogin.setBackground(SystemColor.textHighlightText);
        btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnLogin.setBounds(475, 140, 90, 25);
        btnLogin.setBorder(new LineBorder(SystemColor.activeCaption, 2));
        contentPane.add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUserName.getText();
                String password = new String(passwordField.getPassword());

                try {
                    // Kết nối tới cơ sở dữ liệu
                    Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

                    // Tạo câu truy vấn SQL sử dụng Prepared Statement
                    String query = "SELECT * FROM Users WHERE Tusername = ? AND Tpassword = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    // Thực thi truy vấn SELECT
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Kiểm tra xem có kết quả trả về hay không
                    if (resultSet.next()) {
                        // Đăng nhập thành công
                        System.out.println("Login successful");
                        // Mở giao diện MessengerUI
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                MessengerUI messengerUI = new MessengerUI();
                                messengerUI.setVisible(true);
                            }
                        });


                        // Thực hiện các hành động cần thiết sau khi đăng nhập thành công

                    } else {
                        // Đăng nhập không thành công
                        System.out.println("Login failed");
                        JOptionPane.showMessageDialog(null, "Login fail", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Đóng kết nối và giải phóng tài nguyên
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton btnRegister = new JButton("Register");
        btnRegister.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openRegister();
            }
        });


//        btnRegister.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                new register();
//            }
//        });
        btnRegister.setBackground(SystemColor.textHighlightText);
        btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnRegister.setBounds(567, 140, 90, 25);
        btnRegister.setBorder(new LineBorder(SystemColor.activeCaption, 2));
        contentPane.add(btnRegister);

        JLabel label = new JLabel("");
        label.setBounds(23, 21, 415, 234);
        label.setIcon(new ImageIcon("C:\\Users\\TGDD\\IdeaProjects\\AppChatJavaSwing\\src\\icon\\hinhanh.png.png"));
        contentPane.add(label);

    }
    private void closeLogin() {
        setVisible(false);
        dispose();
    }

    private void openRegister() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                closeLogin();
                register registerFrame = new register();
                registerFrame.setVisible(true);

            }
        });
    }



}


//package Client.GUI;
//
//import java.awt.Color;
//import java.awt.EventQueue;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//import javax.swing.JLabel;
//import java.awt.SystemColor;
//import java.awt.Toolkit;
//import java.awt.Font;
//import javax.swing.JTextField;
//import javax.swing.JPasswordField;
//import java.awt.event.FocusAdapter;
//import java.awt.event.FocusEvent;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.border.LineBorder;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//
//import static Server.Server.DB_URL;
//
//public class login extends JFrame {
//
//    private JPanel contentPane;
//    private JTextField txtUserName;
//    private JPasswordField passwordField;
//
//    /**
//     * Launch the application.
//     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    login frame = new login();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    /**
//     * Create the frame.
//     */
//    public login() {
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(100, 100, 681, 302);
//        contentPane = new JPanel();
//        contentPane.setBackground(new Color(255, 255, 255));
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//
//        setContentPane(contentPane);
//        contentPane.setLayout(null);
//
//        JLabel lblNewLabel = new JLabel("Login");
//        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
//        lblNewLabel.setForeground(SystemColor.activeCaption);
//        lblNewLabel.setBounds(532, 22, 73, 25);
//        contentPane.add(lblNewLabel);
//
//        txtUserName = new JTextField();
//        txtUserName.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if(txtUserName.getText().equals("User name"))
//                {
//                    txtUserName.setText("");
//                    txtUserName.setForeground(new Color(153,153,153));
//                }
//            }
//            @Override
//            public void focusLost(FocusEvent e) {
//                if(txtUserName.getText().equals(""))
//                {
//                    txtUserName.setText("User name");
//                    txtUserName.setForeground(new Color(153,153,153));
//                }
//            }
//        });
//        txtUserName.setFont(new Font("Tahoma", Font.PLAIN, 15));
//        txtUserName.setForeground(SystemColor.activeCaptionBorder);
//        txtUserName.setText("User name");
//        txtUserName.setBounds(475, 57, 182, 31);
//        contentPane.add(txtUserName);
//        txtUserName.setColumns(10);
//
//
//
//
//
//        passwordField = new JPasswordField();
//        passwordField.setBounds(475, 98, 182, 31);
//        contentPane.add(passwordField);
//
//        JButton btnLogin = new JButton("Login");
//        btnLogin.setBackground(SystemColor.textHighlightText);
//        btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
//        btnLogin.setBounds(475, 140, 90, 25);
//        btnLogin.setBorder(new LineBorder(SystemColor.activeCaption, 2));
//        contentPane.add(btnLogin);
//        btnLogin.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String username = txtUserName.getText();
//                String password = new String(passwordField.getPassword());
//
//                try {
//                    // Kết nối tới cơ sở dữ liệu
//                    Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
//
//                    // Tạo câu truy vấn SQL sử dụng Prepared Statement
//                    String query = "SELECT * FROM Users WHERE Tusername = ? AND Tpassword = ?";
//                    PreparedStatement preparedStatement = connection.prepareStatement(query);
//                    preparedStatement.setString(1, username);
//                    preparedStatement.setString(2, password);
//
//                    // Thực thi truy vấn SELECT
//                    ResultSet resultSet = preparedStatement.executeQuery();
//
//                    // Kiểm tra xem có kết quả trả về hay không
//                    if (resultSet.next()) {
//                        // Đăng nhập thành công
//                        System.out.println("Login successful");
//
//                        // Thực hiện các hành động cần thiết sau khi đăng nhập thành công
//
//                    } else {
//                        // Đăng nhập không thành công
//                        System.out.println("Login failed");
//                    }
//
//                    // Đóng kết nối và giải phóng tài nguyên
//                    resultSet.close();
//                    preparedStatement.close();
//                    connection.close();
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//
//
//        JButton btnRegister = new JButton("Register");
//        btnRegister.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                new register();
//            }
//        });
//        btnRegister.setBackground(SystemColor.textHighlightText);
//        btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 15));
//        btnRegister.setBounds(567, 140, 90, 25);
//        btnRegister.setBorder(new LineBorder(SystemColor.activeCaption, 2));
//        contentPane.add(btnRegister);
//
//
//
//
//        JLabel label = new JLabel("");
//        label.setBounds(23, 21, 415, 234);
//        label.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(login.class.getResource("hinhanh.png.png"))));
//        contentPane.add(label);
//
//
//    }
//}
//
