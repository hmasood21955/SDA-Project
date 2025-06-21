import model.AttendanceModel;
import view.AttendanceView;
import controller.AttendanceController;

public class ConsolidatedAttendanceSystem {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            if (!AttendanceView.showLoginDialog()) {
                System.exit(0);
            }
            AttendanceModel model = new AttendanceModel();
            AttendanceView view = new AttendanceView(model);
            new AttendanceController(model, view);
            view.setVisible(true);
        });
    }
}