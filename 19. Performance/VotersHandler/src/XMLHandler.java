import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {
    private Voter voter;// = new Voter("Лёлик Лямин", "2000.12.23", "2020.10.08 14:23:45", 0);
    public XMLHandler(){ }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        voter = new Voter("Лёлик Лямин", "2000.12.23", "2020.10.08 14:23:45", 0);
        DBConnection dbConnection = new DBConnection();
        try {
            if (qName.equals("voter")) {
                String name = attributes.getValue("name");
                String birthDay = attributes.getValue("birthDay");
                voter.setName(name);
                voter.setBirthDay(birthDay);
            } else if (qName.equals("visit")) {
                String visitTime = attributes.getValue("time");
                int station = Integer.parseInt(attributes.getValue("station"));
                voter.setTime(visitTime);
                voter.setStation(station);
            }
            dbConnection.setVoterToDB(voter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        if(qName.equals("voter")) {
        voter = null;
        }
    }

    @Override
    public void startDocument()
    {
        System.out.println("Начало разбора документа!");
    }

    @Override
    public void endDocument()
    {
        System.out.println("Разбор документа завершен!");
    }
}

