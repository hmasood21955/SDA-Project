package MASOOD;

import java.util.List;
import javax.swing.JOptionPane;

public class AttendanceController {
    private AttendanceService service;
    private AttendanceView view;

    public AttendanceController(AttendanceService service, AttendanceView view) {
        this.service = service;
        this.view = view;
        view.setController(this);
    }

    public void recordAttendance(String studentId, String courseId) {
        if (studentId.isEmpty() || courseId.isEmpty()) {
            view.showError("Please enter both Student ID and Course ID");
            return;
        }

        // Ask user if student is late
        int choice = view.showPunctualityDialog();
        boolean isLate = (choice == JOptionPane.YES_OPTION);
        
        try {
            service.recordAttendance(studentId, courseId, isLate);
            view.updateStatus("Attendance recorded successfully!\n" +
                           "Student ID: " + studentId + "\n" +
                           "Course ID: " + courseId + "\n" +
                           "Time (PKT): " + AttendanceService.getCurrentPakistanTime().format(AttendanceService.TIMESTAMP_FORMAT) + "\n" +
                           "Status: " + (isLate ? "Late" : "On Time"));
            view.clearInputFields();
        } catch (Exception e) {
            view.showError("Error recording attendance: " + e.getMessage());
        }
    }

    public void showAttendanceHistory() {
        try {
            List<AttendanceRecord> records = service.getAttendanceHistory();
            view.showHistoryDialog(records);
        } catch (Exception e) {
            view.showError("Error retrieving attendance history: " + e.getMessage());
        }
    }
} 