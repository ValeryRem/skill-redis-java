import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Loader
{
    private final static String fileName = "res/data-0.2M.xml";

    public static void main(String[] args) throws Exception
    {
        long start = System.currentTimeMillis();

        //  Блок SAX Parser
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser parser = saxParserFactory.newSAXParser();
        XMLHandler xmlHandler = new XMLHandler();
        parser.parse(fileName, xmlHandler);
        long end = System.currentTimeMillis();
        System.out.println("Duration of parsing: " + (end - start) + " ms.");
    }
}