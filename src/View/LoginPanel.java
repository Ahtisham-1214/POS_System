package View;


import Controller.User;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JLabel messageLabel;
    private MainFrame parentFrame;

    public LoginPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
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

        loginButton.addActionListener(e -> {
            try {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    messageLabel.setText("Username and password cannot be empty");
                    messageLabel.setForeground(Color.RED);
                    return;
                }
                User user = new User(username, password);

                if (null != user.authenticateUser()) {
                    messageLabel.setText("Login successful!");
                    messageLabel.setForeground(Color.GREEN);

                    // Navigate to dashboard after successful login
                    if (parentFrame != null) {
                        // Short delay to show the success message before switching panels
                        Timer timer = new Timer(800, e1 -> parentFrame.showDashboard());
                        timer.setRepeats(false);
                        timer.start();
                    }
                } else {
                    messageLabel.setText("Invalid username or password");
                    messageLabel.setForeground(Color.RED);
                }
            }catch (Exception exception){
                messageLabel.setText("Error: " + exception.getMessage());
                messageLabel.setForeground(Color.RED);
            }
        });
    }

    /**
     * Clears the username and password fields
     */
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        messageLabel.setText("");
    }

    /**
     * Sets the login button as the default button for the root pane.
     * This method should be called after the panel is added to a frame.
     */
    public void setupDefaultButton() {
        if (SwingUtilities.getWindowAncestor(this) != null) {
            JRootPane rootPane = SwingUtilities.getRootPane(this);
            if (rootPane != null) {
                rootPane.setDefaultButton(loginButton);
            }
        }
    }
}
