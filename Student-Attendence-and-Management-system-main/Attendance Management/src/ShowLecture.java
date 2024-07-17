import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ShowLecture extends JFrame implements ActionListener {

    JPanel heading = new JPanel();
    JLabel title = new JLabel("Lecture Records");
    JPanel bottomSection = new JPanel();
    JButton addNewLecture = GUI.JButton("Add new Lecture");
    JButton StudentRecords = GUI.JButton("Student Records");
    JButton Logout = GUI.JButton("Logout");
    JPanel showPanel = new JPanel();
    JPanel midpanel = new JPanel();
    JLabel date = new JLabel("Enter date:");
    JLabel time = new JLabel("Enter time:");
    String[] lectureTime = new String[]{"08:00 am to 10:00 am", "01:00 pm to 03:00 pm", "06:00 pm to 08:00 pm"};
    JComboBox lectime = new JComboBox(lectureTime);
    JDateChooser dateChooser = new JDateChooser();
    JTable table;
    JTableHeader header;
    DefaultTableModel model;
    String[] column;
    JScrollPane scrollPane;
    JButton Submit;

    ShowLecture() {

        date.setBounds(50, 25, 200, 20);
        date.setFont(new Font("Poppins", Font.BOLD, 20));
        dateChooser.setBounds(200, 20, 200, 30);
        dateChooser.setDateFormatString("dd - MM - yyyy");
        time.setBounds(500, 25, 200, 20);
        time.setFont(new Font("Poppins", Font.BOLD, 20));
        lectime.setBounds(650, 20, 200, 30);

        Submit = new JButton("Show Data");
        Submit.setBounds(975, 15, 100, 50);
        Submit.setFocusable(false);
        Submit.setBackground(new Color(128, 98, 214));
        Submit.setForeground(Color.WHITE);
        Submit.setFont(new Font("Poppins", Font.BOLD, 15));
        Submit.setBorder(BorderFactory.createEtchedBorder());
        Submit.addActionListener(this);

        Logout.setBounds(975, 15, 250, 50);
        Logout.setFocusable(false);
        Logout.setBackground(new Color(128, 98, 214));
        Logout.setForeground(Color.WHITE);
        Logout.setFont(new Font("Poppins", Font.BOLD, 15));
        Logout.setBorder(BorderFactory.createEtchedBorder());
        Logout.addActionListener(this);

        addNewLecture.setBounds(975, 15, 250, 50);
        addNewLecture.setFocusable(false);
        addNewLecture.setLayout(null);
        addNewLecture.setBackground(new Color(128, 98, 214));
        addNewLecture.setForeground(Color.WHITE);
        addNewLecture.setFont(new Font("Poppins", Font.BOLD, 15));
        addNewLecture.setBorder(BorderFactory.createEtchedBorder());
        addNewLecture.addActionListener(this);

        StudentRecords.setBounds(975, 15, 250, 50);
        StudentRecords.setFocusable(false);
        StudentRecords.setBackground(new Color(128, 98, 214));
        StudentRecords.setForeground(Color.WHITE);
        StudentRecords.setFont(new Font("Poppins", Font.BOLD, 15));
        StudentRecords.setBorder(BorderFactory.createEtchedBorder());
        StudentRecords.addActionListener(this);

        heading.setBackground(new Color(128, 98, 214));
        heading.setBounds(0, 0, 1200, 75);
        showPanel.setBackground(new Color(146, 136, 248));
        showPanel.setBounds(0, 75, 1200, 75);
        heading.setLayout(null);
        showPanel.setLayout(null);
        showPanel.add(date);
        showPanel.add(dateChooser);
        showPanel.add(time);
        showPanel.add(lectime);
        showPanel.add(Submit);
        title.setBounds(450, 0, 350, 75);
        title.setFont(new Font("Poppins", Font.BOLD, 35));
        title.setForeground(Color.WHITE);
        heading.add(title);
        this.add(heading);
        this.add(showPanel);

        String[] column = new String[]{"Date", "Time", "StudentID", "Name", "Attendance_status"};

        model = new DefaultTableModel(column, 0);
        table = new JTable(model);
        table.setBounds(50, 75, 500, 575);
        table.setFont(new Font("Poppins", Font.PLAIN, 12));
        header = table.getTableHeader();
        header.setBackground(new Color(255, 210, 215));
        header.setFont(new Font("Poppins", Font.BOLD, 14));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        scrollPane = new JScrollPane(table);
        midpanel.setLayout(new BorderLayout());
        midpanel.add(scrollPane, BorderLayout.CENTER);
        midpanel.setBackground(Color.RED);
        midpanel.setBounds(0, 150, 1200, 450);
        bottomSection.setBackground(new Color(255, 210, 215));
        bottomSection.setBounds(0, 600, 1200, 200);
        bottomSection.setLayout(new FlowLayout(FlowLayout.CENTER, 35, 30));
        bottomSection.add(addNewLecture);
        bottomSection.add(StudentRecords);
        bottomSection.add(Logout);
        this.add(bottomSection);
        this.add(midpanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 700);
        this.setLayout(null);
        this.setResizable(false);
        this.setUndecorated(true);
        this.getContentPane().setBackground(Color.WHITE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public void fetchDataFromDatabase() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd - MM - yyyy");
            String selectedDate = sdf.format(dateChooser.getDate());
            String selectedOption = lectime.getSelectedItem().toString();

            // JDBC connection to your MySQL database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_attendance", "root", "Harshal@770926");

            // Prepare SQL query
            String query = "SELECT * FROM Lecture_records WHERE Date = ? AND Time = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, selectedDate);
            preparedStatement.setString(2, selectedOption);

            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Clear existing table data
            model.setRowCount(0);

            // Populate table with fetched data
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getString("Date"));
                row.add(resultSet.getString("Time"));
                row.add(resultSet.getString("StudentID"));
                row.add(resultSet.getString("Name"));
                row.add(resultSet.getString("Attendance_status"));
                model.addRow(row);
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDataInDatabase(int editedRow, int editedColumn, String updatedValue) {
        try {
            String date = model.getValueAt(editedRow, 0).toString();
            String time = model.getValueAt(editedRow, 1).toString();
            String studentID = model.getValueAt(editedRow, 2).toString();

            // Update the corresponding cell in the MySQL database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_attendance", "root", "Harshal@770926");
            String columnName = model.getColumnName(editedColumn);
            String query = "UPDATE Lecture_records SET " + columnName + " = ? WHERE Date = ? AND Time = ? AND StudentId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, updatedValue);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, time);
            preparedStatement.setString(4, studentID);

            // Execute the update query
            preparedStatement.executeUpdate();

            // Close resources
            preparedStatement.close();
            connection.close();
            JOptionPane.showMessageDialog(null, "Updated successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Submit) {
            fetchDataFromDatabase();
        }else if (e.getSource() == addNewLecture)
        {
            this.dispose();
            AddNewLecture adNewLecture = new AddNewLecture();
        } else if (e.getSource()==StudentRecords)
        {
            this.dispose();
            AdminPage adminPage = new AdminPage();

        } else if (e.getSource()==Logout) {
            this.dispose();
            LoginPage loginPage = new LoginPage();
        }
    }


    public void enableTableEditing() {
        // Enable cell editing
        table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));

        // Add a cell editor listener to update the database when a cell is edited
        table.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int editedRow = e.getFirstRow();
                int editedColumn = e.getColumn();
                String updatedValue = table.getValueAt(editedRow, editedColumn).toString();
                updateDataInDatabase(editedRow, editedColumn, updatedValue);
            }
        });
    }
}
