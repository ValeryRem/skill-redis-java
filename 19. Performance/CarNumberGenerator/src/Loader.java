import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Loader {

    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<>();
        char[] letters = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
        // создаем пул из 12 задач, по числу букв
        for (char c: letters) {
            tasks.add(new Task(c));
        }
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        for (Task t: tasks) {
            executor.execute(t);
        }
        executor.shutdown();
    }
}
