import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;
    private static String dbName = "skillbox";
    private static String dbUser = "root";
    private static String dbPass = "12345678";
    private static String sqlQuery;

    public static Connection getConnection(String sqlQuery) {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbName +
                                "?user=" + dbUser + "&password=" + dbPass + "&serverTimezone=UTC");
                connection.createStatement().execute(sqlQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
