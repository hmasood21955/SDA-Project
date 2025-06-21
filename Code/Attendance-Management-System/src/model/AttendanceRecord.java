package model;

import java.time.LocalDateTime;

public class AttendanceRecord {
    public enum AttendanceStatus {
        PRESENT, LATE, EXCUSED_ABSENCE, UNEXCUSED_ABSENCE
    }
    
    private final Student student;
    private final Course course;
    private final LocalDateTime dateTime;
    private final AttendanceStatus status;

    public AttendanceRecord(Student student, Course course, LocalDateTime dateTime, AttendanceStatus status) {
        this.student = student;
        this.course = course;
        this.dateTime = dateTime;
        this.status = status;
    }

    // Constructor for backward compatibility
    public AttendanceRecord(Student student, Course course, LocalDateTime dateTime, boolean isLate) {
        this.student = student;
        this.course = course;
        this.dateTime = dateTime;
        this.status = isLate ? AttendanceStatus.LATE : AttendanceStatus.PRESENT;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDateTime getDateTime() { return dateTime; }
    public AttendanceStatus getStatus() { return status; }
    
    // Backward compatibility methods
    public boolean isLate() { return status == AttendanceStatus.LATE; }
    public boolean isPresent() { return status == AttendanceStatus.PRESENT; }
    public boolean isExcusedAbsence() { return status == AttendanceStatus.EXCUSED_ABSENCE; }
    public boolean isUnexcusedAbsence() { return status == AttendanceStatus.UNEXCUSED_ABSENCE; }
} 