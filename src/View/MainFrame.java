package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {
    private LoginPanel loginPanel;

    public MainFrame(String title) {
        this.setTitle(title);
        this.setPreferredSize(new Dimension(800, 600));
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Initialize and add the login panel
        loginPanel = new LoginPanel();
        this.add(loginPanel, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // When the 'X' button is clicked, this method is called.
                int confirmResult = JOptionPane.showConfirmDialog(
                        null, // Parent component (this frame)
                        "Are you sure you want to exit?", // Message
                        "Exit Confirmation", // Title
                        JOptionPane.YES_NO_OPTION, // Option type (Yes/No buttons)
                        JOptionPane.QUESTION_MESSAGE // Message type (question icon)
                );

                if (confirmResult == JOptionPane.YES_OPTION) {
                    System.exit(0); 
                }
            }
        });

        // Pack and display the frame
        this.pack();
        this.setLocationRelativeTo(null); // Center on screen
        this.setVisible(true);
    }
}
