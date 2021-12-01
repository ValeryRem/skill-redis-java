import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class XMLHandler extends DefaultHandler  {//SAXParserFactory {
    private Voter voter;// = new Voter();

    private final SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private final  SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private final HashMap<Voter, Integer> voterVisitCounts;
//    private final HashMap<Integer, Date> visitTimes = new HashMap<>();
    private final HashMap<Integer, WorkTime> voteStationWorkTimes = new HashMap<>();
    private final WorkTime workTime = new WorkTime();
    private final HashSet<Integer> stations = new HashSet<>();

    public XMLHandler () {
        voterVisitCounts = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if (qName.equals("voter") && voter == null) {
                Date birthday = birthdayFormat.parse(attributes.getValue("birthDay"));
                voter = new Voter(attributes.getValue("name"), birthday);
            } else if (qName.equals("visit") && voter !=null) {
                int count = voterVisitCounts.getOrDefault(voter, 0);
                Date visitTime = visitDateFormat.parse(attributes.getValue("time"));
                workTime.addVisitTime(visitTime.getTime());
                int  station = Integer.parseInt(attributes.getValue("station"));
                stations.add(station);
                voteStationWorkTimes.put(station, workTime);
                voterVisitCounts.put(voter, count + 1);
//                visitTimes.put(station, visitTime);
            }
        } catch (ParseException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        if(qName.equals("voter")) {
        voter = null;
        }
    }

    public void printDoubledVisits(){
        for (Voter v: voterVisitCounts.keySet()) {
            int count = voterVisitCounts.get(v);
            if(count > 1){
                System.out.println(v.toString() + " - " + count);
            }
        }
    }

//    public void printVisitTimes(){
//        for (int st: visitTimes.keySet()){
//            System.out.println("station: " + st + ", visit time: " + visitTimes.get(st));
//        }
//    }
//    public void fixWorkTimes()
//    {
//        for (int st: stations) {
//            WorkTime workTime = voteStationWorkTimes.get(st);
//            if(workTime == null)
//            {
//                workTime = new WorkTime();
//                voteStationWorkTimes.put(st, workTime);
//            }
////            workTime.addVisitTime(time.getTime());
//        }
//    }
    public void printWorkingTimes(){
        for (int st: stations){
            System.out.println("station: " + st + ", visit time: " + voteStationWorkTimes.get(st));
        }
    }
}

