package korablique.softomatetestapp;


import android.support.annotation.NonNull;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import korablique.softomatetestapp.language_identification.retrofit.IdentifyLanguageResponse;
import korablique.softomatetestapp.language_identification.retrofit.ResultLanguage;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static korablique.softomatetestapp.language_identification.WatsonConstants.PASSWORD;
import static korablique.softomatetestapp.language_identification.WatsonConstants.USERNAME;
import static korablique.softomatetestapp.language_identification.WatsonConstants.VERSION;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class WatsonApiTest {
    @Test
    public void getDataTest() throws InterruptedException {
        final CountDownLatch mutex1 = new CountDownLatch(1);
        final List<ResultLanguage> resultLanguages = new ArrayList<>();
        String credentials = Credentials.basic(USERNAME, PASSWORD);
        String englishText = "Language translator translates text from one language to another";
        SoftomateTestAppApplication.getWatsonApi().identifyLanguage(
                credentials, englishText, VERSION).enqueue(new Callback<IdentifyLanguageResponse>() {
            @Override
            public void onResponse(@NonNull Call<IdentifyLanguageResponse> call, @NonNull Response<IdentifyLanguageResponse> response) {
                resultLanguages.addAll(response.body().getLanguages());
                mutex1.countDown();
            }
            @Override
            public void onFailure(@NonNull Call<IdentifyLanguageResponse> call, @NonNull Throwable t) {
                mutex1.countDown();
            }
        });
        mutex1.await();
        Assert.assertFalse(resultLanguages.isEmpty());
        Assert.assertEquals("en", resultLanguages.get(0).getLanguage());

        CountDownLatch mutex2 = new CountDownLatch(1);
        resultLanguages.clear();
        String russianText = "Переводчик переводит текст с одного языка на другой";
        SoftomateTestAppApplication.getWatsonApi().identifyLanguage(
                credentials, russianText, VERSION).enqueue(new Callback<IdentifyLanguageResponse>() {
            @Override
            public void onResponse(@NonNull Call<IdentifyLanguageResponse> call, @NonNull Response<IdentifyLanguageResponse> response) {
                resultLanguages.addAll(response.body().getLanguages());
                mutex2.countDown();
            }
            @Override
            public void onFailure(@NonNull Call<IdentifyLanguageResponse> call, @NonNull Throwable t) {
                mutex2.countDown();
            }
        });
        mutex2.await();
        Assert.assertFalse(resultLanguages.isEmpty());
        Assert.assertEquals("ru", resultLanguages.get(0).getLanguage());
    }
}