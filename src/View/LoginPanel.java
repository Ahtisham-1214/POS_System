package View;


import Controller.User;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LoginPanel extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JLabel messageLabel;
    private MainFrame parentFrame;

    // Define colors for better UI
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel Blue
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255); // Alice Blue
    private static final Color TEXT_COLOR = new Color(50, 50, 50); // Dark Gray
    private static final Color SUCCESS_COLOR = new Color(46, 139, 87); // Sea Green
    private static final Color ERROR_COLOR = new Color(178, 34, 34); // Firebrick

    public LoginPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create a panel for the login form with a border
        JPanel loginFormPanel = new JPanel(new GridBagLayout());
        loginFormPanel.setBackground(Color.WHITE);
        loginFormPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        // Create title
        JLabel titleLabel = new JLabel("POS System Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create components with improved styling
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(TEXT_COLOR);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(TEXT_COLOR);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(PRIMARY_COLOR);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add components to the login form panel
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.fill = GridBagConstraints.HORIZONTAL;

        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formGbc.gridwidth = 2;
        formGbc.anchor = GridBagConstraints.CENTER;
        loginFormPanel.add(titleLabel, formGbc);

        formGbc.gridy = 1;
        formGbc.gridwidth = 2;
        loginFormPanel.add(messageLabel, formGbc);

        formGbc.gridy = 2;
        formGbc.gridwidth = 1;
        formGbc.anchor = GridBagConstraints.EAST;
        loginFormPanel.add(usernameLabel, formGbc);

        formGbc.gridx = 1;
        formGbc.anchor = GridBagConstraints.WEST;
        loginFormPanel.add(usernameField, formGbc);

        formGbc.gridx = 0;
        formGbc.gridy = 3;
        formGbc.anchor = GridBagConstraints.EAST;
        loginFormPanel.add(passwordLabel, formGbc);

        formGbc.gridx = 1;
        formGbc.anchor = GridBagConstraints.WEST;
        loginFormPanel.add(passwordField, formGbc);

        formGbc.gridx = 0;
        formGbc.gridy = 4;
        formGbc.gridwidth = 2;
        formGbc.anchor = GridBagConstraints.CENTER;
        formGbc.insets = new Insets(15, 5, 5, 5);
        loginFormPanel.add(loginButton, formGbc);

        // Add the login form panel to the main panel
        add(loginFormPanel, gbc);

        // Add hover effect to login button
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(PRIMARY_COLOR.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(PRIMARY_COLOR);
            }
        });

        // Add tooltip to fields
        usernameField.setToolTipText("Enter your username");
        passwordField.setToolTipText("Enter your password");

        // Add keyboard navigation between username and password fields
        usernameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
                    passwordField.requestFocus();
                }
            }
        });

        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
                    usernameField.requestFocus();
                }
            }
        });

        loginButton.addActionListener(e -> {
            try {
                // Show loading indicator
                loginButton.setEnabled(false);
                loginButton.setText("Logging in...");
                messageLabel.setText("Authenticating...");
                messageLabel.setForeground(TEXT_COLOR);

                // Use SwingWorker to handle authentication in background
                SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                    private String username;
                    private String password;
                    private User user;
                    private String errorMessage;

                    @Override
                    protected Boolean doInBackground() {
                        try {
                            username = usernameField.getText().trim();
                            password = new String(passwordField.getPassword());

                            if (username.isEmpty() && password.isEmpty()) {
                                errorMessage = "Username and password cannot be empty";
                                return false;
                            }

                            if (username.isEmpty()) {
                                errorMessage = "Username cannot be empty";
                                return false;
                            }
                            if (password.isEmpty()) {
                                errorMessage = "Password cannot be empty";
                                passwordField.requestFocus();
                                return false;
                            }

                            user = new User(username, password);
                            return user.authenticateUser() != null;
                        } catch (Exception ex) {
                            errorMessage = "Error: " + ex.getMessage();
                            return false;
                        }
                    }

                    @Override
                    protected void done() {
                        try {
                            boolean success = get();
                            if (success) {
                                // Show success message with animation
                                messageLabel.setText("Login successful!");
                                messageLabel.setForeground(SUCCESS_COLOR);

                                // Navigate to dashboard after successful login
                                if (parentFrame != null) {
                                    // Short delay to show the success message before switching panels
                                    Timer timer = new Timer(800, e1 -> parentFrame.showDashboard());
                                    timer.setRepeats(false);
                                    timer.start();
                                }
                            } else {
                                if (errorMessage != null) {
                                    messageLabel.setText(errorMessage);
                                } else {
                                    messageLabel.setText("Invalid username or password");
                                }
                                messageLabel.setForeground(ERROR_COLOR);

                                // Shake animation for failed login
                                shakeComponent(loginFormPanel);

                                // Reset button
                                loginButton.setText("Login");
                                loginButton.setEnabled(true);
                            }
                        } catch (Exception ex) {
                            messageLabel.setText("Error: " + ex.getMessage());
                            messageLabel.setForeground(ERROR_COLOR);
                            loginButton.setText("Login");
                            loginButton.setEnabled(true);
                        }
                    }
                };
                worker.execute();
            } catch (Exception exception) {
                messageLabel.setText("Error: " + exception.getMessage());
                messageLabel.setForeground(ERROR_COLOR);
                loginButton.setText("Login");
                loginButton.setEnabled(true);
            }
        });
    }

    /**
     * Creates a shake animation for the specified component
     * @param component The component to shake
     */
    private void shakeComponent(Component component) {
        final int originalX = component.getLocation().x;
        final int originalY = component.getLocation().y;

        Timer timer = new Timer(30, null);
        final int[] moves = {-5, 5, -5, 5, -3, 3, -2, 2, -1, 1, 0};
        final AtomicInteger currentMove = new AtomicInteger(0);

        timer.addActionListener(e -> {
            if (currentMove.get() >= moves.length) {
                timer.stop();
                component.setLocation(originalX, originalY);
                return;
            }

            component.setLocation(originalX + moves[currentMove.getAndIncrement()], originalY);
        });

        timer.start();
    }

    /**
     * Clears the username and password fields
     */
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        messageLabel.setText("");
        loginButton.setText("Login");
        loginButton.setEnabled(true);
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
