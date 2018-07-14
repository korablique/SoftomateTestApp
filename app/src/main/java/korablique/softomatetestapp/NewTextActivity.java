package korablique.softomatetestapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;

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
