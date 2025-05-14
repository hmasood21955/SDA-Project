# MASOOD Attendance System

A Java-based attendance management system that uses the Model-View-Controller (MVC) architecture pattern. The system automatically records attendance with Pakistan Standard Time (PKT) and allows manual status determination.

## Architecture

The system follows the MVC (Model-View-Controller) architecture pattern:

### 1. Model Layer
- **AttendanceRecord.java**: Data model class
  - Stores attendance record details (ID, student ID, course ID, timestamp, late status)
  - Immutable data structure

- **AttendanceService.java**: Business Logic Layer
  - Handles time management (Pakistan Standard Time)
  - Manages attendance record storage
  - Provides data operations
  - Implements time zone conversion

### 2. View Layer
- **AttendanceView.java**: User Interface
  - Input fields for Student ID and Course ID
  - Buttons for recording attendance and viewing history
  - Status display area
  - History dialog with table view
  - User prompts for late status

### 3. Controller Layer
- **AttendanceController.java**: Coordination Layer
  - Processes user input
  - Updates the view
  - Calls service methods
  - Manages business logic flow

### 4. Main Application
- **Main.java**: Application Entry Point
  - Initializes MVC components
  - Sets up the application

## Features

1. **Automatic Time Management**
   - Uses Pakistan Standard Time (PKT)
   - Automatically converts and stores times in PKT
   - Displays all times in PKT format

2. **Attendance Recording**
   - Record student attendance with ID and course
   - Manual late status determination
   - Automatic timestamp recording

3. **History Viewing**
   - View complete attendance history
   - Tabular display of records
   - Sortable columns

## How to Use

1. **Recording Attendance**
   - Enter Student ID
   - Enter Course ID
   - Click "Record Attendance"
   - Choose if student is late (Yes/No)
   - System records attendance with current PKT

2. **Viewing History**
   - Click "View History" button
   - View all attendance records in table format
   - Records show:
     - Student ID
     - Course ID
     - Time (PKT)
     - Status (Late/On Time)

## Technical Details

### Time Management
- Uses Java's `LocalDateTime` for time handling
- Implements time zone conversion to PKT
- Stores all times in Pakistan Standard Time

### Data Storage
- In-memory storage using ArrayList
- No database dependency
- Records persist during application runtime

### UI Components
- Built using Java Swing
- Responsive design
- User-friendly interface

## Requirements

- Java Runtime Environment (JRE) 8 or higher
- No additional dependencies required

## Running the Application

1. Compile the Java files:
```bash
javac MASOOD/*.java
```

2. Run the application:
```bash
java MASOOD.Main
```

## Architecture Benefits

1. **Maintainability**
   - Clear separation of concerns
   - Easy to modify individual components
   - Well-organized code structure

2. **Extensibility**
   - Easy to add new features
   - Simple to modify existing functionality
   - Clear component boundaries

3. **Testability**
   - Components can be tested independently
   - Clear interfaces between layers
   - Easy to mock dependencies

4. **Scalability**
   - Can be extended with database integration
   - Easy to add new features
   - Modular design

## Future Enhancements

1. Database integration for persistent storage
2. User authentication and authorization
3. Report generation
4. Multiple time zone support
5. Export functionality

## Contributing

Feel free to contribute to this project by:
1. Forking the repository
2. Creating a feature branch
3. Submitting a pull request

## License

This project is open source and available under the MIT License. 