import java.sql.Statement;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Danya on 24.02.2016.
 */
public class DBConnection
{
    private static Connection connection;

    private static String dbName = "skillbox";
    private static String dbUser = "root";
    private static String dbPass = "mmm333";

    public static Connection getConnection()
    {
        if(connection == null)
        {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbName, dbUser, dbPass);
                Statement statement = connection.createStatement();

                System.out.println(statement.execute("SELECT * FROM Courses"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


}
