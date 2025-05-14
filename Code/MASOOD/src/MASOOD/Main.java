package MASOOD;

public class Main {
    public static void main(String[] args) {
        AttendanceService service = new AttendanceService();
        AttendanceView view = new AttendanceView();
        AttendanceController controller = new AttendanceController(service, view);
        view.show();
    }
} 