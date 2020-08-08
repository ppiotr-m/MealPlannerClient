package piotr.michalkiewicz.mealplannerclient.auth.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import piotr.michalkiewicz.mealplannerclient.auth.MyPreference

/**
 * Interceptor to add header with token must be in all requests
 */

class AuthInterceptor : Interceptor {

    /**
     * method add header with oauth2 authorization
     */

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        MyPreference().getToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}