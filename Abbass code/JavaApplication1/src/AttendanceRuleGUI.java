import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Controller class: handles the business logic of attendance rule management
class AttendanceRuleController {
    private int minAttendancePercentage;

    public AttendanceRuleController() {
        // default value
        minAttendancePercentage = 75;
    }

    public void setRule(int minAttendancePercentage) {
        if (minAttendancePercentage < 0 || minAttendancePercentage > 100) {
            throw new IllegalArgumentException("Attendance percentage must be between 0 and 100");
        }
        this.minAttendancePercentage = minAttendancePercentage;
        // Save logic can be added here
        System.out.println("Attendance rule updated: Min Attendance = " + minAttendancePercentage + "%");
    }

    public int getMinAttendancePercentage() {
        return minAttendancePercentage;
    }
}

// GUI class - the boundary object
public class AttendanceRuleGUI extends JFrame {
    private JTextField minAttendanceField;
    private JButton saveButton;
    private JLabel statusLabel;

    private AttendanceRuleController controller;

    public AttendanceRuleGUI(AttendanceRuleController controller) {
        this.controller = controller;
        createUI();
    }

    private void createUI() {
        setTitle("Set Attendance Rule");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Minimum Attendance %:"));
        minAttendanceField = new JTextField(String.valueOf(controller.getMinAttendancePercentage()));
        panel.add(minAttendanceField);

        saveButton = new JButton("Save Rule");
        panel.add(saveButton);

        statusLabel = new JLabel("");
        panel.add(statusLabel);

        add(panel);

        saveButton.addActionListener(e -> onSaveClicked());
    }

    private void onSaveClicked() {
        try {
            int minPercentage = Integer.parseInt(minAttendanceField.getText().trim());

            // Show confirmation dialog
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to save this attendance rule?",
                    "Confirm Save",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                controller.setRule(minPercentage);
                statusLabel.setForeground(new Color(0, 128, 0));
                statusLabel.setText("Rule saved successfully!");
            } else {
                statusLabel.setForeground(Color.BLUE);
                statusLabel.setText("Save cancelled.");
            }
        } catch (NumberFormatException ex) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Please enter a valid number for attendance.");
        } catch (IllegalArgumentException ex) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText(ex.getMessage());
        } catch (Exception ex) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("An error occurred.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AttendanceRuleController controller = new AttendanceRuleController();
            AttendanceRuleGUI gui = new AttendanceRuleGUI(controller);
            gui.setVisible(true);
        });
    }
}
