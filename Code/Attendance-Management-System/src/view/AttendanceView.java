package view;

import model.AttendanceModel;
import model.User;
import javax.swing.*;
import java.awt.*;

public class AttendanceView extends JFrame {
    public final JTabbedPane tabbedPane;
    // Students Tab
    public final JTextField studentIdField;
    public final JTextField studentNameField;
    public final JTextField studentEmailField;
    public final JButton addStudentButton;
    public final JTextArea studentListArea;
    public final JComboBox<String> deleteStudentComboBox;
    public final JButton deleteStudentButton;
    public final JButton editStudentButton;
    public final JTextField editStudentNameField;
    public final JTextField editStudentEmailField;
    public final JButton exportStudentsButton;
    public final JTextField searchStudentField;
    public final JButton searchStudentButton;
    // Courses Tab
    public final JTextField courseIdField;
    public final JTextField courseNameField;
    public final JTextField courseCodeField;
    public final JButton addCourseButton;
    public final JTextArea courseListArea;
    public final JComboBox<String> deleteCourseComboBox;
    public final JButton deleteCourseButton;
    public final JButton editCourseButton;
    public final JTextField editCourseNameField;
    public final JTextField editCourseCodeField;
    public final JButton exportCoursesButton;
    public final JTextField searchCourseField;
    public final JButton searchCourseButton;
    // Assign Tab
    public final JComboBox<String> assignCourseComboBox;
    public final JComboBox<String> assignStudentComboBox;
    public final JButton assignButton;
    public final JTextArea assignmentListArea;
    // Mark Attendance Tab
    public final JComboBox<String> markCourseComboBox;
    public final JComboBox<String> markStudentComboBox;
    public final JButton markAttendanceButton;
    public final JTextArea markAttendanceArea;
    public final JTextField markAttendanceDateField;
    // View Attendance Tab
    public final JComboBox<String> viewCourseComboBox;
    public final JTextArea viewAttendanceArea;
    public final JButton viewAttendanceButton;
    public final JButton exportAttendanceButton;
    public final JTextField attendanceDateField;
    public final JButton filterAttendanceButton;
    // Late Students Tab
    public final JTextArea lateStudentsArea;
    public final JButton viewLateStudentsButton;
    // Statistics Tab
    public final JTextArea statisticsArea;
    public final JButton refreshStatisticsButton;
    // Theme toggle
    public final JToggleButton themeToggleButton;
    public final JButton logoutButton;
    public final JLabel currentUserLabel;
    public final JButton showUsersButton;
    // Attendance Rules Tab
    public final JComboBox<String> rulesCourseComboBox;
    public final JTextField lateThresholdField;
    public final JButton setThresholdButton;
    public final JComboBox<String> excusedStudentComboBox;
    public final JComboBox<String> excusedCourseComboBox;
    public final JTextField excusedDateField;
    public final JButton markExcusedButton;

    public AttendanceView(AttendanceModel model) {
        setTitle("Attendance Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();
        // Students Tab
        JPanel studentPanel = new JPanel(new BorderLayout(10, 10));
        JPanel studentInput = new JPanel(new GridLayout(1, 4, 10, 10));
        studentIdField = new JTextField();
        studentNameField = new JTextField();
        studentEmailField = new JTextField();
        addStudentButton = new JButton("Add Student");
        studentInput.add(new JLabel("ID:"));
        studentInput.add(studentIdField);
        studentInput.add(new JLabel("Name:"));
        studentInput.add(studentNameField);
        studentInput.add(new JLabel("Email:"));
        studentInput.add(studentEmailField);
        studentInput.add(addStudentButton);
        studentListArea = new JTextArea(8, 40);
        studentListArea.setEditable(false);
        JPanel studentDeletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deleteStudentComboBox = new JComboBox<>();
        deleteStudentButton = new JButton("Delete Student");
        studentDeletePanel.add(new JLabel("Delete:"));
        studentDeletePanel.add(deleteStudentComboBox);
        studentDeletePanel.add(deleteStudentButton);
        JPanel studentEditPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        editStudentNameField = new JTextField(10);
        editStudentEmailField = new JTextField(15);
        editStudentButton = new JButton("Edit Student");
        studentEditPanel.add(new JLabel("Edit Name:"));
        studentEditPanel.add(editStudentNameField);
        studentEditPanel.add(new JLabel("Edit Email:"));
        studentEditPanel.add(editStudentEmailField);
        studentEditPanel.add(editStudentButton);
        studentPanel.add(studentInput, BorderLayout.NORTH);
        studentPanel.add(new JScrollPane(studentListArea), BorderLayout.CENTER);
        studentPanel.add(studentDeletePanel, BorderLayout.SOUTH);
        studentPanel.add(studentEditPanel, BorderLayout.EAST);
        exportStudentsButton = new JButton("Export Students to CSV");
        studentPanel.add(exportStudentsButton, BorderLayout.WEST);
        JPanel studentSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchStudentField = new JTextField(12);
        searchStudentButton = new JButton("Search Student");
        studentSearchPanel.add(new JLabel("Search:"));
        studentSearchPanel.add(searchStudentField);
        studentSearchPanel.add(searchStudentButton);
        studentPanel.add(studentSearchPanel, BorderLayout.BEFORE_FIRST_LINE);
        tabbedPane.addTab("Students", studentPanel);
        // Courses Tab
        JPanel coursePanel = new JPanel(new BorderLayout(10, 10));
        JPanel courseInput = new JPanel(new GridLayout(1, 4, 10, 10));
        courseIdField = new JTextField();
        courseNameField = new JTextField();
        courseCodeField = new JTextField();
        addCourseButton = new JButton("Add Course");
        courseInput.add(new JLabel("ID:"));
        courseInput.add(courseIdField);
        courseInput.add(new JLabel("Name:"));
        courseInput.add(courseNameField);
        courseInput.add(new JLabel("Code:"));
        courseInput.add(courseCodeField);
        courseInput.add(addCourseButton);
        courseListArea = new JTextArea(8, 40);
        courseListArea.setEditable(false);
        JPanel courseDeletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deleteCourseComboBox = new JComboBox<>();
        deleteCourseButton = new JButton("Delete Course");
        courseDeletePanel.add(new JLabel("Delete:"));
        courseDeletePanel.add(deleteCourseComboBox);
        courseDeletePanel.add(deleteCourseButton);
        JPanel courseEditPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        editCourseNameField = new JTextField(10);
        editCourseCodeField = new JTextField(10);
        editCourseButton = new JButton("Edit Course");
        courseEditPanel.add(new JLabel("Edit Name:"));
        courseEditPanel.add(editCourseNameField);
        courseEditPanel.add(new JLabel("Edit Code:"));
        courseEditPanel.add(editCourseCodeField);
        courseEditPanel.add(editCourseButton);
        coursePanel.add(courseInput, BorderLayout.NORTH);
        coursePanel.add(new JScrollPane(courseListArea), BorderLayout.CENTER);
        coursePanel.add(courseDeletePanel, BorderLayout.SOUTH);
        coursePanel.add(courseEditPanel, BorderLayout.EAST);
        exportCoursesButton = new JButton("Export Courses to CSV");
        coursePanel.add(exportCoursesButton, BorderLayout.WEST);
        JPanel courseSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchCourseField = new JTextField(12);
        searchCourseButton = new JButton("Search Course");
        courseSearchPanel.add(new JLabel("Search:"));
        courseSearchPanel.add(searchCourseField);
        courseSearchPanel.add(searchCourseButton);
        coursePanel.add(courseSearchPanel, BorderLayout.BEFORE_FIRST_LINE);
        tabbedPane.addTab("Courses", coursePanel);
        // Assign Tab
        JPanel assignPanel = new JPanel(new BorderLayout(10, 10));
        JPanel assignInput = new JPanel(new GridLayout(1, 3, 10, 10));
        assignCourseComboBox = new JComboBox<>();
        assignStudentComboBox = new JComboBox<>();
        assignButton = new JButton("Assign Student to Course");
        assignInput.add(new JLabel("Course:"));
        assignInput.add(assignCourseComboBox);
        assignInput.add(new JLabel("Student:"));
        assignInput.add(assignStudentComboBox);
        assignInput.add(assignButton);
        assignmentListArea = new JTextArea(8, 40);
        assignmentListArea.setEditable(false);
        assignPanel.add(assignInput, BorderLayout.NORTH);
        assignPanel.add(new JScrollPane(assignmentListArea), BorderLayout.CENTER);
        tabbedPane.addTab("Assign Students", assignPanel);
        // Mark Attendance Tab
        JPanel markPanel = new JPanel(new BorderLayout(10, 10));
        JPanel markInput = new JPanel(new GridLayout(1, 4, 10, 10));
        markCourseComboBox = new JComboBox<>();
        markStudentComboBox = new JComboBox<>();
        markAttendanceDateField = new JTextField(10);
        markAttendanceButton = new JButton("Mark Attendance");
        markInput.add(new JLabel("Course:"));
        markInput.add(markCourseComboBox);
        markInput.add(new JLabel("Student:"));
        markInput.add(markStudentComboBox);
        markInput.add(new JLabel("Date (yyyy-MM-dd):"));
        markInput.add(markAttendanceDateField);
        markInput.add(markAttendanceButton);
        markAttendanceArea = new JTextArea(8, 40);
        markAttendanceArea.setEditable(false);
        markPanel.add(markInput, BorderLayout.NORTH);
        markPanel.add(new JScrollPane(markAttendanceArea), BorderLayout.CENTER);
        tabbedPane.addTab("Mark Attendance", markPanel);
        // View Attendance Tab
        JPanel viewPanel = new JPanel(new BorderLayout(10, 10));
        JPanel viewInput = new JPanel(new GridLayout(1, 2, 10, 10));
        viewCourseComboBox = new JComboBox<>();
        viewAttendanceButton = new JButton("View Attendance");
        viewInput.add(new JLabel("Course:"));
        viewInput.add(viewCourseComboBox);
        viewInput.add(viewAttendanceButton);
        viewAttendanceArea = new JTextArea(8, 40);
        viewAttendanceArea.setEditable(false);
        viewPanel.add(viewInput, BorderLayout.NORTH);
        viewPanel.add(new JScrollPane(viewAttendanceArea), BorderLayout.CENTER);
        exportAttendanceButton = new JButton("Export Attendance to CSV");
        viewPanel.add(exportAttendanceButton, BorderLayout.SOUTH);
        JPanel attendanceFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        attendanceDateField = new JTextField(10);
        filterAttendanceButton = new JButton("Filter by Date (yyyy-MM-dd)");
        attendanceFilterPanel.add(new JLabel("Date:"));
        attendanceFilterPanel.add(attendanceDateField);
        attendanceFilterPanel.add(filterAttendanceButton);
        viewPanel.add(attendanceFilterPanel, BorderLayout.AFTER_LAST_LINE);
        tabbedPane.addTab("View Attendance", viewPanel);
        // Late Students Tab
        JPanel latePanel = new JPanel(new BorderLayout(10, 10));
        viewLateStudentsButton = new JButton("Show Late Students");
        lateStudentsArea = new JTextArea(8, 40);
        lateStudentsArea.setEditable(false);
        latePanel.add(viewLateStudentsButton, BorderLayout.NORTH);
        latePanel.add(new JScrollPane(lateStudentsArea), BorderLayout.CENTER);
        tabbedPane.addTab("Late Students", latePanel);
        // Statistics Tab
        JPanel statsPanel = new JPanel(new BorderLayout(10, 10));
        refreshStatisticsButton = new JButton("Refresh Statistics");
        statisticsArea = new JTextArea(12, 40);
        statisticsArea.setEditable(false);
        statsPanel.add(refreshStatisticsButton, BorderLayout.NORTH);
        statsPanel.add(new JScrollPane(statisticsArea), BorderLayout.CENTER);
        tabbedPane.addTab("Statistics", statsPanel);
        // Attendance Rules Tab
        JPanel rulesPanel = new JPanel(new BorderLayout(10, 10));
        JPanel thresholdPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        rulesCourseComboBox = new JComboBox<>();
        lateThresholdField = new JTextField(8);
        setThresholdButton = new JButton("Set Late Threshold (HH:MM)");
        thresholdPanel.add(new JLabel("Course:"));
        thresholdPanel.add(rulesCourseComboBox);
        thresholdPanel.add(new JLabel("Late Threshold:"));
        thresholdPanel.add(lateThresholdField);
        thresholdPanel.add(setThresholdButton);
        
        JPanel excusedPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        excusedStudentComboBox = new JComboBox<>();
        excusedCourseComboBox = new JComboBox<>();
        excusedDateField = new JTextField(10);
        markExcusedButton = new JButton("Mark Excused Absence");
        excusedPanel.add(new JLabel("Student:"));
        excusedPanel.add(excusedStudentComboBox);
        excusedPanel.add(new JLabel("Course:"));
        excusedPanel.add(excusedCourseComboBox);
        excusedPanel.add(new JLabel("Date (yyyy-MM-dd):"));
        excusedPanel.add(excusedDateField);
        excusedPanel.add(markExcusedButton);
        
        JPanel rulesTopPanel = new JPanel(new BorderLayout());
        rulesTopPanel.add(thresholdPanel, BorderLayout.NORTH);
        rulesTopPanel.add(excusedPanel, BorderLayout.SOUTH);
        rulesPanel.add(rulesTopPanel, BorderLayout.NORTH);
        tabbedPane.addTab("Attendance Rules", rulesPanel);
        // Theme toggle
        themeToggleButton = new JToggleButton("Dark Mode");
        themeToggleButton.setFocusable(false);
        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        themePanel.add(themeToggleButton);
        add(themePanel, BorderLayout.NORTH);
        // User info panel
        JPanel userPanel = new JPanel(new BorderLayout());
        currentUserLabel = new JLabel("Not logged in");
        logoutButton = new JButton("Logout");
        showUsersButton = new JButton("Show Available Users");
        userPanel.add(currentUserLabel, BorderLayout.WEST);
        userPanel.add(showUsersButton, BorderLayout.CENTER);
        userPanel.add(logoutButton, BorderLayout.EAST);
        add(userPanel, BorderLayout.SOUTH);
        add(tabbedPane, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }
    public void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }
    
    public void showAvailableUsers(AttendanceModel model) {
        StringBuilder sb = new StringBuilder();
        sb.append("Available Users:\n\n");
        sb.append("Admin Users:\n");
        sb.append("Username: admin, Password: admin123\n\n");
        sb.append("Teacher Users:\n");
        sb.append("Username: teacher, Password: teacher123\n\n");
        sb.append("Student Users:\n");
        
        // Show student users that exist
        boolean hasStudentUsers = false;
        for (User user : model.getUsers().values()) {
            if (user.isStudent()) {
                sb.append("Username: ").append(user.getUsername()).append(", Password: student123\n");
                hasStudentUsers = true;
            }
        }
        
        if (!hasStudentUsers) {
            sb.append("No student users yet. Add students as admin to create student accounts.\n");
        }
        
        sb.append("\nNote: Student accounts are automatically created when students are added.");
        sb.append("\nStudent usernames are their student IDs in lowercase.");
        
        JOptionPane.showMessageDialog(this, sb.toString(), "Available Users", JOptionPane.INFORMATION_MESSAGE);
    }
    public static User showLoginDialog(AttendanceModel model) {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword());
            return model.authenticateUser(user, pass);
        }
        return null;
    }
    public void updateUIForUser(User user) {
        currentUserLabel.setText("Logged in as: " + user.getUsername() + " (" + user.getRole() + ")");
        
        // Show/hide tabs based on role
        if (user.isAdmin()) {
            // Admin sees everything
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                tabbedPane.setEnabledAt(i, true);
            }
        } else if (user.isTeacher()) {
            // Teacher sees: Assign Students, Mark Attendance, View Attendance, Late Students, Statistics
            tabbedPane.setEnabledAt(0, false); // Students
            tabbedPane.setEnabledAt(1, false); // Courses
            tabbedPane.setEnabledAt(2, true);  // Assign Students
            tabbedPane.setEnabledAt(3, true);  // Mark Attendance
            tabbedPane.setEnabledAt(4, true);  // View Attendance
            tabbedPane.setEnabledAt(5, true);  // Late Students
            tabbedPane.setEnabledAt(6, true);  // Statistics
        } else if (user.isStudent()) {
            // Student sees only: View Attendance (filtered to their records)
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                tabbedPane.setEnabledAt(i, false);
            }
            tabbedPane.setEnabledAt(4, true); // View Attendance
        }
    }
} 