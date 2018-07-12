package korablique.softomatetestapp.language_idetification;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import korablique.softomatetestapp.SoftomateTestAppApplication;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static korablique.softomatetestapp.language_idetification.WatsonConstants.PASSWORD;
import static korablique.softomatetestapp.language_idetification.WatsonConstants.USERNAME;
import static korablique.softomatetestapp.language_idetification.WatsonConstants.VERSION;

public class IdentifiableLanguagesHandler {
    public interface GetIdentifiableLanguagesCallback {
        void onResult(List<IdentifiableLanguage> languages);
    }
    private static IdentifiableLanguagesHandler instance;
    private List<IdentifiableLanguage> identifiableLanguages = new ArrayList<>();
    private List<GetIdentifiableLanguagesCallback> callbacks = new ArrayList<>();

    private IdentifiableLanguagesHandler() {
        String credentials = Credentials.basic(USERNAME, PASSWORD);
        SoftomateTestAppApplication.getApi().getIdentifiableLanguages(credentials, VERSION)
                .enqueue(new Callback<GetIdentifiableLanguagesResponce>() {
                    @Override
                    public void onResponse(Call<GetIdentifiableLanguagesResponce> call, Response<GetIdentifiableLanguagesResponce> response) {
                        if (response.body() != null) {
                            identifiableLanguages.addAll(response.body().getLanguages());
                            for (GetIdentifiableLanguagesCallback callback : callbacks) {
                                callback.onResult(identifiableLanguages);
                            }
                            callbacks.clear();
                        }
                    }
                    @Override
                    public void onFailure(Call<GetIdentifiableLanguagesResponce> call, Throwable t) {
                        Log.e(IdentifiableLanguagesHandler.class.getName(), t.getMessage(), t);
                    }
                });
    }

    public static synchronized IdentifiableLanguagesHandler getInstance() {
        if (instance == null) {
            instance = new IdentifiableLanguagesHandler();
        }
        return instance;
    }

    public void getIdentifiableLanguages(GetIdentifiableLanguagesCallback callback) {
        if (!identifiableLanguages.isEmpty()) {
            callback.onResult(identifiableLanguages);
        } else {
            callbacks.add(callback);
        }
    }
}
