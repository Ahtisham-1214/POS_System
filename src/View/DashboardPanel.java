package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Controller.Product;

public class DashboardPanel extends JPanel {
    private JPanel contentPanel;
    private JPanel sidebarPanel;
    private CardLayout cardLayout;

    // Panels for different sections
    private JPanel homePanel;
    private ProductPanel inventoryPanel;
    private SalesPanel salesPanel;
    private JPanel reportsPanel;
    private JPanel settingsPanel;

    private MainFrame parentFrame;

    // Reference to the Total Products value label for dynamic updates
    private JLabel totalProductsLabel;

    // Define colors for better UI consistency
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel Blue
    private static final Color SIDEBAR_BG = new Color(35, 47, 62); // Dark blue-gray
    private static final Color SIDEBAR_HOVER = new Color(45, 57, 72); // Slightly lighter
    private static final Color SIDEBAR_SELECTED = new Color(55, 67, 82); // Even lighter
    private static final Color SIDEBAR_TEXT = new Color(240, 248, 255); // Alice Blue
    private static final Color CONTENT_BG = new Color(245, 245, 250); // Light gray-blue
    private static final Color PANEL_BG = Color.WHITE;

    // Track the currently selected button
    private JButton selectedButton;

    public DashboardPanel() {
        this(null);
    }

    public DashboardPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        // Create sidebar
        createSidebar();

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

        // Show a home panel by default.
        cardLayout.show(contentPanel, "home");

        // Add sidebar and content panel to dashboard
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void createSidebar() {
        sidebarPanel = new JPanel(new BorderLayout()); // Use BorderLayout for better control
        sidebarPanel.setBackground(SIDEBAR_BG);
        sidebarPanel.setPreferredSize(new Dimension(220, 0));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(25, 15, 25, 15));

        // Create a panel for the navigation buttons
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(SIDEBAR_BG);
        sidebarPanel.add(navPanel, BorderLayout.NORTH); // Add to the top

        // Add logo or title with improved styling
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(SIDEBAR_BG);
        logoPanel.setMaximumSize(new Dimension(220, 60));
        logoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel("POS System");
        titleLabel.setForeground(SIDEBAR_TEXT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoPanel.add(titleLabel, BorderLayout.CENTER);

        // Add a subtle border under the logo
        JPanel borderPanel = new JPanel();
        borderPanel.setBackground(new Color(70, 80, 90));
        borderPanel.setPreferredSize(new Dimension(180, 1));
        logoPanel.add(borderPanel, BorderLayout.SOUTH);

        navPanel.add(logoPanel);

        // Add some spacing
        navPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add navigation buttons with improved styling
        JButton homeButton = addSidebarButton(navPanel, "Home", "home", true);
        addSidebarButton(navPanel, "Inventory", "inventory", false);
        addSidebarButton(navPanel, "Sales", "sales", false);
        addSidebarButton(navPanel, "Reports", "reports", false);
        addSidebarButton(navPanel, "Settings", "settings", false);

        // Set the initial selected button
        selectedButton = homeButton;
        selectedButton.setBackground(SIDEBAR_SELECTED);
        selectedButton.setIcon(createCircleIcon(SIDEBAR_TEXT, 8));

        // Create a panel for the logout button with a top border
        JPanel logoutPanel = new JPanel();
        logoutPanel.setLayout(new BoxLayout(logoutPanel, BoxLayout.Y_AXIS));
        logoutPanel.setBackground(SIDEBAR_BG);
        logoutPanel.setMaximumSize(new Dimension(220, 70));
        logoutPanel.setPreferredSize(new Dimension(220, 70)); // Set preferred size
        logoutPanel.setMinimumSize(new Dimension(220, 70)); // Set minimum size
        logoutPanel.setVisible(true); // Ensure the logout panel is visible

        // Add a subtle border above the logout button
        JPanel topBorderPanel = new JPanel();
        topBorderPanel.setBackground(new Color(70, 80, 90));
        topBorderPanel.setMaximumSize(new Dimension(190, 1));
        topBorderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutPanel.add(topBorderPanel);
        logoutPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Create logout button with special styling
        JButton logoutButton = createStyledButton("Logout");
        logoutButton.setMaximumSize(new Dimension(190, 40));
        logoutButton.setPreferredSize(new Dimension(190, 40)); // Set preferred size
        logoutButton.setMinimumSize(new Dimension(190, 40)); // Set minimum size
        logoutButton.setBackground(new Color(178, 34, 34, 180)); // Semi-transparent firebrick
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setVisible(true); // Ensure the logout button is visible

        // Add hover effect
        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(new Color(178, 34, 34)); // Solid firebrick
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutButton.setBackground(new Color(178, 34, 34, 180)); // Back to semi-transparent
            }
        });

        logoutButton.addActionListener(e -> {
            // Handle logout
            if (parentFrame != null) {
                // Show confirmation dialog
                int confirmResult = JOptionPane.showConfirmDialog(
                    DashboardPanel.this,
                    "Are you sure you want to logout?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
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
        });

        logoutPanel.add(logoutButton);
        sidebarPanel.add(logoutPanel, BorderLayout.SOUTH); // Add to the bottom of the sidebar
    }

    private JButton addSidebarButton(JPanel panel, String text, String cardName, boolean isDefault) {
        JButton button = createStyledButton(text);
        button.setMaximumSize(new Dimension(190, 45));
        button.setBackground(isDefault ? SIDEBAR_SELECTED : SIDEBAR_BG);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setHorizontalAlignment(SwingConstants.LEFT);

        // Add icon placeholder (can be replaced with actual icons)
        try {
            // Try to load icon if available
            String iconPath = "/icons/" + cardName.toLowerCase() + ".png";
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            button.setIcon(icon);
        } catch (Exception e) {
            // If icon not available, use a default or no icon
            if (isDefault) {
                button.setIcon(createCircleIcon(SIDEBAR_TEXT, 8));
            }
        }

        // Add hover effect and selection tracking
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button != selectedButton) {
                    button.setBackground(SIDEBAR_HOVER);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button != selectedButton) {
                    button.setBackground(SIDEBAR_BG);
                }
            }
        });

        button.addActionListener(e -> {
            // Update selected button styling
            if (selectedButton != null) {
                selectedButton.setBackground(SIDEBAR_BG);
                selectedButton.setIcon(null); // Remove selection indicator
            }

            selectedButton = button;
            selectedButton.setBackground(SIDEBAR_SELECTED);
            selectedButton.setIcon(createCircleIcon(SIDEBAR_TEXT, 8));

            // Show the corresponding panel
            if (text.equals("Inventory")) {
                cardLayout.show(contentPanel, "inventory");
            } else {
                cardLayout.show(contentPanel, cardName);
            }
        });

        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        return button;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(SIDEBAR_TEXT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Creates a simple circle icon for selection indicator
     */
    private Icon createCircleIcon(Color color, int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(color);
                g2d.fillOval(x, y, size, size);
                g2d.dispose();
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }

    private void createPlaceholderPanels() {
        // Home Panel with improved styling
        homePanel = createStyledPanel("Home Dashboard");
        addDashboardCards(homePanel);

        // Inventory Panel - Using ProductPanel for product management
        inventoryPanel = new ProductPanel(this);

        // Sales Panel - Using SalesPanel for sales management
        salesPanel = new SalesPanel();

        // Reports Panel with improved styling
        reportsPanel = createStyledPanel("Reports and Analytics");
        addReportsPlaceholder(reportsPanel);

        // Settings Panel with improved styling
        settingsPanel = createStyledPanel("System Settings");
        addSettingsPlaceholder(settingsPanel);
    }

    /**
     * Creates a styled panel with a consistent header
     * @param title The panel title
     * @return A styled JPanel
     */
    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);

        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Add a search field to the header (non-functional placeholder)
        if (!title.equals("System Settings")) {
            JTextField searchField = new JTextField(20);
            searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            searchField.setFont(new Font("Arial", Font.PLAIN, 14));
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            searchPanel.setBackground(PRIMARY_COLOR);
            searchPanel.add(new JLabel("Search:"));
            searchPanel.add(searchField);
            headerPanel.add(searchPanel, BorderLayout.EAST);
        }

        panel.add(headerPanel, BorderLayout.NORTH);

        // Add content panel with padding
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(CONTENT_BG);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Adds dashboard cards to the home panel
     * @param panel The home panel
     */
    private void addDashboardCards(JPanel panel) {
        JPanel contentPanel = (JPanel) panel.getComponent(1);
        contentPanel.setLayout(new GridLayout(2, 2, 15, 15));

        // Add summary cards
        JPanel totalProductsCard = createSummaryCard("Total Products", String.valueOf(Product.getTotalProducts()), new Color(41, 128, 185));
        // Store reference to the value label for dynamic updates
        totalProductsLabel = (JLabel) ((JPanel)totalProductsCard.getComponent(1)).getComponent(1);

        contentPanel.add(totalProductsCard);
        contentPanel.add(createSummaryCard("Today's Sales", "$3,890", new Color(39, 174, 96)));
        contentPanel.add(createSummaryCard("Total Customers", "856", new Color(142, 68, 173)));
        contentPanel.add(createSummaryCard("Pending Orders", "12", new Color(230, 126, 34)));
    }

    /**
     * Updates the Total Products count on the dashboard
     */
    public void updateTotalProductsCount() {
        if (totalProductsLabel != null) {
            totalProductsLabel.setText(String.valueOf(Product.getTotalProducts()));
        }
    }

    /**
     * Creates a summary card for the dashboard
     * @param title The card title
     * @param value The card value
     * @param color The card accent color
     * @return A styled JPanel
     */
    private JPanel createSummaryCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(PANEL_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Add title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        titleLabel.setForeground(new Color(100, 100, 100));

        // Add value with large font
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
        valueLabel.setForeground(color);

        // Add a colored indicator bar at the top
        JPanel indicator = new JPanel();
        indicator.setBackground(color);
        indicator.setPreferredSize(new Dimension(card.getWidth(), 5));

        // Add components to card
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setBackground(PANEL_BG);
        textPanel.add(titleLabel);
        textPanel.add(valueLabel);

        card.add(indicator, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    /**
     * Adds placeholder content to the reports panel
     * @param panel The reports panel
     */
    private void addReportsPlaceholder(JPanel panel) {
        JPanel contentPanel = (JPanel) panel.getComponent(1);
        contentPanel.setLayout(new BorderLayout());

        JLabel placeholderLabel = new JLabel("Reports & Analytics Module Coming Soon", SwingConstants.CENTER);
        placeholderLabel.setFont(new Font("Arial", Font.BOLD, 18));
        placeholderLabel.setForeground(new Color(100, 100, 100));

        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        infoPanel.setBackground(PANEL_BG);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        infoPanel.add(placeholderLabel);
        infoPanel.add(new JLabel("This module will provide sales reports, inventory analytics, and business insights.", SwingConstants.CENTER));

        JButton demoButton = new JButton("View Sample Reports");
        demoButton.setFont(new Font("Arial", Font.BOLD, 14));
        demoButton.setBackground(PRIMARY_COLOR);
        demoButton.setForeground(Color.WHITE);
        demoButton.setFocusPainted(false);
        demoButton.setBorderPainted(false);
        demoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(PANEL_BG);
        buttonPanel.add(demoButton);
        infoPanel.add(buttonPanel);

        contentPanel.add(infoPanel, BorderLayout.CENTER);
    }

    /**
     * Adds placeholder content to the settings panel
     * @param panel The settings panel
     */
    private void addSettingsPlaceholder(JPanel panel) {
        JPanel contentPanel = (JPanel) panel.getComponent(1);
        contentPanel.setLayout(new BorderLayout());

        JPanel settingsContainer = new JPanel(new BorderLayout());
        settingsContainer.setBackground(PANEL_BG);
        settingsContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Create tabs for different settings categories
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));

        // General settings tab (placeholder)
        JPanel generalPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        generalPanel.setBackground(PANEL_BG);
        generalPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        generalPanel.add(createSettingRow("Company Name:", "POS System Inc."));
        generalPanel.add(createSettingRow("Currency:", "USD ($)"));
        generalPanel.add(createSettingRow("Language:", "English"));
        generalPanel.add(createSettingRow("Time Zone:", "UTC-5 (Eastern Time)"));

        // User settings tab (placeholder)
        JPanel userPanel = new JPanel();
        userPanel.setBackground(PANEL_BG);

        // Add tabs
        tabbedPane.addTab("General", generalPanel);
        tabbedPane.addTab("Users", userPanel);
        tabbedPane.addTab("Backup", new JPanel());
        tabbedPane.addTab("About", new JPanel());

        settingsContainer.add(tabbedPane, BorderLayout.CENTER);
        contentPanel.add(settingsContainer, BorderLayout.CENTER);
    }

    /**
     * Creates a setting row with label and value
     * @param label The setting label
     * @param value The setting value
     * @return A panel containing the setting row
     */
    private JPanel createSettingRow(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(PANEL_BG);

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField valueField = new JTextField(value);
        valueField.setFont(new Font("Arial", Font.PLAIN, 14));
        valueField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        panel.add(labelComponent, BorderLayout.WEST);
        panel.add(valueField, BorderLayout.CENTER);

        return panel;
    }
}
