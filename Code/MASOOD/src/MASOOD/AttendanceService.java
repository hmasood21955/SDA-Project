package MASOOD;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceService {
    public static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ZoneId PAKISTAN_ZONE = ZoneId.of("Asia/Karachi");
    private List<AttendanceRecord> records;
    private int nextId;

    public AttendanceService() {
        this.records = new ArrayList<>();
        this.nextId = 1;
    }

    public static LocalDateTime getCurrentPakistanTime() {
        return LocalDateTime.now(PAKISTAN_ZONE);
    }

    public void recordAttendance(String studentId, String courseId, boolean isLate) {
        LocalDateTime timestamp = getCurrentPakistanTime();
        AttendanceRecord record = new AttendanceRecord(nextId++, studentId, courseId, timestamp, isLate);
        records.add(record);
    }

    public List<AttendanceRecord> getAttendanceHistory() {
        return new ArrayList<>(records);
    }
} 