package piotr.michalkiewicz.mealplannerclient.connection.auth

import okhttp3.*
import java.io.IOException
import java.util.*

class TokenInterceptor : Interceptor, Authenticator {

    // Method add header to all calls

    override fun intercept(chain: Interceptor.Chain): Response {

        val credentials = Credentials.basic("mealTime", "SjTrPoRuW/=w=8[SMcoWo=`+(-x*?M")

        var request = chain.request()

        request = request.newBuilder()
                .addHeader("OAuth2", credentials)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build()

        return chain.proceed(request)
    }

    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response?): Request? {
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