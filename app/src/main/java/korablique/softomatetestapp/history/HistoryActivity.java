package korablique.softomatetestapp.history;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import korablique.softomatetestapp.BaseActivity;
import korablique.softomatetestapp.R;
import korablique.softomatetestapp.database.AppDatabase;
import korablique.softomatetestapp.database.BackgroundThreadExecutor;
import korablique.softomatetestapp.database.DatabaseHolder;
import korablique.softomatetestapp.database.HistoryDao;
import korablique.softomatetestapp.database.HistoryEntity;

public class HistoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        HistoryAdapter adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);

        DatabaseHolder databaseHolder = DatabaseHolder.getInstance();
        AppDatabase db = databaseHolder.getDatabase();
        HistoryDao historyDao = db.historyDao();

        BackgroundThreadExecutor executor = BackgroundThreadExecutor.getInstance();
        executor.execute(() -> {
            List<HistoryEntity> historyEntityList = historyDao.getAll();
            runOnUiThread(() -> {
                adapter.addItems(historyEntityList);
            });
        });
    }
}
