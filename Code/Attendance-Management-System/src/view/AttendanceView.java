package view;

import model.AttendanceModel;
import model.User;
import javax.swing.*;
import java.awt.*;

public class AttendanceView extends JFrame {
    public final JTabbedPane tabbedPane;
    // Students Tab
    public JTextField studentIdField;
    public JTextField studentNameField;
    public JTextField studentEmailField;
    public JButton addStudentButton;
    public JTable studentTable;
    public JScrollPane studentTableScrollPane;
    public final JComboBox<String> deleteStudentComboBox;
    public final JButton deleteStudentButton;
    public final JButton editStudentButton;
    public final JTextField editStudentNameField;
    public final JTextField editStudentEmailField;
    public final JButton exportStudentsButton;
    public final JTextField searchStudentField;
    public final JButton searchStudentButton;
    public JTextArea studentListArea;
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
    public final JComboBox<String> deleteAssignCourseComboBox;
    public final JComboBox<String> deleteAssignStudentComboBox;
    public final JButton deleteAssignmentButton;
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
        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
        // Add Student Section
        JPanel addStudentSection = new JPanel();
        addStudentSection.setLayout(new BoxLayout(addStudentSection, BoxLayout.Y_AXIS));
        addStudentSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(33, 150, 243), 2),
            "Add New Student", 0, 0, new Font("Segoe UI", Font.BOLD, 18)));
        addStudentSection.setBackground(new Color(232, 245, 253));
        addStudentSection.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Field Panel
        JPanel studentInput = new JPanel();
        studentInput.setLayout(new GridBagLayout());
        studentInput.setBackground(new Color(232, 245, 253));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel idLabel = new JLabel("Student ID:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        studentInput.add(idLabel, gbc);
        gbc.gridx = 1;
        studentIdField = new JTextField(12);
        studentIdField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        studentIdField.setToolTipText("Enter unique student ID (e.g., S123)");
        studentInput.add(studentIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        studentInput.add(nameLabel, gbc);
        gbc.gridx = 1;
        studentNameField = new JTextField(12);
        studentNameField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        studentNameField.setToolTipText("Enter full name of the student");
        studentInput.add(studentNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        studentInput.add(emailLabel, gbc);
        gbc.gridx = 1;
        studentEmailField = new JTextField(12);
        studentEmailField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        studentEmailField.setToolTipText("Enter student email address");
        studentInput.add(studentEmailField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        addStudentButton = new JButton("Add Student");
        addStudentButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        addStudentButton.setBackground(new Color(33, 150, 243));
        addStudentButton.setForeground(Color.WHITE);
        addStudentButton.setFocusPainted(false);
        addStudentButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        addStudentButton.setToolTipText("Click to add the student");
        studentInput.add(addStudentButton, gbc);
        addStudentSection.add(studentInput);
        studentPanel.add(addStudentSection);
        // Student Table Section
        JLabel studentListLabel = new JLabel("Current Students");
        studentListLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        studentListLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        String[] studentColumns = {"ID", "Name", "Email"};
        Object[][] studentData = {};
        studentTable = new JTable(studentData, studentColumns);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentTable.setRowHeight(22);
        studentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        studentTableScrollPane = new JScrollPane(studentTable);
        JPanel studentTablePanel = new JPanel(new BorderLayout());
        studentTablePanel.add(studentListLabel, BorderLayout.NORTH);
        studentTablePanel.add(studentTableScrollPane, BorderLayout.CENTER);
        studentPanel.add(studentTablePanel);
        this.studentListArea = new JTextArea(8, 40);
        this.studentListArea.setEditable(false);
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
        studentPanel.add(studentEditPanel);
        studentPanel.add(studentDeletePanel);
        exportStudentsButton = new JButton("Export Students to CSV");
        studentPanel.add(exportStudentsButton);
        JPanel studentSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchStudentField = new JTextField(12);
        searchStudentButton = new JButton("Search Student");
        studentSearchPanel.add(new JLabel("Search:"));
        studentSearchPanel.add(searchStudentField);
        studentSearchPanel.add(searchStudentButton);
        studentPanel.add(studentSearchPanel);
        tabbedPane.addTab("Students", studentPanel);
        // Courses Tab
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));
        // Add Course Section
        JPanel addCourseSection = new JPanel();
        addCourseSection.setLayout(new BoxLayout(addCourseSection, BoxLayout.Y_AXIS));
        addCourseSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
            "Add New Course", 0, 0, new Font("Segoe UI", Font.BOLD, 18)));
        addCourseSection.setBackground(new Color(232, 253, 245));
        addCourseSection.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel courseInput = new JPanel();
        courseInput.setLayout(new GridBagLayout());
        courseInput.setBackground(new Color(232, 253, 245));
        GridBagConstraints gbcCourse = new GridBagConstraints();
        gbcCourse.insets = new Insets(10, 10, 10, 10);
        gbcCourse.anchor = GridBagConstraints.WEST;
        gbcCourse.gridx = 0; gbcCourse.gridy = 0;
        JLabel courseIdLabel = new JLabel("Course ID:");
        courseIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        courseInput.add(courseIdLabel, gbcCourse);
        gbcCourse.gridx = 1;
        courseIdField = new JTextField(12);
        courseIdField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        courseIdField.setToolTipText("Enter unique course ID (e.g., C101)");
        courseInput.add(courseIdField, gbcCourse);
        gbcCourse.gridx = 0; gbcCourse.gridy = 1;
        JLabel courseNameLabel = new JLabel("Name:");
        courseNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        courseInput.add(courseNameLabel, gbcCourse);
        gbcCourse.gridx = 1;
        courseNameField = new JTextField(12);
        courseNameField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        courseNameField.setToolTipText("Enter course name");
        courseInput.add(courseNameField, gbcCourse);
        gbcCourse.gridx = 0; gbcCourse.gridy = 2;
        JLabel courseCodeLabel = new JLabel("Code:");
        courseCodeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        courseInput.add(courseCodeLabel, gbcCourse);
        gbcCourse.gridx = 1;
        courseCodeField = new JTextField(12);
        courseCodeField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        courseCodeField.setToolTipText("Enter course code (e.g., MATH101)");
        courseInput.add(courseCodeField, gbcCourse);
        gbcCourse.gridx = 0; gbcCourse.gridy = 3; gbcCourse.gridwidth = 2;
        addCourseButton = new JButton("Add Course");
        addCourseButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        addCourseButton.setBackground(new Color(76, 175, 80));
        addCourseButton.setForeground(Color.WHITE);
        addCourseButton.setFocusPainted(false);
        addCourseButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        addCourseButton.setToolTipText("Click to add the course");
        courseInput.add(addCourseButton, gbcCourse);
        addCourseSection.add(courseInput);
        coursePanel.add(addCourseSection);
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
        coursePanel.add(new JScrollPane(courseListArea));
        coursePanel.add(courseEditPanel);
        coursePanel.add(courseDeletePanel);
        exportCoursesButton = new JButton("Export Courses to CSV");
        coursePanel.add(exportCoursesButton);
        JPanel courseSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchCourseField = new JTextField(12);
        searchCourseButton = new JButton("Search Course");
        courseSearchPanel.add(new JLabel("Search:"));
        courseSearchPanel.add(searchCourseField);
        courseSearchPanel.add(searchCourseButton);
        coursePanel.add(courseSearchPanel);
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
        // New: Delete assignment controls
        JPanel deleteAssignPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deleteAssignCourseComboBox = new JComboBox<>();
        deleteAssignStudentComboBox = new JComboBox<>();
        deleteAssignmentButton = new JButton("Delete Assignment");
        deleteAssignPanel.add(new JLabel("Course:"));
        deleteAssignPanel.add(deleteAssignCourseComboBox);
        deleteAssignPanel.add(new JLabel("Student:"));
        deleteAssignPanel.add(deleteAssignStudentComboBox);
        deleteAssignPanel.add(deleteAssignmentButton);
        assignPanel.add(assignInput, BorderLayout.NORTH);
        assignPanel.add(new JScrollPane(assignmentListArea), BorderLayout.CENTER);
        assignPanel.add(deleteAssignPanel, BorderLayout.SOUTH);
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