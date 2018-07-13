package korablique.softomatetestapp;


import android.app.Application;

import korablique.softomatetestapp.database.DatabaseHolder;
import korablique.softomatetestapp.language_identification.WatsonApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static korablique.softomatetestapp.language_identification.WatsonConstants.HOST;

public class SoftomateTestAppApplication extends Application {
    private Retrofit retrofit;
    private static WatsonApi watsonApi;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        watsonApi = retrofit.create(WatsonApi.class);

        DatabaseHolder databaseHolder = DatabaseHolder.getInstance();
        databaseHolder.init(this);
    }

    public static WatsonApi getApi() {
        return watsonApi;
    }
}
