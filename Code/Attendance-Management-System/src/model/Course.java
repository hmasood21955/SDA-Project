package model;

public class Course {
    private final String id;
    private final String name;
    private final String code;
    public Course(String id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }
} 