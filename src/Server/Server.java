package Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import org.json.JSONException;
import org.json.JSONObject;

public class Server {
    private static final int PORT = 5000;
    public static final String DB_URL = "jdbc:mysql://localhost:3306/appchatjava";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Create a new thread to handle each client
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());

            // Read the JSON message from the client
            String jsonMessage = inputStream.readUTF();

            // Parse the JSON message
            JSONObject registerInfo = new JSONObject(jsonMessage);

            // Extract the registration information
            String Tusername = registerInfo.getString("username");
            String Tpassword = registerInfo.getString("password");
            String Inumber = registerInfo.getString("number");
            String Temail = registerInfo.getString("email");

            // Save the user registration to the database
            saveUserRegistration(Tusername, Tpassword, Inumber, Temail);

            // Close the client socket
            clientSocket.close();
        } catch (IOException | JSONException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void saveUserRegistration(String username, String password, String number, String email)
            throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Insert the user registration into the Users table
            String insertQuery = "INSERT INTO Users (Tusername, Tpassword, Temail, Inumber) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, number);
            preparedStatement.executeUpdate();
        }
//        try {
//            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
//
//            // Read the JSON message from the client
//            String jsonMessage = inputStream.readUTF();
//
//            // Parse the JSON message
//            JSONObject message = new JSONObject(jsonMessage);
//
//            // Extract the message content, timestamp, image path, and file path
//            String content = message.getString("content");
//            long timestamp = message.getLong("timestamp");
//            String imagePath = message.getString("imagePath");
//            String filePath = message.getString("filePath");
//
//            // Save the message to the database
//            saveMessage(content, timestamp, imagePath, filePath);
//
//            // Close the client socket
//            clientSocket.close();
//        } catch (IOException | JSONException | SQLException e) {
//            e.printStackTrace();
//        }
    }

    private static synchronized void saveMessage(String content, long timestamp, String imagePath, String filePath)
            throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Insert the message into the MSG table
            String insertQuery = "INSERT INTO MSG (content, timestamp) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, content);
            preparedStatement.setTimestamp(2, new Timestamp(timestamp));
            preparedStatement.executeUpdate();

            // Get the generated ID of the inserted message
            int messageId;
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                messageId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve generated message ID.");
            }

            // Insert the attachments into the Attachments table
            String attachmentsQuery = "INSERT INTO Attachments (message_id, file_path, file_type) VALUES (?, ?, ?)";
            PreparedStatement attachmentsStatement = connection.prepareStatement(attachmentsQuery);
            attachmentsStatement.setInt(1, messageId);
            attachmentsStatement.setString(2, filePath);
            attachmentsStatement.setString(3, getFileType(filePath));
            attachmentsStatement.executeUpdate();
        }
    }

    private static String getFileType(String filePath) {
        // Implement logic to get the file type based on the file path
        return "file_type";
    }
}


//package Server;
//
//import java.io.DataInputStream;
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.sql.*;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class Server {
//    private static final int PORT = 5000;
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/appchat";
//    private static final String DB_USERNAME = "root";
//    private static final String DB_PASSWORD = "";
//
//    public static void main(String[] args) {
//        try {
//            ServerSocket serverSocket = new ServerSocket(PORT);
//            System.out.println("Server started on port " + PORT);
//
//            while (true) {
//                Socket clientSocket = serverSocket.accept();
//                System.out.println("Client connected: " + clientSocket.getInetAddress());
//
//                // Create a new thread to handle each client
//                Thread clientThread = new Thread(() -> handleClient(clientSocket));
//                clientThread.start();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void handleClient(Socket clientSocket) {
//        try {
//            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
//
//            // Read the JSON message from the client
//            String jsonMessage = inputStream.readUTF();
//
//            // Parse the JSON message
//            JSONObject message = new JSONObject(jsonMessage);
//
//            // Extract the message content, timestamp, image path, and file path
//            String content = message.getString("content");
//            long timestamp = message.getLong("timestamp");
//            String imagePath = message.getString("imagePath");
//            String filePath = message.getString("filePath");
//
//            // Save the message to the database
//            saveMessage(content, timestamp, imagePath, filePath);
//
//            // Close the client socket
//            clientSocket.close();
//        } catch (IOException | JSONException | SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static synchronized void saveMessage(String content, long timestamp, String imagePath, String filePath)
//            throws SQLException {
//        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
//            // Insert the message into the MSG table
//            String insertQuery = "INSERT INTO MSG (content, timestamp) VALUES (?, ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
//            preparedStatement.setString(1, content);
//            preparedStatement.setTimestamp(2, new Timestamp(timestamp));
//            preparedStatement.executeUpdate();
//
//            // Get the generated ID of the inserted message
//            int messageId;
//            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                messageId = generatedKeys.getInt(1);
//            } else {
//                throw new SQLException("Failed to retrieve generated message ID.");
//            }
//
//            // Insert the attachments into the Attachments table
//            String attachmentsQuery = "INSERT INTO Attachments (message_id, file_path, file_type) VALUES (?, ?, ?)";
//            PreparedStatement attachmentsStatement = connection.prepareStatement(attachmentsQuery);
//            attachmentsStatement.setInt(1, messageId);
//            attachmentsStatement.setString(2, filePath);
//            attachmentsStatement.setString(3, getFileType(filePath));
//            attachmentsStatement.executeUpdate();
//        }
//    }
//
//    private static String getFileType(String filePath) {
//        // Implement logic to get the file type based on the file path
//        return "file_type";
//    }
//}


//package Server;
//import java.io.DataInputStream;
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.sql.*;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class Server {
//    private static final int PORT = 5000;
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/appchat";
//    private static final String DB_USERNAME = "root";
//    private static final String DB_PASSWORD = "";
//
//    public static void main(String[] args) {
//        try {
//            ServerSocket serverSocket = new ServerSocket(PORT);
//            System.out.println("Server started on port " + PORT);
//
//            while (true) {
//                Socket clientSocket = serverSocket.accept();
//                System.out.println("Client connected: " + clientSocket.getInetAddress());
//
//                // Create a new thread to handle each client
//                Thread clientThread = new Thread(() -> handleClient(clientSocket));
//                clientThread.start();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void handleClient(Socket clientSocket) {
//        try {
//            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
//
//            // Read the JSON message from the client
//            String jsonMessage = inputStream.readUTF();
//
//            // Parse the JSON message
//            JSONObject message = new JSONObject(jsonMessage);
//
//            // Extract the message content, timestamp, image path, and file path
//            String content = message.getString("content");
//            long timestamp = message.getLong("timestamp");
//            String imagePath = message.getString("imagePath");
//            String filePath = message.getString("filePath");
//
//            // Save the message to the database
//            saveMessage(content, timestamp, imagePath, filePath);
//
//            // Close the client socket
//            clientSocket.close();
//        } catch (IOException | JSONException | SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void saveMessage(String content, long timestamp, String imagePath, String filePath)
//            throws SQLException {
//        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
//            // Insert the message into the MSG table
//            String insertQuery = "INSERT INTO MSG (content, timestamp) VALUES (?, ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
//            preparedStatement.setString(1, content);
//            preparedStatement.setTimestamp(2, new Timestamp(timestamp));
//            preparedStatement.executeUpdate();
//
//            // Get the generated ID of the inserted message
//            int messageId;
//            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                messageId = generatedKeys.getInt(1);
//            } else {
//                throw new SQLException("Failed to retrieve generated message ID.");
//            }
//
//            // Insert the attachments into the Attachments table
//            String attachmentsQuery = "INSERT INTO Attachments (message_id, file_path, file_type) VALUES (?, ?, ?)";
//            PreparedStatement attachmentsStatement = connection.prepareStatement(attachmentsQuery);
//            attachmentsStatement.setInt(1, messageId);
//            attachmentsStatement.setString(2, filePath);
//            attachmentsStatement.setString(3, getFileType(filePath));
//            attachmentsStatement.executeUpdate();
//        }
//    }
//
//    private static String getFileType(String filePath) {
//        // Implement logic to get the file type based on the file path
//        return "file_type";
//    }
//}
//
