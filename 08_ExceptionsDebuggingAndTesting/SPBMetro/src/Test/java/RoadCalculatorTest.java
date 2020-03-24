/*
Написать тесты на все методы класса RouteCalculator в проекте SPBMetro.
С помощью тестов и отладки исправить все ошибки, которые Вам удастся найти в проекте SPBMetro.
*/

import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.*;
import java.util.stream.Collectors;

public class RoadCalculatorTest extends TestCase {
//    HashMap<Integer, Line> number2line;
//    TreeSet<Station> stations;
//    TreeMap<Station, TreeSet<Station>> connections;
    StationIndex stationIndex = new StationIndex();
    RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
    List<Station> rout;
    Line line1 = new Line(1, "1");
    Line line2 = new Line(2, "2");
    Line line3 = new Line(3, "3");

    @Override
    protected void setUp() throws Exception {
        rout = new ArrayList<>();

        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);

        Station A = new Station("A", line1);
        Station B = new Station("B", line1);
        Station C = new Station("C", line2);
        Station D = new Station("D", line2);
        Station E = new Station("E", line3);
        Station F = new Station("F", line3);

        stationIndex.addStation(A);
        stationIndex.addStation(B);
        stationIndex.addStation(C);
        stationIndex.addStation(D);
        stationIndex.addStation(E);
        stationIndex.addStation(F);

        rout.add(A);
        rout.add(B);
        rout.add(C);
        rout.add(D);
        rout.add(E);
        rout.add(F);

        stationIndex.addConnection(rout);
    }

    public void testCalculateDuration() {
        rout = routeCalculator.getShortestRoute(stationIndex.getStation("B", 1),
                stationIndex.getStation("E", 3));
        double actual = RouteCalculator.calculateDuration(rout);
        double expected = 14.5;
        assertEquals(expected, actual);
    }

    public void testGetRouteWithOneConnection() throws Exception{
//        setUp();
        List<Station> listExpected = routeCalculator.getRouteWithOneConnection(stationIndex.getStation("A"),
                stationIndex.getStation("D"));
        List<Station> listActual = new ArrayList<>();
        listActual.add(rout.get(0));
        listActual.add(rout.get(1));
        listActual.add(rout.get(2));
        listActual.add(rout.get(3));
        assertEquals(listExpected, listActual);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
