package View;

import Controller.Category;
import Controller.Product;
import Controller.ProductVariant;
import Controller.UnitType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
    private JTextField variantIdField;
    private JComboBox<String> productComboBox;
    private JTextField unitQuantityField;
    private JComboBox<String> variantUnitTypeComboBox;
    private JTextField variantPriceField;
    private JButton addVariantButton;
    private JButton updateVariantButton;
    private JButton deleteVariantButton;
    private JButton clearVariantButton;
    private JLabel variantMessageLabel;
    
    // Lists to store data
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<UnitType> unitTypes = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<ProductVariant> productVariants = new ArrayList<>();
    
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
    
    private void loadData() {
        // TODO: Load data from database
        // This is a placeholder for loading categories, unit types, products, and product variants
        
        // Example data for testing
        categories.add(new Category(1, "Beverages"));
        categories.add(new Category(2, "Bakery"));
        categories.add(new Category(3, "Dairy"));
        
        unitTypes.add(new UnitType(1, "Kilogram", 1.0f));
        unitTypes.add(new UnitType(2, "Gram", 0.001f));
        unitTypes.add(new UnitType(3, "Liter", 1.0f));
        unitTypes.add(new UnitType(4, "Milliliter", 0.001f));
        
        // Update combo boxes
        updateCategoryComboBox();
        updateUnitTypeComboBoxes();
    }
    
    private void updateCategoryComboBox() {
        categoryComboBox.removeAllItems();
        for (Category category : categories) {
            categoryComboBox.addItem(category.getName());
        }
    }
    
    private void updateUnitTypeComboBoxes() {
        unitTypeComboBox.removeAllItems();
        variantUnitTypeComboBox.removeAllItems();
        
        for (UnitType unitType : unitTypes) {
            String item = unitType.getName();
            unitTypeComboBox.addItem(item);
            variantUnitTypeComboBox.addItem(item);
        }
    }
    
    private void updateProductComboBox() {
        productComboBox.removeAllItems();
        for (Product product : products) {
            productComboBox.addItem(product.getId() + " - " + product.getName());
        }
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
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));
        tabbedPane.setBackground(BACKGROUND_COLOR);
        
        // Create product panel
        productPanel = createProductPanel();
        tabbedPane.addTab("Products", productPanel);
        
        // Create variant panel
        variantPanel = createVariantPanel();
        tabbedPane.addTab("Product Variants", variantPanel);
        
        // Add components to the main panel
        add(titlePanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
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
        productIdField.setToolTipText("Enter the product ID");
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
        
        categoryComboBox = new JComboBox<>();
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
        
        unitTypeComboBox = new JComboBox<>();
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
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        
        updateProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });
        
        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });
        
        clearProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearProductFields();
            }
        });
        
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createVariantPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
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
        variantIdField.setToolTipText("Enter the variant ID");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(variantIdField, gbc);
        
        // Product field
        JLabel productLabel = createStyledLabel("Product:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(productLabel, gbc);
        
        productComboBox = new JComboBox<>();
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
        
        variantUnitTypeComboBox = new JComboBox<>();
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
        addVariantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProductVariant();
            }
        });
        
        updateVariantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProductVariant();
            }
        });
        
        deleteVariantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProductVariant();
            }
        });
        
        clearVariantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearVariantFields();
            }
        });
        
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
     * @param isProductTab Whether the message is for the product tab
     */
    private void showMessage(String message, String type, boolean isProductTab) {
        JLabel messageLabel = isProductTab ? productMessageLabel : variantMessageLabel;
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
                // Get selected category ID
                int categoryId = getCategoryIdFromComboBox();
                
                // Get selected unit type ID
                int unitTypeId = getUnitTypeIdFromComboBox(true);
                
                // Create new product
                int id = Integer.parseInt(productIdField.getText().trim());
                String name = productNameField.getText().trim();

                Product product = new Product(id, name, categoryId, unitTypeId);
                products.add(product);
                
                // Update product combo box in variant tab
                updateProductComboBox();
                
                showMessage("Product added successfully!", "success", true);
                clearProductFields();
            } catch (Exception ex) {
                showMessage("Error adding product: " + ex.getMessage(), "error", true);
            } finally {
                addProductButton.setText("Add");
                addProductButton.setEnabled(true);
            }
        }
    }
    
    private void updateProduct() {
        if (validateProductFields()) {
            // Validate that ID is provided for update
            if (productIdField.getText().trim().isEmpty()) {
                showMessage("Please enter a product ID to update", "error", true);
                highlightErrorField(productIdField);
                return;
            }
            
            // Disable button temporarily
            updateProductButton.setEnabled(false);
            updateProductButton.setText("Updating...");
            
            try {
                int id = Integer.parseInt(productIdField.getText().trim());
                String name = productNameField.getText().trim();
                int categoryId = getCategoryIdFromComboBox();
                int unitTypeId = getUnitTypeIdFromComboBox(true);

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
                    showMessage("Product updated successfully!", "success", true);
                } else {
                    showMessage("Product with ID " + id + " not found", "error", true);
                }
            } catch (Exception ex) {
                showMessage("Error updating product: " + ex.getMessage(), "error", true);
            } finally {
                updateProductButton.setText("Update");
                updateProductButton.setEnabled(true);
            }
        }
    }
    
    private void deleteProduct() {
        if (productIdField.getText().trim().isEmpty()) {
            showMessage("Please enter a product ID to delete", "error", true);
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
            // Disable button temporarily
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
                    showMessage("Product deleted successfully!", "success", true);
                    clearProductFields();
                } else {
                    showMessage("Product with ID " + id + " not found", "error", true);
                }
            } catch (Exception ex) {
                showMessage("Error deleting product: " + ex.getMessage(), "error", true);
            } finally {
                deleteProductButton.setText("Delete");
                deleteProductButton.setEnabled(true);
            }
        }
    }
    
    private void addProductVariant() {
        if (validateVariantFields()) {
            // Disable the button temporarily to prevent double submission
            addVariantButton.setEnabled(false);
            
            // Show loading state
            addVariantButton.setText("Adding...");
            
            try {
                // Get selected product ID
                int productId = getProductIdFromComboBox();
                
                // Get selected unit type ID
                int unitTypeId = getUnitTypeIdFromComboBox(false);
                
                // Create new product variant
                int id = Integer.parseInt(variantIdField.getText().trim());
                float unitQuantity = Float.parseFloat(unitQuantityField.getText().trim());
                float price = Float.parseFloat(variantPriceField.getText().trim());
                
                ProductVariant variant = new ProductVariant(id, productId, unitQuantity, unitTypeId, price);
                productVariants.add(variant);
                
                showMessage("Product variant added successfully!", "success", false);
                clearVariantFields();
            } catch (Exception ex) {
                showMessage("Error adding product variant: " + ex.getMessage(), "error", false);
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
                showMessage("Please enter a variant ID to update", "error", false);
                highlightErrorField(variantIdField);
                return;
            }
            
            // Disable button temporarily
            updateVariantButton.setEnabled(false);
            updateVariantButton.setText("Updating...");
            
            try {
                int id = Integer.parseInt(variantIdField.getText().trim());
                int productId = getProductIdFromComboBox();
                float unitQuantity = Float.parseFloat(unitQuantityField.getText().trim());
                int unitTypeId = getUnitTypeIdFromComboBox(false);
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
                    showMessage("Product variant updated successfully!", "success", false);
                } else {
                    showMessage("Product variant with ID " + id + " not found", "error", false);
                }
            } catch (Exception ex) {
                showMessage("Error updating product variant: " + ex.getMessage(), "error", false);
            } finally {
                updateVariantButton.setText("Update");
                updateVariantButton.setEnabled(true);
            }
        }
    }
    
    private void deleteProductVariant() {
        if (variantIdField.getText().trim().isEmpty()) {
            showMessage("Please enter a variant ID to delete", "error", false);
            highlightErrorField(variantIdField);
            return;
        }
        
        // Show confirmation dialog
        int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this product variant?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            // Disable button temporarily
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
                    showMessage("Product variant deleted successfully!", "success", false);
                    clearVariantFields();
                } else {
                    showMessage("Product variant with ID " + id + " not found", "error", false);
                }
            } catch (Exception ex) {
                showMessage("Error deleting product variant: " + ex.getMessage(), "error", false);
            } finally {
                deleteVariantButton.setText("Delete");
                deleteVariantButton.setEnabled(true);
            }
        }
    }
    
    private void clearProductFields() {
        // Clear all input fields
        productIdField.setText("");
        productNameField.setText("");

        // Reset combo boxes to first item if available
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
        productIdField.requestFocusInWindow();
    }
    
    private void clearVariantFields() {
        // Clear all input fields
        variantIdField.setText("");
        unitQuantityField.setText("");
        variantPriceField.setText("");
        
        // Reset combo boxes to first item if available
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
        variantIdField.requestFocusInWindow();
    }
    
    private boolean validateProductFields() {
        // Basic validation
        if (productIdField.getText().trim().isEmpty()) {
            showMessage("Product ID cannot be empty", "error", true);
            highlightErrorField(productIdField);
            return false;
        }
        
        try {
            Integer.parseInt(productIdField.getText().trim());
        } catch (NumberFormatException e) {
            showMessage("Product ID must be a number", "error", true);
            highlightErrorField(productIdField);
            return false;
        }
        
        if (productNameField.getText().trim().isEmpty()) {
            showMessage("Product name cannot be empty", "error", true);
            highlightErrorField(productNameField);
            return false;
        }
        
        if (categoryComboBox.getSelectedIndex() == -1) {
            showMessage("Please select a category", "error", true);
            return false;
        }
        
        if (unitTypeComboBox.getSelectedIndex() == -1) {
            showMessage("Please select a unit type", "error", true);
            return false;
        }
        return true;
    }
    
    private boolean validateVariantFields() {
        // Basic validation
        if (variantIdField.getText().trim().isEmpty()) {
            showMessage("Variant ID cannot be empty", "error", false);
            highlightErrorField(variantIdField);
            return false;
        }
        
        try {
            Integer.parseInt(variantIdField.getText().trim());
        } catch (NumberFormatException e) {
            showMessage("Variant ID must be a number", "error", false);
            highlightErrorField(variantIdField);
            return false;
        }
        
        if (productComboBox.getSelectedIndex() == -1) {
            showMessage("Please select a product", "error", false);
            return false;
        }
        
        if (unitQuantityField.getText().trim().isEmpty()) {
            showMessage("Unit quantity cannot be empty", "error", false);
            highlightErrorField(unitQuantityField);
            return false;
        }
        
        try {
            Float.parseFloat(unitQuantityField.getText().trim());
        } catch (NumberFormatException e) {
            showMessage("Unit quantity must be a number", "error", false);
            highlightErrorField(unitQuantityField);
            return false;
        }
        
        if (variantUnitTypeComboBox.getSelectedIndex() == -1) {
            showMessage("Please select a unit type", "error", false);
            return false;
        }
        
        if (variantPriceField.getText().trim().isEmpty()) {
            showMessage("Price cannot be empty", "error", false);
            highlightErrorField(variantPriceField);
            return false;
        }
        
        try {
            Float.parseFloat(variantPriceField.getText().trim());
        } catch (NumberFormatException e) {
            showMessage("Price must be a number", "error", false);
            highlightErrorField(variantPriceField);
            return false;
        }
        
        return true;
    }
    
    private int getCategoryIdFromComboBox() {
        String selected = (String) categoryComboBox.getSelectedItem();
        if (selected == null || selected.isEmpty()) {
            return -1;
        }
        
        // Extract ID from the format "ID - Name"
        return Integer.parseInt(selected.split(" - ")[0]);
    }
    
    private int getUnitTypeIdFromComboBox(boolean isProductTab) {
        JComboBox<String> comboBox = isProductTab ? unitTypeComboBox : variantUnitTypeComboBox;
        String selected = (String) comboBox.getSelectedItem();
        if (selected == null || selected.isEmpty()) {
            return -1;
        }
        
        // Extract ID from the format "ID - Name"
        return Integer.parseInt(selected.split(" - ")[0]);
    }
    
    private int getProductIdFromComboBox() {
        String selected = (String) productComboBox.getSelectedItem();
        if (selected == null || selected.isEmpty()) {
            return -1;
        }
        
        // Extract ID from the format "ID - Name"
        return Integer.parseInt(selected.split(" - ")[0]);
    }
}