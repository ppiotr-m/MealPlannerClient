package piotr.michalkiewicz.mealplannerclient.auth.interceptor

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import piotr.michalkiewicz.mealplannerclient.auth.MyPreference

class SignUpInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val credentials = Credentials.basic("mealTime", "SjTrPoRuW/=w=8[SMcoWo=`+(-x*?M")
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader("Authorization", credentials)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")

        return chain.proceed(requestBuilder.build())
    }
}