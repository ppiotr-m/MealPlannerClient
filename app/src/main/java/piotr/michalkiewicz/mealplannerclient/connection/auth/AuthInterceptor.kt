package piotr.michalkiewicz.mealplannerclient.connection.auth

import android.content.Context
import android.util.Log
import okhttp3.*
import java.io.IOException


class AuthInterceptor(context: Context) : Interceptor, Authenticator {

    private val context: Context = context
    private val myPreference: MyPreference = MyPreference(context)
    private val loginClient: LoginClient = LoginClient()

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        myPreference.getToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }

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