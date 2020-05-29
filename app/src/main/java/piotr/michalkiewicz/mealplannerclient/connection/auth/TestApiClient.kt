package piotr.michalkiewicz.mealplannerclient.connection.auth

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.BASIC_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TestApiClient {

    private lateinit var testApiService: TestApiService

    private val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

    public fun getApiService(context: Context): TestApiService {

        if (!::testApiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BASIC_URL)
                    .client(okhttpClient(context))
                    .build()

            testApiService = retrofit.create(TestApiService::class.java)
        }
        return testApiService
    }

    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .authenticator(AuthInterceptor(context))
                .build()
    }
}