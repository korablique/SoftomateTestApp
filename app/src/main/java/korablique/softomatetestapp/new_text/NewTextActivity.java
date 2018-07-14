package korablique.softomatetestapp.new_text;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;

import korablique.softomatetestapp.BaseActivity;
import korablique.softomatetestapp.R;
import korablique.softomatetestapp.database.AppDatabase;
import korablique.softomatetestapp.database.BackgroundThreadExecutor;
import korablique.softomatetestapp.database.DatabaseHolder;
import korablique.softomatetestapp.database.HistoryEntity;
import korablique.softomatetestapp.language_identification.LanguageIdentificator;

public class NewTextActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_text);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            EditText newText = findViewById(R.id.new_text);
            String text = newText.getText().toString().trim();
            if (text.isEmpty()) {
                IdentificationLangDialog.showDialogWithError(getSupportFragmentManager(), getString(R.string.type_text_error));
                return;
            }

            LanguageIdentificator langIdentificator = new LanguageIdentificator(this);
            langIdentificator.identifyLanguage(text, language -> {
                IdentificationLangDialog.showDialog(getSupportFragmentManager(), language);
                writeToDb(text, language);
            }, errorMessage -> {
                IdentificationLangDialog.showDialogWithError(getSupportFragmentManager(), errorMessage);
            });
        });
    }

    private void writeToDb(String text, String language) {
        DatabaseHolder databaseHolder = DatabaseHolder.getInstance();
        AppDatabase db = databaseHolder.getDatabase();

        HistoryEntity historyEntity = new HistoryEntity(text, language);
        BackgroundThreadExecutor executor = BackgroundThreadExecutor.getInstance();
        executor.execute(() -> {
            db.historyDao().insert(historyEntity);
        });
    }
}
