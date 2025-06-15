package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardPanel extends JPanel {
    private JPanel contentPanel;
    private JPanel sidebarPanel;
    private CardLayout cardLayout;

    // Placeholder panels for different sections
    private JPanel homePanel;
    private JPanel inventoryPanel;
    private JPanel salesPanel;
    private JPanel reportsPanel;
    private JPanel settingsPanel;

    private MainFrame parentFrame;

    public DashboardPanel() {
        this(null);
    }

    public DashboardPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        // Create sidebar
        createSidebar();

        // Create content panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Create placeholder panels
        createPlaceholderPanels();

        // Add panels to content panel
        contentPanel.add(homePanel, "home");
        contentPanel.add(inventoryPanel, "inventory");
        contentPanel.add(salesPanel, "sales");
        contentPanel.add(reportsPanel, "reports");
        contentPanel.add(settingsPanel, "settings");

        // Show home panel by default
        cardLayout.show(contentPanel, "home");

        // Add sidebar and content panel to dashboard
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(50, 50, 50));
        sidebarPanel.setPreferredSize(new Dimension(200, 0));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Add logo or title
        JLabel titleLabel = new JLabel("POS System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(titleLabel);

        // Add some spacing
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Add navigation buttons
        addSidebarButton("Home", "home");
        addSidebarButton("Inventory", "inventory");
        addSidebarButton("Sales", "sales");
        addSidebarButton("Reports", "reports");
        addSidebarButton("Settings", "settings");

        // Add logout button at the bottom
        sidebarPanel.add(Box.createVerticalGlue());
        JButton logoutButton = createStyledButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout
                if (parentFrame != null) {
                    // Show confirmation dialog
                    int confirmResult = JOptionPane.showConfirmDialog(
                        DashboardPanel.this,
                        "Are you sure you want to logout?",
                        "Logout Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );

                    if (confirmResult == JOptionPane.YES_OPTION) {
                        parentFrame.showLogin();
                    }
                } else {
                    JOptionPane.showMessageDialog(DashboardPanel.this, 
                        "Logout functionality not available in this context.", 
                        "Logout", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        sidebarPanel.add(logoutButton);
    }

    private void addSidebarButton(String text, String cardName) {
        JButton button = createStyledButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, cardName);
            }
        });
        sidebarPanel.add(button);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    private void createPlaceholderPanels() {
        // Home Panel
        homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(Color.WHITE);
        JLabel homeLabel = new JLabel("Home Dashboard", SwingConstants.CENTER);
        homeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        homePanel.add(homeLabel, BorderLayout.CENTER);

        // Inventory Panel
        inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.setBackground(Color.WHITE);
        JLabel inventoryLabel = new JLabel("Inventory Management", SwingConstants.CENTER);
        inventoryLabel.setFont(new Font("Arial", Font.BOLD, 24));
        inventoryPanel.add(inventoryLabel, BorderLayout.CENTER);

        // Sales Panel
        salesPanel = new JPanel(new BorderLayout());
        salesPanel.setBackground(Color.WHITE);
        JLabel salesLabel = new JLabel("Sales Management", SwingConstants.CENTER);
        salesLabel.setFont(new Font("Arial", Font.BOLD, 24));
        salesPanel.add(salesLabel, BorderLayout.CENTER);

        // Reports Panel
        reportsPanel = new JPanel(new BorderLayout());
        reportsPanel.setBackground(Color.WHITE);
        JLabel reportsLabel = new JLabel("Reports and Analytics", SwingConstants.CENTER);
        reportsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        reportsPanel.add(reportsLabel, BorderLayout.CENTER);

        // Settings Panel
        settingsPanel = new JPanel(new BorderLayout());
        settingsPanel.setBackground(Color.WHITE);
        JLabel settingsLabel = new JLabel("System Settings", SwingConstants.CENTER);
        settingsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        settingsPanel.add(settingsLabel, BorderLayout.CENTER);
    }
}
