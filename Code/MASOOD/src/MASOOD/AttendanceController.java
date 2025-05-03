package MASOOD;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceController {
    private final AttendanceView view;
    private final AttendanceService service;

    public AttendanceController(AttendanceView view, AttendanceService service) {
        this.view = view;
        this.service = service;
    }

    public void recordAttendance(String studentId, String courseId) {
        if (studentId.isEmpty() || courseId.isEmpty()) {
            view.showError("Please enter both Student ID and Course ID");
            return;
        }

        try {
            LocalDateTime now = LocalDateTime.now();
            service.recordAttendance(studentId, courseId, now);
            
            view.updateStatus("Attendance recorded successfully!\n" +
                           "Student ID: " + studentId + "\n" +
                           "Course ID: " + courseId + "\n" +
                           "Time: " + now.format(AttendanceService.TIMESTAMP_FORMAT) + "\n" +
                           "Status: " + (service.isLateEntry(now) ? "Late" : "On Time"));
            
            view.clearInputFields();
        } catch (SQLException ex) {
            view.showError("Error recording attendance: " + ex.getMessage());
        }
    }

    public void checkTimeStatus(LocalDateTime timeToCheck) {
        String status = service.checkTimeStatus(timeToCheck);
        view.updateStatus("Time Status Check:\n" +
                        "Time: " + timeToCheck.format(AttendanceService.TIMESTAMP_FORMAT) + "\n" +
                        "Status: " + status);
    }

    public void showAttendanceHistory() {
        try {
            List<AttendanceRecord> records = service.getAttendanceHistory();
            view.showHistoryDialog(records);
        } catch (SQLException ex) {
            view.showError("Error loading attendance history: " + ex.getMessage());
        }
    }
} 