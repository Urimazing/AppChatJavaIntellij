package Client.GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import static Server.Server.getFriendsFromDatabase;


public class MessengerUI extends JFrame {
    private DefaultListModel<Friend> friendListModel;
    private JList<Friend> friendList;
    private JTextArea messageArea;

    public MessengerUI() {
        setTitle("Messenger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(847, 509));
        getContentPane().setBackground(new Color(255, 255, 255));
        setLayout(null);

        // Khởi tạo friendListModel và lấy danh sách bạn bè từ cơ sở dữ liệu
        friendListModel = new DefaultListModel<>();
        try {
            getFriendsFromDatabase(friendListModel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        friendList = new JList<>(friendListModel);
        friendList.setCellRenderer(new FriendListRenderer());
        JScrollPane friendScrollPane = new JScrollPane(friendList);
        friendScrollPane.setBounds(10, 48, 162, 414);
        add(friendScrollPane);


        // Vùng hiển thị tin nhắn
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        messageScrollPane.setBounds(196, 48, 627, 350);
        add(messageScrollPane);

        // Vùng nhập tin nhắn
        JTextField inputField = new JTextField();
        inputField.setBounds(10, 5, 535, 20);

        JButton sendButton = new JButton("Send");
        sendButton.setBounds(561, 0, 74, 28);
//        sendButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String message = inputField.getText();
//                messageArea.append("You: " + message + "\n");
//                inputField.setText("");
//            }
//
//        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                messageArea.append("You: " + message + "\n");
                inputField.setText("");

                saveMessage("You: " + message);
            }
        });



        JPanel inputPanel = new JPanel(null);
        inputPanel.setBounds(196, 434, 647, 28);
        inputPanel.setBorder(new LineBorder(new Color(105, 107, 116), 2, true));
        inputPanel.setBackground(new Color(255, 255, 255));
        inputPanel.add(inputField);
        inputPanel.add(sendButton);
        add(inputPanel);

        // Labels
        JLabel btnGroup = new JLabel("");
        btnGroup.setBounds(97, 0, 55, 28);
        btnGroup.setIcon(new ImageIcon("C:\\Users\\TGDD\\Downloads\\Icons8-Ios7-Healthcare-Groups.32.png"));
        add(btnGroup);

        JLabel btnMSG = new JLabel("");
        btnMSG.setBounds(10, 0, 55, 28);
        btnMSG.setIcon(new ImageIcon("C:\\Users\\TGDD\\Downloads\\Pictogrammers-Material-Light-Message.32.png"));
        add(btnMSG);

        JLabel lblNameChatting = new JLabel("Name");
        lblNameChatting.setBounds(10, 0, 122, 28);
        lblNameChatting.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openInforUser();
            }
        });
        add(lblNameChatting);



        // Set component positions
        btnGroup.setLocation(97, 0);
        btnMSG.setLocation(10, 0);
        lblNameChatting.setLocation(196, 10);

        pack();
        setVisible(true);


    }

    // Hàm lưu tin nhắn
    private void saveMessage(String message) {
        try {
            File file = new File("messages.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(message + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm load tin nhắn
    private void loadMessages() {
        try {
            File file = new File("messages.txt");
            if (!file.exists()) {
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                messageArea.append(line + "\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openInforUser() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InforUser().getFrame().setVisible(true);
            }
        });
    }
    private class InforUser {
        private JFrame frame;

        public InforUser() {
            initialize();
        }

        public JFrame getFrame() {
            return frame;
        }

        private void initialize() {
            frame = new JFrame();
            frame.getContentPane().setBackground(new Color(186, 225, 247));
            frame.setBounds(100, 100, 388, 270);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().setLayout(null);

            // ... existing code ...

            JLabel lblNameUser = new JLabel("User's Name");
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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MessengerUI();
            }
        });
    }

    public static class Friend {
        private String name;
        private String avatarPath;

        public Friend(String name, String avatarPath) {
            this.name = name;
            this.avatarPath = avatarPath;
        }

        public String getName() {
            return name;
        }

        public String getAvatarPath() {
            return avatarPath;
        }
    }

    private class FriendListRenderer extends JLabel implements ListCellRenderer<Friend> {
        public FriendListRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Friend> list, Friend friend, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setIcon(new ImageIcon(friend.getAvatarPath()));
            setText(friend.getName());

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            return this;
        }
    }
    public void MessengerUI() {
        // Các khai báo khác của giao diện

        // Tạo danh sách bạn bè
        friendListModel = new DefaultListModel<>();
        try {
            getFriendsFromDatabase(friendListModel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Các thành phần khác của giao diện
    }

}


//package Client.GUI;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class MessengerUI extends JFrame {
//    private DefaultListModel<Friend> friendListModel;
//    private JList<Friend> friendList;
//    private JTextArea messageArea;
//
//    public MessengerUI() {
//        setTitle("Messenger");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setPreferredSize(new Dimension(600, 400));
//
//        // Tạo danh sách bạn bè
//        friendListModel = new DefaultListModel<>();
//        friendListModel.addElement(new Friend("Friend 1", "avatar1.jpg"));
//        friendListModel.addElement(new Friend("Friend 2", "avatar2.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 1", "avatar1.jpg"));
//        friendListModel.addElement(new Friend("Friend 2", "avatar2.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//        friendListModel.addElement(new Friend("Friend 3", "avatar3.jpg"));
//
//        friendList = new JList<>(friendListModel);
//        friendList.setCellRenderer(new FriendListRenderer());
//        JScrollPane friendScrollPane = new JScrollPane(friendList);
//        friendScrollPane.setPreferredSize(new Dimension(150, getHeight()));
//
//        // Vùng hiển thị tin nhắn
//        messageArea = new JTextArea();
//        messageArea.setEditable(false);
//        JScrollPane messageScrollPane = new JScrollPane(messageArea);
//
//        // Vùng nhập tin nhắn
//        JTextField inputField = new JTextField();
//        JButton sendButton = new JButton("Send");
//        sendButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String message = inputField.getText();
//                messageArea.append("You: " + message + "\n");
//                inputField.setText("");
//            }
//        });
//
//        JPanel inputPanel = new JPanel(new BorderLayout());
//        inputPanel.add(inputField, BorderLayout.CENTER);
//        inputPanel.add(sendButton, BorderLayout.EAST);
//
//        // Sắp xếp các thành phần trong giao diện
//        JPanel contentPane = new JPanel(new BorderLayout());
//        contentPane.add(friendScrollPane, BorderLayout.WEST);
//        contentPane.add(messageScrollPane, BorderLayout.CENTER);
//        contentPane.add(inputPanel, BorderLayout.SOUTH);
//
//        setContentPane(contentPane);
//        pack();
//        setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new MessengerUI();
//            }
//        });
//    }
//
//    private class Friend {
//        private String name;
//        private String avatarPath="\"C:\\Users\\TGDD\\Downloads\\hinhanh.png.png\"";
//
//        public Friend(String name, String avatarPath) {
//            this.name = name;
//            this.avatarPath = avatarPath;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getAvatarPath() {
//            return avatarPath;
//        }
//    }
//
//    private class FriendListRenderer extends JLabel implements ListCellRenderer<Friend> {
//        public FriendListRenderer() {
//            setOpaque(true);
//        }
//
//        @Override
//        public Component getListCellRendererComponent(JList<? extends Friend> list, Friend friend, int index,
//                                                      boolean isSelected, boolean cellHasFocus) {
//            setIcon(new ImageIcon(friend.getAvatarPath()));
//            setText(friend.getName());
//
//            if (isSelected) {
//                setBackground(list.getSelectionBackground());
//                setForeground(list.getSelectionForeground());
//            } else {
//                setBackground(list.getBackground());
//                setForeground(list.getForeground());
//            }
//
//            return this;
//        }
//    }
//}
//
