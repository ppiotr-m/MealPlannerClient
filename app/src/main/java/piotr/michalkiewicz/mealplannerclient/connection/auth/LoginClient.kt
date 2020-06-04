package piotr.michalkiewicz.mealplannerclient.connection.auth

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import piotr.michalkiewicz.mealplannerclient.connection.auth.interceptor.LoginInterceptor
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.BASIC_URL
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PASSWORD_GRANT_TYPE
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.REFRESH_TOKEN_GRANT_TYPE
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class LoginClient {

    private lateinit var myPreference: MyPreference

    private val loginInterceptor: LoginInterceptor = LoginInterceptor()
    private var loginClient: OkHttpClient = OkHttpClient().newBuilder().addInterceptor(loginInterceptor).build()

    private val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

    private val loginConnection by lazy {
        LoginClient().loginConnection()
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
        val loginEndpoint = loginConnection.login(username, password, PASSWORD_GRANT_TYPE)
        loginEndpoint.subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.i("Token expires in: ", result.expiresIn.toString())
                    myPreference.setToken(result)
                },
                        { error -> Log.i("Login error", error.toString()) })
    }

    fun refreshToken(context: Context) {
        initPreference(context)
        val refreshToken = myPreference.getRefreshToken()
        val refreshTokenEndpoint = loginConnection.refreshToken(REFRESH_TOKEN_GRANT_TYPE, refreshToken.toString())

        try {
            val response = refreshTokenEndpoint.execute()
            val token = response.body()
            token?.let { myPreference.setToken(it) }
            Log.i("Token refershed", token?.expiresIn.toString())
        }catch (e: Exception) {
            Log.i("RefreshToken exception", e.toString())
        }
    }

    private fun initPreference(context: Context) {
        if (!::myPreference.isInitialized) {
            myPreference = MyPreference(context)
        }
    }
}