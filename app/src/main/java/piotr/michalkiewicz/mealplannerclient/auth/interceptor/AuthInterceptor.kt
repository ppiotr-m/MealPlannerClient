package piotr.michalkiewicz.mealplannerclient.auth.interceptor

import android.content.Context
import android.util.Log
import okhttp3.*
import piotr.michalkiewicz.mealplannerclient.auth.LoginClient
import piotr.michalkiewicz.mealplannerclient.auth.MyPreference
import java.io.IOException

/**
 * Interceptor to add header with token must be in all requests
 */

class AuthInterceptor(private val context: Context) : Interceptor, Authenticator {

    private val myPreference: MyPreference = MyPreference(context)
    private val loginClient: LoginClient = LoginClient()

    /**
     * method add header with oauth2 authorization
     */

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        myPreference.getToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }

    /**
     * Method automatically refresh token if get response with 401, if get 500 must return null to avoid multi same queries to refresh token.
     */

    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response?): Request? {
        var requestAvailable: Request? = null
        if (response?.code() == 401) {
            loginClient.refreshToken(context)
            try {
                myPreference.getToken()?.let {
                    requestAvailable = response.request().newBuilder().header("Authorization", "Bearer $it").build()
                }
                return requestAvailable
            } catch (ex: Exception) {
                Log.i("AuthInterceptor ex: ", ex.toString())
            }
        } else if (response?.code() == 500) {
            return null
        }
        return requestAvailable
    }
}