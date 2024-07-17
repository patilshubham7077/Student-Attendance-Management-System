import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.sql.*;

public class AdminPage extends JFrame implements ActionListener {
    JPanel heading = new JPanel();
    JPanel leftSection = new JPanel();
    JPanel bottomSection = new JPanel();
    JPanel rightSection = new JPanel();
    JLabel title = new JLabel("Student Records");
    JButton add = GUI.JButton("Add");
    JButton update = GUI.JButton("Update");
    JButton delete = GUI.JButton("Delete");
    JButton print =  GUI.JButton("Print");
    JButton logout = GUI.JButton("Logout");
    JButton addNewLecture = GUI.JButton("Add New Lecture");
    JButton showLecture = GUI.JButton("Show Lecture");

    JLabel studentName = GUI.JLabel("Name:");
    JLabel studentId = GUI.JLabel("ID:");
    JLabel studentYear = GUI.JLabel("Year:");
    JLabel Attended = GUI.JLabel("Lecture Attended:");
    JLabel conducted = GUI.JLabel("Lecture Conducted:");
    JTextField studentNametb = GUI.JTextField();
    JTextField studentIdtb = GUI.JTextField();
    JTextField Attendedtb = GUI.JTextField();
    JTextField conductedtb = GUI.JTextField();
    String[] year = new String[]{"1st Year", "2nd Year", "3rd Year"};
    JComboBox studentYeartb = new JComboBox(year);

    JTable table;
    JTableHeader header;
    DefaultTableModel model;
    String[] column;
    JScrollPane scrollPane;
    AdminPage()
    {
        countFrequencyAndUpdate();
        calculatePercentageAndUpdate();
        ImageIcon icon = new ImageIcon("icon.png");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(icon.getImage());
        this.setSize(1200,750);
        this.setLayout(null);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.WHITE);
        this.setLocationRelativeTo(null);

        countFrequencyAndUpdate();

        heading.setBackground(new Color(128, 98, 214));
        heading.setBounds(0,0,1200,75);
        heading.setLayout(null);
        title.setBounds(450,0,350,75);
        title.setFont(new Font("Poppins",Font.BOLD,35));
        title.setForeground(Color.WHITE);
        heading.add(title);
        this.add(heading);

        studentName.setBounds(20,20,250,30);
        studentId.setBounds(20,80,250,30);
        studentYear.setBounds(20,140,250,30);
        Attended.setBounds(20,200,250,30);
        conducted.setBounds(20,260,250,30);
        addNewLecture.setBackground(new Color(255, 210, 215));
        addNewLecture.setForeground(Color.BLACK);
        showLecture.setBackground(new Color(255, 210, 215));
        showLecture.setForeground(Color.BLACK);
        addNewLecture.setBounds(100,400,300,50);
        showLecture.setBounds(100,500,300,50);


        studentNametb.setBounds(230,20,250,30);
        studentIdtb.setBounds(230,80,250,30);
        studentYeartb.setBounds(230,140,250,30);
        studentYeartb.setEditable(true);
        Attendedtb.setBounds(230,200,250,30);
        Attendedtb.setFont(new Font("Poppins",Font.PLAIN,15));
        conductedtb.setBounds(230,260,250,30);
        conductedtb.setFont(new Font("Poppins",Font.PLAIN,15));



        leftSection.setBackground(new Color(146, 136, 248));
        leftSection.setBounds(0,75,500,725);
        leftSection.setLayout(null);
        leftSection.add(studentName);
        leftSection.add(studentId);
        leftSection.add(studentYear);
        leftSection.add(Attended);
        leftSection.add(conducted);
        leftSection.add(studentNametb);
        leftSection.add(studentIdtb);
        leftSection.add(studentYeartb);
        leftSection.add(Attendedtb);
        leftSection.add(conductedtb);
        leftSection.add(addNewLecture);
        leftSection.add(showLecture);
        this.add(leftSection);

        String[] column = new String[]{"Name", "Student ID", "Year", "Lec.Attended", "Lec.Conducted", "Percentage"};

        model = new DefaultTableModel(column, 0);
        table = new JTable(model);
        table.setBounds(50,75,500,575);
        table.setFont(new Font("Poppins",Font.PLAIN,12));
        header = table.getTableHeader();
        header.setBackground(new Color(255, 210, 215));
        header.setFont(new Font("Poppins",Font.BOLD,14));
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        fetchDataFromDatabase();
        scrollPane = new JScrollPane(table);

        rightSection.setBackground(Color.WHITE);
        rightSection.setBounds(500,75,700,575);
        rightSection.setLayout(new BorderLayout());
        rightSection.add(scrollPane, BorderLayout.CENTER);
        this.add(rightSection);


        bottomSection.setBackground(new Color(255, 210, 215));
        bottomSection.setBounds(500,650,700,150);
        bottomSection.setLayout(new FlowLayout(FlowLayout.CENTER,35,25));
        bottomSection.add(add);
        bottomSection.add(update);
        bottomSection.add(delete);
        bottomSection.add(print);
        bottomSection.add(logout);


        add.addActionListener(this);
        update.addActionListener(this);
        delete.addActionListener(this);
        print.addActionListener(this);
        logout.addActionListener(this);
        addNewLecture.addActionListener(this);
        showLecture.addActionListener(this);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowIndex = table.getSelectedRow();

                String name = (String) model.getValueAt(rowIndex,0);
                String id = (String) model.getValueAt(rowIndex,1);
                String year = (String) model.getValueAt(rowIndex,2);
                String attended = (String) model.getValueAt(rowIndex,3);
                String conducted = (String) model.getValueAt(rowIndex,4);

                studentNametb.setText(name);
                studentIdtb.setText(id);
                studentYeartb.setName(year);
                Attendedtb.setText(attended);
                conductedtb.setText(conducted);
            }
        });
        this.add(bottomSection);
        this.setUndecorated(true);
        this.setVisible(true);

    }
    private void fetchDataFromDatabase() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_attendance", "root", "password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM student_records");

            while (resultSet.next()) {
                String name = resultSet.getString("Student_name");
                String id = resultSet.getString("Student_id");
                String year = resultSet.getString("Year");
                String attended = resultSet.getString("lec_attended");
                String conducted = resultSet.getString("Lec_conducted");
                String percentage = resultSet.getString("percentage");


                // Add a new row to the table model
                model.addRow(new Object[]{name, id, year, attended, conducted, percentage});
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void my_db_delete(String id) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/student_attendance";
        String user = "root";
        String password = "Harshal@770926";

        // SQL query to delete a record
        String query = "DELETE FROM student_records WHERE Student_id=?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the delete query
            preparedStatement.setString(1, id);

            // Execute the delete query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Record deleted successfully!");
            } else {
                System.out.println("No records found for delete.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query1 = "DELETE FROM studentUsers WHERE username=?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query1)) {

            // Set parameters for the delete query
            preparedStatement.setString(1, id);

            // Execute the delete query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Record deleted successfully!");
            } else {
                System.out.println("No records found for delete.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void my_db_update(String name, String id, String year, String attended, String conducted, String percentage) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/student_attendance";
        String user = "root";
        String password = "Harshal@770926";

        // SQL query to update a record
        String query = "UPDATE student_records SET Student_name=?, Year=?, lec_attended=?, Lec_conducted=?, percentage=? WHERE Student_id=?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the update query
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, year);
            preparedStatement.setString(3, attended);
            preparedStatement.setString(4, conducted);
            preparedStatement.setString(5, percentage);
            preparedStatement.setString(6, id);

            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Record updated successfully!");
            } else {
                System.out.println("No records found for update.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void exportToPDF() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("table.pdf"));
            document.open();

            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            addTableHeader(pdfTable);
            addRows(pdfTable);

            document.add(pdfTable);

            JOptionPane.showMessageDialog(null, "Table exported to PDF successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private void addTableHeader(PdfPTable pdfTable) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            PdfPCell cell = new PdfPCell(new Phrase(table.getColumnName(i)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(cell);
        }
    }

    private void addRows(PdfPTable pdfTable) {
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                PdfPCell cell = new PdfPCell(new Phrase(table.getValueAt(i, j).toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell);
            }
        }
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    void countFrequencyAndUpdate() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_attendance", "root", "password");
            Statement statement = connection.createStatement();

            // Query to get the frequency of each StudentId with attendance_status=1
            String query = "SELECT StudentId, COUNT(*) as frequency FROM Lecture_records WHERE attendance_status=1 GROUP BY StudentId";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String studentId = resultSet.getString("StudentId");
                int frequency = resultSet.getInt("frequency");

                // Update Lec_attended in student_records
                updateLecAttended(studentId, frequency);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while updating frequency count.");
        }
    }

    private void updateLecAttended(String studentId, int frequency) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/student_attendance";
        String user = "root";
        String password = "Harshal@770926";

        // SQL query to update Lec_attended in student_records table
        String query = "UPDATE student_records SET lec_attended = ? WHERE Student_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the update query
            preparedStatement.setInt(1, frequency);
            preparedStatement.setString(2, studentId);

            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Lec_attended updated successfully for StudentId: " + studentId);
            } else {
                System.out.println("No records found for update.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void calculatePercentageAndUpdate() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_attendance", "root", "password");
            Statement statement = connection.createStatement();

            // Query to get lec_attended and lec_conducted for each StudentId
            String query = "SELECT Student_id, SUM(lec_attended) as total_attended, SUM(Lec_conducted) as total_conducted FROM student_records GROUP BY Student_id";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String studentId = resultSet.getString("Student_id");
                int totalAttended = resultSet.getInt("total_attended");
                int totalConducted = resultSet.getInt("total_conducted");

                // Calculate percentage
                double percentage = (totalAttended * 100.0) / totalConducted;

                // Update percentage in student_records
                updatePercentage(studentId, percentage);
            }

            resultSet.close();
            statement.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while updating percentage.");
        }
    }

    private void updatePercentage(String studentId, double percentage) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/student_attendance";
        String user = "root";
        String password = "Harshal@770926";

        // SQL query to update percentage in student_records table
        String query = "UPDATE student_records SET percentage = ? WHERE Student_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the update query
            preparedStatement.setDouble(1, percentage);
            preparedStatement.setString(2, studentId);

            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Percentage updated successfully for StudentId: " + studentId);
            } else {
                System.out.println("No records found for update.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addNewLectureAndUpdate() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_attendance", "root", "password");
            Statement statement = connection.createStatement();

            // Increment lec_conducted for all students in student_records
            String query = "UPDATE student_records SET Lec_conducted = CAST(Lec_conducted AS SIGNED) + 1";

            int rowsAffected = statement.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("Lec_conducted updated successfully for all students.");
            } else {
                System.out.println("No records found for update.");
            }

            statement.close();
            connection.close();

            JOptionPane.showMessageDialog(null, "New lecture added successfully.");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while updating lec_conducted.");
        }
    }
    public static void lectureConductedIncrement()
    {
        addNewLectureAndUpdate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if(e.getSource()==logout)
        {
            this.dispose();
            LoginPage loginPage = new LoginPage();
            return;
        }
        if (e.getSource() == print) {
            exportToPDF();
        } else if (e.getSource()==addNewLecture) {
            this.dispose();
            AddNewLecture addNewLecture1 = new AddNewLecture();
        } else if (e.getSource()==showLecture) {
            this.dispose();
            ShowLecture showLecture = new ShowLecture();
            showLecture.enableTableEditing();
        } else {
            if (studentNametb.getText().isEmpty() || studentIdtb.getText().isEmpty() || Attendedtb.getText().isEmpty() || conductedtb.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                String name = studentNametb.getText();
                String id = studentIdtb.getText();
                String year = studentYeartb.getSelectedItem().toString();
                String attended = Attendedtb.getText();
                String conducted = conductedtb.getText();
                double a = Double.parseDouble(attended);
                double c = Double.parseDouble(conducted);
                double percentage = (a / c) * 100;
                String percen = Double.toString(round(percentage, 3));
                Object[] newRow = {name, id, year, attended, conducted, percen};
                if (e.getSource() == add) {
                    InsertData insertData = new InsertData();
                    insertData.my_db_update(name, id, year, attended, conducted, percen);
                    model.setRowCount(0);
                    fetchDataFromDatabase();
                    JOptionPane.showMessageDialog(null, "New student data added successfully.");
                    studentNametb.setText(null);
                    studentIdtb.setText(null);
                    Attendedtb.setText(null);
                    conductedtb.setText(null);


                }
                if (e.getSource() == update) {
                    my_db_update(name, id, year, attended, conducted, percen);

                    // Clear and update the JTable
                    model.setRowCount(0);
                    fetchDataFromDatabase();
                    JOptionPane.showMessageDialog(null, "Student data updated successfully.");
                    studentNametb.setText(null);
                    studentIdtb.setText(null);
                    Attendedtb.setText(null);
                    conductedtb.setText(null);

                }
            }
        }
        if (e.getSource() == delete) {
            String idToDelete = studentIdtb.getText();
            my_db_delete(idToDelete);

            // Clear and update the JTable
            model.setRowCount(0);
            fetchDataFromDatabase();
            JOptionPane.showMessageDialog(null, "Student data Deleted successfully.");
            studentNametb.setText(null);
            studentIdtb.setText(null);
            Attendedtb.setText(null);
            conductedtb.setText(null);
        }
    }
}


