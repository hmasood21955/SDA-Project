package model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.io.*;

public class AttendanceModel {
    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Course> courses = new HashMap<>();
    private final Map<String, List<AttendanceRecord>> attendanceRecords = new HashMap<>();
    private final Map<String, Set<String>> courseAssignments = new HashMap<>();
    private final Map<String, User> users = new HashMap<>();
    private User currentUser = null;
    public final LocalTime LATE_THRESHOLD = LocalTime.of(9, 0);
    private static final String STUDENTS_FILE = "students.txt";
    private static final String COURSES_FILE = "courses.txt";
    private static final String ASSIGNMENTS_FILE = "assignments.txt";
    private static final String USERS_FILE = "users.txt";

    public AttendanceModel() {
        loadUsersFromFile();
        loadStudentsFromFile();
        loadCoursesFromFile();
        loadAssignmentsFromFile();
    }

    private void loadStudentsFromFile() {
        students.clear();
        File file = new File(STUDENTS_FILE);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    students.put(parts[0], new Student(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCoursesFromFile() {
        courses.clear();
        File file = new File(COURSES_FILE);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length >= 3) {
                    LocalTime threshold = parts.length > 3 ? LocalTime.parse(parts[3]) : LocalTime.of(9, 0);
                    courses.put(parts[0], new Course(parts[0], parts[1], parts[2], threshold));
                    attendanceRecords.putIfAbsent(parts[0], new ArrayList<>());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAssignmentsFromFile() {
        courseAssignments.clear();
        File file = new File(ASSIGNMENTS_FILE);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String courseId = parts[0];
                    String studentId = parts[1];
                    courseAssignments.computeIfAbsent(courseId, k -> new HashSet<>()).add(studentId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsersFromFile() {
        users.clear();
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            // Create default users (without referencing specific students)
            users.put("admin", new User("admin", "admin", "admin123", User.UserRole.ADMIN));
            users.put("teacher", new User("teacher", "teacher", "teacher123", User.UserRole.TEACHER));
            
            // Save default users to file
            saveUserToFile(new User("admin", "admin", "admin123", User.UserRole.ADMIN));
            saveUserToFile(new User("teacher", "teacher", "teacher123", User.UserRole.TEACHER));
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length >= 4) {
                    User.UserRole role = User.UserRole.valueOf(parts[3]);
                    String studentId = parts.length > 4 ? parts[4] : null;
                    users.put(parts[1], new User(parts[0], parts[1], parts[2], role, studentId));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveStudentToFile(Student student) {
        try {
            File file = new File(STUDENTS_FILE);
            // Create parent directories if they don't exist
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            
            FileWriter fw = new FileWriter(file, true);
            fw.write(student.getId() + "," + student.getName() + "," + student.getEmail() + "\n");
            fw.close();
        } catch (IOException e) {
            System.err.println("Error saving student to file: " + e.getMessage());
        }
    }

    private void saveCourseToFile(Course course) {
        try {
            File file = new File(COURSES_FILE);
            // Create parent directories if they don't exist
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            
            FileWriter fw = new FileWriter(file, true);
            fw.write(course.getId() + "," + course.getName() + "," + course.getCode() + "," + course.getCustomLateThreshold() + "\n");
            fw.close();
        } catch (IOException e) {
            System.err.println("Error saving course to file: " + e.getMessage());
        }
    }

    private void saveAssignmentToFile(String courseId, String studentId) {
        try {
            File file = new File(ASSIGNMENTS_FILE);
            // Create parent directories if they don't exist
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            
            FileWriter fw = new FileWriter(file, true);
            fw.write(courseId + "," + studentId + "\n");
            fw.close();
        } catch (IOException e) {
            System.err.println("Error saving assignment to file: " + e.getMessage());
        }
    }

    private void saveUserToFile(User user) {
        try {
            File file = new File(USERS_FILE);
            // Create parent directories if they don't exist
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            
            FileWriter fw = new FileWriter(file, true);
            fw.write(user.getId() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getRole());
            if (user.getStudentId() != null) {
                fw.write("," + user.getStudentId());
            }
            fw.write("\n");
            fw.close();
        } catch (IOException e) {
            System.err.println("Error saving user to file: " + e.getMessage());
        }
    }

    public Map<String, Student> getStudents() { return students; }
    public Map<String, Course> getCourses() { return courses; }
    public Map<String, List<AttendanceRecord>> getAttendanceRecords() { return attendanceRecords; }
    public Map<String, Set<String>> getCourseAssignments() { return courseAssignments; }
    public Map<String, User> getUsers() { return users; }

    public void addStudent(Student student) {
        students.put(student.getId(), student);
        saveStudentToFile(student);
        // Automatically create a student user account
        createStudentUserAccount(student);
    }

    private void createStudentUserAccount(Student student) {
        // Create username from student ID (lowercase)
        String username = student.getId().toLowerCase();
        String password = "student123"; // Default password
        String userId = "user_" + student.getId();
        
        // Check if user already exists
        if (!users.containsKey(username)) {
            User studentUser = new User(userId, username, password, User.UserRole.STUDENT, student.getId());
            users.put(username, studentUser);
            try {
                saveUserToFile(studentUser);
            } catch (Exception e) {
                System.err.println("Warning: Could not save student user account: " + e.getMessage());
            }
        }
    }

    public void addCourse(Course course) {
        courses.put(course.getId(), course);
        attendanceRecords.putIfAbsent(course.getId(), new ArrayList<>());
        saveCourseToFile(course);
    }
    public void assignStudentToCourse(String courseId, String studentId) {
        courseAssignments.computeIfAbsent(courseId, k -> new HashSet<>()).add(studentId);
        saveAssignmentToFile(courseId, studentId);
    }
    public Set<String> getStudentsForCourse(String courseId) {
        return courseAssignments.getOrDefault(courseId, Collections.emptySet());
    }
    public Set<String> getCoursesForStudent(String studentId) {
        Set<String> result = new HashSet<>();
        for (Map.Entry<String, Set<String>> entry : courseAssignments.entrySet()) {
            if (entry.getValue().contains(studentId)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
    public void addAttendanceRecord(String courseId, AttendanceRecord record) {
        attendanceRecords.get(courseId).add(record);
    }

    public void deleteStudent(String studentId) {
        students.remove(studentId);
        for (Set<String> assigned : courseAssignments.values()) {
            assigned.remove(studentId);
        }
        for (List<AttendanceRecord> records : attendanceRecords.values()) {
            records.removeIf(r -> r.getStudent().getId().equals(studentId));
        }
        rewriteStudentsFile();
        rewriteAssignmentsFile();
    }

    public void deleteCourse(String courseId) {
        courses.remove(courseId);
        courseAssignments.remove(courseId);
        attendanceRecords.remove(courseId);
        rewriteCoursesFile();
        rewriteAssignmentsFile();
    }

    private void rewriteStudentsFile() {
        try (FileWriter fw = new FileWriter(STUDENTS_FILE, false)) {
            for (Student s : students.values()) {
                fw.write(s.getId() + "," + s.getName() + "," + s.getEmail() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rewriteCoursesFile() {
        try (FileWriter fw = new FileWriter(COURSES_FILE, false)) {
            for (Course c : courses.values()) {
                fw.write(c.getId() + "," + c.getName() + "," + c.getCode() + "," + c.getCustomLateThreshold() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rewriteAssignmentsFile() {
        try (FileWriter fw = new FileWriter(ASSIGNMENTS_FILE, false)) {
            for (Map.Entry<String, Set<String>> entry : courseAssignments.entrySet()) {
                String courseId = entry.getKey();
                for (String studentId : entry.getValue()) {
                    fw.write(courseId + "," + studentId + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editStudent(String studentId, String newName, String newEmail) {
        Student s = students.get(studentId);
        if (s != null) {
            students.put(studentId, new Student(studentId, newName, newEmail));
            rewriteStudentsFile();
        }
    }

    public void editCourse(String courseId, String newName, String newCode) {
        Course c = courses.get(courseId);
        if (c != null) {
            courses.put(courseId, new Course(courseId, newName, newCode));
            rewriteCoursesFile();
        }
    }

    public User authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return user;
        }
        return null;
    }

    public User getCurrentUser() { return currentUser; }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
        saveUserToFile(user);
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public LocalTime getLateThresholdForCourse(String courseId) {
        Course course = courses.get(courseId);
        return course != null ? course.getCustomLateThreshold() : LATE_THRESHOLD;
    }
} 