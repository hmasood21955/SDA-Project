package MASOOD;

import java.time.LocalDateTime;

public class AttendanceRecord {
    private final int id;
    private final String studentId;
    private final String courseId;
    private final LocalDateTime timestamp;
    private final boolean isLate;

    public AttendanceRecord(int id, String studentId, String courseId, LocalDateTime timestamp, boolean isLate) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.timestamp = timestamp;
        this.isLate = isLate;
    }

    public int getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isLate() {
        return isLate;
    }
} 