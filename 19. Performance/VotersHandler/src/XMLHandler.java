import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.ResultSet;

public class XMLHandler extends DefaultHandler {
    private Voter voter;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        String name = "XXX";
        String birthDay = "1900.12.12";
        int station = 999;
        String visitTime = "2000.00.00 00:00:00";
        try {
            if (qName.equals("voter") && voter == null) {
                name = attributes.getValue("name");
                birthDay = attributes.getValue("birthDay");

            } else if (qName.equals("visit") && voter != null) {
                station = Integer.parseInt(attributes.getValue("station"));
                visitTime = attributes.getValue("time");
            }
            voter = new Voter(name, birthDay, station, visitTime);
            setToDB(voter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("voter")) {
            voter = null;
        }
    }

    @Override
    public void startDocument() {
        System.out.println("Начало разбора документа!");
    }

    @Override
    public void endDocument() {
        System.out.println("Разбор документа завершен!");
    }

    public void setToDB(Voter voter) throws Exception {
        String sql = "SELECT id FROM voters WHERE name='" + voter.getName() + "' AND birthDate='" + voter.getBirthDay() +
                "' AND station='" + voter.getStation() + "' AND visitTime='" + voter.getVisitTime() + "'";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        if (rs == null) {
            DBConnection.getConnection().createStatement()
                    .execute("INSERT INTO voters(name, birthDate, station, visitTime) VALUES('" +
                            voter.getName() + "', '" + voter.getBirthDay() + "', '" + voter.getStation() +
                            "', '" + voter.getVisitTime() + "')");
        } else {
            rs.close();
        }
    }

}
