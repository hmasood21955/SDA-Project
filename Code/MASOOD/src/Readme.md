

### 📘 README.md

# MASOOD Attendance Management System

This is a Java-based Attendance Management System. The project is built using standard Java classes and uses an SQLite database via the `sqlite-jdbc` library.

---

## 📁 Project Structure

```
MASOOD/
│
├── sqlite-jdbc-3.45.1.0.jar         # JDBC driver for SQLite
├── src/
│   └── MASOOD/
│       ├── Main.java
│       ├── AttendanceController.java
│       ├── AttendanceService.java
│       ├── AttendanceView.java
│       └── AttendanceRecord.java
```

---

## ✅ Requirements

* Java Development Kit (JDK) 8 or later
* Command-line terminal or IDE
* Internet (for downloading SQLite JAR if not included)

---

## 🛠️ How to Compile

1. **Navigate to the project root directory:**

```bash
cd MASOOD
```

2. **Compile the code using `javac`, including the SQLite JDBC driver in the classpath:**

```bash
javac -cp ".;sqlite-jdbc-3.45.1.0.jar" src/MASOOD/*.java
```

> **Note:** On macOS/Linux, replace the semicolon (`;`) with a colon (`:`):
>
> ```bash
> javac -cp ".:sqlite-jdbc-3.45.1.0.jar" src/MASOOD/*.java
> ```

---

## ▶️ How to Run

Run the main class from the project root like this:

```bash
java -cp ".;sqlite-jdbc-3.45.1.0.jar;src" MASOOD.Main
```

Again, on macOS/Linux:

```bash
java -cp ".:sqlite-jdbc-3.45.1.0.jar:src" MASOOD.Main
```

---

## 📦 External Libraries

This project uses:

* [`sqlite-jdbc`](https://github.com/xerial/sqlite-jdbc): a JDBC driver for SQLite

Ensure `sqlite-jdbc-3.45.1.0.jar` is present in the root folder.

---

