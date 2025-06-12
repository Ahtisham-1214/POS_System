package View;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JLabel messageLabel;
    private boolean isLoggedIn = false;

    public LoginPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        messageLabel = new JLabel("");

        // Add components to panel
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(messageLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(messageLabel, gbc);

        // Add action listener to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    messageLabel.setText("Username and password cannot be empty");
                    messageLabel.setForeground(Color.RED);
                    return;
                }

                if (authenticate(username, password)) {
                    messageLabel.setText("Login successful!");
                    messageLabel.setForeground(Color.GREEN);
                    isLoggedIn = true;
                    

                    // You might want to notify the MainFrame about successful login
                } else {
                    messageLabel.setText("Invalid username or password");
                    messageLabel.setForeground(Color.RED);
                }
            }
        });
    }

    private boolean authenticate(String username, String password) {
        // For demonstration purposes, we'll use a hardcoded authentication
        // In a real application, you would check against a database

        // Hardcoded credentials for demonstration
        if ("admin".equals(username) && "password".equals(password)) {
            return true;
        }

        // For future database implementation:
        // Uncomment this code when database and users table are set up
        /*
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password); // In a real app, use password hashing

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If there's a result, authentication is successful

        } catch (SQLException e) {
            System.err.println("Authentication error: " + e.getMessage());
            messageLabel.setText("Database error: " + e.getMessage());
            return false;
        }
        */

        return false;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
