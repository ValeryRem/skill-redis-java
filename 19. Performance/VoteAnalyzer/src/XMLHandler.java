import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

public class XMLHandler extends DefaultHandler  {//SAXParserFactory {
    private Voter voter;// = new Voter();

    private final SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private final  SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private final HashMap<Voter, Integer> voterVisitCounts;
    private final WorkTime workTime = new WorkTime();
//    private final HashSet<Integer> stations = new HashSet<>();
    private final HashSet<Visit> visits = new HashSet<>();

    public XMLHandler () {
        voterVisitCounts = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        TimePeriod timePeriod;
        try {
            if (qName.equals("voter") && voter == null) {
                Date birthday = birthdayFormat.parse(attributes.getValue("birthDay"));
                voter = new Voter(attributes.getValue("name"), birthday);
            } else if (qName.equals("visit") && voter !=null) {

                int count = voterVisitCounts.getOrDefault(voter, 0);
                Date visitTime = visitDateFormat.parse(attributes.getValue("time"));
                int station = Integer.parseInt(attributes.getValue("station"));
                workTime.addVisitTime(visitTime.getTime());
//                stations.add(station);
                processVisits(station, visitTime);
                voterVisitCounts.put(voter, count + 1);
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
        List<Visit> sortedList = visits.stream()
                .sorted(Comparator.comparing(Visit::getStation)) //comparator - how you want to sort it
                .collect(Collectors.toList());
        for (Visit v: sortedList){
            System.out.println("Station " + v.getStation() + "; visit times: " +
                    visitDateFormat.format(new Date(v.getTimePeriod().getFrom())) + " - " +
                    visitDateFormat.format(new Date(v.getTimePeriod().getTo())));
        }
    }

    public void processVisits (int station, Date visitTime){
        boolean isDatePresent = false;
        for (Visit v: visits) {
            long ONE_DAY_MILLIS = 86_400_000;
            if(station == v.getStation() &&
                    visitTime.getTime()/ ONE_DAY_MILLIS == v.getTimePeriod().getTo()/ ONE_DAY_MILLIS) {
                v.getTimePeriod().appendTime(visitTime.getTime());
                isDatePresent = true;
                break;
            }
        }
        if(!isDatePresent){
            visits.add(new Visit(station, new TimePeriod(visitTime.getTime(), visitTime.getTime())));
        }
    }
}

