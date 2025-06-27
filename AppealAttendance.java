import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

// === MODEL ===
class Appeal {
    private String studentId;
    private String message;

    public Appeal(String studentId, String message) {
        this.studentId = studentId;
        this.message = message;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getMessage() {
        return message;
    }
}

// === CONTROLLER ===
class AppealController {
    private List<Appeal> appeals = new ArrayList<>();

    public void submitAppeal(String studentId, String message) {
        appeals.add(new Appeal(studentId, message));
        System.out.println("Appeal stored: " + studentId + " -> " + message);
    }

    public List<Appeal> getAllAppeals() {
        return appeals;
    }
}

// === VIEW (GUI) ===
public class AppealAttendance extends JFrame {
    private AppealController controller;

    public AppealAttendance(AppealController controller) {
        this.controller = controller;

        setTitle("Submit Attendance Appeal");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel labelId = new JLabel("Student ID:");
        labelId.setBounds(30, 30, 100, 25);
        add(labelId);

        JTextField fieldId = new JTextField();
        fieldId.setBounds(140, 30, 200, 25);
        add(fieldId);

        JLabel labelMessage = new JLabel("Appeal Message:");
        labelMessage.setBounds(30, 70, 120, 25);
        add(labelMessage);

        JTextField fieldMessage = new JTextField();
        fieldMessage.setBounds(140, 70, 200, 25);
        add(fieldMessage);

        JButton submitBtn = new JButton("Submit Appeal");
        submitBtn.setBounds(140, 120, 150, 30);
        add(submitBtn);

        submitBtn.addActionListener((ActionEvent e) -> {
            String id = fieldId.getText().trim();
            String msg = fieldMessage.getText().trim();

            if (id.isEmpty() || msg.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            } else {
                controller.submitAppeal(id, msg);
                JOptionPane.showMessageDialog(this, "Appeal Submitted Successfully.");
                fieldId.setText("");
                fieldMessage.setText("");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        AppealController controller = new AppealController();
        new AppealAttendance(controller);
    }
}

