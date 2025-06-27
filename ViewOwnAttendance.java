import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

// === MODEL LAYER ===
class Attendance {
    private Map<String, Integer> attendanceMap = new HashMap<>();

    public void markAttendance(String userId, int daysPresent) {
        attendanceMap.put(userId, daysPresent);
    }

    public int getAttendance(String userId) {
        return attendanceMap.getOrDefault(userId, -1); // -1 = not found
    }
}

// === CONTROLLER LAYER ===
class AttendanceController {
    private Attendance attendance;

    public AttendanceController(Attendance attendance) {
        this.attendance = attendance;
    }

    public void markSampleData() {
        attendance.markAttendance("T101", 20);
        attendance.markAttendance("S202", 18);
        attendance.markAttendance("S203", 15);
    }

    public int fetchOwnAttendance(String userId) {
        return attendance.getAttendance(userId);
    }
}

// === VIEW (GUI) LAYER ===
public class ViewOwnAttendance extends JFrame {
    private AttendanceController controller;

    public ViewOwnAttendance(AttendanceController controller) {
        this.controller = controller;

        setTitle("View Own Attendance");
        setSize(400, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Enter Your ID:");
        label.setBounds(30, 30, 100, 25);
        add(label);

        JTextField field = new JTextField();
        field.setBounds(140, 30, 180, 25);
        add(field);

        JButton viewBtn = new JButton("View Attendance");
        viewBtn.setBounds(140, 70, 150, 30);
        add(viewBtn);

        viewBtn.addActionListener((ActionEvent e) -> {
            String id = field.getText().trim();
            int days = controller.fetchOwnAttendance(id);

            if (days == -1) {
                JOptionPane.showMessageDialog(this, "No record found for ID: " + id);
            } else {
                JOptionPane.showMessageDialog(this, "User ID: " + id + " - Days Present: " + days);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        Attendance model = new Attendance();
        AttendanceController controller = new AttendanceController(model);
        controller.markSampleData(); // dummy data
        new ViewOwnAttendance(controller); // launch GUI
    }
}
