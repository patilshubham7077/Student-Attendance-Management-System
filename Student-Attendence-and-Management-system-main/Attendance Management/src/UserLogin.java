import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserLogin extends JFrame implements ActionListener {
    JButton loginbutton, AdminLogin;
    JPanel rightpanel = new JPanel();
    JPanel leftpanel = new JPanel();
    JLabel rightlabel = new JLabel();
    JLabel user,pass,title;
    JTextField usert;
    JPasswordField passt;
    UserLogin()
    {


        ImageIcon image = new ImageIcon(new ImageIcon("login.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
        ImageIcon icon = new ImageIcon(new ImageIcon("icon.png").getImage().getScaledInstance(85, 75, Image.SCALE_DEFAULT));

        //labels
        rightlabel.setIcon(image);
        rightlabel.setText("Welcome Back !");
        rightlabel.setForeground(Color.WHITE);
        rightlabel.setFont(new Font("Poppins",Font.BOLD,35));
        rightlabel.setBounds(30,20,400,400);
        rightlabel.setVerticalTextPosition(JLabel.TOP);
        rightlabel.setHorizontalTextPosition(JLabel.CENTER);
        user = new JLabel("Username:");
        usert = new JTextField();
        pass = new JLabel("Password:");
        passt = new JPasswordField();
        title = new JLabel("User Portal");

        title.setBounds(100,40,350,75);
        user.setBounds(100,150,200,20);
        usert.setBounds(100,200,200,30);
        pass.setBounds(100,250,200,20);
        passt.setBounds(100,300,200,30);

        title.setFont(new Font("Poppins",Font.BOLD,35));
        title.setForeground(new Color(128, 98, 214));
        user.setFont(new Font("Poppins",Font.BOLD,20));
        pass.setFont(new Font("Poppins",Font.BOLD,20));
        usert.setFont(new Font("Poppins",Font.PLAIN,15));
        passt.setFont(new Font("Poppins",Font.PLAIN,15));

        loginbutton = new JButton("Login");
        loginbutton.setBounds(150,400,100,50);
        loginbutton.setFocusable(false);
        loginbutton.setText("Login");
        loginbutton.setBackground(new Color(128, 98, 214));
        loginbutton.setForeground(Color.WHITE);
        loginbutton.setFont(new Font("Poppins",Font.BOLD,15));
        loginbutton.setBorder(BorderFactory.createEtchedBorder());
        loginbutton.addActionListener(this);
        AdminLogin = new JButton("Admin Login");
        AdminLogin.setBounds(150,450,100,50);
        AdminLogin.setFocusable(false);
        AdminLogin.setText("Admin Login");
        AdminLogin.setBackground(new Color(255, 210, 215));
        AdminLogin.setForeground(new Color(128, 98, 214));
        AdminLogin.setFont(new Font("Poppins",Font.BOLD,15));
        AdminLogin.setBorder(BorderFactory.createEtchedBorder());
        AdminLogin.addActionListener(this);


        //panel
        rightpanel.setBounds(400,0,400,600);
        rightpanel.setBackground(new Color(128, 98, 214));
        rightpanel.setLayout(null);
        rightpanel.add(rightlabel);
        rightpanel.add(AdminLogin);
        leftpanel.setBounds(0,0,400,600);
        leftpanel.setBackground(Color.WHITE);
        leftpanel.setLayout(null);
        leftpanel.add(title);
        leftpanel.add(user);
        leftpanel.add(usert);
        leftpanel.add(pass);
        leftpanel.add(passt);
        leftpanel.add(loginbutton);

        //frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setLayout(null);
        this.setResizable(false);
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(Color.WHITE);
        this.add(rightpanel);
        this.add(leftpanel);
        this.setTitle("User Login Portal");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private boolean validateLogin(String username, String password) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/student_attendance";
        String dbUser = "root";
        String dbPassword = "Harshal@770926";

        // SQL query to validate login
        String query = "SELECT * FROM StudentUsers WHERE username=? AND password=?";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // If there is a match in the database, return true

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginbutton) {
            String username = usert.getText();
            String password = new String(passt.getPassword());

            if (validateLogin(username, password)) {
                this.dispose();
                UserPage userPage = new UserPage(username);
            } else {
                usert.setText(null);
                passt.setText(null);
                JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == AdminLogin) {
            this.dispose();
            LoginPage loginPage = new LoginPage();
        }
    }
}
