package piotr.michalkiewicz.mealplannerclient.connection.auth

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

class RoleTester : AppCompatActivity(){

    private val BASIC_URL = "http://10.0.2.2:8080/"
    private val interceptor: TokenInterceptor = TokenInterceptor()
    private var client: OkHttpClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
    private var disposable: Disposable? = null

    private val authTester by lazy {
        RoleTester().createTestRetroAuthConnection()
    }

    private val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

    private fun createTestRetroAuthConnection(): AuthTestEndpoints {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(BASIC_URL)
                .build()

        return retrofit.create(AuthTestEndpoints::class.java)
    }

    fun ping() {
        val ping = authTester.pingNoAuth()
        ping.subscribeOn(Schedulers.io())
                .subscribe(
                        { result -> Log.i("PING KURWA", result) },
                        { error -> Log.i("PING KURWA erroe", error.toString()) }
                )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}