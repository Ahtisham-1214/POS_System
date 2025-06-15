package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductPanel extends JPanel {
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

    public ProductPanel() {
        initializeUI();
    }

    private void initializeUI() {
        // Set layout
        setLayout(new BorderLayout());

        // Create title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(240, 240, 240));
        JLabel titleLabel = new JLabel("Product Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Message label for feedback
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(messageLabel, gbc);

        // ID field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Product ID:"), gbc);

        idField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(idField, gbc);

        // Name field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Product Name:"), gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameField, gbc);

        // Quantity field
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Quantity:"), gbc);

        quantityField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(quantityField, gbc);

        // Category field
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Category:"), gbc);

        categoryField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(categoryField, gbc);

        // Unit Price field
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Unit Price:"), gbc);

        unitPriceField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(unitPriceField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        addButton = createStyledButton("Add");
        updateButton = createStyledButton("Update");
        deleteButton = createStyledButton("Delete");
        clearButton = createStyledButton("Clear");

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

        // Add components to main panel
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 30));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }

    private void addProduct() {
        // Placeholder for add product functionality
        if (validateFields()) {
            messageLabel.setText("Product added successfully!");
            messageLabel.setForeground(Color.GREEN);
            // Add actual implementation here
        }
    }

    private void updateProduct() {
        // Placeholder for update product functionality
        if (validateFields()) {
            messageLabel.setText("Product updated successfully!");
            messageLabel.setForeground(Color.GREEN);
            // Add actual implementation here
        }
    }

    private void deleteProduct() {
        // Placeholder for delete product functionality
        if (!idField.getText().trim().isEmpty()) {
            messageLabel.setText("Product deleted successfully!");
            messageLabel.setForeground(Color.GREEN);
            // Add actual implementation here
        } else {
            messageLabel.setText("Please enter a product ID to delete");
            messageLabel.setForeground(Color.RED);
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        quantityField.setText("");
        categoryField.setText("");
        unitPriceField.setText("");
        messageLabel.setText("");
    }

    private boolean validateFields() {
        // Basic validation
        if (nameField.getText().trim().isEmpty()) {
            messageLabel.setText("Product name cannot be empty");
            messageLabel.setForeground(Color.RED);
            return false;
        }

        try {
            if (!quantityField.getText().trim().isEmpty()) {
                Integer.parseInt(quantityField.getText().trim());
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Quantity must be a number");
            messageLabel.setForeground(Color.RED);
            return false;
        }

        try {
            if (!unitPriceField.getText().trim().isEmpty()) {
                Double.parseDouble(unitPriceField.getText().trim());
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Unit price must be a number");
            messageLabel.setForeground(Color.RED);
            return false;
        }

        return true;
    }
}
