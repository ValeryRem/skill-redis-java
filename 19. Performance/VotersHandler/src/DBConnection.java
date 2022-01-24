import javax.xml.stream.XMLStreamException;
import java.sql.*;

public class DBConnection
{
    private static Connection connection;

    private static final String dbName = "learn";
    private static final String dbUser = "root";
    private static final String dbPass = "valery_56";
    private static StringBuilder insertQuery = new StringBuilder();

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

    public static void setVoterDB(Voter voter) throws SQLException, XMLStreamException {
        String birthDay = voter.getBirthDay().replace('.', '-');
        String name = voter.getName();
        int station = voter.getStation();
        String visitTime = voter.getVisitTime();

        insertQuery.append(insertQuery.length() == 0 ? "" : ",")
                .append("('")
                .append(name)
                .append("', '")
                .append(birthDay)
                .append("', '")
                .append(station)
                .append("', '")
                .append(visitTime)
                .append("')");

            if (insertQuery.length() > 65000){
                executeMultiInsert();
                insertQuery.delete(0, (insertQuery.length()));
            }
    }

    public static void executeMultiInsert() throws SQLException {
        String sql = "INSERT INTO voters(name, birthDate, station, visitTime) VALUES" + insertQuery;
        DBConnection.getConnection().createStatement().execute(sql);
    }
}
