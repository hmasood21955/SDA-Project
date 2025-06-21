package model;

public class User {
    private final String id;
    private final String username;
    private final String password;
    private final UserRole role;
    private final String studentId; // Only for STUDENT role

    public enum UserRole {
        ADMIN, TEACHER, STUDENT
    }

    public User(String id, String username, String password, UserRole role) {
        this(id, username, password, role, null);
    }

    public User(String id, String username, String password, UserRole role, String studentId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.studentId = studentId;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public UserRole getRole() { return role; }
    public String getStudentId() { return studentId; }

    public boolean isAdmin() { return role == UserRole.ADMIN; }
    public boolean isTeacher() { return role == UserRole.TEACHER; }
    public boolean isStudent() { return role == UserRole.STUDENT; }
} 