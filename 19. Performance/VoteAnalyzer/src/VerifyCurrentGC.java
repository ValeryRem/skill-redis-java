import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

public class VerifyCurrentGC {
    public void verifyGC(List<GarbageCollectorMXBean> gcBeans) {
        gcBeans.forEach(gc -> {
            System.out.printf("GC Name : %s%n", gc.getName());
            var poolNames = gc.getMemoryPoolNames();
            if (poolNames != null) {
                List.of(poolNames).forEach(pool ->
                        System.out.printf("Pool name %s%n", pool));
            } else {
                System.out.println("No memory pools for " + gc.getName());
            }
        });
    }
}
