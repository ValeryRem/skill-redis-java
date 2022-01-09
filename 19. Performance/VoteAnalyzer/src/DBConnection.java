import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.Format;

public class DBConnection
{
    private static Connection connection;

    private static String dbName = "learn";
    private static String dbUser = "root";
    private static String dbPass = "valery_56";

    private static StringBuilder insertQuery = new StringBuilder();

    public static Connection getConnection()
    {
        if(connection == null)
        {
            try {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbName +
                    "?user=" + dbUser + "&password=" + dbPass + "&useSSL=false");
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("set names utf8");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT CHARSET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "count INT NOT NULL, " +
                        "PRIMARY KEY(id), " +
                        "UNIQUE KEY name_date(name(50), birthDate))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void executeMultiInsert () throws SQLException{
        String sql = "INSERT INTO voter_count(name, birthDate, count) VALUES" + convertToUTF8(insertQuery.toString()) +
                "ON DUPLICATE KEY UPDATE count=count + 1";
        DBConnection.getConnection().createStatement().execute(sql);
    }

    public static void countVoter(String name, String birthDay)
    {
        birthDay = birthDay.replace('.', '-');
        insertQuery.append((insertQuery.length() == 0 ? "" : ",") + "('" + convertToUTF8(name) + "', '" + birthDay + "' 1)");

//        String sql = "INSERT INTO voter_count(name, birthDate, count) VALUES('" +
//                name + "', '" + birthDay + "', 1) ON DUPLICATE KEY UPDATE count=count + 1";
//        DBConnection.getConnection().createStatement().execute(sql);


//        String sql = "SELECT id FROM voter_count WHERE birthDate='" + birthDay + "' AND name='" + name + "'";
//        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
//        if(!rs.next())
//        {
//            DBConnection.getConnection().createStatement()
//                    .execute("INSERT INTO voter_count(name, birthDate, `count`) VALUES('" +
//                            name + "', '" + birthDay + "', 1)");
//        }
//        else {
//            int id = rs.getInt("id");
//            DBConnection.getConnection().createStatement()
//                    .execute("UPDATE voter_count SET `count`=`count`+1 WHERE id=" + id);
//        }
//        rs.close();
    }

    public static void printVoterCounts() throws SQLException
    {
        String sql = "SELECT name, birthDate, count FROM voter_count WHERE count > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while(rs.next())
        {
            System.out.println("\t" + rs.getString("name") + " (" +
                    rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
        rs.close();
    }

        public static String convertToUTF8 (String s) {
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(s);
    return
         new String(byteBuffer.array(), StandardCharsets.UTF_8);
    }

}
