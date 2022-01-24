import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;

public class XMLHandler extends DefaultHandler {
    // Создаем стартовую строку-загулшку:
    private final Voter voter = new Voter("Заглушка", "1900.12.12", 999, "0000.00.00 00:00:00");
    private boolean isNewVoter = true;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if (qName.equals("voter") && isNewVoter) {
                String name = attributes.getValue("name");
                String birthDay = attributes.getValue("birthDay");
                voter.setName(name);
                voter.setBirthDay(birthDay);
                isNewVoter = false;
            } else if (qName.equals("visit") && !isNewVoter) {
                int station = Integer.parseInt(attributes.getValue("station"));
                String visitTime = attributes.getValue("time");
                voter.setStation(station);
                voter.setVisitTime(visitTime);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("voter")) {
            try {
                DBConnection.setVoterDB(voter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            isNewVoter = true;
        }
    }

    @Override
    public void startDocument() {
        System.out.println("Начало разбора документа!");
    }

    @Override
    public void endDocument() {
        try {
            DBConnection.executeMultiInsert();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        System.out.println("Разбор документа завершен!");
    }
}
