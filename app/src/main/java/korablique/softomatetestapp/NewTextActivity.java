package korablique.softomatetestapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import korablique.softomatetestapp.language_idetification.IdentifiableLanguage;
import korablique.softomatetestapp.language_idetification.IdentifiableLanguagesHandler;
import korablique.softomatetestapp.language_idetification.IdentifyLanguageResponce;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static korablique.softomatetestapp.language_idetification.WatsonConstants.PASSWORD;
import static korablique.softomatetestapp.language_idetification.WatsonConstants.USERNAME;
import static korablique.softomatetestapp.language_idetification.WatsonConstants.VERSION;

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

            String credentials = Credentials.basic(USERNAME, PASSWORD);
            SoftomateTestAppApplication.getApi().identifyLanguage(credentials, text, VERSION)
                    .enqueue(new Callback<IdentifyLanguageResponce>() {
                @Override
                public void onResponse(Call<IdentifyLanguageResponce> call, Response<IdentifyLanguageResponce> response) {
                    if (response.body() != null) {
                        String language = response.body().getLanguages().get(0).getLanguage();
                        String languageName = null;
                        for (IdentifiableLanguage l : identifiableLanguages) {
                            if (l.getLanguage().equals(language)) {
                                languageName = l.getName();
                                break;
                            }
                        }
                        // if loading identifiable languages has failed
                        if (languageName == null) {
                            languageName = language;
                        }
                        IdentificationLangDialog.showDialog(getSupportFragmentManager(), languageName);
                    } else {
                        IdentificationLangDialog.showDialogWithError(getSupportFragmentManager(), response.message());
                    }
                }
                @Override
                public void onFailure(Call<IdentifyLanguageResponce> call, Throwable t) {
                    if (t instanceof UnknownHostException) {
                        IdentificationLangDialog.showDialogWithError(
                                getSupportFragmentManager(), getString(R.string.no_internet_connection));
                        return;
                    }
                    IdentificationLangDialog.showDialogWithError(getSupportFragmentManager(), t.getMessage());
                }
            });
        });
    }
}
