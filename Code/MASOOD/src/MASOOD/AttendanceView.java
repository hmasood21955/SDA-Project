package MASOOD;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AttendanceView {
    private AttendanceController controller;
    private JFrame frame;
    private JTextField studentIdField;
    private JTextField courseIdField;
    private JTextArea statusArea;

    public AttendanceView() {
        initializeUI();
    }

    public void setController(AttendanceController controller) {
        this.controller = controller;
    }

    private void initializeUI() {
        frame = new JFrame("Attendance System - Pakistan Time");
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
        
        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton recordButton = new JButton("Record Attendance");
        JButton viewHistoryButton = new JButton("View History");
        
        recordButton.addActionListener(e -> controller.recordAttendance(
            studentIdField.getText().trim(),
            courseIdField.getText().trim()
        ));
        
        viewHistoryButton.addActionListener(e -> controller.showAttendanceHistory());
        
        buttonPanel.add(recordButton);
        buttonPanel.add(viewHistoryButton);
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

    public int showPunctualityDialog() {
        return JOptionPane.showConfirmDialog(
            frame,
            "Is the student late?",
            "Attendance Status",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
    }

    public void showHistoryDialog(List<AttendanceRecord> records) {
        JDialog historyDialog = new JDialog(frame, "Attendance History - Pakistan Time", true);
        historyDialog.setSize(600, 400);
        historyDialog.setLocationRelativeTo(frame);

        String[] columnNames = {"ID", "Student ID", "Course ID", "Time (PKT)", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        for (AttendanceRecord record : records) {
            Object[] row = {
                record.getId(),
                record.getStudentId(),
                record.getCourseId(),
                record.getTimestamp().format(AttendanceService.TIMESTAMP_FORMAT),
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