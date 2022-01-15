package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection con = null;

    public static Connection getConnection() throws SQLException {
        if (con==null || con.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                con = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/car_location",
                                "ziedyaich", "");
                System.out.println("Opened database successfully");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }
        return con;
    }
}
