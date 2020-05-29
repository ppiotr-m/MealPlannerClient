package piotr.michalkiewicz.mealplannerclient.connection.auth

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class LoginInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val credentials = Credentials.basic("mealTime", "SjTrPoRuW/=w=8[SMcoWo=`+(-x*?M")  // toDo hide it ?

        var request = chain.request()

        request = request.newBuilder()
                .addHeader("Authorization", credentials)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build()

        return chain.proceed(request)
    }
}