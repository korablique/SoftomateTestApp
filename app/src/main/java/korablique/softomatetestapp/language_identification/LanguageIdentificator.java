package korablique.softomatetestapp.language_identification;


import android.content.Context;

import java.net.UnknownHostException;

import korablique.softomatetestapp.R;
import korablique.softomatetestapp.SoftomateTestAppApplication;
import korablique.softomatetestapp.database.AppDatabase;
import korablique.softomatetestapp.database.BackgroundThreadExecutor;
import korablique.softomatetestapp.database.DatabaseHolder;
import korablique.softomatetestapp.database.HistoryEntity;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static korablique.softomatetestapp.language_identification.WatsonConstants.PASSWORD;
import static korablique.softomatetestapp.language_identification.WatsonConstants.USERNAME;
import static korablique.softomatetestapp.language_identification.WatsonConstants.VERSION;

public class LanguageIdentificator {
    public interface LanguageIdentificationCallback {
        void onResult(String language);
        void onFailure(String errorMessage);
    }
    private Context context;
    private AppDatabase db;

    public LanguageIdentificator(Context context) {
        this.context = context;

        DatabaseHolder databaseHolder = DatabaseHolder.getInstance();
        db = databaseHolder.getDatabase();
    }

    public void identifyLanguage(String text, LanguageIdentificationCallback callback) {
        String credentials = Credentials.basic(USERNAME, PASSWORD);

        IdentifiableLanguagesHandler languagesHandler = IdentifiableLanguagesHandler.getInstance();
        languagesHandler.getIdentifiableLanguages(identifiableLanguages -> {
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

                                HistoryEntity historyEntity = new HistoryEntity(text, languageName);
                                BackgroundThreadExecutor executor = BackgroundThreadExecutor.getInstance();
                                executor.execute(() -> {
                                    db.historyDao().insert(historyEntity);
                                });

                                callback.onResult(languageName);
                            } else {
                                callback.onFailure(response.message());
                            }
                        }
                        @Override
                        public void onFailure(Call<IdentifyLanguageResponce> call, Throwable t) {
                            if (t instanceof UnknownHostException) {
                                callback.onFailure(context.getString(R.string.no_internet_connection));
                                return;
                            }
                            callback.onFailure(t.getMessage());
                        }
                    });
        });
    }
}
