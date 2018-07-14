package korablique.softomatetestapp.language_identification;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import korablique.softomatetestapp.SoftomateTestAppApplication;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static korablique.softomatetestapp.language_identification.WatsonConstants.PASSWORD;
import static korablique.softomatetestapp.language_identification.WatsonConstants.USERNAME;
import static korablique.softomatetestapp.language_identification.WatsonConstants.VERSION;

public class IdentifiableLanguagesHandler {
    public interface SuccessCallback {
        void onResult(List<IdentifiableLanguage> languages);
    }
    public interface FailCallback {
        void onFailure(Throwable t);
    }
    private static IdentifiableLanguagesHandler instance;
    private List<IdentifiableLanguage> identifiableLanguages = new ArrayList<>();

    private IdentifiableLanguagesHandler() {

    }

    public static synchronized IdentifiableLanguagesHandler getInstance() {
        if (instance == null) {
            instance = new IdentifiableLanguagesHandler();
        }
        return instance;
    }

    public void getIdentifiableLanguages(SuccessCallback successCallback, FailCallback failCallback) {
        if (!identifiableLanguages.isEmpty()) {
            successCallback.onResult(identifiableLanguages);
        } else {
            performRequest(successCallback, failCallback);
        }
    }

    private void performRequest(SuccessCallback successCallback, FailCallback failCallback) {
        String credentials = Credentials.basic(USERNAME, PASSWORD);
        SoftomateTestAppApplication.getApi().getIdentifiableLanguages(credentials, VERSION)
                .enqueue(new Callback<GetIdentifiableLanguagesResponce>() {
                    @Override
                    public void onResponse(
                            Call<GetIdentifiableLanguagesResponce> call,
                            Response<GetIdentifiableLanguagesResponce> response) {
                        if (response.body() != null) {
                            identifiableLanguages.addAll(response.body().getLanguages());
                            successCallback.onResult(identifiableLanguages);
                        } else {
                            failCallback.onFailure(new IllegalStateException("Empty response: " + response.message()));
                        }
                    }
                    @Override
                    public void onFailure(Call<GetIdentifiableLanguagesResponce> call, Throwable t) {
                        Log.e(IdentifiableLanguagesHandler.class.getName(), t.getMessage(), t);
                        failCallback.onFailure(t);
                    }
                });
    }
}
