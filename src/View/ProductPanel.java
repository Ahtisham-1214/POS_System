package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductPanel extends JPanel {
    // UI Components
    private JTextField idField;
    private JTextField nameField;
    private JTextField quantityField;
    private JTextField categoryField;
    private JTextField unitPriceField;

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;

    private JLabel messageLabel;

    // Define colors for better UI consistency
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel Blue
    private static final Color SECONDARY_COLOR = new Color(100, 149, 237); // Cornflower Blue
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255); // Alice Blue
    private static final Color TEXT_COLOR = new Color(50, 50, 50); // Dark Gray
    private static final Color SUCCESS_COLOR = new Color(46, 139, 87); // Sea Green
    private static final Color ERROR_COLOR = new Color(178, 34, 34); // Firebrick
    private static final Color PANEL_BACKGROUND = Color.WHITE;

    public ProductPanel() {
        initializeUI();
    }

    private void initializeUI() {
        // Set main panel properties
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        // Create title panel with improved styling
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(PRIMARY_COLOR);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        titlePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Product Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Create the main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a form panel with card-like appearance
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PANEL_BACKGROUND);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Message label for feedback with improved styling
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(messageLabel, gbc);

        // Create and style form fields
        // ID field
        JLabel idLabel = createStyledLabel("Product ID:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(idLabel, gbc);

        idField = createStyledTextField();
        idField.setToolTipText("Enter the product ID");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(idField, gbc);

        // Name field
        JLabel nameLabel = createStyledLabel("Product Name:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nameLabel, gbc);

        nameField = createStyledTextField();
        nameField.setToolTipText("Enter the product name");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameField, gbc);

        // Quantity field
        JLabel quantityLabel = createStyledLabel("Quantity:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(quantityLabel, gbc);

        quantityField = createStyledTextField();
        quantityField.setToolTipText("Enter the product quantity (numeric value)");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(quantityField, gbc);

        // Category field
        JLabel categoryLabel = createStyledLabel("Category:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(categoryLabel, gbc);

        categoryField = createStyledTextField();
        categoryField.setToolTipText("Enter the product category");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(categoryField, gbc);

        // Unit Price field
        JLabel priceLabel = createStyledLabel("Unit Price:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(priceLabel, gbc);

        unitPriceField = createStyledTextField();
        unitPriceField.setToolTipText("Enter the unit price (numeric value)");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(unitPriceField, gbc);

        // Buttons panel with improved styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(PANEL_BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        addButton = createStyledButton("Add");
        addButton.setToolTipText("Add a new product");
        addButton.setBackground(PRIMARY_COLOR);

        updateButton = createStyledButton("Update");
        updateButton.setToolTipText("Update the selected product");
        updateButton.setBackground(SECONDARY_COLOR);

        deleteButton = createStyledButton("Delete");
        deleteButton.setToolTipText("Delete the selected product");
        deleteButton.setBackground(new Color(220, 53, 69)); // Bootstrap danger red

        clearButton = createStyledButton("Clear");
        clearButton.setToolTipText("Clear all fields");
        clearButton.setBackground(new Color(108, 117, 125)); // Bootstrap secondary gray

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        // Add a form panel to the content panel
        contentPanel.add(formPanel, BorderLayout.CENTER);

        // Add components to the main panel
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * Creates a styled label with consistent formatting
     * @param text The label text
     * @return A styled JLabel
     */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    /**
     * Creates a styled text field with consistent formatting
     * @return A styled JTextField
     */
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    /**
     * Creates a styled button with consistent formatting
     * @param text The button text
     * @return A styled JButton
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(110, 35));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(button.getBackground().darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(button.getBackground().brighter());
            }
        });

        return button;
    }

    /**
     * Displays a message with the specified type (success, error, info)
     * @param message The message to display
     * @param type The message type (success, error, info)
     */
    private void showMessage(String message, String type) {
        messageLabel.setText(message);

        switch (type.toLowerCase()) {
            case "success":
                messageLabel.setForeground(SUCCESS_COLOR);
                break;
            case "error":
                messageLabel.setForeground(ERROR_COLOR);
                break;
            default:
                messageLabel.setForeground(TEXT_COLOR);
                break;
        }

        // Auto-hide message after 5 seconds for success messages
        if (type.equalsIgnoreCase("success")) {
            Timer timer = new Timer(5000, e -> messageLabel.setText(""));
            timer.setRepeats(false);
            timer.start();
        }
    }

    /**
     * Highlights a field with an error state
     * @param field The field to highlight
     */
    private void highlightErrorField(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ERROR_COLOR, 2),
            BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));

        // Reset border after 2 seconds
        Timer timer = new Timer(2000, e -> {
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        });
        timer.setRepeats(false);
        timer.start();

        // Set focus to the field
        field.requestFocusInWindow();
    }

    private void addProduct() {
        // Placeholder to add product functionality
        if (validateFields()) {
            // Disable the button temporarily to prevent double submission
            addButton.setEnabled(false);

            // Show loading state
            addButton.setText("Adding...");

            // Simulate processing delay (remove in actual implementation)
            Timer timer = new Timer(800, e -> {
                showMessage("Product added successfully!", "success");
                addButton.setText("Add");
                addButton.setEnabled(true);

                // Clear fields after successful add
                clearFields();

                // Add actual implementation here
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void updateProduct() {
        // Placeholder for update product functionality
        if (validateFields()) {
            // Validate that ID is provided for update
            if (idField.getText().trim().isEmpty()) {
                showMessage("Please enter a product ID to update", "error");
                highlightErrorField(idField);
                return;
            }

            // Disable button temporarily
            updateButton.setEnabled(false);
            updateButton.setText("Updating...");

            // Simulate processing delay (remove in actual implementation)
            Timer timer = new Timer(800, e -> {
                showMessage("Product updated successfully!", "success");
                updateButton.setText("Update");
                updateButton.setEnabled(true);

                // Add actual implementation here
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void deleteProduct() {
        // Placeholder for delete product functionality
        if (!idField.getText().trim().isEmpty()) {
            // Show confirmation dialog
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this product?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                // Disable button temporarily
                deleteButton.setEnabled(false);
                deleteButton.setText("Deleting...");

                // Simulate processing delay (remove in actual implementation)
                Timer timer = new Timer(800, e -> {
                    showMessage("Product deleted successfully!", "success");
                    deleteButton.setText("Delete");
                    deleteButton.setEnabled(true);

                    // Clear fields after successful delete
                    clearFields();

                    // Add actual implementation here
                });
                timer.setRepeats(false);
                timer.start();
            }
        } else {
            showMessage("Please enter a product ID to delete", "error");
            highlightErrorField(idField);
        }
    }

    private void clearFields() {
        // Clear all input fields
        idField.setText("");
        nameField.setText("");
        quantityField.setText("");
        categoryField.setText("");
        unitPriceField.setText("");

        // Clear message only if it's not a success message
        if (!messageLabel.getForeground().equals(SUCCESS_COLOR)) {
            messageLabel.setText("");
        }

        // Set focus to ID field
        idField.requestFocusInWindow();
    }

    private boolean validateFields() {
        // Basic validation
        if (nameField.getText().trim().isEmpty()) {
            showMessage("Product name cannot be empty", "error");
            highlightErrorField(nameField);
            return false;
        }

        try {
            if (!quantityField.getText().trim().isEmpty()) {
                Integer.parseInt(quantityField.getText().trim());
            }
        } catch (NumberFormatException e) {
            showMessage("Quantity must be a number", "error");
            highlightErrorField(quantityField);
            return false;
        }

        try {
            if (!unitPriceField.getText().trim().isEmpty()) {
                Double.parseDouble(unitPriceField.getText().trim());
            }
        } catch (NumberFormatException e) {
            showMessage("Unit price must be a number", "error");
            highlightErrorField(unitPriceField);
            return false;
        }

        return true;
    }
}
