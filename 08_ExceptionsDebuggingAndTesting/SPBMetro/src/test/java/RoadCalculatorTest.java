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

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;

public class RoadCalculatorTest {
    StationIndex stationIndex = new StationIndex();
    RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
    List<Station> stationList = new ArrayList<>();

    @Before
    public void setUp(){
        Line line1 = new Line(1, "Line 1");
        Line line2 = new Line(2, "Line 2");
        Line line3 = new Line(3, "Line 3");
        Line line4 = new Line(4, "Line 4");

        Station A = new Station( "A", line1);
        Station B = new Station("B", line1);
        Station C = new Station("C", line1);
        Station D = new Station("D", line1);
        Station E = new Station("E", line1);
        Station F = new Station("F", line2);
        Station G = new Station("G", line2);
        Station H = new Station("H", line2);
        Station J = new Station("J", line3);
        Station K = new Station("K", line3);
        Station L = new Station("L", line3);
        Station M = new Station("M", line4);
        Station N = new Station("N", line4);

        line1.addStation(A);
        line1.addStation(B);
        line1.addStation(C);
        line1.addStation(D);
        line2.addStation(F);
        line2.addStation(G);
        line2.addStation(H);
        line3.addStation(J);
        line3.addStation(K);
        line3.addStation(L);
        line4.addStation(M);
        line4.addStation(N);

        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);
        stationIndex.addLine(line4);

        stationList = Arrays.asList(A, B, C, D, E, F, G, H, J, K, L, M, N);
        stationList.forEach(station ->  stationIndex.addStation(station));
        stationIndex.addConnection(Arrays.asList(B, F));
        stationIndex.addConnection(Arrays.asList(D, J));
        stationIndex.addConnection(Arrays.asList(K, M));
    }

    @Test
    public void testCalculateDuration() {
        stationList = makeRoute("BCDJK");
        double expected = RouteCalculator.calculateDuration(stationList);
        double actual = 11.0;
        assertEquals(expected, actual, 0.0);
    }

    @Test
    public void testGetRouteOnTheLine () {
        List<Station> actual = makeRoute("JKL");
        List<Station> expected =  routeCalculator.getRouteOnTheLine (stationIndex.getStation("J"), stationIndex.getStation("L"));
        assertEquals("Ожидается путь J-K-L", expected, actual);
    }

    @Test
    public void testGetRouteWithOneConnection() {
        List<Station> actual  = makeRoute("CDJKL");
        List<Station> expected = routeCalculator.getRouteWithOneConnection(stationIndex.getStation("C"), stationIndex.getStation("L"));
        assertEquals("Ожидается путь  C D J K L", expected, actual);
    }

    @Test
    public void testGetRouteWithTwoConnections () {
        List<Station> actual  = makeRoute("GFBCDJK");
        List<Station> expected = routeCalculator.getRouteWithTwoConnections(stationIndex.getStation("G"), stationIndex.getStation("K"));
        assertEquals("Ожидается путь  G F B C D J K", expected, actual);
    }

    @Test
    public void testGetRouteViaConnectedLine() {
        List<Station> actual  = makeRoute("ABCDJK");
        List<Station> expected =  routeCalculator.getRouteViaConnectedLine(stationIndex.getStation("A"), stationIndex.getStation("K"));
        assertEquals("Ожидается путь A B C D J K", expected, actual);
    }

    @Test
    public void testGetShortestRoute () {
        List<Station> actual = makeRoute("GFBCDJK");
        List<Station> expected =  routeCalculator.getShortestRoute (stationIndex.getStation("G"), stationIndex.getStation("K"));
        assertEquals("Ожидается путь G F B C D J K", expected, actual);
    }

    @After
    public void tearDown(){
        stationList = null;
    }

    private List<Station> makeRoute(String namesJoint) {
        List<Station> routeExpected = new ArrayList<>();
        for (int i = 0; i < namesJoint.length(); i++) {
            String name = String.valueOf(namesJoint.charAt(i));
            routeExpected.add(stationIndex.getStation(name));
        }
        return routeExpected;
    }

//    private Station getStation(String nameOfStation) {
//        return
//                stationList.stream()
//                        .filter(station -> station.getName().equals(nameOfStation)).collect(toList()).get(0);
//    }
}
