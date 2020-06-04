package piotr.michalkiewicz.mealplannerclient.connection.auth

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import piotr.michalkiewicz.mealplannerclient.connection.ApiInterface
import piotr.michalkiewicz.mealplannerclient.connection.auth.interceptor.AuthInterceptor
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.BASIC_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {

    private lateinit var testApiService: TestApiService
    private lateinit var testApiSecond: TestApiSecond
    private lateinit var apiInterface: ApiInterface

    fun getApiInterface(context: Context): ApiInterface {
        if (!::apiInterface.isInitialized) {
            val retrofit = retrofitBuilder(context)
            apiInterface = retrofit.create(ApiInterface::class.java)
        }
        return apiInterface
    }

    fun getTestApiService(context: Context): TestApiService {
        if (!::testApiService.isInitialized) {
            val retrofit = retrofitBuilder(context)
            testApiService = retrofit.create(TestApiService::class.java)
        }
        return testApiService
    }

    fun getTestApiServiceSecond(context: Context): TestApiSecond {
        if (!::testApiSecond.isInitialized) {
            val retrofit = retrofitBuilder(context)
            testApiSecond = retrofit.create(TestApiSecond::class.java)
        }
        return testApiSecond
    }

    private fun retrofitBuilder(context: Context): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASIC_URL)
                .client(okHttpClient(context))
                .build()
    }

    private val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

    private fun okHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .authenticator(AuthInterceptor(context))
                .build()
    }
}