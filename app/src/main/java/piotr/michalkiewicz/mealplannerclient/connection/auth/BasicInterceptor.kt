package piotr.michalkiewicz.mealplannerclient.connection.auth

import android.content.Context
import android.util.Log
import okhttp3.*
import java.io.IOException
import java.util.*

class BasicInterceptor(context: Context) : Interceptor,  Authenticator {

    private val myPreference: MyPreference = MyPreference(context)

    override fun intercept(chain: Interceptor.Chain): Response {
//        val credentials = Credentials.basic("mealTime", "SjTrPoRuW/=w=8[SMcoWo=`+(-x*?M")
        val requestBuilder = chain.request().newBuilder()

        myPreference.getToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }

    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response?): Request? {
        Log.i("kurwa", "tutaj");
        var requestAvailable: Request? = null
        try {
            requestAvailable = response?.request()?.newBuilder()
                    ?.addHeader("AUTH_TOKEN", UUID.randomUUID().toString())
                    ?.build()
            return requestAvailable
        } catch (ex: Exception) {
        }
        return requestAvailable
    }
}