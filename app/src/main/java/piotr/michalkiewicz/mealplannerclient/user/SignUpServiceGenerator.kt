package piotr.michalkiewicz.mealplannerclient.user

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.LoginInterceptor
import piotr.michalkiewicz.mealplannerclient.user.api.SignUpAPI
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SignUpServiceGenerator {

    private lateinit var singUpAPI: SignUpAPI

    init {
        if (!::singUpAPI.isInitialized) {
            val retrofit = retrofitBuilder()
            singUpAPI = retrofit.create(SignUpAPI::class.java)
        }
    }

    fun signUp(userAccount: UserAccount, callback: Callback<UserAccount>) {
        val callAsync = singUpAPI.signUp(userAccount)
        callAsync.enqueue(callback)
    }

    fun singUpPhoneMemory(fakeUsername: String) {
        val signUpPhoneMemoryAPI = singUpAPI.signUpPhoneMemory(fakeUsername)
        signUpPhoneMemoryAPI.subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.i("result: ", result.toString())
                }, { error ->
                    Log.i("singUpPhoneMemory error", error.toString())
                })
    }

    private fun retrofitBuilder(): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(okHttpClientBuilder())
                .baseUrl(ConstantValues.BASE_URL)
                .build()
    }

    private fun getGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .create()
    }

    private fun okHttpClientBuilder() = OkHttpClient().newBuilder().addInterceptor(LoginInterceptor()).build()
}