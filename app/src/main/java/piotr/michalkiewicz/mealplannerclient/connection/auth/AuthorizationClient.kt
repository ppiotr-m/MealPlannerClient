package piotr.michalkiewicz.mealplannerclient.connection.auth

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import piotr.michalkiewicz.mealplannerclient.utils.BASIC_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AuthorizationClient() {

    private lateinit var myPreference: MyPreference

    private val loginInterceptor: LoginInterceptor = LoginInterceptor()
    private var loginClient: OkHttpClient = OkHttpClient().newBuilder().addInterceptor(loginInterceptor).build()

    private val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

    private val loginConnection by lazy {
        AuthorizationClient().loginConnection()
    }

    private fun loginConnection(): OAuth2 {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(loginClient)
                .baseUrl(BASIC_URL)
                .build()

        return retrofit.create(OAuth2::class.java)
    }

    fun login(context: Context, username: String, password: String) {
        initPreference(context)
        val loginEndpoint = loginConnection.login(username, password, "password")
        loginEndpoint.subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.i("Token ok", result.expiresIn.toString())
                    myPreference.setToken(result)
                },
                        { error -> Log.i("Token nie ok", error.toString()) })
    }

    private fun initPreference(context: Context){
        if(!::myPreference.isInitialized){
            myPreference = MyPreference(context)
        }
    }
}