package korablique.softomatetestapp.language_identification;


import android.content.Context;
import android.util.Log;

import java.net.UnknownHostException;
import java.util.List;

import korablique.softomatetestapp.R;
import korablique.softomatetestapp.SoftomateTestAppApplication;
import korablique.softomatetestapp.language_identification.retrofit.IdentifiableLanguage;
import korablique.softomatetestapp.language_identification.retrofit.IdentifyLanguageResponse;
import korablique.softomatetestapp.language_identification.retrofit.ResultLanguage;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static korablique.softomatetestapp.language_identification.WatsonConstants.PASSWORD;
import static korablique.softomatetestapp.language_identification.WatsonConstants.USERNAME;
import static korablique.softomatetestapp.language_identification.WatsonConstants.VERSION;

public class LanguageIdentificator {
    public interface SuccessCallback {
        void onResult(String language);
    }
    public interface FailCallback {
        void onFailure(String errorMessage);
    }
    public static final String TAG = LanguageIdentificator.class.getName();
    private Context context;

    public LanguageIdentificator(Context context) {
        this.context = context;
    }

    public void identifyLanguage(String text, SuccessCallback successCallback, FailCallback failCallback) {
        String credentials = Credentials.basic(USERNAME, PASSWORD);

        WatsonApi watson = SoftomateTestAppApplication.getWatsonApi();
        IdentifiableLanguagesHandler languagesHandler = IdentifiableLanguagesHandler.getInstance();
        languagesHandler.getIdentifiableLanguages(identifiableLanguages -> {
            watson.identifyLanguage(credentials, text, VERSION)
                    .enqueue(new CallbackImpl(identifiableLanguages, successCallback, failCallback));
        }, t -> {
            Log.e(TAG, "Could not get identifiable languages", t);
        });
    }

    private String getLanguageName(
            List<ResultLanguage> guessedResponse,
            List<IdentifiableLanguage> identifiableLanguages) {
        String language = guessedResponse.get(0).getLanguage();
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

        return languageName;
    }

    private class CallbackImpl implements Callback<IdentifyLanguageResponse> {
        private List<IdentifiableLanguage> identifiableLanguages;
        private SuccessCallback clientSuccessCallback;
        private FailCallback clientFailCallback;

        CallbackImpl(
                List<IdentifiableLanguage> identifiableLanguages,
                SuccessCallback clientSuccessCallback,
                FailCallback clientFailCallback) {
            this.identifiableLanguages = identifiableLanguages;
            this.clientSuccessCallback = clientSuccessCallback;
            this.clientFailCallback = clientFailCallback;
        }

        @Override
        public void onResponse(Call<IdentifyLanguageResponse> call, Response<IdentifyLanguageResponse> response) {
            if (response.body() != null) {
                List<ResultLanguage> guessedLanguages = response.body().getLanguages();
                String languageName = getLanguageName(guessedLanguages, identifiableLanguages);
                clientSuccessCallback.onResult(languageName);
            } else {
                clientFailCallback.onFailure(response.message());
            }
        }

        @Override
        public void onFailure(Call<IdentifyLanguageResponse> call, Throwable t) {
            if (t instanceof UnknownHostException) {
                clientFailCallback.onFailure(context.getString(R.string.no_internet_connection));
                return;
            }
            clientFailCallback.onFailure(t.getMessage());
        }
    }
}
