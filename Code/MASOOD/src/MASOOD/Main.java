package MASOOD;

public class Main {
    public static void main(String[] args) {
        // Create and initialize components
        AttendanceService service = new AttendanceService();
        AttendanceView view = new AttendanceView();
        AttendanceController controller = new AttendanceController(view, service);
        
        // Set up the controller in the view
        view.setController(controller);
        
        // Show the application window
        view.show();
    }
} 