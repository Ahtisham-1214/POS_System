package View;

import Controller.Category;
import Controller.Product;
import Controller.UnitType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Controller.Product.products;
import static Controller.UnitType.unitTypes;

public class ProductPanel extends JPanel {
    // UI Components
    private JTabbedPane tabbedPane;

    // Product tab components
    private JPanel productPanel;
    private JTextField productIdField;
    private JTextField productNameField;
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> unitTypeComboBox;
    private JButton addProductButton;
    private JButton updateProductButton;
    private JButton deleteProductButton;
    private JButton clearProductButton;
    private JLabel productMessageLabel;

    // Product Variant tab components
    private JPanel variantPanel;

    // Category tab components
    private JPanel categoryPanel;

    // Unit Type tab components
    private JPanel unitTypePanel;

    // Reference to the dashboard panel for updating product count
    private DashboardPanel dashboardPanel;


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
        loadData();
    }

    /**
     * Constructor that accepts a dashboard panel for updating product count
     * @param dashboardPanel The dashboard panel to update
     */
    public ProductPanel(DashboardPanel dashboardPanel) {
        this.dashboardPanel = dashboardPanel;
        initializeUI();
        loadData();
    }

    private void loadData() {
        // TODO: Load data from database
        // This is a placeholder for loading categories, unit types, products, and product variants

        // Example data for testing

        // Update combo boxes
        new CategoryPanel().updateCategoryComboBox();
//        updateUnitTypeComboBoxes();
        unitTypeComboBox.removeAllItems();

        for (UnitType unitType : unitTypes) {
            String item = unitType.getName();
            unitTypeComboBox.addItem(item);
        }
    }


    private void updateProductComboBox() {
        ProductVariantPanel.getProductComboBox().removeAllItems();
        for (Product product : products) {
            ProductVariantPanel.getProductComboBox().addItem(product.getName());
        }
    }

    private void initializeUI() {
        // Set main panel properties
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        // Create a title panel with improved styling
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(PRIMARY_COLOR);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        titlePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Product Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Create a tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));
        tabbedPane.setBackground(BACKGROUND_COLOR);

        // Create product panel
        productPanel = createProductPanel();
        tabbedPane.addTab("Products", productPanel);

        // Create variant panel
        variantPanel = new ProductVariantPanel().getPanel();
        tabbedPane.addTab("Product Variants", variantPanel);

        // Create category panel
        categoryPanel = new CategoryPanel().getPanel();
        tabbedPane.addTab("Categories", categoryPanel);

        // Create a unit type panel
        unitTypePanel = new UnitTypePanel().getPanel();
        tabbedPane.addTab("Unit Types", unitTypePanel);

        // Add components to the main panel
        add(titlePanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
        productMessageLabel = new JLabel("");
        productMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        productMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(productMessageLabel, gbc);

        // Create and style form fields
        // ID field
        JLabel idLabel = createStyledLabel("Product ID:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(idLabel, gbc);

        productIdField = createStyledTextField();
        productIdField.setText(getId());
        productIdField.setEditable(false);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(productIdField, gbc);

        // Name field
        JLabel nameLabel = createStyledLabel("Product Name:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nameLabel, gbc);

        productNameField = createStyledTextField();
        productNameField.setToolTipText("Enter the product name");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(productNameField, gbc);

        // Category field
        JLabel categoryLabel = createStyledLabel("Category:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(categoryLabel, gbc);

        categoryComboBox = CategoryPanel.getCategoryComboBox();
        categoryComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryComboBox.setToolTipText("Select the product category");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(categoryComboBox, gbc);

        // Unit Type field
        JLabel unitTypeLabel = createStyledLabel("Unit Type:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(unitTypeLabel, gbc);

        unitTypeComboBox = UnitTypePanel.getUnitTypeComboBox();
        unitTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        unitTypeComboBox.setToolTipText("Select the unit type");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(unitTypeComboBox, gbc);


        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Buttons panel with improved styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(PANEL_BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        addProductButton = createStyledButton("Add");
        addProductButton.setToolTipText("Add a new product");
        addProductButton.setBackground(PRIMARY_COLOR);

        updateProductButton = createStyledButton("Update");
        updateProductButton.setToolTipText("Update the selected product");
        updateProductButton.setBackground(SECONDARY_COLOR);

        deleteProductButton = createStyledButton("Delete");
        deleteProductButton.setToolTipText("Delete the selected product");
        deleteProductButton.setBackground(new Color(220, 53, 69)); // Bootstrap danger red

        clearProductButton = createStyledButton("Clear");
        clearProductButton.setToolTipText("Clear all fields");
        clearProductButton.setBackground(new Color(108, 117, 125)); // Bootstrap secondary gray

        buttonPanel.add(addProductButton);
        buttonPanel.add(updateProductButton);
        buttonPanel.add(deleteProductButton);
        buttonPanel.add(clearProductButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        // Add action listeners
        addProductButton.addActionListener(e -> addProduct());

        updateProductButton.addActionListener(e -> updateProduct());

        deleteProductButton.addActionListener(e -> deleteProduct());

        clearProductButton.addActionListener(e -> clearProductFields());

        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
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
        JLabel messageLabel = productMessageLabel;

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
        if (validateProductFields()) {
            // Disable the button temporarily to prevent double submission
            addProductButton.setEnabled(false);

            // Show loading state
            addProductButton.setText("Adding...");

            try {
                // Get the selected category ID
                int categoryId = getCategoryIdFromComboBox();

                // Get selected unit type ID
                int unitTypeId = getUnitTypeIdFromComboBox();

                // Create a new product
                int id = Integer.parseInt(productIdField.getText().trim());
                String name = productNameField.getText().trim();

                Product product = new Product(id, name, categoryId, unitTypeId);
                products.add(product);

                // Update product combo box in variant tab
                updateProductComboBox();

                // Update dashboard product count if dashboard panel is available
                if (dashboardPanel != null) {
                    dashboardPanel.updateTotalProductsCount();
                }

                showMessage("Product added successfully!", "success");
            } catch (Exception ex) {
                showMessage("Error adding product: " + ex.getMessage(), "error");
            } finally {
                addProductButton.setText("Add");
                addProductButton.setEnabled(true);
                clearProductFields();
                productIdField.setText(getId());
                productNameField.requestFocusInWindow();
            }
        }
    }

    private void updateProduct() {
        if (validateProductFields()) {
            // Validate that ID is provided for update
            if (productIdField.getText().trim().isEmpty()) {
                showMessage("Please enter a product ID to update", "error");
                highlightErrorField(productIdField);
                return;
            }

            // Disable the button temporarily
            updateProductButton.setEnabled(false);
            updateProductButton.setText("Updating...");

            try {
                int id = Integer.parseInt(productIdField.getText().trim());
                String name = productNameField.getText().trim();
                int categoryId = getCategoryIdFromComboBox();
                int unitTypeId = getUnitTypeIdFromComboBox();

                // Find and update the product
                boolean found = false;
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getId() == id) {
                        products.set(i, new Product(id, name, categoryId, unitTypeId));
                        found = true;
                        break;
                    }
                }

                if (found) {
                    // Update product combo box in variant tab
                    updateProductComboBox();

                    // Update dashboard product count if dashboard panel is available
                    if (dashboardPanel != null) {
                        dashboardPanel.updateTotalProductsCount();
                    }

                    showMessage("Product updated successfully!", "success");
                } else {
                    showMessage("Product with ID " + id + " not found", "error");
                }
            } catch (Exception ex) {
                showMessage("Error updating product: " + ex.getMessage(), "error");
            } finally {
                updateProductButton.setText("Update");
                updateProductButton.setEnabled(true);
                clearProductFields();
                productIdField.setText(getId());
                productNameField.requestFocusInWindow();
            }
        }
    }

    private void deleteProduct() {
        if (productIdField.getText().trim().isEmpty()) {
            showMessage("Please enter a product ID to delete", "error");
            highlightErrorField(productIdField);
            return;
        }

        // Show confirmation dialog
        int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this product?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            // Disable the button temporarily
            deleteProductButton.setEnabled(false);
            deleteProductButton.setText("Deleting...");

            try {
                int id = Integer.parseInt(productIdField.getText().trim());

                // Find and remove the product
                boolean found = false;
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getId() == id) {
                        products.remove(i);
                        found = true;
                        break;
                    }
                }

                if (found) {
                    // Update product combo box in variant tab
                    updateProductComboBox();

                    // Update dashboard product count if dashboard panel is available
                    if (dashboardPanel != null) {
                        dashboardPanel.updateTotalProductsCount();
                    }

                    showMessage("Product deleted successfully!", "success");
                } else {
                    showMessage("Product with ID " + id + " not found", "error");
                }
            } catch (Exception ex) {
                showMessage("Error deleting product: " + ex.getMessage(), "error");
            } finally {
                deleteProductButton.setText("Delete");
                deleteProductButton.setEnabled(true);
                clearProductFields();
                productIdField.setText(getId());
                productNameField.requestFocusInWindow();
            }
        }
    }


    private void clearProductFields() {
        // Clear all input fields
        productIdField.setText(getId());
        productNameField.setText("");

        // Reset combo boxes to the first item if available
        if (categoryComboBox.getItemCount() > 0) {
            categoryComboBox.setSelectedIndex(0);
        }

        if (unitTypeComboBox.getItemCount() > 0) {
            unitTypeComboBox.setSelectedIndex(0);
        }

        // Clear message only if it's not a success message
        if (!productMessageLabel.getForeground().equals(SUCCESS_COLOR)) {
            productMessageLabel.setText("");
        }

        // Set focus to ID field
        productNameField.requestFocusInWindow();
    }


    private boolean validateProductFields() {
        // Basic validation
        if (productIdField.getText().trim().isEmpty()) {
            showMessage("Product ID cannot be empty", "error");
            highlightErrorField(productIdField);
            return false;
        }

        try {
            Integer.parseInt(productIdField.getText().trim());
        } catch (NumberFormatException e) {
            showMessage("Product ID must be a number", "error");
            highlightErrorField(productIdField);
            return false;
        }

        if (productNameField.getText().trim().isEmpty()) {
            showMessage("Product name cannot be empty", "error");
            highlightErrorField(productNameField);
            return false;
        }

        if (categoryComboBox.getSelectedIndex() == -1) {
            showMessage("Please select a category", "error");
            return false;
        }

        if (unitTypeComboBox.getSelectedIndex() == -1) {
            showMessage("Please select a unit type", "error");
            return false;
        }
        return true;
    }


    private int getCategoryIdFromComboBox() {
        String selected = (String) categoryComboBox.getSelectedItem();
        if (selected == null || selected.isEmpty()) {
            return -1;
        }
        try {
            // First check if the selected value is just the unit type name (like "ml")
            if (!selected.contains(" - ")) {
                return findCategoryTypeIdByName(selected);
            }

            // If it's in "ID - Name" format, extract the ID
            return Integer.parseInt(selected.split(" - ")[0]);
        } catch (NumberFormatException e) {
            return -1;
        }

    }

    private int getUnitTypeIdFromComboBox() {
        String selected = (String) unitTypeComboBox.getSelectedItem();
        if (selected == null || selected.isEmpty()) {
            return -1;
        }
        try {
            // First check if the selected value is just the unit type name (like "ml")
            if (!selected.contains(" - ")) {
                return findUnitTypeIdByName(selected);
            }

            // If it's in "ID - Name" format, extract the ID
            return Integer.parseInt(selected.split(" - ")[0]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    private int findUnitTypeIdByName(String unitTypeName) {
        // You'll need to implement this based on your UnitType data structure
        // This is just an example - adjust according to your actual UnitType implementation
        for (UnitType unitType : unitTypes) {
            if (unitType.getName().equalsIgnoreCase(unitTypeName)) {
                return unitType.getId();
            }
        }
        return -1;
    }

    private int findCategoryTypeIdByName(String categoryName) {
        // You'll need to implement this based on your UnitType data structure
        // This is just an example - adjust according to your actual UnitType implementation
        for (Category category : Category.categories) {
            if (category.getName().equalsIgnoreCase(categoryName)) {
                return category.getId();
            }
        }
        return -1;
    }

    private String getId(){
        if (!products.isEmpty()){
            return String.valueOf(products.getLast().getId() + 1);
        }
        return "1";
    }

}
