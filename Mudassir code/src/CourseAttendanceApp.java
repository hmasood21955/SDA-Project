
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

// Information Expert: Responsible for managing courses and attendance
class CourseManager {
    private Map<String, Integer> attendanceMap;

    public CourseManager() {
        attendanceMap = new HashMap<>();
        attendanceMap.put("Mathematics - Module 1", 0);
        attendanceMap.put("Physics - Module 1", 0);
        attendanceMap.put("Chemistry - Module 1", 0);
        attendanceMap.put("Computer Science - Module 1", 0);
    }

    public void attendCourse(String courseModule) {
        attendanceMap.put(courseModule, attendanceMap.getOrDefault(courseModule, 0) + 1);
    }

    public int getAttendance(String courseModule) {
        return attendanceMap.getOrDefault(courseModule, 0);
    }
}

// Frame to select the course module
class CourseSelectionFrame extends JFrame {
    private JComboBox<String> courseComboBox;
    private JButton nextButton;
    private CourseManager courseManager;

    public CourseSelectionFrame(CourseManager manager) {
        super("Select Course Module");
        this.courseManager = manager;

        setLayout(new FlowLayout());

        courseComboBox = new JComboBox<>(new String[] {
                "Mathematics - Module 1",
                "Physics - Module 1",
                "Chemistry - Module 1",
                "Computer Science - Module 1"
        });

        nextButton = new JButton("Next");

        nextButton.addActionListener(e -> {
            String selectedCourse = (String) courseComboBox.getSelectedItem();
            if (selectedCourse != null) {
                new MarkAttendanceFrame(courseManager, selectedCourse);
                this.dispose();
            }
        });

        add(new JLabel("Select Course Module:"));
        add(courseComboBox);
        add(nextButton);

        setSize(300, 120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

// Frame to mark attendance for a selected course
class MarkAttendanceFrame extends JFrame {
    private JButton attendButton;
    private JButton confirmButton;
    private JLabel attendanceLabel;
    private CourseManager courseManager;
    private String courseModule;

    public MarkAttendanceFrame(CourseManager manager, String courseModule) {
        super("Mark Attendance for " + courseModule);
        this.courseManager = manager;
        this.courseModule = courseModule;

        setLayout(new FlowLayout());

        attendanceLabel = new JLabel("Current attendance: " + courseManager.getAttendance(courseModule));
        attendButton = new JButton("Mark Attendance");
        confirmButton = new JButton("Confirm Attendance");

        attendButton.addActionListener(e -> {
            courseManager.attendCourse(courseModule);
            attendanceLabel.setText("Current attendance: " + courseManager.getAttendance(courseModule));
        });

        confirmButton.addActionListener(e -> {
            int attendanceCount = courseManager.getAttendance(courseModule);
            JOptionPane.showMessageDialog(this,
                "Total students present for " + courseModule + ": " + attendanceCount,
                "Attendance Confirmed",
                JOptionPane.INFORMATION_MESSAGE);
        });

        add(new JLabel("Course: " + courseModule));
        add(attendButton);
        add(confirmButton);
        add(attendanceLabel);

        setSize(350, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

public class CourseAttendanceApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CourseManager courseManager = new CourseManager();
            new CourseSelectionFrame(courseManager);
        });
    }
}
