import model.AttendanceModel;
import model.User;
import view.AttendanceView;
import controller.AttendanceController;

public class ConsolidatedAttendanceSystem {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            AttendanceModel model = new AttendanceModel();
            User loggedInUser = AttendanceView.showLoginDialog(model);
            if (loggedInUser == null) {
                System.exit(0);
            }
            AttendanceView view = new AttendanceView(model);
            view.updateUIForUser(loggedInUser);
            new AttendanceController(model, view);
            view.setVisible(true);
        });
    }
}