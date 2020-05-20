package piotr.michalkiewicz.mealplannerclient.connection.auth

import android.accounts.AccountManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BasicAuth : AppCompatActivity() {

    //DEV local host
    private val BASIC_URL = "http://10.0.2.2:8080/"
    private val interceptor: TokenInterceptor = TokenInterceptor()
    private var client: OkHttpClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
    private var disposable: Disposable? = null
    private val accountManager: AccountManager = AccountManager.get(this)
    private val options = Bundle()


    private val authorization by lazy {
        BasicAuth().createAuthorizationConnection()
    }

    private val gson: Gson = GsonBuilder()
            .setLenient()
            .create()


    private fun createAuthorizationConnection(): OAuth2 {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(BASIC_URL)
                .build()

        return retrofit.create(OAuth2::class.java)
    }

    fun getToken() {
        val token = authorization.getToken("user", "user", "password")
        token.subscribeOn(Schedulers.io())
                .subscribe({ result -> Log.i("Token ok", result.accessToken) },
                        { error -> Log.i("Token nie ok", error.toString()) })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}