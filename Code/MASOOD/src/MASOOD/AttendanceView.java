package MASOOD;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AttendanceView {
    private AttendanceController controller;
    private JFrame frame;
    private JTextField studentIdField;
    private JTextField courseIdField;
    private JTextField timeCheckField;
    private JTextArea statusArea;

    public AttendanceView() {
        initializeUI();
    }

    public void setController(AttendanceController controller) {
        this.controller = controller;
    }

    private void initializeUI() {
        frame = new JFrame("Attendance System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        
        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Input panel
        JPanel inputPanel = createInputPanel();
        // Buttons panel
        JPanel buttonPanel = createButtonPanel();
        // Status area
        statusArea = createStatusArea();
        
        // Add components to main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(statusArea), BorderLayout.SOUTH);
        
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null); // Center the window
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Record Attendance"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Student ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        studentIdField = new JTextField(20);
        inputPanel.add(studentIdField, gbc);
        
        // Course ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Course ID:"), gbc);
        gbc.gridx = 1;
        courseIdField = new JTextField(20);
        inputPanel.add(courseIdField, gbc);

        // Time Check
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Check Time (HH:mm):"), gbc);
        gbc.gridx = 1;
        timeCheckField = new JTextField(20);
        timeCheckField.setText("09:00");
        inputPanel.add(timeCheckField, gbc);
        
        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton recordButton = new JButton("Record Attendance");
        JButton viewHistoryButton = new JButton("View History");
        JButton checkTimeButton = new JButton("Check Time Status");
        
        recordButton.addActionListener(e -> controller.recordAttendance(
            studentIdField.getText().trim(),
            courseIdField.getText().trim()
        ));
        
        viewHistoryButton.addActionListener(e -> controller.showAttendanceHistory());
        
        checkTimeButton.addActionListener(e -> {
            try {
                String timeStr = timeCheckField.getText().trim();
                String[] parts = timeStr.split(":");
                if (parts.length != 2) {
                    showError("Please enter time in HH:mm format");
                    return;
                }
                
                int hour = Integer.parseInt(parts[0]);
                int minute = Integer.parseInt(parts[1]);
                
                if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                    showError("Invalid time format. Use HH:mm (24-hour format)");
                    return;
                }
                
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime timeToCheck = now.withHour(hour).withMinute(minute).withSecond(0);
                controller.checkTimeStatus(timeToCheck);
            } catch (NumberFormatException ex) {
                showError("Please enter valid numbers for hours and minutes");
            }
        });
        
        buttonPanel.add(recordButton);
        buttonPanel.add(viewHistoryButton);
        buttonPanel.add(checkTimeButton);
        return buttonPanel;
    }

    private JTextArea createStatusArea() {
        JTextArea area = new JTextArea(5, 40);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    public void show() {
        frame.setVisible(true);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void updateStatus(String message) {
        statusArea.setText(message);
    }

    public void clearInputFields() {
        studentIdField.setText("");
        courseIdField.setText("");
    }

    public void showHistoryDialog(List<AttendanceRecord> records) {
        JDialog historyDialog = new JDialog(frame, "Attendance History", true);
        historyDialog.setSize(600, 400);
        historyDialog.setLocationRelativeTo(frame);

        String[] columnNames = {"ID", "Student ID", "Course ID", "Timestamp", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        for (AttendanceRecord record : records) {
            Object[] row = {
                record.getId(),
                record.getStudentId(),
                record.getCourseId(),
                record.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                record.isLate() ? "Late" : "On Time"
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        historyDialog.add(new JScrollPane(table));
        historyDialog.setVisible(true);
    }
} 