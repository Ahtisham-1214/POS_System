package View;

import Controller.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Controller.Category.categories;

// Define colors for better UI consistency

public class CategoryPanel {
    private JPanel panel;
    private JTextField categoryIdField;
    private JTextField categoryNameField;
    private JButton addCategoryButton;
    private JButton updateCategoryButton;
    private JButton deleteCategoryButton;
    private JButton clearCategoryButton;
    private JLabel categoryMessageLabel;
    private static JComboBox<String> categoryComboBox = new JComboBox<>();



    private static final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel Blue
    private static final Color SECONDARY_COLOR = new Color(100, 149, 237); // Cornflower Blue
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255); // Alice Blue
    private static final Color TEXT_COLOR = new Color(50, 50, 50); // Dark Gray
    private static final Color SUCCESS_COLOR = new Color(46, 139, 87); // Sea Green
    private static final Color ERROR_COLOR = new Color(178, 34, 34); // Firebrick
    private static final Color PANEL_BACKGROUND = Color.WHITE;

    public JPanel getPanel() {
        return panel;
    }

    public static JComboBox<String> getCategoryComboBox() {
        return categoryComboBox;
    }
    public static void setCategoryComboBox(JComboBox<String> categoryComboBox) {
        CategoryPanel.categoryComboBox = categoryComboBox;
    }

    public CategoryPanel() {
        this.panel = new JPanel();
        this.panel.setLayout(new BorderLayout());
        this.panel.setBackground(BACKGROUND_COLOR);
        this.panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a form panel with a card-like appearance
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
        categoryMessageLabel = new JLabel("");
        categoryMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(categoryMessageLabel, gbc);

        // Create and style form fields
        // ID field
        JLabel idLabel = createStyledLabel("Category ID:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(idLabel, gbc);

        categoryIdField = createStyledTextField();
//        categoryIdField.setEditable(false);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(categoryIdField, gbc);

        // Name field
        JLabel nameLabel = createStyledLabel("Category Name:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nameLabel, gbc);

        categoryNameField = createStyledTextField();
        categoryNameField.setToolTipText("Enter the category name");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(categoryNameField, gbc);

        // Buttons panel with improved styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(PANEL_BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        addCategoryButton = createStyledButton("Add");
        addCategoryButton.setToolTipText("Add a new category");
        addCategoryButton.setBackground(PRIMARY_COLOR);

        updateCategoryButton = createStyledButton("Update");
        updateCategoryButton.setToolTipText("Update the selected category");
        updateCategoryButton.setBackground(SECONDARY_COLOR);

        deleteCategoryButton = createStyledButton("Delete");
        deleteCategoryButton.setToolTipText("Delete the selected category");
        deleteCategoryButton.setBackground(new Color(220, 53, 69)); // Bootstrap danger red

        clearCategoryButton = createStyledButton("Clear");
        clearCategoryButton.setToolTipText("Clear all fields");
        clearCategoryButton.setBackground(new Color(108, 117, 125)); // Bootstrap secondary gray

        buttonPanel.add(addCategoryButton);
        buttonPanel.add(updateCategoryButton);
        buttonPanel.add(deleteCategoryButton);
        buttonPanel.add(clearCategoryButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        // Add action listeners
        addCategoryButton.addActionListener(e -> addCategory());

        updateCategoryButton.addActionListener(e -> updateCategory());

        deleteCategoryButton.addActionListener(e -> deleteCategory());

        clearCategoryButton.addActionListener(e -> clearCategoryFields());

        this.panel.add(formPanel, BorderLayout.CENTER);


    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

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

    private void addCategory() {
        if (validateCategoryFields()) {
            // Disable the button temporarily to prevent double submission
            addCategoryButton.setEnabled(false);

            // Show loading state
            addCategoryButton.setText("Adding...");

            try {
                // Create a new category
                String name = categoryNameField.getText().trim();

                Category category = new Category(name);
                categories.add(category);

                // Update category combo box in the product tab
                updateCategoryComboBox();

                showMessage("Category added successfully!", "success", false);
                clearCategoryFields();
            } catch (Exception ex) {
                showMessage("Error adding category: " + ex.getMessage(), "error", false);
            } finally {
                addCategoryButton.setText("Add");
                addCategoryButton.setEnabled(true);
            }
        }
    }

    private void updateCategory() {
        if (validateCategoryFields()) {
            // Validate that ID is provided for update
            if (categoryIdField.getText().trim().isEmpty()) {
                showMessage("Please enter a category ID to update", "error", false);
                highlightErrorField(categoryIdField);
                return;
            }

            // Disable the button temporarily
            updateCategoryButton.setEnabled(false);
            updateCategoryButton.setText("Updating...");

            try {
                int id = Integer.parseInt(categoryIdField.getText().trim());
                String name = categoryNameField.getText().trim();

                // Find and update the category
                boolean found = false;
                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).getId() == id) {
                        categories.set(i, new Category(id, name));
                        found = true;
                        break;
                    }
                }

                if (found) {
                    // Update category combo box in the product tab
                    updateCategoryComboBox();
                    showMessage("Category updated successfully!", "success", false);
                } else {
                    showMessage("Category with ID " + id + " not found", "error", false);
                }
            } catch (Exception ex) {
                showMessage("Error updating category: " + ex.getMessage(), "error", false);
            } finally {
                updateCategoryButton.setText("Update");
                updateCategoryButton.setEnabled(true);
            }
        }
    }

    private void deleteCategory() {
        if (categoryIdField.getText().trim().isEmpty()) {
            showMessage("Please enter a category ID to delete", "error", false);
            highlightErrorField(categoryIdField);
            return;
        }

        // Show confirmation dialog
        int result = JOptionPane.showConfirmDialog(
                this.panel,
                "Are you sure you want to delete this category?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            // Disable the button temporarily
            deleteCategoryButton.setEnabled(false);
            deleteCategoryButton.setText("Deleting...");

            try {
                int id = Integer.parseInt(categoryIdField.getText().trim());

                // Find and remove the category
                boolean found = false;
                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).getId() == id) {
                        categories.remove(i);
                        found = true;
                        break;
                    }
                }

                if (found) {
                    // Update category combo box in the product tab
                    updateCategoryComboBox();
                    showMessage("Category deleted successfully!", "success", false);
                    clearCategoryFields();
                } else {
                    showMessage("Category with ID " + id + " not found", "error", false);
                }
            } catch (Exception ex) {
                showMessage("Error deleting category: " + ex.getMessage(), "error", false);
            } finally {
                deleteCategoryButton.setText("Delete");
                deleteCategoryButton.setEnabled(true);
            }
        }
    }

    private void clearCategoryFields() {
        // Clear all input fields
        categoryIdField.setText("");
        categoryNameField.setText("");

        // Clear message only if it's not a success message
        if (!categoryMessageLabel.getForeground().equals(SUCCESS_COLOR)) {
            categoryMessageLabel.setText("");
        }

        // Set focus to ID field
        categoryIdField.requestFocusInWindow();
    }

    private boolean validateCategoryFields() {
        // Basic validation

        if (categoryNameField.getText().trim().isEmpty()) {
            showMessage("Category name cannot be empty", "error", false);
            highlightErrorField(categoryNameField);
            return false;
        }

        return true;
    }

    public void updateCategoryComboBox() {
        categoryComboBox.removeAllItems();
        for (Category category : categories) {
            categoryComboBox.addItem(category.getName());
        }
    }

    private void showMessage(String message, String type, boolean isProductTab) {
        JLabel messageLabel;
          // Category tab
            messageLabel = categoryMessageLabel;

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

}
