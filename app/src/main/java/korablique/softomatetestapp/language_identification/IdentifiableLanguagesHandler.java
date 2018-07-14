package korablique.softomatetestapp.language_identification;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import korablique.softomatetestapp.SoftomateTestAppApplication;
import korablique.softomatetestapp.language_identification.retrofit.GetIdentifiableLanguagesResponse;
import korablique.softomatetestapp.language_identification.retrofit.IdentifiableLanguage;
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
    private static IdentifiableLanguagesHandler instance = new IdentifiableLanguagesHandler();
    private List<IdentifiableLanguage> identifiableLanguages = new ArrayList<>();

    private IdentifiableLanguagesHandler() {
        // preventive loading
        performRequest((unused) -> {}, (unused) -> {});
    }

    public static IdentifiableLanguagesHandler getInstance() {
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
        SoftomateTestAppApplication.getWatsonApi().getIdentifiableLanguages(credentials, VERSION)
                .enqueue(new Callback<GetIdentifiableLanguagesResponse>() {
                    @Override
                    public void onResponse(
                            Call<GetIdentifiableLanguagesResponse> call,
                            Response<GetIdentifiableLanguagesResponse> response) {
                        if (response.body() != null) {
                            identifiableLanguages.addAll(response.body().getLanguages());
                            successCallback.onResult(identifiableLanguages);
                        } else {
                            failCallback.onFailure(new IllegalStateException("Empty response: " + response.message()));
                        }
                    }
                    @Override
                    public void onFailure(Call<GetIdentifiableLanguagesResponse> call, Throwable t) {
                        Log.e(IdentifiableLanguagesHandler.class.getName(), t.getMessage(), t);
                        failCallback.onFailure(t);
                    }
                });
    }
}
