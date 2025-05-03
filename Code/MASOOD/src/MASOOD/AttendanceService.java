package MASOOD;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceService {
    public static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String DB_URL = "jdbc:sqlite:attendance.db";
    private static final int LATE_THRESHOLD_MINUTES = 5;

    public AttendanceService() {
        createDatabase();
    }

    private void createDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            String sql = "CREATE TABLE IF NOT EXISTS attendance (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "student_id TEXT NOT NULL," +
                         "course_id TEXT NOT NULL," +
                         "timestamp TEXT NOT NULL," +
                         "is_late BOOLEAN NOT NULL)";
            
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    public void recordAttendance(String studentId, String courseId, LocalDateTime timestamp) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO attendance (student_id, course_id, timestamp, is_late) VALUES (?, ?, ?, ?)")) {
            
            pstmt.setString(1, studentId);
            pstmt.setString(2, courseId);
            pstmt.setString(3, timestamp.format(TIMESTAMP_FORMAT));
            pstmt.setBoolean(4, isLateEntry(timestamp));
            
            pstmt.executeUpdate();
        }
    }

    public boolean isLateEntry(LocalDateTime entryTime) {
        // Assuming class starts at 9:00 AM
        LocalDateTime classStartTime = entryTime.withHour(9).withMinute(0).withSecond(0);
        return entryTime.isAfter(classStartTime.plusMinutes(LATE_THRESHOLD_MINUTES));
    }

    public String checkTimeStatus(LocalDateTime timeToCheck) {
        LocalDateTime classStartTime = timeToCheck.withHour(9).withMinute(0).withSecond(0);
        LocalDateTime lateThreshold = classStartTime.plusMinutes(LATE_THRESHOLD_MINUTES);
        
        if (timeToCheck.isBefore(classStartTime)) {
            return "Early";
        } else if (timeToCheck.isAfter(lateThreshold)) {
            return "Late";
        } else {
            return "On Time";
        }
    }

    public List<AttendanceRecord> getAttendanceHistory() throws SQLException {
        List<AttendanceRecord> records = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM attendance ORDER BY timestamp DESC")) {
            
            while (rs.next()) {
                records.add(new AttendanceRecord(
                    rs.getInt("id"),
                    rs.getString("student_id"),
                    rs.getString("course_id"),
                    LocalDateTime.parse(rs.getString("timestamp"), TIMESTAMP_FORMAT),
                    rs.getBoolean("is_late")
                ));
            }
        }
        
        return records;
    }
} 