import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.*;

public class Loader {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newScheduledThreadPool(4);
        for (int i = 0; i < 10; i++) {
            Task task = new Task("task " + i);
            executor.execute(task);
        }
        executor.shutdown();
    }

}
