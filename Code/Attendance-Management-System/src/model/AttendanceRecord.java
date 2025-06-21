package model;

import java.time.LocalDateTime;

public class AttendanceRecord {
    private final Student student;
    private final Course course;
    private final LocalDateTime dateTime;
    private final boolean isLate;
    public AttendanceRecord(Student student, Course course, LocalDateTime dateTime, boolean isLate) {
        this.student = student;
        this.course = course;
        this.dateTime = dateTime;
        this.isLate = isLate;
    }
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDateTime getDateTime() { return dateTime; }
    public boolean isLate() { return isLate; }
} 