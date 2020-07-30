package piotr.michalkiewicz.mealplannerclient.auth

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.AuthAuthenticator
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.AuthInterceptor
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Class build ready service with auth
 */

class ServiceGenerator {

    /*
    example how to add new api

     private lateinit var <variableName>: <class>

    fun getApiInterface(context: Context): <class> {
        if (!::<variableName>.isInitialized) {
            val retrofit = retrofitBuilder(context)
            <variableName> = retrofit.create(<class>::class.java)
        }
        return <variableName>
    }
     */

    private fun retrofitBuilder(): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient())
                .build()
    }

    private val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .authenticator(AuthAuthenticator())
                .build()
    }
}