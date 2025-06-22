package View;

import Controller.Product;
import Controller.CartItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static Controller.Product.products;

public class SalesPanel extends JPanel {
    // UI Components
    private JTextField customerField;
    private JTextField orderIdField;
    private static final JComboBox<String> productComboBox = new JComboBox<>();
    private JTextField quantityField;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel totalAmountLabel;
    
    // Data
    private ArrayList<CartItem> cartItems;
    private float totalAmount = 0.0f;
    private static int orderId = 1;
    
    // Constants for styling
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel Blue
    private static final Color SECONDARY_COLOR = new Color(60, 179, 113); // Medium Sea Green
    private static final Color PANEL_BACKGROUND = new Color(245, 245, 250); // Light gray-blue
    private static final Color ERROR_COLOR = new Color(220, 53, 69); // Bootstrap danger red


    public SalesPanel() {
        cartItems = new ArrayList<>();
        initializeUI();
    }

    public static int getOrderId() {
        return orderId;
    }

    public static JComboBox<String> getProductComboBox() {
        return productComboBox;
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(PANEL_BACKGROUND);
        
        // Create a header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Sales Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(PANEL_BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Form panel for customer and order details
        JPanel formPanel = createFormPanel();
        contentPanel.add(formPanel, BorderLayout.NORTH);
        
        // Product selection panel
        JPanel productPanel = createProductSelectionPanel();
        contentPanel.add(productPanel, BorderLayout.CENTER);
        
        // Cart panel
        JPanel cartPanel = createCartPanel();
        contentPanel.add(cartPanel, BorderLayout.SOUTH);
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Customer field
        JLabel customerLabel = createStyledLabel("Customer Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(customerLabel, gbc);
        
        customerField = createStyledTextField();
        customerField.setToolTipText("Enter customer name");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(customerField, gbc);
        
        // Order ID field
        JLabel orderIdLabel = createStyledLabel("Order ID:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(orderIdLabel, gbc);
        
        orderIdField = createStyledTextField();
        orderIdField.setText(generateOrderId());
        orderIdField.setEditable(false);
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(orderIdField, gbc);
        
        return panel;
    }
    
    private JPanel createProductSelectionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Product selection
        JLabel productLabel = createStyledLabel("Select Product:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(productLabel, gbc);
        
        productComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(productComboBox, gbc);
        
        // Quantity field
        JLabel quantityLabel = createStyledLabel("Quantity:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(quantityLabel, gbc);
        
        quantityField = createStyledTextField();
        quantityField.setText("1");
        quantityField.setToolTipText("Enter quantity");
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(quantityField, gbc);
        
        // Add to cart button
        JButton addToCartButton = createStyledButton("Add to Cart");
        addToCartButton.setBackground(SECONDARY_COLOR);
        addToCartButton.addActionListener(e -> addToCart());
        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addToCartButton, gbc);
        
        return panel;
    }
    
    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Create a table model with columns
        String[] columns = {"Product ID", "Product Name", "Price", "Quantity", "Total"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        
        // Create table
        cartTable = new JTable(tableModel);
        cartTable.setFont(new Font("Arial", Font.PLAIN, 14));
        cartTable.setRowHeight(25);
        cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        cartTable.getTableHeader().setBackground(PRIMARY_COLOR);
        cartTable.getTableHeader().setForeground(Color.WHITE);
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton removeItemButton = createStyledButton("Remove Item");
        removeItemButton.setBackground(ERROR_COLOR);
        removeItemButton.addActionListener(e -> removeSelectedItem());

        JButton clearCartButton = createStyledButton("Clear Cart");
        clearCartButton.setBackground(new Color(108, 117, 125)); // Gray
        clearCartButton.addActionListener(e -> clearCart());

        JButton generateReceiptButton = createStyledButton("Generate Receipt");
        generateReceiptButton.setBackground(PRIMARY_COLOR);
        generateReceiptButton.addActionListener(e -> generateReceipt());
        
        buttonPanel.add(removeItemButton);
        buttonPanel.add(clearCartButton);
        buttonPanel.add(generateReceiptButton);
        
        // Create a total panel
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setBackground(Color.WHITE);
        
        JLabel totalLabel = createStyledLabel("Total Amount: ");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        totalAmountLabel = new JLabel("$0.00");
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalAmountLabel.setForeground(PRIMARY_COLOR);
        
        totalPanel.add(totalLabel);
        totalPanel.add(totalAmountLabel);
        
        // Create a bottom panel to hold both button and total panels
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(totalPanel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private void addToCart() {
        int productId;
        if (productComboBox.getSelectedIndex() == -1) {
            showMessage("Please select a product", "error");
            return;
        }
        
        String quantityText = quantityField.getText().trim();
        if (quantityText.isEmpty()) {
            showMessage("Please enter quantity", "error");
            highlightErrorField(quantityField);
            return;
        }
        
        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                showMessage("Quantity must be greater than zero", "error");
                highlightErrorField(quantityField);
                return;
            }
        } catch (NumberFormatException e) {
            showMessage("Invalid quantity format", "error");
            highlightErrorField(quantityField);
            return;
        }

        try {
            // Get the selected product
            String selectedItem = (String) productComboBox.getSelectedItem();
            productId = findProductIdByName(selectedItem);

            // Find a product in the list
            Product selectedProduct = null;
            for (Product product : products) {
                if (product.getId() == productId) {
                    selectedProduct = product;
                    break;
                }
            }

            if (selectedProduct == null) {
                showMessage("Product not found", "error");
                return;
            }


            // Calculate total for this item
            float price = selectedProduct.getPricePerUnit();
            float itemTotal = price * quantity;

            // Check if the product already exists in the cart
            boolean found = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                int id = (int) tableModel.getValueAt(i, 0);
                if (id == productId) {
                    // Update quantity and total
                    int currentQty = (int) tableModel.getValueAt(i, 3);
                    int newQty = currentQty + quantity;
                    float newTotal = price * newQty;

                    tableModel.setValueAt(newQty, i, 3);
                    tableModel.setValueAt(newTotal, i, 4);

                    found = true;
                    break;
                }
            }

            // If product not in carts, add a new row
            if (!found) {
                Object[] row = {
                        productId,
                        selectedProduct.getName(),
                        price,
                        quantity,
                        itemTotal
                };
                tableModel.addRow(row);

                // Add to a cart items list
                cartItems.add(new CartItem(selectedProduct, quantity));
            }

            // Update total amount
            updateTotalAmount();

            // Reset quantity field
            quantityField.setText("1");

            showMessage("Product added to cart", "success");
        }catch (Exception e){
            showMessage(e.getMessage(), "error");
        }
    }
    
    private void removeSelectedItem() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Please select an item to remove", "error");
            return;
        }
        
        tableModel.removeRow(selectedRow);
        cartItems.remove(selectedRow);
        
        updateTotalAmount();
        showMessage("Item removed from cart", "success");
    }
    
    private void clearCart() {
        tableModel.setRowCount(0);
        cartItems.clear();
        updateTotalAmount();
        showMessage("Cart cleared", "success");
    }
    
    private void generateReceipt() {
        if (tableModel.getRowCount() == 0) {
            showMessage("Cart is empty. Add items before generating receipt", "error");
            return;
        }
        
        String customerName = customerField.getText().trim();
        if (customerName.isEmpty()) {
            showMessage("Please enter customer name", "error");
            highlightErrorField(customerField);
            return;
        }
        
        // Build receipt content
        StringBuilder receipt = new StringBuilder();
        receipt.append("===================================\n");
        receipt.append("            SALES RECEIPT          \n");
        receipt.append("===================================\n\n");
        receipt.append("Order ID: ").append(orderIdField.getText()).append("\n");
        receipt.append("Date: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
        receipt.append("Customer: ").append(customerName).append("\n\n");
        receipt.append("-----------------------------------\n");
        receipt.append(String.format("%-5s %-20s %-8s %-8s %-10s\n", "ID", "Product", "Price", "Qty", "Total"));
        receipt.append("-----------------------------------\n");
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int id = (int) tableModel.getValueAt(i, 0);
            String name = (String) tableModel.getValueAt(i, 1);
            float price = (float) tableModel.getValueAt(i, 2);
            int qty = (int) tableModel.getValueAt(i, 3);
            float total = (float) tableModel.getValueAt(i, 4);
            
            receipt.append(String.format("%-5d %-20s $%-7.2f %-8d $%-9.2f\n", 
                id, name.length() > 20 ? name.substring(0, 17) + "..." : name, 
                price, qty, total));
        }
        
        receipt.append("-----------------------------------\n");
        receipt.append(String.format("%-43s $%.2f\n", "TOTAL:", totalAmount));
        receipt.append("===================================\n\n");
        receipt.append("Thank you for your purchase!\n");
        
        // Display the receipt in a dialog
        JTextArea textArea = new JTextArea(receipt.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Receipt - Order #" + orderIdField.getText(),
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Clear cart and generate new order ID
        clearCart();
        orderIdField.setText(generateOrderId());
        customerField.setText("");
        
        showMessage("Receipt generated successfully", "success");
    }
    
    private void updateTotalAmount() {
        totalAmount = 0.0f;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            totalAmount += (float) tableModel.getValueAt(i, 4);
        }
        totalAmountLabel.setText(String.format("$%.2f", totalAmount));
    }
    
    private String generateOrderId() {
        // Generate a random order ID with format ORD-YYYYMMDD-XXXX
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String datePart = dateFormat.format(new Date());
        

        
        return "ORD-" + datePart + "-" + orderId++;
    }
    
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }
    
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(15);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
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
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        if (type.equals("error")) {
            messageLabel.setForeground(ERROR_COLOR);
            JOptionPane.showMessageDialog(this, messageLabel, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (type.equals("success")) {
            messageLabel.setForeground(SECONDARY_COLOR);
            JOptionPane.showMessageDialog(this, messageLabel, "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            messageLabel.setForeground(Color.BLACK);
            JOptionPane.showMessageDialog(this, messageLabel, "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void highlightErrorField(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ERROR_COLOR, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Reset border after a delay
        Timer timer = new Timer(2000, e -> field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        )));
        timer.setRepeats(false);
        timer.start();
    }

    private int findProductIdByName(String productName) {
        // You'll need to implement this based on your UnitType data structure
        // This is just an example - adjusting, according to your actual UnitType implementation
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product.getId();
            }
        }
        return -1;
    }

}