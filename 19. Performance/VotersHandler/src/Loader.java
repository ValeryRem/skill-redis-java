import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.sql.ResultSet;

public class Loader
{
    private final static String fileName = "res/data-1572M.xml";

    public static void main(String[] args) throws Exception
    {
        long start = System.currentTimeMillis();

        //  Блок SAX Parser
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser parser = saxParserFactory.newSAXParser();
        XMLHandler xmlHandler = new XMLHandler();
        parser.parse(fileName, xmlHandler);
//        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(DBConnection.getQuery());
//        rs.close();
        long end = System.currentTimeMillis();
        System.out.println("Duration of parsing: " + (end - start) + " ms.");
    }
}