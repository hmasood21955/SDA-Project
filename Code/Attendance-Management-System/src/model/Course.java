package model;

import java.time.LocalTime;

public class Course {
    private final String id;
    private final String name;
    private final String code;
    private LocalTime customLateThreshold; // Custom late threshold for this course

    public Course(String id, String name, String code) {
        this(id, name, code, LocalTime.of(9, 0)); // Default 9:00 AM
    }

    public Course(String id, String name, String code, LocalTime customLateThreshold) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.customLateThreshold = customLateThreshold;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public LocalTime getCustomLateThreshold() { return customLateThreshold; }
    
    public void setCustomLateThreshold(LocalTime threshold) {
        this.customLateThreshold = threshold;
    }
} 