package controller;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import model.*;
import view.AttendanceView;


public class AttendanceController {
    private final AttendanceModel model;
    private final AttendanceView view;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public AttendanceController(AttendanceModel model, AttendanceView view) {
        this.model = model;
        this.view = view;
        // Students
        view.addStudentButton.addActionListener(e -> addStudent());
        view.deleteStudentButton.addActionListener(e -> deleteStudent());
        view.editStudentButton.addActionListener(e -> editStudent());
        // Courses
        view.addCourseButton.addActionListener(e -> addCourse());
        view.deleteCourseButton.addActionListener(e -> deleteCourse());
        view.editCourseButton.addActionListener(e -> editCourse());
        // Assignments
        view.assignButton.addActionListener(e -> assignStudentToCourse());
        view.deleteAssignmentButton.addActionListener(e -> deleteAssignment());
        // Mark Attendance
        view.markAttendanceButton.addActionListener(e -> markAttendance());
        // View Attendance
        view.viewAttendanceButton.addActionListener(e -> viewAttendance());
        // Late Students
        view.viewLateStudentsButton.addActionListener(e -> showLateStudents());
        // Export buttons
        view.exportStudentsButton.addActionListener(e -> exportStudentsToCSV());
        view.exportCoursesButton.addActionListener(e -> exportCoursesToCSV());
        view.exportAttendanceButton.addActionListener(e -> exportAttendanceToCSV());
        view.refreshStatisticsButton.addActionListener(e -> showStatistics());
        view.searchStudentButton.addActionListener(e -> searchStudents());
        view.searchCourseButton.addActionListener(e -> searchCourses());
        view.filterAttendanceButton.addActionListener(e -> filterAttendanceByDate());
        view.themeToggleButton.addActionListener(e -> toggleTheme());
        view.logoutButton.addActionListener(e -> logout());
        view.showUsersButton.addActionListener(e -> showAvailableUsers());
        // Edit buttons
        view.deleteStudentComboBox.addActionListener(e -> populateEditStudentFields());
        view.editStudentButton.addActionListener(e -> editStudent());
        view.deleteCourseComboBox.addActionListener(e -> populateEditCourseFields());
        view.editCourseButton.addActionListener(e -> editCourse());
        // New buttons
        view.setThresholdButton.addActionListener(e -> setCustomLateThreshold());
        view.markExcusedButton.addActionListener(e -> markExcusedAbsence());
        // Initial population
        refreshAllData();
    }

    private void refreshAllData() {
        updateStudentList();
        updateCourseList();
        updateAssignmentList();
        updateAllCourseCombos();
        updateAllStudentCombos();
        updateMarkStudentCombo();
        updateViewAttendanceArea();
        updateDeleteCombos();
        updateRulesCombos();
        updateDeleteAssignmentCombos();
    }

    private void updateStudentList() {
        StringBuilder sb = new StringBuilder();
        for (Student s : model.getStudents().values()) {
            sb.append(String.format("%s: %s (%s)\n", s.getId(), s.getName(), s.getEmail()));
        }
        view.studentListArea.setText(sb.toString());
    }

    private void updateCourseList() {
        StringBuilder sb = new StringBuilder();
        for (Course c : model.getCourses().values()) {
            sb.append(String.format("%s: %s [%s]\n", c.getId(), c.getName(), c.getCode()));
        }
        view.courseListArea.setText(sb.toString());
    }

    private void updateAssignmentList() {
        StringBuilder sb = new StringBuilder();
        for (String courseId : model.getCourses().keySet()) {
            Course c = model.getCourses().get(courseId);
            sb.append(String.format("%s: %s\n", c.getId(), c.getName()));
            Set<String> studentIds = model.getStudentsForCourse(courseId);
            if (studentIds.isEmpty()) {
                sb.append("  No students assigned.\n");
            } else {
                for (String sid : studentIds) {
                    Student s = model.getStudents().get(sid);
                    if (s != null) sb.append(String.format("  - %s: %s\n", s.getId(), s.getName()));
                }
            }
        }
        view.assignmentListArea.setText(sb.toString());
    }

    private void updateAllCourseCombos() {
        JComboBox[] courseCombos = {view.assignCourseComboBox, view.markCourseComboBox, view.viewCourseComboBox};
        User currentUser = model.getCurrentUser();
        
        for (JComboBox combo : courseCombos) {
            combo.removeAllItems();
            
            if (currentUser != null && currentUser.isStudent()) {
                // For students, show only courses they are enrolled in
                String studentId = currentUser.getStudentId();
                Set<String> enrolledCourses = model.getCoursesForStudent(studentId);
                for (String courseId : enrolledCourses) {
                    combo.addItem(courseId);
                }
            } else {
                // For admin/teacher, show all courses
                for (String id : model.getCourses().keySet()) {
                    combo.addItem(id);
                }
            }
        }
    }

    private void updateAllStudentCombos() {
        JComboBox[] studentCombos = {view.assignStudentComboBox};
        for (JComboBox combo : studentCombos) {
            combo.removeAllItems();
            for (String id : model.getStudents().keySet()) {
                combo.addItem(id);
            }
        }
    }

    private void updateMarkStudentCombo() {
        view.markStudentComboBox.removeAllItems();
        String courseId = (String) view.markCourseComboBox.getSelectedItem();
        if (courseId == null) return;
        Set<String> studentIds = model.getStudentsForCourse(courseId);
        for (String sid : studentIds) {
            view.markStudentComboBox.addItem(sid);
        }
    }

    private void updateViewAttendanceArea() {
        String courseId = (String) view.viewCourseComboBox.getSelectedItem();
        if (courseId == null) {
            view.viewAttendanceArea.setText("");
            return;
        }
        StringBuilder sb = new StringBuilder();
        Course course = model.getCourses().get(courseId);
        sb.append("Attendance Records for ").append(course.getName()).append("\n");
        sb.append("Course Code: ").append(course.getCode()).append("\n\n");
        List<AttendanceRecord> records = model.getAttendanceRecords().get(courseId);
        if (records == null || records.isEmpty()) {
            sb.append("No attendance records found for this course.");
        } else {
            // If current user is a student, show only their records
            User currentUser = model.getCurrentUser();
            if (currentUser != null && currentUser.isStudent()) {
                String studentId = currentUser.getStudentId();
                boolean foundStudentRecords = false;
                for (AttendanceRecord record : records) {
                    if (record.getStudent().getId().equals(studentId)) {
                        sb.append(String.format("%s - %s - %s\n", record.getStudent().getName(), record.getDateTime().format(DATE_TIME_FORMATTER), record.isLate() ? "LATE" : "PRESENT"));
                        foundStudentRecords = true;
                    }
                }
                if (!foundStudentRecords) {
                    sb.append("No attendance records found for you in this course.");
                }
            } else {
                // Show all records for admin/teacher
                for (AttendanceRecord record : records) {
                    sb.append(String.format("%s - %s - %s\n", record.getStudent().getName(), record.getDateTime().format(DATE_TIME_FORMATTER), record.isLate() ? "LATE" : "PRESENT"));
                }
            }
        }
        view.viewAttendanceArea.setText(sb.toString());
    }

    private void updateDeleteCombos() {
        view.deleteStudentComboBox.removeAllItems();
        for (String id : model.getStudents().keySet()) {
            view.deleteStudentComboBox.addItem(id);
        }
        view.deleteCourseComboBox.removeAllItems();
        for (String id : model.getCourses().keySet()) {
            view.deleteCourseComboBox.addItem(id);
        }
    }

    private void addStudent() {
        if (!checkAdminPermission()) return;
        String id = view.studentIdField.getText().trim();
        String name = view.studentNameField.getText().trim();
        String email = view.studentEmailField.getText().trim();
        if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
            view.showMessage("All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (model.getStudents().containsKey(id)) {
            view.showMessage("Student ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Student student = new Student(id, name, email);
        model.addStudent(student);
        view.studentIdField.setText("");
        view.studentNameField.setText("");
        view.studentEmailField.setText("");
        refreshAllData();
        view.showMessage("Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addCourse() {
        if (!checkAdminPermission()) return;
        String id = view.courseIdField.getText().trim();
        String name = view.courseNameField.getText().trim();
        String code = view.courseCodeField.getText().trim();
        if (id.isEmpty() || name.isEmpty() || code.isEmpty()) {
            view.showMessage("All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (model.getCourses().containsKey(id)) {
            view.showMessage("Course ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Course course = new Course(id, name, code);
        model.addCourse(course);
        view.courseIdField.setText("");
        view.courseNameField.setText("");
        view.courseCodeField.setText("");
        refreshAllData();
        view.showMessage("Course added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void assignStudentToCourse() {
        if (!checkTeacherPermission()) return;
        String courseId = (String) view.assignCourseComboBox.getSelectedItem();
        String studentId = (String) view.assignStudentComboBox.getSelectedItem();
        if (courseId == null || studentId == null) {
            view.showMessage("Please select both course and student.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (model.getStudentsForCourse(courseId).contains(studentId)) {
            view.showMessage("Student already assigned to this course!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        model.assignStudentToCourse(courseId, studentId);
        refreshAllData();
        view.showMessage("Student assigned to course successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void markAttendance() {
        if (!checkTeacherPermission()) return;
        String courseId = (String) view.markCourseComboBox.getSelectedItem();
        String studentId = (String) view.markStudentComboBox.getSelectedItem();
        String dateStr = view.markAttendanceDateField.getText().trim();
        if (courseId == null || studentId == null) {
            view.showMessage("Please select both course and student.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.LocalDate date;
        if (!dateStr.isEmpty()) {
            try {
                date = java.time.LocalDate.parse(dateStr);
            } catch (Exception ex) {
                view.showMessage("Invalid date format. Use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            date = now.toLocalDate();
        }
        java.time.LocalTime time = now.toLocalTime();
        java.time.LocalDateTime attendanceDateTime = java.time.LocalDateTime.of(date, time);
        // Use course-specific late threshold
        boolean isLateDefault = attendanceDateTime.toLocalTime().isAfter(model.getLateThresholdForCourse(courseId));
        Student student = model.getStudents().get(studentId);
        Course course = model.getCourses().get(courseId);
        String status = isLateDefault ? "LATE" : "ON TIME";
        int userLate = JOptionPane.showConfirmDialog(view,
            String.format("%s is %s for %s at %s.\nIs the student actually late? (Yes = Late, No = On Time)",
                student.getName(), status, course.getName(), attendanceDateTime.format(DATE_TIME_FORMATTER)),
            "Confirm Late Status", JOptionPane.YES_NO_OPTION);
        boolean isLate = (userLate == JOptionPane.YES_OPTION);
        int confirm = JOptionPane.showConfirmDialog(view,
            String.format("Do you want to mark attendance for %s as %s?",
                student.getName(), isLate ? "LATE" : "ON TIME"),
            "Confirm Attendance", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        AttendanceRecord record = new AttendanceRecord(student, course, attendanceDateTime, isLate ? AttendanceRecord.AttendanceStatus.LATE : AttendanceRecord.AttendanceStatus.PRESENT);
        model.addAttendanceRecord(courseId, record);
        refreshAllData();
        String message = String.format("Marked %s as %s for %s at %s", student.getName(), isLate ? "LATE" : "ON TIME", course.getName(), attendanceDateTime.format(DATE_TIME_FORMATTER));
        view.markAttendanceArea.setText(message);
        view.showMessage(message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewAttendance() {
        User currentUser = model.getCurrentUser();
        if (currentUser == null) return;
        
        if (currentUser.isStudent()) {
            // Show only student's own attendance
            showStudentAttendance(currentUser.getStudentId());
        } else {
            updateViewAttendanceArea();
        }
    }

    private void showStudentAttendance(String studentId) {
        StringBuilder sb = new StringBuilder();
        sb.append("Your Attendance Records\n\n");
        boolean hasRecords = false;
        for (String courseId : model.getCoursesForStudent(studentId)) {
            Course course = model.getCourses().get(courseId);
            sb.append("Course: ").append(course.getName()).append("\n");
            List<AttendanceRecord> records = model.getAttendanceRecords().get(courseId);
            if (records != null) {
                for (AttendanceRecord record : records) {
                    if (record.getStudent().getId().equals(studentId)) {
                        sb.append(String.format("  %s - %s\n", 
                            record.getDateTime().format(DATE_TIME_FORMATTER), 
                            record.isLate() ? "LATE" : "PRESENT"));
                        hasRecords = true;
                    }
                }
            }
            sb.append("\n");
        }
        if (!hasRecords) {
            sb.append("No attendance records found.");
        }
        view.viewAttendanceArea.setText(sb.toString());
    }

    private void showLateStudents() {
        StringBuilder sb = new StringBuilder();
        sb.append("Late Students Report\n");
        sb.append("Generated at: ").append(LocalDateTime.now().format(DATE_TIME_FORMATTER)).append("\n\n");
        boolean hasLateStudents = false;
        for (String courseId : model.getCourses().keySet()) {
            Course course = model.getCourses().get(courseId);
            List<AttendanceRecord> records = model.getAttendanceRecords().get(courseId);
            if (records == null) continue;
            for (AttendanceRecord record : records) {
                if (record.isLate()) {
                    sb.append(String.format("%s (%s) was LATE for %s at %s\n", record.getStudent().getName(), record.getStudent().getId(), course.getName(), record.getDateTime().format(DATE_TIME_FORMATTER)));
                    hasLateStudents = true;
                }
            }
        }
        if (!hasLateStudents) {
            sb.append("No late students found.\n");
        }
        view.lateStudentsArea.setText(sb.toString());
    }

    private void deleteStudent() {
        if (!checkAdminPermission()) return;
        String studentId = (String) view.deleteStudentComboBox.getSelectedItem();
        if (studentId == null) {
            view.showMessage("Please select a student to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete student " + studentId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        model.deleteStudent(studentId);
        refreshAllData();
        view.showMessage("Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteCourse() {
        if (!checkAdminPermission()) return;
        String courseId = (String) view.deleteCourseComboBox.getSelectedItem();
        if (courseId == null) {
            view.showMessage("Please select a course to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete course " + courseId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        model.deleteCourse(courseId);
        refreshAllData();
        view.showMessage("Course deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void populateEditStudentFields() {
        String studentId = (String) view.deleteStudentComboBox.getSelectedItem();
        if (studentId == null) {
            view.editStudentNameField.setText("");
            view.editStudentEmailField.setText("");
            return;
        }
        Student s = model.getStudents().get(studentId);
        if (s != null) {
            view.editStudentNameField.setText(s.getName());
            view.editStudentEmailField.setText(s.getEmail());
        }
    }

    private void editStudent() {
        if (!checkAdminPermission()) return;
        String studentId = (String) view.deleteStudentComboBox.getSelectedItem();
        String newName = view.editStudentNameField.getText().trim();
        String newEmail = view.editStudentEmailField.getText().trim();
        if (studentId == null || newName.isEmpty() || newEmail.isEmpty()) {
            view.showMessage("Select a student and fill in new name and email.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        model.editStudent(studentId, newName, newEmail);
        refreshAllData();
        view.showMessage("Student updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void populateEditCourseFields() {
        String courseId = (String) view.deleteCourseComboBox.getSelectedItem();
        if (courseId == null) {
            view.editCourseNameField.setText("");
            view.editCourseCodeField.setText("");
            return;
        }
        Course c = model.getCourses().get(courseId);
        if (c != null) {
            view.editCourseNameField.setText(c.getName());
            view.editCourseCodeField.setText(c.getCode());
        }
    }

    private void editCourse() {
        if (!checkAdminPermission()) return;
        String courseId = (String) view.deleteCourseComboBox.getSelectedItem();
        String newName = view.editCourseNameField.getText().trim();
        String newCode = view.editCourseCodeField.getText().trim();
        if (courseId == null || newName.isEmpty() || newCode.isEmpty()) {
            view.showMessage("Select a course and fill in new name and code.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        model.editCourse(courseId, newName, newCode);
        refreshAllData();
        view.showMessage("Course updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportStudentsToCSV() {
        String filename = "students_export.csv";
        try (java.io.FileWriter fw = new java.io.FileWriter(filename)) {
            fw.write("ID,Name,Email\n");
            for (Student s : model.getStudents().values()) {
                fw.write(String.format("%s,%s,%s\n", s.getId(), s.getName(), s.getEmail()));
            }
            view.showMessage("Students exported to " + filename, "Export Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            view.showMessage("Export failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportCoursesToCSV() {
        String filename = "courses_export.csv";
        try (java.io.FileWriter fw = new java.io.FileWriter(filename)) {
            fw.write("ID,Name,Code\n");
            for (Course c : model.getCourses().values()) {
                fw.write(String.format("%s,%s,%s\n", c.getId(), c.getName(), c.getCode()));
            }
            view.showMessage("Courses exported to " + filename, "Export Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            view.showMessage("Export failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportAttendanceToCSV() {
        String courseId = (String) view.viewCourseComboBox.getSelectedItem();
        if (courseId == null) {
            view.showMessage("Select a course to export attendance.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String filename = "attendance_" + courseId + "_export.csv";
        try (java.io.FileWriter fw = new java.io.FileWriter(filename)) {
            fw.write("Student Name,DateTime,Status\n");
            List<AttendanceRecord> records = model.getAttendanceRecords().get(courseId);
            if (records != null) {
                for (AttendanceRecord record : records) {
                    fw.write(String.format("%s,%s,%s\n",
                        record.getStudent().getName(),
                        record.getDateTime().format(DATE_TIME_FORMATTER),
                        record.isLate() ? "LATE" : "ON TIME"));
                }
            }
            view.showMessage("Attendance exported to " + filename, "Export Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            view.showMessage("Export failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Attendance Statistics ---\n\n");
        // Per Student
        sb.append("Per Student:\n");
        for (Student s : model.getStudents().values()) {
            int present = 0, late = 0, total = 0;
            for (String courseId : model.getCoursesForStudent(s.getId())) {
                List<AttendanceRecord> records = model.getAttendanceRecords().get(courseId);
                if (records != null) {
                    for (AttendanceRecord r : records) {
                        if (r.getStudent().getId().equals(s.getId())) {
                            total++;
                            if (r.isLate()) late++; else present++;
                        }
                    }
                }
            }
            double percent = total > 0 ? (present * 100.0 / total) : 0.0;
            sb.append(String.format("%s: Present=%d, Late=%d, Total=%d, %%=%.1f\n", s.getName(), present, late, total, percent));
        }
        sb.append("\nPer Course:\n");
        for (Course c : model.getCourses().values()) {
            int present = 0, late = 0, total = 0;
            List<AttendanceRecord> records = model.getAttendanceRecords().get(c.getId());
            if (records != null) {
                for (AttendanceRecord r : records) {
                    total++;
                    if (r.isLate()) late++; else present++;
                }
            }
            double percent = total > 0 ? (present * 100.0 / total) : 0.0;
            sb.append(String.format("%s: Present=%d, Late=%d, Total=%d, %%=%.1f\n", c.getName(), present, late, total, percent));
        }
        view.statisticsArea.setText(sb.toString());
    }

    private void searchStudents() {
        String query = view.searchStudentField.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            updateStudentList();
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Student s : model.getStudents().values()) {
            if (s.getId().toLowerCase().contains(query) ||
                s.getName().toLowerCase().contains(query) ||
                s.getEmail().toLowerCase().contains(query)) {
                sb.append(String.format("%s: %s (%s)\n", s.getId(), s.getName(), s.getEmail()));
            }
        }
        view.studentListArea.setText(sb.toString());
    }

    private void searchCourses() {
        String query = view.searchCourseField.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            updateCourseList();
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Course c : model.getCourses().values()) {
            if (c.getId().toLowerCase().contains(query) ||
                c.getName().toLowerCase().contains(query) ||
                c.getCode().toLowerCase().contains(query)) {
                sb.append(String.format("%s: %s [%s]\n", c.getId(), c.getName(), c.getCode()));
            }
        }
        view.courseListArea.setText(sb.toString());
    }

    private void filterAttendanceByDate() {
        String courseId = (String) view.viewCourseComboBox.getSelectedItem();
        String dateStr = view.attendanceDateField.getText().trim();
        if (courseId == null || dateStr.isEmpty()) {
            updateViewAttendanceArea();
            return;
        }
        StringBuilder sb = new StringBuilder();
        Course course = model.getCourses().get(courseId);
        sb.append("Attendance Records for ").append(course.getName()).append(" on ").append(dateStr).append("\n");
        sb.append("Course Code: ").append(course.getCode()).append("\n\n");
        List<AttendanceRecord> records = model.getAttendanceRecords().get(courseId);
        boolean found = false;
        if (records != null) {
            for (AttendanceRecord record : records) {
                String recordDate = record.getDateTime().toLocalDate().toString();
                if (recordDate.equals(dateStr)) {
                    sb.append(String.format("%s - %s - %s\n", record.getStudent().getName(), record.getDateTime().format(DATE_TIME_FORMATTER), record.isLate() ? "LATE" : "PRESENT"));
                    found = true;
                }
            }
        }
        if (!found) sb.append("No attendance records found for this date.");
        view.viewAttendanceArea.setText(sb.toString());
    }

    private void toggleTheme() {
        try {
            if (view.themeToggleButton.isSelected()) {
                javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                view.themeToggleButton.setText("Light Mode");
                view.showMessage("Dark mode enabled!", "Theme", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } else {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
                view.themeToggleButton.setText("Dark Mode");
                view.showMessage("Light mode enabled!", "Theme", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
            javax.swing.SwingUtilities.updateComponentTreeUI(view);
        } catch (Exception ex) {
            view.showMessage("Failed to change theme: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logout() {
        model.setCurrentUser(null);
        view.currentUserLabel.setText("Not logged in");
        view.showMessage("Logged out successfully!", "Logout", JOptionPane.INFORMATION_MESSAGE);
        // Disable all tabs
        for (int i = 0; i < view.tabbedPane.getTabCount(); i++) {
            view.tabbedPane.setEnabledAt(i, false);
        }
        
        // Show login dialog for new user
        User newUser = AttendanceView.showLoginDialog(model);
        if (newUser != null) {
            view.updateUIForUser(newUser);
            refreshAllData();
        } else {
            // If no user logged in, exit the application
            System.exit(0);
        }
    }

    private boolean checkAdminPermission() {
        if (model.getCurrentUser() == null || !model.getCurrentUser().isAdmin()) {
            view.showMessage("Admin permission required!", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean checkTeacherPermission() {
        if (model.getCurrentUser() == null || (!model.getCurrentUser().isTeacher() && !model.getCurrentUser().isAdmin())) {
            view.showMessage("Teacher permission required!", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void updateRulesCombos() {
        view.rulesCourseComboBox.removeAllItems();
        for (String id : model.getCourses().keySet()) {
            view.rulesCourseComboBox.addItem(id);
        }
        view.excusedCourseComboBox.removeAllItems();
        for (String id : model.getCourses().keySet()) {
            view.excusedCourseComboBox.addItem(id);
        }
        view.excusedStudentComboBox.removeAllItems();
        for (String id : model.getStudents().keySet()) {
            view.excusedStudentComboBox.addItem(id);
        }
    }

    private void setCustomLateThreshold() {
        if (!checkAdminPermission()) return;
        String courseId = (String) view.rulesCourseComboBox.getSelectedItem();
        String timeStr = view.lateThresholdField.getText().trim();
        if (courseId == null || timeStr.isEmpty()) {
            view.showMessage("Select a course and enter time (HH:MM).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            LocalTime threshold = LocalTime.parse(timeStr);
            Course course = model.getCourses().get(courseId);
            course.setCustomLateThreshold(threshold);
            model.rewriteCoursesFile();
            view.showMessage("Late threshold set to " + threshold + " for " + course.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            view.showMessage("Invalid time format. Use HH:MM (e.g., 09:30).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void markExcusedAbsence() {
        if (!checkTeacherPermission()) return;
        String studentId = (String) view.excusedStudentComboBox.getSelectedItem();
        String courseId = (String) view.excusedCourseComboBox.getSelectedItem();
        String dateStr = view.excusedDateField.getText().trim();
        if (studentId == null || courseId == null || dateStr.isEmpty()) {
            view.showMessage("Select student, course, and enter date.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            LocalDate date = LocalDate.parse(dateStr);
            LocalTime time = LocalTime.of(9, 0); // Default time for absence
            LocalDateTime absenceDateTime = LocalDateTime.of(date, time);
            Student student = model.getStudents().get(studentId);
            Course course = model.getCourses().get(courseId);
            AttendanceRecord record = new AttendanceRecord(student, course, absenceDateTime, AttendanceRecord.AttendanceStatus.EXCUSED_ABSENCE);
            model.addAttendanceRecord(courseId, record);
            refreshAllData();
            view.showMessage("Marked " + student.getName() + " as excused absence for " + course.getName() + " on " + dateStr, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            view.showMessage("Invalid date format. Use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAvailableUsers() {
        view.showAvailableUsers(model);
    }

    private void updateDeleteAssignmentCombos() {
        view.deleteAssignCourseComboBox.removeAllItems();
        for (String courseId : model.getCourses().keySet()) {
            view.deleteAssignCourseComboBox.addItem(courseId);
        }
        updateDeleteAssignStudentCombo();
        view.deleteAssignCourseComboBox.addActionListener(e -> updateDeleteAssignStudentCombo());
    }

    private void updateDeleteAssignStudentCombo() {
        view.deleteAssignStudentComboBox.removeAllItems();
        String courseId = (String) view.deleteAssignCourseComboBox.getSelectedItem();
        if (courseId == null) return;
        Set<String> studentIds = model.getStudentsForCourse(courseId);
        for (String sid : studentIds) {
            view.deleteAssignStudentComboBox.addItem(sid);
        }
    }

    private void deleteAssignment() {
        if (!checkTeacherPermission()) return;
        String courseId = (String) view.deleteAssignCourseComboBox.getSelectedItem();
        String studentId = (String) view.deleteAssignStudentComboBox.getSelectedItem();
        if (courseId == null || studentId == null) {
            view.showMessage("Please select both course and student to delete assignment.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to unassign student " + studentId + " from course " + courseId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        model.deleteAssignment(courseId, studentId);
        refreshAllData();
        updateDeleteAssignmentCombos();
        view.showMessage("Assignment deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
} 