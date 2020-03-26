/**
* Написать тесты на все методы класса RouteCalculator в проекте SPBMetro.
* С помощью тестов и отладки исправить все ошибки, которые Вам удастся найти в проекте SPBMetro.
 *  Metro scheme that used in tests:
 * <pre>{@code
 *       (Line 1)
 *      Station A
 *          ↓
 *      Station B / Station F → Station G → Station H (Line 2)
 *          ↓
 *      Station C
 *          ↓
 *      Station D / Station J → Station K → Station L (Line 3)
 *          ↓                      /
 *      Station E               Station M
 *                                  ↓
 *                              Station N
 *                              (Line 4)
 * }</pre>
*/

import core.Line;
import core.Station;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;

public class RoadCalculatorTest {
    StationIndex stationIndex = new StationIndex();
    RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
    List<Station> stationList = new ArrayList<>();
    private static final double ROUTE_TIME = 2.5;
    private static final double CONNECTION_TIME = 3.5;
    private static final double DELTA = 0.00001;

    @Before
    public void setUp(){
        Line line1 = new Line(1, "Line 1");
        Line line2 = new Line(2, "Line 2");
        Line line3 = new Line(3, "Line 3");
        Line line4 = new Line(4, "Line 4");

        Station A1 = new Station( "A1", line1);
        Station B1 = new Station("B1", line1);
        Station C1 = new Station("C1", line1);
        Station D1 = new Station("D1", line1);
        Station E1 = new Station("E1", line1);
        Station F2 = new Station("F2", line2);
        Station G2 = new Station("G2", line2);
        Station H2 = new Station("H2", line2);
        Station J3 = new Station("J3", line3);
        Station K3 = new Station("K3", line3);
        Station L3 = new Station("L3", line3);
        Station M4 = new Station("M4", line4);
        Station N4 = new Station("N4", line4);

        line1.addStation(A1);
        line1.addStation(B1);
        line1.addStation(C1);
        line1.addStation(D1);
        line2.addStation(F2);
        line2.addStation(G2);
        line2.addStation(H2);
        line3.addStation(J3);
        line3.addStation(K3);
        line3.addStation(L3);
        line4.addStation(M4);
        line4.addStation(N4);

        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);
        stationIndex.addLine(line4);

        stationList = Arrays.asList(A1, B1, C1, D1, E1, F2, G2, H2, J3, K3, L3, M4, N4);
        stationList.forEach(station ->  stationIndex.addStation(station));
        stationIndex.addConnection(Arrays.asList(B1, F2));
        stationIndex.addConnection(Arrays.asList(D1, J3));
        stationIndex.addConnection(Arrays.asList(K3, M4));
    }

    @Test
    public void testCalculateDuration() {
        stationList = makeRoute("B1-C1-D1-J3-K3");
        double expected = RouteCalculator.calculateDuration(stationList);
        double actual = ROUTE_TIME * 2 + CONNECTION_TIME + ROUTE_TIME;
        assertEquals(expected, actual, DELTA);
    }

    @Test
    public void testGetRouteOnTheLine () {
        List<Station> actual = makeRoute("J3-K3-L3");
        List<Station> expected =  routeCalculator.getRouteOnTheLine (stationIndex.getStation("J3"), stationIndex.getStation("L3"));
        assertEquals("Ожидается путь J3-K3-L3", expected, actual);
    }

    @Test
    public void testGetRouteWithOneConnection() {
        List<Station> actual  = makeRoute("C1-D1-J3-K3-L3");
        List<Station> expected = routeCalculator.getRouteWithOneConnection(stationIndex.getStation("C1"), stationIndex.getStation("L3"));
        assertEquals("Ожидается путь  C1-D1-J3-K3-L3", expected, actual);
    }

    @Test
    public void testGetRouteWithTwoConnections () {
        List<Station> actual  = makeRoute("G2-F2-B1-C1-D1-J3-K3");
        List<Station> expected = routeCalculator.getRouteWithTwoConnections(stationIndex.getStation("G2"), stationIndex.getStation("K3"));
        assertEquals("Ожидается путь  G2-F2-B1-C1-D1-J3-K3", expected, actual);
    }

    @Test
    public void testGetRouteViaConnectedLine() {
        List<Station> actual  = makeRoute("B1-C1-D1");
        List<Station> expected =  routeCalculator.getRouteViaConnectedLine(stationIndex.getStation("G2"), stationIndex.getStation("K3"));
        assertEquals("Ожидается путь B1-C1-D1", expected, actual);
    }

    @Test
    public void testGetShortestRoute () {
        List<Station> actual = makeRoute("G2-F2-B1-C1-D1-J3-K3");
        List<Station> expected =  routeCalculator.getShortestRoute (stationIndex.getStation("G2"), stationIndex.getStation("K3"));
        assertEquals("Ожидается путь G2-F2-B1-C1-D1-J3-K3", expected, actual);
    }

    @After
    public void tearDown(){
        stationList = null;
    }

    private List<Station> makeRoute(String namesJoint) {
        List<Station> list = new ArrayList<>();
        for (int i = 0; i < namesJoint.split("-").length; i++) {
            String name = namesJoint.split("-")[i];
            list.add(stationIndex.getStation(name));
        }
        return list;
    }

//    private Station getStation(String nameOfStation) {
//        return
//                stationList.stream()
//                        .filter(station -> station.getName().equals(nameOfStation)).collect(toList()).get(0);
//    }
}
