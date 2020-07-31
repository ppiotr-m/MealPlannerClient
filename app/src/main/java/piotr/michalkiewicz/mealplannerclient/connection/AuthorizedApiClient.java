package piotr.michalkiewicz.mealplannerclient.connection;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import okhttp3.OkHttpClient;
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.AuthInterceptor;
import piotr.michalkiewicz.mealplannerclient.recipes.model.conversion.BinaryToBitmapConverter;
import piotr.michalkiewicz.mealplannerclient.recipes.model.conversion.CustomGsonConverterCreator;
import piotr.michalkiewicz.mealplannerclient.support.Constants;
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthorizedApiClient {

    private Retrofit retrofit;

    public AuthorizedApiClient(Context context){
        if(context==null) Log.d(Constants.TAG, "Null context at AuthorizedApiClient constructor");
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(
                new AuthInterceptor(context)).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantValues.getBASE_URL())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
      //          .addConverterFactory(CustomGsonConverterCreator
      //                  .createGsonConverter(new TypeToken<byte[]>(){}.getType(),
      //                          new BinaryToBitmapConverter()))
                .build();
    }

    public <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
