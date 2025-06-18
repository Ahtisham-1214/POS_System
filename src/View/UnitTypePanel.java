package View;

import Controller.UnitType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Controller.UnitType.unitTypes;

public class UnitTypePanel {

    // Define colors for better UI consistency
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel Blue
    private static final Color SECONDARY_COLOR = new Color(100, 149, 237); // Cornflower Blue
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255); // Alice Blue
    private static final Color TEXT_COLOR = new Color(50, 50, 50); // Dark Gray
    private static final Color SUCCESS_COLOR = new Color(46, 139, 87); // Sea Green
    private static final Color ERROR_COLOR = new Color(178, 34, 34); // Firebrick
    private static final Color PANEL_BACKGROUND = Color.WHITE;

    private final JPanel panel;
    private final JTextField unitTypeIdField;
    private final JTextField unitTypeNameField;
    private final JTextField conversionRateField;
    private final JTextField searchField;
    private final JButton addUnitTypeButton;
    private final JButton updateUnitTypeButton;
    private final JButton deleteUnitTypeButton;
    private final JLabel unitTypeMessageLabel;
    private static final JComboBox<String> unitTypeComboBox = new JComboBox<>();

    public JPanel getPanel() {
        return panel;
    }

    public static JComboBox<String> getUnitTypeComboBox() {
        return unitTypeComboBox;
    }

    public UnitTypePanel() {

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
        unitTypeMessageLabel = new JLabel("");
        unitTypeMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        unitTypeMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(unitTypeMessageLabel, gbc);

        // Search field
        JLabel searchLabel = createStyledLabel("Search Unit Type:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(searchLabel, gbc);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        searchPanel.setBackground(PANEL_BACKGROUND);

        searchField = createStyledTextField();
        searchField.setToolTipText("Enter unit type name to search");
        searchField.setPreferredSize(new Dimension(150, 30));
        // Add action listener to search field for the Enter key
        searchField.addActionListener(e -> searchUnitType());
        searchPanel.add(searchField);

        JButton searchButton = createStyledButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setBackground(PRIMARY_COLOR);
        searchPanel.add(searchButton);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(searchPanel, gbc);

        // Create and style form fields
        // ID field
        JLabel idLabel = createStyledLabel("Unit Type ID:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(idLabel, gbc);

        unitTypeIdField = createStyledTextField();
        unitTypeIdField.setEditable(false);
        if (!unitTypes.isEmpty())
            unitTypeIdField.setText(String.valueOf(unitTypes.getLast().getId() + 1));
        else
            unitTypeIdField.setText("1");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(unitTypeIdField, gbc);

        // Name field
        JLabel nameLabel = createStyledLabel("Unit Type Name:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nameLabel, gbc);

        unitTypeNameField = createStyledTextField();
        unitTypeNameField.setToolTipText("Enter the unit type name");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(unitTypeNameField, gbc);

        // Conversion Rate field
        JLabel conversionLabel = createStyledLabel("Conversion Rate:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(conversionLabel, gbc);

        conversionRateField = createStyledTextField();
        conversionRateField.setToolTipText("Enter the conversion rate to base unit (numeric value)");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(conversionRateField, gbc);

        // Buttons panel with improved styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(PANEL_BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        addUnitTypeButton = createStyledButton("Add");
        addUnitTypeButton.setToolTipText("Add a new unit type");
        addUnitTypeButton.setBackground(PRIMARY_COLOR);

        updateUnitTypeButton = createStyledButton("Update");
        updateUnitTypeButton.setToolTipText("Update the selected unit type");
        updateUnitTypeButton.setBackground(SECONDARY_COLOR);

        deleteUnitTypeButton = createStyledButton("Delete");
        deleteUnitTypeButton.setToolTipText("Delete the selected unit type");
        deleteUnitTypeButton.setBackground(new Color(220, 53, 69)); // Bootstrap danger red

        JButton clearUnitTypeButton = createStyledButton("Clear");
        clearUnitTypeButton.setToolTipText("Clear all fields");
        clearUnitTypeButton.setBackground(new Color(108, 117, 125)); // Bootstrap secondary gray

        buttonPanel.add(addUnitTypeButton);
        buttonPanel.add(updateUnitTypeButton);
        buttonPanel.add(deleteUnitTypeButton);
        buttonPanel.add(clearUnitTypeButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        // Add action listeners
        addUnitTypeButton.addActionListener(e -> addUnitType());

        updateUnitTypeButton.addActionListener(e -> updateUnitType());

        deleteUnitTypeButton.addActionListener(e -> deleteUnitType());

        clearUnitTypeButton.addActionListener(e -> clearUnitTypeFields());

        searchButton.addActionListener(e -> searchUnitType());

        panel.add(formPanel, BorderLayout.CENTER);

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

    private void highlightErrorField(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ERROR_COLOR, 2),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));

        // Reset border after 2 seconds
        Timer timer = new Timer(2000, e -> field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        )));
        timer.setRepeats(false);
        timer.start();

        // Set focus to the field
        field.requestFocusInWindow();
    }

    private void showMessage(String message, String type) {
        JLabel messageLabel = unitTypeMessageLabel;
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

    private void addUnitType() {
        if (validateUnitTypeFields()) {
            // Disable the button temporarily to prevent double submission
            addUnitTypeButton.setEnabled(false);

            // Show loading state
            addUnitTypeButton.setText("Adding...");

            try {
                // Create a new unit type
                int id = Integer.parseInt(unitTypeIdField.getText().trim());
                String name = unitTypeNameField.getText().trim();
                float conversionRate = Float.parseFloat(conversionRateField.getText().trim());

                UnitType unitType = new UnitType(id, name, conversionRate);
                unitTypes.add(unitType);

                // Update unit type combo boxes
                updateUnitTypeComboBoxes();

                showMessage("Unit Type added successfully!", "success");
                clearUnitTypeFields();
                unitTypeIdField.setText(String.valueOf(unitTypes.getLast().getId() + 1));
            } catch (Exception ex) {
                showMessage("Error adding unit type: " + ex.getMessage(), "error");
            } finally {
                addUnitTypeButton.setText("Add");
                addUnitTypeButton.setEnabled(true);
            }
        }
    }

    private void updateUnitType() {
        if (validateUnitTypeFields()) {
            // Validate that ID is provided for update
            if (unitTypeIdField.getText().trim().isEmpty()) {
                showMessage("Please enter a unit type ID to update", "error");
                highlightErrorField(unitTypeIdField);
                return;
            }

            // Disable the button temporarily
            updateUnitTypeButton.setEnabled(false);
            updateUnitTypeButton.setText("Updating...");

            try {
                int id = Integer.parseInt(unitTypeIdField.getText().trim());
                String name = unitTypeNameField.getText().trim();
                float conversionRate = Float.parseFloat(conversionRateField.getText().trim());

                // Find and update the unit type
                boolean found = false;
                for (int i = 0; i < unitTypes.size(); i++) {
                    if (unitTypes.get(i).getId() == id) {
                        UnitType unitType = new UnitType();
                        unitType.setId(id);
                        unitType.setName(name);
                        unitType.setConversionToBaseUnit(conversionRate);
                        unitTypes.set(i, unitType);
                        unitType.updateUnitType();
                        found = true;
                        break;
                    }
                }

                if (found) {
                    // Update unit type combo boxes
                    updateUnitTypeComboBoxes();
                    clearUnitTypeFields();
                    unitTypeIdField.setText(String.valueOf(unitTypes.getLast().getId() + 1));
                    unitTypeNameField.requestFocusInWindow();
                    showMessage("Unit Type updated successfully!", "success");
                } else {
                    showMessage("Unit Type with ID " + id + " not found", "error");
                }
            } catch (Exception ex) {
                showMessage("Error updating unit type: " + ex.getMessage(), "error");
            } finally {
                updateUnitTypeButton.setText("Update");
                updateUnitTypeButton.setEnabled(true);
            }
        }
    }

    private void deleteUnitType() {
        if (unitTypeIdField.getText().trim().isEmpty()) {
            showMessage("Please enter a unit type ID to delete", "error");
            highlightErrorField(unitTypeIdField);
            return;
        }

        // Show confirmation dialog
        int result = JOptionPane.showConfirmDialog(
                this.panel,
                "Are you sure you want to delete this unit type?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            // Disable the button temporarily
            deleteUnitTypeButton.setEnabled(false);
            deleteUnitTypeButton.setText("Deleting...");

            try {
                int id = Integer.parseInt(unitTypeIdField.getText().trim());

                // Find and remove the unit type
                boolean found = false;
                for (int i = 0; i < unitTypes.size(); i++) {
                    if (unitTypes.get(i).getId() == id) {
                        unitTypes.get(i).deleteUnitType();
                        unitTypes.remove(i);
                        found = true;
                        break;
                    }
                }

                if (found) {
                    // Update unit type combo boxes
                    updateUnitTypeComboBoxes();
                    showMessage("Unit Type deleted successfully!", "success");
                    clearUnitTypeFields();
                    unitTypeIdField.setText(String.valueOf(unitTypes.getLast().getId() + 1));
                    unitTypeNameField.requestFocusInWindow();
                } else {
                    showMessage("Unit Type with ID " + id + " not found", "error");
                }
            } catch (Exception ex) {
                showMessage("Error deleting unit type: " + ex.getMessage(), "error");
            } finally {
                deleteUnitTypeButton.setText("Delete");
                deleteUnitTypeButton.setEnabled(true);
            }
        }
    }

    private void clearUnitTypeFields() {
        // Clear all input fields
        unitTypeIdField.setText(String.valueOf(unitTypes.getLast().getId() + 1));
        unitTypeNameField.setText("");
        conversionRateField.setText("");
        searchField.setText("");

        // Clear message only if it's not a success message
        if (!unitTypeMessageLabel.getForeground().equals(SUCCESS_COLOR)) {
            unitTypeMessageLabel.setText("");
        }

        // Set focus to ID field
        unitTypeNameField.requestFocusInWindow();
    }

    private boolean validateUnitTypeFields() {
        // Basic validation
        if (unitTypeIdField.getText().trim().isEmpty()) {
            showMessage("Unit Type ID cannot be empty", "error");
            highlightErrorField(unitTypeIdField);
            return false;
        }

        try {
            Integer.parseInt(unitTypeIdField.getText().trim());
        } catch (NumberFormatException e) {
            showMessage("Unit Type ID must be a number", "error");
            highlightErrorField(unitTypeIdField);
            return false;
        }

        if (unitTypeNameField.getText().trim().isEmpty()) {
            showMessage("Unit Type name cannot be empty", "error");
            highlightErrorField(unitTypeNameField);
            return false;
        }

        if (conversionRateField.getText().trim().isEmpty()) {
            showMessage("Conversion Rate cannot be empty", "error");
            highlightErrorField(conversionRateField);
            return false;
        }

        try {
            Float.parseFloat(conversionRateField.getText().trim());
        } catch (NumberFormatException e) {
            showMessage("Conversion Rate must be a number", "error");
            highlightErrorField(conversionRateField);
            return false;
        }

        return true;
    }

    private void updateUnitTypeComboBoxes() {
        getUnitTypeComboBox().removeAllItems();

        for (UnitType unitType : unitTypes) {
            String item = unitType.getName();
            getUnitTypeComboBox().addItem(item);
        }
    }

    private void searchUnitType() {
        String searchTerm = searchField.getText().trim().toLowerCase();

        if (searchTerm.isEmpty()) {
            showMessage("Please enter a search term", "error");
            highlightErrorField(searchField);
            return;
        }

        boolean found = false;

        for (UnitType unitType : unitTypes) {
            if (unitType.getName().toLowerCase().contains(searchTerm)) {
                // Populate form fields with the matching unit type's data
                unitTypeIdField.setText(String.valueOf(unitType.getId()));
                unitTypeNameField.setText(unitType.getName());
                conversionRateField.setText(String.valueOf(unitType.getConversionToBaseUnit()));

                showMessage("Unit Type found: " + unitType.getName(), "success");
                found = true;
                break;
            }
        }

        if (!found) {
            showMessage("No Unit Type found matching: " + searchTerm, "error");
            // Don't clear the fields, so the user can modify their search
        }
    }

}
