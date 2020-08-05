package piotr.michalkiewicz.mealplannerclient.auth

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.LoginInterceptor
import piotr.michalkiewicz.mealplannerclient.auth.model.Token
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.BASE_URL
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PASSWORD_GRANT_TYPE
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.REFRESH_TOKEN
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Class contains: login method and refresh token
 */

class LoginClient {

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
                    MyPreference().setToken(result)
                    loginListener.loginSuccessful()
                },
                        { error ->
                            Log.i("Login error", error.toString())
                            loginListener.loginFailed()
                        })
    }

    /**
     * refresh token and replace actual token in shared preference
     */

    fun refreshToken(refreshToken: String, loginListener: LoginListener) {
        val refreshTokenEndpoint = loginConnection.refreshToken(REFRESH_TOKEN, refreshToken)

        refreshTokenEndpoint.enqueue(object : Callback<Token> {
            override fun onFailure(call: Call<Token>, t: Throwable) {
         //       Log.i("refreshToken()", t.message)
                loginListener.loginFailed()
            }

            override fun onResponse(call: Call<Token>, response: Response<Token>) { //toDo check method with shorter token validity
                val token = response.body()
                if (response.code() == 400) {
                    loginListener.loginFailed()
                } else {
                    token?.let { MyPreference().setToken(it) }
                    loginListener.loginSuccessful()
                }
            }
        })
    }

    fun refreshToken(refreshToken: String) {
        val refreshTokenEndpoint = loginConnection.refreshToken(REFRESH_TOKEN, refreshToken)
        try {
            val token = refreshTokenEndpoint.execute().body()
            token?.let { MyPreference().setToken(it) }

        } catch (ex: Exception) {
    //        Log.i("Exception refresh T: ", ex.message)
        }
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