package MASOOD;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceService {
    public static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final int LATE_THRESHOLD_MINUTES = 5;
    private final List<AttendanceRecord> attendanceRecords;
    private int nextId = 1;
    private static final ZoneId PAKISTAN_ZONE = ZoneId.of("Asia/Karachi");

    public AttendanceService() {
        attendanceRecords = new ArrayList<>();
    }

    public void recordAttendance(String studentId, String courseId, LocalDateTime timestamp, boolean isLate) {
        // Convert to Pakistan time
        LocalDateTime pakistanTime = timestamp.atZone(ZoneId.systemDefault())
                                            .withZoneSameInstant(PAKISTAN_ZONE)
                                            .toLocalDateTime();
        
        AttendanceRecord record = new AttendanceRecord(
            nextId++,
            studentId,
            courseId,
            pakistanTime,
            isLate
        );
        attendanceRecords.add(record);
    }

    public String checkTimeStatus(LocalDateTime timeToCheck) {
        // Get today's date
        LocalDateTime today = LocalDateTime.now();
        
        // Set class start time to 9:00 AM today
        LocalDateTime classStartTime = today.withHour(9).withMinute(0).withSecond(0);
        LocalDateTime lateThreshold = classStartTime.plusMinutes(LATE_THRESHOLD_MINUTES);
        
        if (timeToCheck.isBefore(classStartTime)) {
            return "Early";
        } else if (timeToCheck.isAfter(lateThreshold)) {
            return "Late";
        } else {
            return "On Time";
        }
    }

    public List<AttendanceRecord> getAttendanceHistory() {
        return new ArrayList<>(attendanceRecords);
    }

    public static LocalDateTime getCurrentPakistanTime() {
        return LocalDateTime.now(PAKISTAN_ZONE);
    }
} 