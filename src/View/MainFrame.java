package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {
    private LoginPanel loginPanel;
    private DashboardPanel dashboardPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Define application constants
    private static final String APP_NAME = "POS System";
    private static final String APP_VERSION = "1.0";
    private static final Dimension MIN_SIZE = new Dimension(900, 650);

    public MainFrame(String title) {
        // Set the application title with a version
        this.setTitle(APP_NAME + " " + APP_VERSION + " - " + title);

        // Set window properties
        this.setPreferredSize(new Dimension(900, 650));
        this.setMinimumSize(MIN_SIZE);
//        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Try to set the application icon if available
        try {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/app_icon.png")));
            this.setIconImage(icon.getImage());
        } catch (Exception e) {
            // If icon not available, continue without it
            System.out.println("App icon not found, using default.");
        }

        // Create the main panel with CardLayout for switching between login and dashboard
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        this.add(mainPanel, BorderLayout.CENTER);

        // Initialize panels
        loginPanel = new LoginPanel(this);
        dashboardPanel = new DashboardPanel(this);

        // Add panels to the main panel with card names
        mainPanel.add(loginPanel, "login");
        mainPanel.add(dashboardPanel, "dashboard");

        // Show the login panel by default
        cardLayout.show(mainPanel, "login");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // When the 'X' button is clicked, this method is called.
                int confirmResult = JOptionPane.showConfirmDialog(
                        null, // Parent component (this frame)
                        "Are you sure you want to exit?", // Message
                        "Exit Confirmation", // Title
                        JOptionPane.YES_NO_OPTION, // Option type (Yes/No buttons)
                        JOptionPane.QUESTION_MESSAGE // Message type (question icon)
                );

                if (confirmResult == JOptionPane.YES_OPTION) {
                    System.exit(0); 
                }
            }
        });

        // Pack and display the frame
        this.pack();
        this.setLocationRelativeTo(null); // Center on screen
        this.setVisible(true);

        // Set up the default button for the login panel
        loginPanel.setupDefaultButton();
    }

    /**
     * Switch to the dashboard panel after successful login
     */
    public void showDashboard() {
        cardLayout.show(mainPanel, "dashboard");
        this.setTitle("Dashboard");
    }

    /**
     * Switch back to the login panel (for logout)
     */
    public void showLogin() {
        // Clear login fields
        loginPanel.clearFields();

        // Switch to login panel
        cardLayout.show(mainPanel, "login");
        this.setTitle("Login");

        // Set up the default button for the login panel
        loginPanel.setupDefaultButton();
    }
}
