package tools.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by sircodesalot on 14-2-26.
 */
public class ScatterTask<T> {
    private List<Future<T>> tasks = new ArrayList<>();

    public ScatterTask() {
    }

    public void add(Supplier<T> task) {
        this.tasks.add(Future.promise(task));
    }

    public void process() {
        boolean tasksStillRunning = true;

        while (tasksStillRunning) {
            tasksStillRunning = false;
            for (Future<T> task : tasks) {
                if (task.isComplete() == false) tasksStillRunning = true;
            }
        }
    }
}
