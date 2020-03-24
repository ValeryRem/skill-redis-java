/*
Написать тесты на все методы класса RouteCalculator в проекте SPBMetro.
С помощью тестов и отладки исправить все ошибки, которые Вам удастся найти в проекте SPBMetro.
 * Metro scheme that used in tests:
 * <pre>{@code
 *
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
                                   ↓
 *                              Station N
 *                              (Line 4)
 *
 * }</pre>
*/

import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.*;
import java.util.stream.Collectors;

public class RoadCalculatorTest extends TestCase {
    StationIndex stationIndex = new StationIndex();
    RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
    List<Station> rout;


    @Override
    protected void setUp(){
        rout = new ArrayList<>();

        Line line1 = new Line(1, "Line 1");
        Line line2 = new Line(2, "Line 2");
        Line line3 = new Line(3, "Line 3");
        Line line4 = new Line(4, "Line 4");

        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);
        stationIndex.addLine(line4);

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

        stationIndex.addStation(A);
        stationIndex.addStation(B);
        stationIndex.addStation(C);
        stationIndex.addStation(D);
        stationIndex.addStation(E);
        stationIndex.addStation(F);
        stationIndex.addStation(G);
        stationIndex.addStation(H);
        stationIndex.addStation(J);
        stationIndex.addStation(K);
        stationIndex.addStation(L);
        stationIndex.addStation(M);
        stationIndex.addStation(N);

        stationIndex.addConnection(makeRoute("ABCDEFGHJKLMN"));
    }

    public void testCalculateDuration() {
        rout = makeRoute("BCDJK");
        double actual = RouteCalculator.calculateDuration(rout);
        double expected = 11.0;
        assertEquals(expected, actual);
    }

    public void testGetRouteOnTheLine () {
        rout = makeRoute("ABCDEFGHJKLMN");
        List<Station> actual = makeRoute("ABCDE");
        List<Station> expected =  routeCalculator.getRouteOnTheLine (rout.get(0), rout.get(4));
        assertEquals(expected, actual);
    }

    public void testGetRouteWithOneConnection() {
        List<Station> actual  = makeRoute("ABFG");
        List<Station> expected = routeCalculator.getRouteWithOneConnection(rout.get(0), rout.get(6));
        assertEquals(expected,actual);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    private List<Station> makeRoute(String namesJoint) {
        List<Station> routeExpected = new ArrayList<>();
        for (int i = 0; i < namesJoint.length(); i++) {
            String name = String.valueOf(namesJoint.charAt(i));
            routeExpected.add(stationIndex.getStation(name));
        }
        return routeExpected;
    }
}
