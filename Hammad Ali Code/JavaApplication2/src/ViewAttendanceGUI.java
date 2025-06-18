import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

// Model class representing a single attendance record
class AttendanceRecord {
    private String studentId;
    private String studentName;
    private String date;
    private String status;  // Present/Absent/Leave etc.

    public AttendanceRecord(String studentId, String studentName, String date, String status) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.date = date;
        this.status = status;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

// Controller class: handles retrieval of attendance records
class AttendanceRecordController {
    // In real app, data would come from DB or service
    private List<AttendanceRecord> records;

    public AttendanceRecordController() {
        // Dummy sample data
        records = new ArrayList<>();
        records.add(new AttendanceRecord("S001", "M.Ali", "2025-06-17", "Present"));
        records.add(new AttendanceRecord("S002", "Hammad K", "2025-06-17", "Absent"));
        records.add(new AttendanceRecord("S003", "Ahmed Ali", "2025-06-17", "Leave"));
        records.add(new AttendanceRecord("S004", "Hamid Malik", "2025-06-17", "Present"));
    }

    public List<AttendanceRecord> getAttendanceRecords() {
        return records;
    }

    public void updateRecord(int index, AttendanceRecord updatedRecord) {
        if (index >= 0 && index < records.size()) {
            records.set(index, updatedRecord);
        }
    }
}

// GUI class - boundary object displaying the attendance records
public class ViewAttendanceGUI extends JFrame {
    private JTable attendanceTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton editButton;
    private AttendanceRecordController controller;

    public ViewAttendanceGUI(AttendanceRecordController controller) {
        this.controller = controller;
        initUI();
        loadAttendanceRecords();
    }

    private void initUI() {
        setTitle("View Attendance Records");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Table columns
        String[] columns = {"Student ID", "Student Name", "Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            // Make table cells non-editable
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        attendanceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(attendanceTable);

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadAttendanceRecords());

        editButton = new JButton("Edit Selected");
        editButton.addActionListener(e -> editSelectedRecord());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshButton);
        bottomPanel.add(editButton);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadAttendanceRecords() {
        List<AttendanceRecord> records = controller.getAttendanceRecords();

        // Clear existing data
        tableModel.setRowCount(0);

        // Add rows
        for (AttendanceRecord record : records) {
            Object[] row = {
                record.getStudentId(),
                record.getStudentName(),
                record.getDate(),
                record.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void editSelectedRecord() {
        int selectedRow = attendanceTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get current values from the table model
        String studentId = (String) tableModel.getValueAt(selectedRow, 0);
        String studentName = (String) tableModel.getValueAt(selectedRow, 1);
        String date = (String) tableModel.getValueAt(selectedRow, 2);
        String status = (String) tableModel.getValueAt(selectedRow, 3);

        // Create input fields for editing
        JTextField studentIdField = new JTextField(studentId);
        JTextField studentNameField = new JTextField(studentName);
        JTextField dateField = new JTextField(date);
        String[] statuses = {"Present", "Absent", "Leave"};
        JComboBox<String> statusComboBox = new JComboBox<>(statuses);
        statusComboBox.setSelectedItem(status);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Student ID:"));
        panel.add(studentIdField);
        panel.add(new JLabel("Student Name:"));
        panel.add(studentNameField);
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Attendance Record", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Update the record in controller and table
            AttendanceRecord updatedRecord = new AttendanceRecord(
                studentIdField.getText().trim(),
                studentNameField.getText().trim(),
                dateField.getText().trim(),
                (String) statusComboBox.getSelectedItem()
            );

            controller.updateRecord(selectedRow, updatedRecord);
            loadAttendanceRecords();  // reload table with updated data
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AttendanceRecordController controller = new AttendanceRecordController();
            ViewAttendanceGUI gui = new ViewAttendanceGUI(controller);
            gui.setVisible(true);
        });
    }
}
