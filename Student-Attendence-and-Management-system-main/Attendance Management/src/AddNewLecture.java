import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class AddNewLecture extends JFrame implements ActionListener {


    JButton Submit, Student_Records;
    JPanel leftpanel = new JPanel();
    JPanel rightpanel = new JPanel();
    JLabel leftlabel = new JLabel();
    JDateChooser dateChooser = new JDateChooser();
    JLabel title = new JLabel("Add lecture!");
    JLabel date=new JLabel("Enter date:");
    JLabel time = new JLabel("Enter time:");
    String[] lectureTime = new String[]{"08:00 am to 10:00 am", "01:00 pm to 03:00 pm", "06:00 pm to 08:00 pm"};
    JComboBox lectime = new JComboBox(lectureTime);
    AddNewLecture()
    {
        ImageIcon image = new ImageIcon(new ImageIcon("login.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));



        title.setBounds(100,40,350,75);
        title.setFont(new Font("Poppins", Font.BOLD, 35));
        title.setForeground(new Color(128, 98, 214));
        date.setBounds(100,150,200,20);
        date.setFont(new Font("Poppins", Font.BOLD, 20));
        dateChooser.setBounds(100,200,200,30);
        dateChooser.setDateFormatString("dd - MM - yyyy");
        time.setBounds(100,250,200,20);
        time.setFont(new Font("Poppins", Font.BOLD, 20));
        lectime.setBounds(100, 300, 200, 30);



        Submit = new JButton("Submit");
        Submit.setBounds(150, 400, 100, 50);
        Submit.setFocusable(false);
        Submit.setBackground(new Color(128, 98, 214));
        Submit.setForeground(Color.WHITE);
        Submit.setFont(new Font("Poppins", Font.BOLD, 15));
        Submit.setBorder(BorderFactory.createEtchedBorder());
        Submit.addActionListener(this);
        Student_Records = new JButton("Student records");
        Student_Records.setBounds(100, 450, 200, 50);
        Student_Records.setFocusable(false);
        Student_Records.setBackground(new Color(255, 210, 215));
        Student_Records.setForeground(new Color(128, 98, 214));
        Student_Records.setFont(new Font("Poppins", Font.BOLD, 15));
        Student_Records.setBorder(BorderFactory.createEtchedBorder());
        Student_Records.addActionListener(this);
        leftlabel.setIcon(image);
        leftlabel.setForeground(Color.WHITE);
        leftlabel.setFont(new Font("Poppins",Font.BOLD,35));
        leftlabel.setBounds(30,20,400,400);
        leftlabel.setVerticalTextPosition(JLabel.TOP);
        leftlabel.setHorizontalTextPosition(JLabel.CENTER);
        leftpanel.setBounds(0,0,400,600);
        leftpanel.setBackground(new Color(128, 98, 214));
        leftpanel.setLayout(null);
        leftpanel.add(leftlabel);
        leftpanel.add(Student_Records);
        rightpanel.setBounds(400,0,400,600);
        rightpanel.setBackground(Color.WHITE);
        rightpanel.setLayout(null);
        rightpanel.add(title);
        rightpanel.add(date);
        rightpanel.add(dateChooser);
        rightpanel.add(time);
        rightpanel.add(lectime);
        rightpanel.add(Submit);



        //frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,550);
        this.setLayout(null);
        this.setResizable(false);
        this.setUndecorated(true);
        this.getContentPane().setBackground(Color.WHITE);
        this.add(leftpanel);
        this.add(rightpanel);
        this.setTitle("Add new Lecture");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    private JRadioButton createRadioButton(String value) {
        JRadioButton radioButton = new JRadioButton();
        radioButton.setText(value);
        radioButton.setSelected(value.equals("Present") || value.equals("Absent"));
        return radioButton;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Submit) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd - MM - yyyy");
                String selectedDate = sdf.format(dateChooser.getDate());
                String selectedOption = lectime.getSelectedItem().toString();

                // Establish a connection to your MySQL database
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_attendance", "root", "password");

                // Fetch student data from the Student_records table
                String fetchQuery = "SELECT student_id, student_name FROM Student_records";
                PreparedStatement fetchStatement = connection.prepareStatement(fetchQuery);
                ResultSet resultSet = fetchStatement.executeQuery();

                // Insert fetched data into the Lecture_records table
                String insertQuery = "INSERT INTO Lecture_records (Date, Time, StudentId, Name, Attendance_status) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    while (resultSet.next()) {
                        String studentId = resultSet.getString("student_id");
                        String studentName = resultSet.getString("student_name");

                        // Set the values in the prepared statement
                        insertStatement.setString(1, selectedDate);
                        insertStatement.setString(2, selectedOption);
                        insertStatement.setString(3, studentId);
                        insertStatement.setString(4, studentName);
                        insertStatement.setString(5, "1"); // Assuming default value for present_column is false

                        // Execute the SQL statement
                        insertStatement.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(null, "Records added successfully!");
                }
                AdminPage.lectureConductedIncrement();
                this.dispose();
                ShowLecture showLecture = new ShowLecture();
                showLecture.enableTableEditing();
                // Close the database connection
                resultSet.close();
                fetchStatement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource()==Student_Records) {
            this.dispose();
            AdminPage adminPage = new AdminPage();
        }
    }

}
