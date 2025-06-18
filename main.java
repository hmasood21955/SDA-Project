package com.example.reminder;

import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Coordinates the “Send automatic reminders” use‑case. */
public final class ReminderController {

    private final ReminderService reminderService;

    /** Inject the service (better for testing and reuse). */
    public ReminderController(ReminderService reminderService) {
        this.reminderService = Objects.requireNonNull(reminderService);
    }

    /** System‑level entry point. */
    public void sendReminders() {
        reminderService.sendAutomaticReminders();
    }
}
java
Copy code
package com.example.reminder;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Domain service that does the real work. */
public final class ReminderService {

    private static final Logger log = LoggerFactory.getLogger(ReminderService.class);

    private final List<String> students;

    /** Accept students from the caller (no hidden I/O). */
    public ReminderService(List<String> students) {
        this.students = List.copyOf(students);   // defensive copy
    }

    /** Sends a reminder to every student, then notifies teacher and admin. */
    public void sendAutomaticReminders() {
        students.forEach(this::sendReminder);
        notifyTeacher();
        alertAdmin();
    }

    // ---------------------------------------------------------------------

    private void sendReminder(String student) {
        log.info("Reminder sent to {}", student);
    }

    private void notifyTeacher() {
        log.info("Teacher notified of reminders.");
    }

    private void alertAdmin() {
        log.info("Admin alerted of reminder status.");
    }
}
java
Copy code
package com.example.reminder;

import java.util.List;

/** Quick console driver (delete or replace with tests). */
public final class Main {

    public static void main(String[] args) {
        List<String> students = List.of("Student A", "Student B");

        ReminderService service   = new ReminderService(students);
        ReminderController ctlr   = new ReminderController(service);

        ctlr.sendReminders();
    }
}