import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Loader
{
//    private final static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
//    private final static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//    private final static HashMap<Integer, WorkTime> voteStationWorkTimes = new HashMap<>();
//    private final static HashMap<Voter, Integer> voterCounts = new HashMap<>();
//    private final static List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();

    public static void main(String[] args) throws Exception
    {
        String fileName = "res/data-1M.xml";
        long freeMemory = Runtime.getRuntime().freeMemory();
        System.out.println("*** free memory at beginning: " + freeMemory);

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser parser = saxParserFactory.newSAXParser();
        XMLHandler xmlHandler = new XMLHandler();
        parser.parse(fileName, xmlHandler);
        xmlHandler.printDoubledVisits();
        xmlHandler.printWorkingTimes();

//        xmlHandler.printVisitTimes();
//        System.out.println("***** starting freeMemory: " + freeMemory); //
//        parseFile(fileName);
//
        //Printing results
//        System.out.println("Voting station work times: ");
//        for(Integer votingStation : voteStationWorkTimes.keySet())
//        {
//            WorkTime workTime = voteStationWorkTimes.get(votingStation);
//            System.out.println("\t" + votingStation + " - " + workTime);
//        }
//
//        System.out.println("Duplicated voters: ");
//        for(Voter voter : voterCounts.keySet())
//        {
//            Integer count = voterCounts.get(voter);
//            if(count > 1) {
//                System.out.println("\t" + voter + " - " + count);
//            }
//        }
        long usedMemory = freeMemory - Runtime.getRuntime().freeMemory();
        System.out.println("usedMemory : " + usedMemory);

//        VerifyCurrentGC verifyCurrentGC = new VerifyCurrentGC();
//        verifyCurrentGC.verifyGC(gcBeans);
    }
//    private static void parseFile(String fileName) throws Exception
//    {
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        DocumentBuilder db = dbf.newDocumentBuilder();
//        Document doc = db.parse(new File(fileName));
//
//        findEqualVoters(doc);
//        fixWorkTimes(doc);
//    }
//
//    private static void findEqualVoters(Document doc) throws Exception
//    {
//        NodeList voters = doc.getElementsByTagName("voter");
//        int votersCount = voters.getLength();
//        for(int i = 0; i < votersCount; i++)
//        {
//            Node node = voters.item(i);
//            NamedNodeMap attributes = node.getAttributes();
//
//            String name = attributes.getNamedItem("name").getNodeValue();
//            Date birthDay = birthDayFormat.parse(attributes.getNamedItem("birthDay").getNodeValue());
//
//            Voter voter = new Voter(name, birthDay);
//            Integer count = voterCounts.get(voter);
//            voterCounts.put(voter, count == null ? 1 : count + 1);
//        }
//    }
//
//    private static void fixWorkTimes(Document doc) throws Exception
//    {
//        NodeList visits = doc.getElementsByTagName("visit");
//        int visitCount = visits.getLength();
//        for(int i = 0; i < visitCount; i++)
//        {
//            Node node = visits.item(i);
//            NamedNodeMap attributes = node.getAttributes();
//
//            Integer station = Integer.parseInt(attributes.getNamedItem("station").getNodeValue());
//            Date time = visitDateFormat.parse(attributes.getNamedItem("time").getNodeValue());
//            WorkTime workTime = voteStationWorkTimes.get(station);
//            if(workTime == null)
//            {
//                workTime = new WorkTime();
//                voteStationWorkTimes.put(station, workTime);
//            }
//            workTime.addVisitTime(time.getTime());
//        }
//    }
}