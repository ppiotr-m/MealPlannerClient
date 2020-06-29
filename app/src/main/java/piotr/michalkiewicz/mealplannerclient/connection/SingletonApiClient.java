package piotr.michalkiewicz.mealplannerclient.connection;


import android.content.Context;

import okhttp3.OkHttpClient;
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.AuthInterceptor;
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingletonApiClient {

    private static OkHttpClient httpClient = new OkHttpClient.Builder().build();

    private static Retrofit retrofit = (new Retrofit.Builder()
            .baseUrl(ConstantValues.getBASE_URL())
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())).build();

    public static <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}

