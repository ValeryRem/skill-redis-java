import java.text.SimpleDateFormat;
import java.util.Date;

public class Voter
{
    private String name;
    private String birthDay;
    private String time;
    private Integer station;


    public Voter(String name, String birthDay, String time, Integer station) {
        this.name = name;
        this.birthDay = birthDay;
        this.time = time;
        this.station = station;
    }

    @Override
    public boolean equals(Object obj)
    {
        Voter voter = (Voter) obj;
        return name.equals(voter.name) && birthDay.equals(voter.birthDay);// && time.equals(voter.getTime());
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

//    public String toString()
//    {
//        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
//        return name + " (" + dayFormat.format(birthDay) + ")";
//    }

    public String getName()
    {
        return name;
    }

    public String getBirthDay()
    {
        return birthDay;
    }

    public String getTime() {
        return time;
    }

    public Integer getStation() {
        return station;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStation(Integer station) {
        this.station = station;
    }
}
