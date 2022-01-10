import java.sql.*;

public class DBConnection
{
    private static Connection connection;

    private static final String dbName = "learn";
    private static final String dbUser = "root";
    private static final String dbPass = "valery_56";
//    private static String query = "INSERT INTO voters(name, birthDate, station, visitTime) VALUES";

    public static Connection getConnection()
    {
        if(connection == null)
        {
            try {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbName +
                    "?user=" + dbUser + "&password=" + dbPass + "&characterEncoding=UTF-8&useSSL=false");
                connection.createStatement().execute("DROP TABLE IF EXISTS voters");
                connection.createStatement().execute("CREATE TABLE voters(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT CHARSET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL, " +
                        "birthDate TINYTEXT CHARSET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL, " +
                        "station INT NOT NULL," +
                        "visitTime TINYTEXT CHARSET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL, " +
                        "PRIMARY KEY(id))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

//    public static String getQuery() {
//        return query;
//    }
//
//    public static void setQuery(String query) {
//        DBConnection.query = query;
//    }

    //    public void setVoterToDB(Voter voter) throws Exception
//    {
//        String sql = "SELECT id FROM voters WHERE name='" + voter.getName() + "' AND birthDate='" + voter.getBirthDay() + "' " +
//                " AND visitTime='" + voter.getTime() + "' " + " AND station='" + voter.getStation() + "'";
//        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
//        if(rs == null)
//        {
//            DBConnection.getConnection().createStatement()
//                    .execute("INSERT INTO voters(name, birthDate, visitTime, station) VALUES('"  +
//                            voter.getName() + "', '" + birthdayFormat.format(voter.getBirthDay()) + "', '" +
//                            visitDateFormat.format(voter.getTime()) + "', '" + voter.getStation() + "')");
//        } else {
//            rs.close();
//        }
//    }
}
