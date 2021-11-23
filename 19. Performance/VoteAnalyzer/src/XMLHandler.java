import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class XMLHandler extends DefaultHandler {
    private Voter voter;// = new Voter();

    private static final SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private HashMap<Voter, Integer> voterCounts;
    public XMLHandler () {
        voterCounts = new HashMap<>();
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if (qName.equals("voter") && voter == null) {
                Date birthday = birthdayFormat.parse(attributes.getValue("birthDay"));
                voter = new Voter(attributes.getValue("name"), birthday);
//                System.out.println("started: " + qName);
            } else if (qName.equals("visit") && voter !=null) {
                int count = voterCounts.getOrDefault(voter, 0);
                voterCounts.put(voter, count + 1);
            }
        } catch (ParseException e) {
                e.printStackTrace();
            }
//        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        if(qName.equals("voter")) {
        voter = null;
        }
    }
//        super.endElement(uri, localName, qName);
//        System.out.println("ended: " + qName);

    public void printDoubledVisits(){
        for (Voter v: voterCounts.keySet()) {
            int count = voterCounts.get(v);
            if(count > 1){
                System.out.println(v.toString() + " - " + count);
            }

        }
    }
}

