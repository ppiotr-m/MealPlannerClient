package piotr.michalkiewicz.mealplannerclient.auth

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.LoginInterceptor
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.BASE_URL
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.GRANT_TYPE
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PASSWORD_GRANT_TYPE
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.REFRESH_TOKEN
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Class contains: login method and refresh token
 */

class LoginClient {

    private val myPreference = MyPreference()
    private var loginClient: OkHttpClient = okHttpClientBuilder()

    private val loginConnection by lazy {
        LoginClient().loginConnection()
    }

    /**
     * login by username/email + pass toDo make call not observable + w8 until get response
     */

    fun login(username: String, password: String, loginListener: LoginListener) {
        val loginEndpoint = loginConnection.login(username, password, PASSWORD_GRANT_TYPE)
        loginEndpoint.subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.i("Token expires in: ", result.expiresIn.toString())
                    myPreference.setToken(result)
                    loginListener.loginSuccessful()
                },
                        { error ->
                            Log.i("Login error", error.toString())
                            loginListener.loginFailed()
                        })
    }



//    fun login(username: String, password: String, loginListener: LoginListener) {
//        val loginEndpoint = loginConnection.login(username, password, PASSWORD_GRANT_TYPE)
//
//        try {
//            val token = loginEndpoint.execute().body()
//            token?.let { myPreference.setToken(it) }
//        } catch (ex: Exception){
//            ex.printStackTrace()
//        }
//    }

    /**
     * refresh token and replace actual token in shared preference
     */

    fun refreshToken() {
        val refreshTokenEndpoint = loginConnection.refreshToken(GRANT_TYPE, REFRESH_TOKEN)
        val tokenResponse = refreshTokenEndpoint.execute()
        val token = tokenResponse.body()
        token?.let { myPreference.setToken(it) }
    }

    private fun loginConnection(): OAuth2 {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(loginClient)
                .baseUrl(BASE_URL)
                .build()

        return retrofit.create(OAuth2::class.java)
    }

    private fun okHttpClientBuilder() =
            OkHttpClient().newBuilder().addInterceptor(LoginInterceptor()).build()

    private val gson: Gson = GsonBuilder()
            .setLenient()
            .create()
}