import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// === MODEL LAYER ===
enum Role {
    ADMIN, TEACHER, STUDENT
}

class User {
    private String username;
    private String password;
    private Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }

    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }
}

// === SERVICE LAYER ===
class UserService {
    private List<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User("admin", "admin123", Role.ADMIN));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void deleteUser(String username) {
        users.removeIf(u -> u.getUsername().equals(username));
    }

    public void updateUser(String username, String newPassword, Role newRole) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                u.setPassword(newPassword);
                u.setRole(newRole);
                break;
            }
        }
    }

    public boolean userExists(String username) {
        return users.stream().anyMatch(u -> u.getUsername().equals(username));
    }

    public User getUser(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }
}

// === CONTROLLER LAYER ===
class AuthController {
    private UserService userService;

    public AuthController() {
        userService = new UserService();
    }

    public UserService getUserService() {
        return userService;
    }
}

// === VIEW LAYER ===
public class ManageUsersApp extends JFrame {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private UserService userService;

    public ManageUsersApp(AuthController controller) {
        this.userService = controller.getUserService();

        setTitle("Manage Users");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Table
        String[] columns = {"Username", "Role"};
        tableModel = new DefaultTableModel(columns, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);
        refreshTable();

        // Form
        JPanel formPanel = new JPanel(new FlowLayout());
        JTextField usernameField = new JTextField(10);
        JTextField passwordField = new JTextField(10);
        JComboBox<Role> roleBox = new JComboBox<>(Role.values());

        JButton addButton = new JButton("Add User");
        JButton deleteButton = new JButton("Delete Selected");
        JButton updateButton = new JButton("Update Selected");

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Role:"));
        formPanel.add(roleBox);
        formPanel.add(addButton);
        formPanel.add(deleteButton);
        formPanel.add(updateButton);
        add(formPanel, BorderLayout.SOUTH);

        // Button: Add
        addButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            Role role = (Role) roleBox.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            if (userService.userExists(username)) {
                JOptionPane.showMessageDialog(this, "User already exists.");
                return;
            }

            userService.addUser(new User(username, password, role));
            refreshTable();
            clearFields(usernameField, passwordField);
        });

        // Button: Delete
        deleteButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                String username = (String) tableModel.getValueAt(selectedRow, 0);
                userService.deleteUser(username);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Select a user to delete.");
            }
        });

        // Button: Update
        updateButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                String username = (String) tableModel.getValueAt(selectedRow, 0);
                String newPassword = passwordField.getText().trim();
                Role newRole = (Role) roleBox.getSelectedItem();

                if (newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter new password for update.");
                    return;
                }

                userService.updateUser(username, newPassword, newRole);
                refreshTable();
                clearFields(usernameField, passwordField);
            } else {
                JOptionPane.showMessageDialog(this, "Select a user to update.");
            }
        });

        // Load selected user details into fields
        userTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                String username = (String) tableModel.getValueAt(selectedRow, 0);
                User selectedUser = userService.getUser(username);
                if (selectedUser != null) {
                    usernameField.setText(selectedUser.getUsername());
                    passwordField.setText(selectedUser.getPassword());
                    roleBox.setSelectedItem(selectedUser.getRole());
                    usernameField.setEnabled(false); // prevent changing username
                }
            }
        });

        setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (User u : userService.getAllUsers()) {
            tableModel.addRow(new Object[]{u.getUsername(), u.getRole()});
        }
    }

    private void clearFields(JTextField usernameField, JTextField passwordField) {
        usernameField.setText("");
        passwordField.setText("");
        usernameField.setEnabled(true); // re-enable for next add
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuthController controller = new AuthController();
            new ManageUsersApp(controller);
        });
    }
}
