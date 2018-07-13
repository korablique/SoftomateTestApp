package korablique.softomatetestapp.database;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundThreadExecutor {
    private static BackgroundThreadExecutor instance;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private BackgroundThreadExecutor() {}

    public static BackgroundThreadExecutor getInstance() {
        if (instance == null) {
            instance = new BackgroundThreadExecutor();
        }
        return instance;
    }

    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }
}
