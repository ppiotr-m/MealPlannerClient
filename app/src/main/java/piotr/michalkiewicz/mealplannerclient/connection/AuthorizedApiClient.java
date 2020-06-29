package piotr.michalkiewicz.mealplannerclient.connection;

import android.content.Context;

import okhttp3.OkHttpClient;
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.AuthInterceptor;
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthorizedApiClient {

    private Retrofit retrofit;

    public AuthorizedApiClient(Context context){
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(
                new AuthInterceptor(context)).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantValues.getBASE_URL())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
