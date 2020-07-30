package piotr.michalkiewicz.mealplannerclient.auth.interceptor

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Class used to login contains all necessary data
 */

class LoginInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val credentials = Credentials.basic("mealTime", "SjTrPoRuW/=w=8[SMcoWo=`+(-x*?M") //ToDo hide it
        var request = chain.request()

        request = request.newBuilder()
                .addHeader("Authorization", credentials)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build()

        return chain.proceed(request)
    }
}