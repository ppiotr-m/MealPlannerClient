package piotr.michalkiewicz.mealplannerclient.auth

import android.graphics.Bitmap
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import piotr.michalkiewicz.mealplannerclient.auth.authenticator.AuthAuthenticator
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.AuthInterceptor
import piotr.michalkiewicz.mealplannerclient.recipes.model.conversion.BinaryToBitmapConverter
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Class build ready service with auth
 */

abstract class AuthServiceGenerator {

    protected fun retrofitBuilder(): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .client(okHttpClient())
            .build()
    }

    protected fun retrofitBuilder(url: String): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(url)
            .client(okHttpClient())
            .build()
    }

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Bitmap::class.java, BinaryToBitmapConverter())
        .setLenient()
        .create()

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .authenticator(AuthAuthenticator())
            .build()
    }
}