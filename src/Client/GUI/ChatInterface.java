package Client.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatInterface extends JFrame {
    private JTextField messageField;
    private JTextArea chatArea;

    public ChatInterface() {
        // Thiết lập các thuộc tính cửa sổ chat
        setTitle("Chat Interface");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Vùng hiển thị chat
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // Vùng nhập tin nhắn
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.SOUTH);

        messageField = new JTextField();
        inputPanel.add(messageField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                appendMessage("You: " + message);
                messageField.setText("");
            }
        });
        inputPanel.add(sendButton, BorderLayout.EAST);
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ChatInterface chatInterface = new ChatInterface();
                chatInterface.setVisible(true);
            }
        });
    }
}

