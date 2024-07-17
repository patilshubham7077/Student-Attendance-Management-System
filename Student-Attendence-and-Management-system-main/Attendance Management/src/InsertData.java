import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InsertData {
    void my_db_update(String str1, String str2, String str3, String str4, String str5, String str6) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_attendance", "root", "Harshal@770926");
            Statement st = connection.createStatement();
            Statement ut = connection.createStatement();
            st.executeUpdate("INSERT INTO student_records " + "VALUES ('"+str1+"','"+str2+"','"+str3+"','"+str4+"','"+str5+"','"+str6+"')");
            ut.executeUpdate("INSERT INTO studentUsers " + "VALUES ('"+str2+"','"+str2+"')");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
