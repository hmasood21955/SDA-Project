package MASOOD;

import java.time.LocalDateTime;
import java.util.List;

public class AttendanceController {
    private final AttendanceView view;
    private final AttendanceService service;

    public AttendanceController(AttendanceView view, AttendanceService service) {
        this.view = view;
        this.service = service;
    }

    public void recordAttendance(String studentId, String courseId, boolean isLate) {
        if (studentId.isEmpty() || courseId.isEmpty()) {
            view.showError("Please enter both Student ID and Course ID");
            return;
        }

        // Get current Pakistan time
        LocalDateTime now = AttendanceService.getCurrentPakistanTime();
        service.recordAttendance(studentId, courseId, now, isLate);
        
        view.updateStatus("Attendance recorded successfully!\n" +
                       "Student ID: " + studentId + "\n" +
                       "Course ID: " + courseId + "\n" +
                       "Time (PKT): " + now.format(AttendanceService.TIMESTAMP_FORMAT) + "\n" +
                       "Status: " + (isLate ? "Late" : "On Time"));
        
        view.clearInputFields();
    }

    public void showAttendanceHistory() {
        List<AttendanceRecord> records = service.getAttendanceHistory();
        view.showHistoryDialog(records);
    }
} 