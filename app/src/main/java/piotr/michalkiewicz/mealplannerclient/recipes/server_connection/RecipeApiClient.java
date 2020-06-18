package piotr.michalkiewicz.mealplannerclient.recipes.server_connection;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeApiClient {
    private static final String BASE_URL = "https://api.github.com/";

    private static Retrofit.Builder builder
            = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient
            = new OkHttpClient.Builder();

    public static <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
