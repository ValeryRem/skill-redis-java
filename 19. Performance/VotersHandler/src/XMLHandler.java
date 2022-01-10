import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {
    // Создаем стартовую строку-загулшку:
    private final Voter voter = new Voter("Заглушка", "1900.12.12", 999, "0000.00.00 00:00:00");
    private boolean isNewVoter = true;
//    private StringBuilder insertQuery = new StringBuilder();
//    private int step;

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
//        String query = DBConnection.getQuery();
        if (qName.equals("voter")) {
//            DBConnection.setQuery(query + (insertQuery.append((insertQuery.length() == 0 ? "" : ",") +
//                    "('" + voter.getName() + "', '" + voter.getBirthDay() + "', " + voter.getStation() + ", '" +
//                    voter.getVisitTime()+ "')")).toString());
            try {
                setToDB(voter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            isNewVoter = true;
        }
//        System.out.println(step++);///////////
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
        String query = "INSERT INTO voters(name, birthDate, station, visitTime) VALUES('" +
                voter.getName() + "', '" + voter.getBirthDay() + "', " + voter.getStation() +
                ", '" + voter.getVisitTime() + "')";
        DBConnection.getConnection().createStatement().execute(query);
    }
}
