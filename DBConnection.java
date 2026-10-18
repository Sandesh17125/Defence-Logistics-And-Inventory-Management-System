import java.sql.*;

public class DBConnection {
    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe",
                "system",
                "root"
            );

            System.out.println("Connected Successfully");
            return con;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}