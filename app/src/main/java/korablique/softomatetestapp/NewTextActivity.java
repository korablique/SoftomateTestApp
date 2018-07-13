package korablique.softomatetestapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import korablique.softomatetestapp.language_identification.IdentifiableLanguage;
import korablique.softomatetestapp.language_identification.IdentifiableLanguagesHandler;
import korablique.softomatetestapp.language_identification.LanguageIdentificator;

public class NewTextActivity extends BaseActivity {
    private List<IdentifiableLanguage> identifiableLanguages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_text);

        IdentifiableLanguagesHandler languagesHandler = IdentifiableLanguagesHandler.getInstance();
        languagesHandler.getIdentifiableLanguages(languages -> {
            identifiableLanguages.addAll(languages);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            EditText newText = findViewById(R.id.new_text);
            String text = newText.getText().toString();

            LanguageIdentificator langIdentificator = new LanguageIdentificator(this);
            langIdentificator.identifyLanguage(text, new LanguageIdentificator.LanguageIdentificationCallback() {
                @Override
                public void onResult(String language) {
                    IdentificationLangDialog.showDialog(getSupportFragmentManager(), language);
                }

                @Override
                public void onFailure(String errorMessage) {
                    IdentificationLangDialog.showDialogWithError(getSupportFragmentManager(), errorMessage);
                }
            });
        });
    }
}
