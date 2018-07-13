package korablique.softomatetestapp.database;


import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseHolder {
    private static DatabaseHolder instance;
    private static AppDatabase db;
    private Context context;

    private DatabaseHolder() {}

    public static synchronized DatabaseHolder getInstance() {
        if (instance == null) {
            instance = new DatabaseHolder();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    public AppDatabase getDatabase() {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "history-database").build();
        }
        return db;
    }
}
