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
    public final LocalTime LATE_THRESHOLD = LocalTime.of(9, 0);
    private static final String STUDENTS_FILE = "students.txt";
    private static final String COURSES_FILE = "courses.txt";
    private static final String ASSIGNMENTS_FILE = "assignments.txt";

    public AttendanceModel() {
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
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    courses.put(parts[0], new Course(parts[0], parts[1], parts[2]));
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

    private void saveStudentToFile(Student student) {
        try (FileWriter fw = new FileWriter(STUDENTS_FILE, true)) {
            fw.write(student.getId() + "," + student.getName() + "," + student.getEmail() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCourseToFile(Course course) {
        try (FileWriter fw = new FileWriter(COURSES_FILE, true)) {
            fw.write(course.getId() + "," + course.getName() + "," + course.getCode() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAssignmentToFile(String courseId, String studentId) {
        try (FileWriter fw = new FileWriter(ASSIGNMENTS_FILE, true)) {
            fw.write(courseId + "," + studentId + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Student> getStudents() { return students; }
    public Map<String, Course> getCourses() { return courses; }
    public Map<String, List<AttendanceRecord>> getAttendanceRecords() { return attendanceRecords; }
    public Map<String, Set<String>> getCourseAssignments() { return courseAssignments; }

    public void addStudent(Student student) {
        students.put(student.getId(), student);
        saveStudentToFile(student);
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

    private void rewriteCoursesFile() {
        try (FileWriter fw = new FileWriter(COURSES_FILE, false)) {
            for (Course c : courses.values()) {
                fw.write(c.getId() + "," + c.getName() + "," + c.getCode() + "\n");
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
} 