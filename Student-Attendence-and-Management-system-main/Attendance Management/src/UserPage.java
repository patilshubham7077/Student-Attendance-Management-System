import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserPage extends JFrame implements ActionListener
{
    JPanel leftpanel = new JPanel();
    JPanel rightpanel = new JPanel();
    JLabel leftlabel = new JLabel();
    JLabel title;
    JLabel studentName = GUI.JLabel("Name:");
    JLabel studentId = GUI.JLabel("ID:");
    JLabel studentYear = GUI.JLabel("Year:");
    JLabel Attended = GUI.JLabel("Attended:");
    JLabel conducted = GUI.JLabel("Conducted:");
    JLabel percentage = GUI.JLabel("Percentage:");
    JTextField studentNametb = GUI.JTextField();
    JTextField studentIdtb = GUI.JTextField();
    JTextField yeartb = GUI.JTextField();

    JTextField Attendedtb = GUI.JTextField();
    JTextField conductedtb = GUI.JTextField();
    JTextField percentagetb = GUI.JTextField();
    JButton Logout;

    UserPage(String user) {


        ImageIcon image = new ImageIcon(new ImageIcon("Attendance.png").getImage().getScaledInstance(350, 330, Image.SCALE_DEFAULT));
        ImageIcon icon = new ImageIcon(new ImageIcon("icon.png").getImage().getScaledInstance(95, 85, Image.SCALE_DEFAULT));

        fetchDataFromDatabase(user);
        //labels
        leftlabel.setIcon(image);
        leftlabel.setText("Attendance");
        leftlabel.setForeground(Color.WHITE);
        leftlabel.setFont(new Font("Poppins", Font.BOLD, 35));
        leftlabel.setBounds(30, 30, 400, 400);
        leftlabel.setVerticalTextPosition(JLabel.TOP);
        leftlabel.setHorizontalTextPosition(JLabel.CENTER);

        title = new JLabel("Student Details");
        title.setBounds(65, 20, 350, 75);

        title.setFont(new Font("Poppins", Font.BOLD, 35));
        title.setForeground(new Color(128, 98, 214));

        studentName.setBounds(20,140,200,30);
        studentName.setForeground(Color.BLACK);
        studentNametb.setBounds(150,140,220,30);
        studentNametb.setBackground(new Color(128, 98, 214));
        studentNametb.setForeground(Color.WHITE);
        studentNametb.setHorizontalAlignment(JTextField.CENTER);
        studentNametb.setEditable(false);
        studentId.setBounds(20,200,200,30);
        studentId.setForeground(Color.BLACK);
        studentIdtb.setBounds(150,200,220,30);
        studentIdtb.setBackground(new Color(128, 98, 214));
        studentIdtb.setForeground(Color.WHITE);
        studentIdtb.setHorizontalAlignment(JTextField.CENTER);
        studentIdtb.setEditable(false);
        studentYear.setBounds(20,260,200,30);
        studentYear.setForeground(Color.BLACK);
        yeartb.setBounds(150,260,220,30);
        yeartb.setBackground(new Color(128, 98, 214));
        yeartb.setForeground(Color.WHITE);
        yeartb.setHorizontalAlignment(JTextField.CENTER);
        yeartb.setEditable(false);
        Attended.setBounds(20,320,200,30);
        Attended.setForeground(Color.BLACK);
        Attendedtb.setBounds(150,320,220,30);
        Attendedtb.setBackground(new Color(128, 98, 214));
        Attendedtb.setForeground(Color.WHITE);
        Attendedtb.setHorizontalAlignment(JTextField.CENTER);
        Attendedtb.setEditable(false);
        conducted.setBounds(20,380,200,30);
        conducted.setForeground(Color.BLACK);
        conductedtb.setBounds(150,380,220,30);
        conductedtb.setBackground(new Color(128, 98, 214));
        conductedtb.setForeground(Color.WHITE);
        conductedtb.setHorizontalAlignment(JTextField.CENTER);
        conductedtb.setEditable(false);
        percentage.setBounds(20,440,200,30);
        percentage.setForeground(Color.BLACK);
        percentagetb.setBounds(150,440,220,30);
        percentagetb.setBackground(new Color(128, 98, 214));
        percentagetb.setForeground(Color.WHITE);
        percentagetb.setHorizontalAlignment(JTextField.CENTER);
        percentagetb.setEditable(false);
        Logout = new JButton("User Login");
        Logout.setBounds(150, 450, 100, 50);
        Logout.setFocusable(false);
        Logout.setText("Logout");
        Logout.setBackground(Color.WHITE);
        Logout.setForeground(new Color(128, 98, 214));
        Logout.setFont(new Font("Poppins", Font.BOLD, 15));
        Logout.setBorder(BorderFactory.createEtchedBorder());
        Logout.addActionListener(this);

        //panel
        leftpanel.setBounds(0, 0, 400, 600);
        leftpanel.setBackground(new Color(128, 98, 214));
        leftpanel.setLayout(null);
        leftpanel.add(leftlabel);
        leftpanel.add(Logout);
        rightpanel.setBounds(400, 0, 400, 600);
        rightpanel.setBackground(Color.WHITE);
        rightpanel.setLayout(null);
        rightpanel.add(title);
        rightpanel.add(studentName);
        rightpanel.add(studentNametb);
        rightpanel.add(studentId);
        rightpanel.add(studentIdtb);
        rightpanel.add(studentYear);
        rightpanel.add(yeartb);
        rightpanel.add(Attended);
        rightpanel.add(Attendedtb);
        rightpanel.add(conducted);
        rightpanel.add(conductedtb);
        rightpanel.add(percentage);
        rightpanel.add(percentagetb);

        //frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 570);
        this.setLayout(null);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.WHITE);
        this.add(leftpanel);
        this.add(rightpanel);
        this.setTitle("Admin Login Portal");
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setVisible(true);
    }
    void fetchDataFromDatabase(String studentId) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/student_attendance";
        String user = "root";
        String password = "Harshal@770926";

        // SQL query to select a student's data by ID
        String query = "SELECT * FROM student_records WHERE Student_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameter for the query
            preparedStatement.setString(1, studentId);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the result set has data
            if (resultSet.next()) {
                // Retrieve data from the result set
                String name = resultSet.getString("Student_name");
                String year = resultSet.getString("Year");
                String attended = resultSet.getString("lec_attended");
                String conducted = resultSet.getString("Lec_conducted");
                String percentageValue = resultSet.getString("percentage");

                // Populate JTextFields with retrieved data
                studentNametb.setText(name);
                studentIdtb.setText(studentId);
                yeartb.setText(year);
                Attendedtb.setText(attended);
                conductedtb.setText(conducted);
                percentagetb.setText(percentageValue);
            } else {
                // Handle the case where no data is found for the given student ID
                JOptionPane.showMessageDialog(null, "Student not found in the database", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==Logout)
        {
            this.dispose();
            UserLogin userLogin = new UserLogin();
        }
    }
}
