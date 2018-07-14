package korablique.softomatetestapp.language_identification;

import korablique.softomatetestapp.language_identification.retrofit.GetIdentifiableLanguagesResponse;
import korablique.softomatetestapp.language_identification.retrofit.IdentifyLanguageResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.Query;

public interface WatsonApi {
    @POST("/language-translator/api/v3/identify")
    @Headers("Content-Type: text/plain")
    Call<IdentifyLanguageResponse> identifyLanguage(
            @Header("Authorization") String credentials,
            @Body String text,
            @Query("version") String version);

    @GET("/language-translator/api/v3/identifiable_languages")
    Call<GetIdentifiableLanguagesResponse> getIdentifiableLanguages(
            @Header("Authorization") String credentials,
            @Query("version") String version);
}
