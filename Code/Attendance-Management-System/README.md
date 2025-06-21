# Attendance Management System

A comprehensive Java Swing-based attendance management system with role-based access control and multi-user support.

## Features

### 🔐 **Multi-User Authentication**
- **Admin Module**: Full system access
- **Teacher Module**: Attendance management and reporting
- **Student Module**: View personal attendance records
- **Automatic User Creation**: Student accounts created automatically when students are added

### 👥 **User Management**
- **Default Users**:
  - Admin: `admin` / `admin123`
  - Teacher: `teacher` / `teacher123`
  - Students: `s001`, `s002`, etc. / `student123`

### 📊 **Core Features**
- **Student Management**: Add, edit, delete, search students
- **Course Management**: Add, edit, delete, search courses
- **Attendance Tracking**: Mark attendance with late detection
- **Assignment System**: Link students to courses
- **Reporting**: View attendance, late students, statistics
- **Data Export**: CSV export for all data types
- **Custom Rules**: Per-course late thresholds and excused absences

### 🎨 **User Interface**
- **Tabbed Interface**: 7 organized tabs
- **Role-Based Access**: Different features based on user role
- **Theme Toggle**: Dark/light mode switching
- **Responsive Design**: Clean, modern interface

### 🔄 **Module Switching**
- **Logout/Login**: Switch between different user accounts
- **Session Management**: Proper user session handling
- **Access Control**: Role-appropriate interface elements

## How to Use

### 1. **Compilation**
```bash
javac -cp "src" src/ConsolidatedAttendanceSystem.java src/model/*.java src/view/*.java src/controller/*.java
```

### 2. **Running the Application**
```bash
java -cp "src" ConsolidatedAttendanceSystem
```

### 3. **Login with Different Users**

#### **Admin Access** (Full System)
- Username: `admin`
- Password: `admin123`
- **Features**: All tabs enabled, full system management

#### **Teacher Access** (Attendance Management)
- Username: `teacher`
- Password: `teacher123`
- **Features**: Assign Students, Mark Attendance, View Attendance, Late Students, Statistics

#### **Student Access** (Personal Records)
- Username: `s001`, `s002`, etc. (student ID in lowercase)
- Password: `student123`
- **Features**: View personal attendance records only

### 4. **Adding New Students**
1. Login as Admin
2. Go to "Students" tab
3. Enter student details and click "Add Student"
4. Student account is automatically created with username = student ID (lowercase)

### 5. **Switching Between Modules**
1. Click "Logout" button
2. Login dialog appears
3. Enter credentials for different user
4. Interface updates based on user role

### 6. **Viewing Available Users**
- Click "Show Available Users" button to see all user credentials

## File Structure

```
src/
├── ConsolidatedAttendanceSystem.java  # Main entry point
├── model/                            # Data models
│   ├── AttendanceModel.java         # Main business logic
│   ├── Student.java                 # Student entity
│   ├── Course.java                  # Course entity
│   ├── AttendanceRecord.java        # Attendance records
│   └── User.java                    # User authentication
├── view/                            # User interface
│   └── AttendanceView.java          # Main UI components
└── controller/                      # Event handling
    └── AttendanceController.java    # Business logic controller
```

## Data Files

- `students.txt`: Student information
- `courses.txt`: Course information
- `assignments.txt`: Student-course assignments
- `users.txt`: User authentication data

## Architecture

The system follows the **MVC (Model-View-Controller)** pattern:
- **Model**: Data management and business logic
- **View**: User interface components
- **Controller**: Event handling and user interactions

## Security Features

- **Role-Based Access Control**: Different permissions for different user types
- **Input Validation**: Proper data validation and error handling
- **Session Management**: Secure user session handling
- **File Persistence**: Data saved to secure text files

## Sample Data

The system comes with sample data:
- 5 students (S001-S005)
- 5 courses (C001-C005)
- Pre-configured assignments
- Default user accounts

## Next Steps

For advanced features, consider adding:
- Data visualization (charts and graphs)
- Bulk import/export functionality
- Email notifications
- Advanced reporting
- Database integration
