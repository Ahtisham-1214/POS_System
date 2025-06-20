package View;

import Controller.Product;
import Controller.ProductVariant;
import Controller.UnitType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Controller.Product.products;
import static Controller.ProductVariant.productVariants;
import static Controller.UnitType.unitTypes;

public class ProductVariantPanel {

    // Define colors for better UI consistency
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel Blue
    private static final Color SECONDARY_COLOR = new Color(100, 149, 237); // Cornflower Blue
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255); // Alice Blue
    private static final Color TEXT_COLOR = new Color(50, 50, 50); // Dark Gray
    private static final Color SUCCESS_COLOR = new Color(46, 139, 87); // Sea Green
    private static final Color ERROR_COLOR = new Color(178, 34, 34); // Firebrick
    private static final Color PANEL_BACKGROUND = Color.WHITE;


    private JTextField variantIdField;
    private static final JComboBox<String> productComboBox = new JComboBox<>();
    private JTextField unitQuantityField;
    private  static JComboBox<String> variantUnitTypeComboBox =  new JComboBox<>();
    private JTextField variantPriceField;
    private JButton addVariantButton;
    private JButton updateVariantButton;
    private JButton deleteVariantButton;
    private JButton clearVariantButton;
    private JLabel variantMessageLabel;
    private JPanel panel;

    public static JComboBox<String> getVariantUnitTypeComboBox() {
        return variantUnitTypeComboBox;
    }

    public static JComboBox<String> getProductComboBox() {
        return productComboBox;
    }

    public JPanel getPanel() {
        return panel;
    }

    public ProductVariantPanel() {
            panel = new JPanel(new BorderLayout());
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
            variantMessageLabel = new JLabel("");
            variantMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            variantMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            formPanel.add(variantMessageLabel, gbc);

            // Create and style form fields
            // ID field
            JLabel idLabel = createStyledLabel("Variant ID:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.EAST;
            formPanel.add(idLabel, gbc);

            variantIdField = createStyledTextField();
            variantIdField.setText(String.valueOf(productVariants.size() + 1));
            variantIdField.setEditable(false);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            formPanel.add(variantIdField, gbc);

            // Product field
            JLabel productLabel = createStyledLabel("Product:");
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.EAST;
            formPanel.add(productLabel, gbc);

            productComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
            productComboBox.setToolTipText("Select the product");
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            formPanel.add(productComboBox, gbc);

            // Unit Quantity field
            JLabel quantityLabel = createStyledLabel("Unit Quantity:");
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.anchor = GridBagConstraints.EAST;
            formPanel.add(quantityLabel, gbc);

            unitQuantityField = createStyledTextField();
            unitQuantityField.setToolTipText("Enter the unit quantity (numeric value)");
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            formPanel.add(unitQuantityField, gbc);

            // Unit Type field
            JLabel unitTypeLabel = createStyledLabel("Unit Type:");
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.anchor = GridBagConstraints.EAST;
            formPanel.add(unitTypeLabel, gbc);

            variantUnitTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
            variantUnitTypeComboBox.setToolTipText("Select the unit type");
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            formPanel.add(variantUnitTypeComboBox, gbc);

            // Price field
            JLabel priceLabel = createStyledLabel("Price:");
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.anchor = GridBagConstraints.EAST;
            formPanel.add(priceLabel, gbc);

            variantPriceField = createStyledTextField();
            variantPriceField.setToolTipText("Enter the price (numeric value)");
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            formPanel.add(variantPriceField, gbc);

            // Buttons panel with improved styling
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
            buttonPanel.setBackground(PANEL_BACKGROUND);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

            addVariantButton = createStyledButton("Add");
            addVariantButton.setToolTipText("Add a new product variant");
            addVariantButton.setBackground(PRIMARY_COLOR);

            updateVariantButton = createStyledButton("Update");
            updateVariantButton.setToolTipText("Update the selected product variant");
            updateVariantButton.setBackground(SECONDARY_COLOR);

            deleteVariantButton = createStyledButton("Delete");
            deleteVariantButton.setToolTipText("Delete the selected product variant");
            deleteVariantButton.setBackground(new Color(220, 53, 69)); // Bootstrap danger red

            clearVariantButton = createStyledButton("Clear");
            clearVariantButton.setToolTipText("Clear all fields");
            clearVariantButton.setBackground(new Color(108, 117, 125)); // Bootstrap secondary gray

            buttonPanel.add(addVariantButton);
            buttonPanel.add(updateVariantButton);
            buttonPanel.add(deleteVariantButton);
            buttonPanel.add(clearVariantButton);

            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            formPanel.add(buttonPanel, gbc);

            // Add action listeners
            addVariantButton.addActionListener(e -> addProductVariant());

            updateVariantButton.addActionListener(e -> updateProductVariant());

            deleteVariantButton.addActionListener(e -> deleteProductVariant());

            clearVariantButton.addActionListener(e -> clearVariantFields());

            panel.add(formPanel, BorderLayout.CENTER);

            getVariantUnitTypeComboBox().removeAllItems();
            for (UnitType unitType : unitTypes) {
                getVariantUnitTypeComboBox().addItem(unitType.getName());
            }

            getProductComboBox().removeAllItems();
            for (Product product : products) {
                getProductComboBox().addItem(product.getName());
            }

    }

    private void addProductVariant() {
        if (validateVariantFields()) {
            // Disable the button temporarily to prevent double submission
            addVariantButton.setEnabled(false);

            // Show loading state
            addVariantButton.setText("Adding...");

            try {
                // Get a selected product ID
                int productId = getProductIdFromComboBox();

                // Get selected unit type ID
                int unitTypeId = getUnitTypeIdFromComboBox();

                // Create a new product variant
                int id = Integer.parseInt(variantIdField.getText().trim());
                float unitQuantity = Float.parseFloat(unitQuantityField.getText().trim());
                float price = Float.parseFloat(variantPriceField.getText().trim());

                ProductVariant variant = new ProductVariant(id, productId, unitQuantity, unitTypeId, price);
                productVariants.add(variant);

                showMessage("Product variant added successfully!", "success");
                clearVariantFields();
                variantIdField.setText(String.valueOf(productVariants.size() + 1));
            } catch (Exception ex) {
                showMessage("Error adding product variant: " + ex.getMessage(), "error");
            } finally {
                addVariantButton.setText("Add");
                addVariantButton.setEnabled(true);
            }
        }
    }

    private void updateProductVariant() {
        if (validateVariantFields()) {
            // Validate that ID is provided for update
            if (variantIdField.getText().trim().isEmpty()) {
                showMessage("Please enter a variant ID to update", "error");
                highlightErrorField(variantIdField);
                return;
            }

            // Disable the button temporarily
            updateVariantButton.setEnabled(false);
            updateVariantButton.setText("Updating...");

            try {
                int id = Integer.parseInt(variantIdField.getText().trim());
                int productId = getProductIdFromComboBox();
                float unitQuantity = Float.parseFloat(unitQuantityField.getText().trim());
                int unitTypeId = getUnitTypeIdFromComboBox();
                float price = Float.parseFloat(variantPriceField.getText().trim());

                // Find and update the product variant
                boolean found = false;
                for (int i = 0; i < productVariants.size(); i++) {
                    if (productVariants.get(i).getId() == id) {
                        productVariants.set(i, new ProductVariant(id, productId, unitQuantity, unitTypeId, price));
                        found = true;
                        break;
                    }
                }

                if (found) {
                    showMessage("Product variant updated successfully!", "success");
                } else {
                    showMessage("Product variant with ID " + id + " not found", "error");
                }
            } catch (Exception ex) {
                showMessage("Error updating product variant: " + ex.getMessage(), "error");
            } finally {
                updateVariantButton.setText("Update");
                updateVariantButton.setEnabled(true);
            }
        }
    }

    private void deleteProductVariant() {
        if (variantIdField.getText().trim().isEmpty()) {
            showMessage("Please enter a variant ID to delete", "error");
            highlightErrorField(variantIdField);
            return;
        }

        // Show confirmation dialog
        int result = JOptionPane.showConfirmDialog(
                this.panel,
                "Are you sure you want to delete this product variant?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            // Disable the button temporarily
            deleteVariantButton.setEnabled(false);
            deleteVariantButton.setText("Deleting...");

            try {
                int id = Integer.parseInt(variantIdField.getText().trim());

                // Find and remove the product variant
                boolean found = false;
                for (int i = 0; i < productVariants.size(); i++) {
                    if (productVariants.get(i).getId() == id) {
                        productVariants.remove(i);
                        found = true;
                        break;
                    }
                }

                if (found) {
                    showMessage("Product variant deleted successfully!", "success");
                    clearVariantFields();
                    variantIdField.setText(String.valueOf(productVariants.size()));

                } else {
                    showMessage("Product variant with ID " + id + " not found", "error");
                }
            } catch (Exception ex) {
                showMessage("Error deleting product variant: " + ex.getMessage(), "error");
            } finally {
                deleteVariantButton.setText("Delete");
                deleteVariantButton.setEnabled(true);
            }
        }
    }

    private void clearVariantFields() {
        // Clear all input fields
//        variantIdField.setText("");
        unitQuantityField.setText("");
        variantPriceField.setText("");

        // Reset combo boxes to the first item if available
        if (productComboBox.getItemCount() > 0) {
            productComboBox.setSelectedIndex(0);
        }

        if (variantUnitTypeComboBox.getItemCount() > 0) {
            variantUnitTypeComboBox.setSelectedIndex(0);
        }

        // Clear message only if it's not a success message
        if (!variantMessageLabel.getForeground().equals(SUCCESS_COLOR)) {
            variantMessageLabel.setText("");
        }

        // Set focus to ID field
        productComboBox.requestFocusInWindow();
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

    private void showMessage(String message, String type) {
        JLabel messageLabel;
            messageLabel = variantMessageLabel;

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

    private boolean validateVariantFields() {
        // Basic validation
        if (variantIdField.getText().trim().isEmpty()) {
            showMessage("Variant ID cannot be empty", "error");
            highlightErrorField(variantIdField);
            return false;
        }

        try {
            Integer.parseInt(variantIdField.getText().trim());
        } catch (NumberFormatException e) {
            showMessage("Variant ID must be a number", "error");
            highlightErrorField(variantIdField);
            return false;
        }

        if (productComboBox.getSelectedIndex() == -1) {
            showMessage("Please select a product", "error");
            return false;
        }

        if (unitQuantityField.getText().trim().isEmpty()) {
            showMessage("Unit quantity cannot be empty", "error");
            highlightErrorField(unitQuantityField);
            return false;
        }

        try {
            Float.parseFloat(unitQuantityField.getText().trim());
        } catch (NumberFormatException e) {
            showMessage("Unit quantity must be a number", "error");
            highlightErrorField(unitQuantityField);
            return false;
        }

        if (variantUnitTypeComboBox.getSelectedIndex() == -1) {
            showMessage("Please select a unit type", "error");
            return false;
        }

        if (variantPriceField.getText().trim().isEmpty()) {
            showMessage("Price cannot be empty", "error");
            highlightErrorField(variantPriceField);
            return false;
        }

        try {
            Float.parseFloat(variantPriceField.getText().trim());
        } catch (NumberFormatException e) {
            showMessage("Price must be a number", "error");
            highlightErrorField(variantPriceField);
            return false;
        }

        return true;
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

    private int getProductIdFromComboBox() {
        String selected = (String) productComboBox.getSelectedItem();
        if (selected == null || selected.isEmpty()) {
            return -1;
        }

        // Extract ID from the format "ID - Name"
        return Integer.parseInt(selected.split(" - ")[0]);
    }

    private int getUnitTypeIdFromComboBox() {
        JComboBox<String> comboBox = variantUnitTypeComboBox;
        String selected = (String) comboBox.getSelectedItem();
        if (selected == null || selected.isEmpty()) {
            return -1;
        }

        // Extract ID from the format "ID - Name"
        return Integer.parseInt(selected.split(" - ")[0]);
    }

}

