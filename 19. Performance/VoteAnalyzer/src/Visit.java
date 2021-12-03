public class Visit {
    private int station;
    private TimePeriod timePeriod;

    public Visit(int station, TimePeriod timePeriod) {
        this.station = station;
        this.timePeriod = timePeriod;
    }

    public Visit() {
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }
}
