import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

// === MODEL ===
class Attendance {
    private Map<String, Integer> attendanceData = new HashMap<>();

    public void markAttendance(String userId, int daysPresent) {
        attendanceData.put(userId, daysPresent);
    }

    public Map<String, Integer> getAllAttendance() {
        return attendanceData;
    }
}

// === CONTROLLER ===
class ReportController {
    private Attendance attendance;

    public ReportController(Attendance attendance) {
        this.attendance = attendance;
    }

    public void markSampleData() {
        attendance.markAttendance("T101", 20);
        attendance.markAttendance("T102", 19);
        attendance.markAttendance("S201", 22);
        attendance.markAttendance("S202", 18);
        attendance.markAttendance("S203", 21);
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder("Attendance Report:\n\n");
        for (Map.Entry<String, Integer> entry : attendance.getAllAttendance().entrySet()) {
            report.append("User ID: ").append(entry.getKey())
                  .append(" → Days Present: ").append(entry.getValue()).append("\n");
        }
        return report.toString();
    }
}

// === VIEW (GUI) ===
public class GenerateAttendanceReport extends JFrame {
    private ReportController controller;

    public GenerateAttendanceReport(ReportController controller) {
        this.controller = controller;

        setTitle("Generate Attendance Report");
        setSize(500, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton generateBtn = new JButton("Generate Report");
        add(generateBtn, BorderLayout.SOUTH);

        generateBtn.addActionListener((ActionEvent e) -> {
            String report = controller.generateReport();
            reportArea.setText(report);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        Attendance model = new Attendance();
        ReportController controller = new ReportController(model);
        controller.markSampleData();  // Sample data
        new GenerateAttendanceReport(controller);  // Launch GUI
    }
}

