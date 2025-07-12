package View;

import Controller.User;

import javax.swing.*;
import java.awt.*;

public class UserPanel {
    private final JPanel panel;
    private final JLabel userIdLabel;
    private final JLabel userNameLabel;
    private final JLabel passwordLabel;
    private final JTextField userIdField;
    private final JTextField userNameField;
    private final JPasswordField passwordField;
    private final JButton changeButton;

    User user;

    public JPanel getPanel() {
        return panel;
    }
    public UserPanel() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        userIdLabel = new JLabel("ID:");
        userNameLabel = new JLabel("User Name:");
        passwordLabel = new JLabel("Password:");
        userIdField = new JTextField();
        userNameField = new JTextField();
        passwordField = new JPasswordField();
        changeButton = new JButton("Change");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userIdLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(userIdField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(userNameLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(userNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(changeButton, gbc);





        panel.setBackground(Color.WHITE);
    }
}
