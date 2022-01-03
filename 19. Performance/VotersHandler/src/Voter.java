import java.text.SimpleDateFormat;
import java.util.Date;

public class Voter
{
    private String name;
    private String birthDay;
    int station;
    String visitTime;


    public Voter(String name, String birthDay, int station, String visitTime) {
        this.name = name;
        this.birthDay = birthDay;
        this.station = station;
        this.visitTime = visitTime;
    }

    @Override
    public boolean equals(Object obj)
    {
        Voter voter = (Voter) obj;
        return name.equals(voter.name) && birthDay.equals(voter.birthDay);
    }

    @Override
    public int hashCode()
    {
        long code = name.hashCode() + birthDay.hashCode();
        while(code > Integer.MAX_VALUE) {
            code = code/10;
        }
        return (int) code;
    }

    public String toString()
    {
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        return name + " (" + dayFormat.format(birthDay) + ")";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }
}
