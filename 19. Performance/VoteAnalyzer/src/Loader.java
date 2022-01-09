import java.io.File;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class Loader
{
    private final static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private final static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private final static HashMap<Voter, Integer> voterCounts = new HashMap<>();
    private final static List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
    private final static String fileName = "res/data-0.2M.xml";

    public static void main(String[] args) throws Exception
    {
        long freeMemory = Runtime.getRuntime().freeMemory();
        long start = System.currentTimeMillis();
        System.out.println("*** free memory at beginning: " + freeMemory);

        //  Блок SAX Parser
//        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
//        SAXParser parser = saxParserFactory.newSAXParser();
//        XMLHandler xmlHandler = new XMLHandler();
//        parser.parse(fileName, xmlHandler);
//        xmlHandler.printDoubledVisits();
//        xmlHandler.printWorkingTimes();
//

        parseFile(fileName);
        DBConnection.printVoterCounts();
        long end = System.currentTimeMillis();
        System.out.println("Duration of process: " + (end - start) + " ms.");

//        System.out.println("Duplicated voters: ");
//        for(Voter voter : voterCounts.keySet())
//        {
//            Integer count = voterCounts.get(voter);
//            if(count > 1) {
//                System.out.println("\t" + voter + " - " + count);
//            }
//        }
//        long usedMemory = freeMemory - Runtime.getRuntime().freeMemory();
//        System.out.println("usedMemory : " + usedMemory);
//
//        VerifyCurrentGC verifyCurrentGC = new VerifyCurrentGC();
//        verifyCurrentGC.verifyGC(gcBeans);
    }

    private static void parseFile(String fileName) throws Exception
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(fileName));

        findEqualVoters(doc);
//        fixWorkTimes(doc);
    }

    private static void findEqualVoters(Document doc) throws Exception
    {
        NodeList voters = doc.getElementsByTagName("voter");
        int votersCount = voters.getLength();
        for(int i = 0; i < votersCount; i++)
        {
            Node node = voters.item(i);
            NamedNodeMap attributes = node.getAttributes();

            String name = attributes.getNamedItem("name").getNodeValue();
            String birthDay = attributes.getNamedItem("birthDay").getNodeValue();
//            Date birthDay = birthDayFormat.parse(attributes.getNamedItem("birthDay").getNodeValue());
            DBConnection.countVoter(name, birthDay);
//            Voter voter = new Voter(name, birthDay);
//            Integer count = voterCounts.get(voter);
//            voterCounts.put(voter, count == null ? 1 : count + 1);
        }
        DBConnection.executeMultiInsert();
    }

    private static void fixWorkTimes(Document doc) throws Exception
    {
        XMLHandler xmlHandler = new XMLHandler();
        NodeList visits = doc.getElementsByTagName("visit");
        int visitCount = visits.getLength();
        for(int i = 0; i < visitCount; i++)
        {
            Node node = visits.item(i);
            NamedNodeMap attributes = node.getAttributes();
            int station = Integer.parseInt(attributes.getNamedItem("station").getNodeValue());
            Date time = visitDateFormat.parse(attributes.getNamedItem("time").getNodeValue());
            xmlHandler.processVisits(station, time);
//            WorkTime workTime = voteStationWorkTimes.get(station);
//            if(workTime == null)
//            {
//                workTime = new WorkTime();
//                voteStationWorkTimes.put(station, workTime);
//            }
//            workTime.addVisitTime(time.getTime());
        }
        xmlHandler.printWorkingTimes();
    }
}