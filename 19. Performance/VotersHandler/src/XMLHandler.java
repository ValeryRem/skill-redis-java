import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.text.Format;

public class XMLHandler extends DefaultHandler {
    private Voter voter = new Voter("Заглушка Заглот",  "1900.12.12", 999, "2000.00.00 00:00:00");
    private boolean isNewElement = true;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if (qName.equals("voter") && isNewElement) {
//                String name = Format.class.getField(attributes.getValue("name")).getName();
//                String name = convertToUTF8(attributes.getValue("name"));
                String name = attributes.getValue("name");

                String birthDay = attributes.getValue("birthDay");
                voter.setName(name);
                voter.setBirthDay(birthDay);
                isNewElement = false;
            } else if (qName.equals("visit") && !isNewElement) {
                int station = Integer.parseInt(attributes.getValue("station"));
                String visitTime = attributes.getValue("time");
                voter.setStation(station);
                voter.setVisitTime(visitTime);
            }
            setToDB(voter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("voter")) {
            isNewElement = true;
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
        String sql = "SELECT id FROM voters WHERE 'name'='" + voter.getName() +
                "' AND 'birthDate'='" + voter.getBirthDay() +
                "' AND 'station'='" + voter.getStation() + "' AND 'visitTime'='" + voter.getVisitTime() + "'";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        if (rs == null) {
            DBConnection.getConnection().createStatement()
                    .execute("INSERT INTO 'voters'(name, birthDate, station, visitTime) VALUES('" +
                            voter.getName() + "', '" + voter.getBirthDay() + "', '" + voter.getStation() +
                            "', '" + voter.getVisitTime() + "')");
        } else {
            rs.close();
        }
    }

//    private String convertToUTF8 (String s) {
//        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(s);
//    return
//         new String(byteBuffer.array(), StandardCharsets.UTF_8);
//    }
}
