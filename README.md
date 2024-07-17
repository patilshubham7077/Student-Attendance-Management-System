#       💻 Student-Attendance-Management-System 💻


# Overview
This Java Attendance Tracker is a comprehensive multi-page web application designed to streamline the attendance management process for small classes or tuition settings. Utilizing Java GUI and OOP concepts, the application integrates seamlessly with a database using DBMS principles to ensure efficient data management. The application features distinct portals for administrators and students, enabling easy attendance tracking, data manipulation, and reporting.

# Features
   
1. Admin and Student Login:
   * The application provides secure login functionalities for both administrators and students. Administrators can access the admin portal using unique credentials, while students can log in to view their attendance records and percentages throughout the year.

2. Insert Student Data:
   * Administrators can add new student records to the database, including details such as name, roll number, class, and other relevant information. The system ensures all required fields are filled out correctly before submission, providing data validation.

3. Add Lecture for Marking Attendance:
   * Admins can schedule new lectures by specifying the date, time, and subject. During or after a lecture, they can mark student attendance, selecting present or absent for each student, which simplifies the attendance tracking process.

6. Update Student Data and Show Data:
   * The application allows admins to update existing student information, making it easy to correct details or change student status. Both admins and students can view detailed student data, including personal information and attendance history, ensuring transparency and accuracy.

8. Print Attendance Sheet:
     * Administrators can generate and print comprehensive attendance sheets for classes or individual students. The attendance sheets can be printed directly or exported to various formats for record-keeping and reporting purposes, providing flexibility in managing attendance records.

10. Logout:
    * The application ensures users can safely log out of their accounts, terminating their session and protecting sensitive information. It implements proper session handling to prevent unauthorized access after logout, enhancing security.

# Technical Details
The application is developed using Java, with Java Swing for the GUI framework. It connects to a MySQL database (or any other DBMS) using JDBC for database connectivity. The project leverages various OOP concepts such as classes, objects, inheritance, polymorphism, and encapsulation to build a robust and maintainable codebase.
