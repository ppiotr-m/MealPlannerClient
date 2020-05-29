package piotr.michalkiewicz.mealplannerclient.connection.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import piotr.michalkiewicz.mealplannerclient.R
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class BasicAuth : AppCompatActivity() {

    //DEV local host
    private val BASIC_URL = "http://10.0.2.2:8080/"
    private val loginInterceptor: LoginInterceptor = LoginInterceptor()
    private var loginClient: OkHttpClient = OkHttpClient().newBuilder().addInterceptor(loginInterceptor).build()

    private lateinit var basicInterceptor: BasicInterceptor
    private lateinit var basicClient: OkHttpClient
    private lateinit var myPreference: MyPreference

//    private val accountManager: AccountManager = AccountManager.get(this) toDo  http://blog.udinic.com/2013/04/24/write-your-own-android-authenticator/

    private val authorization by lazy {
        BasicAuth().createLoginConnection()
    }

    private val basicConnection by lazy {
        BasicAuth().createBasicConnection()
    }

    private val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_auth)  // loading animation ?

        myPreference = MyPreference(this)
        basicInterceptor = BasicInterceptor(this)
        basicClient = OkHttpClient().newBuilder().addInterceptor(BasicInterceptor(this)).authenticator(BasicInterceptor(this)).build()

//for test start here

        val pingUser = findViewById<View>(R.id.button) as Button
        val pingAdmin = findViewById<View>(R.id.button2) as Button
        val pingNoAuth = findViewById<View>(R.id.button3) as Button
        val showToken = findViewById<View>(R.id.button4) as Button

        pingNoAuth.setOnClickListener {
            val tokenNoAuth = basicConnection.pingNoAuth();
            tokenNoAuth.subscribeOn(Schedulers.io()).subscribe({ result ->
                Log.i("result no auth: ", result)
            }, { er -> Log.i("ERROR no auth: ", er.toString()) })
        }

        pingAdmin.setOnClickListener {
            val tokenNoAuth = basicConnection.pingAdmin();
            tokenNoAuth.subscribeOn(Schedulers.io()).subscribe({ result ->
                Log.i("result no auth: ", result)
            }, { er -> Log.i("ERROR no auth: ", er.toString()) })
        }


        pingUser.setOnClickListener {
            val tokenNoAuth = basicConnection.pingUser()
            tokenNoAuth.subscribeOn(Schedulers.io()).subscribe({ result ->
                Log.i("result no auth: ", result)
            }, { er -> Log.i("ERROR no auth: ", er.toString()) })
        }

        showToken.setOnClickListener {
            Log.i("TOKEN TEST: ", myPreference.getToken().toString())
        }

//for test end here

        getToken()
    }

    private fun createBasicConnection(): OAuth2 {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(basicClient)
                .baseUrl(BASIC_URL)
                .build()

        return retrofit.create(OAuth2::class.java)
    }

    private fun createLoginConnection(): OAuth2 {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(loginClient)
                .baseUrl(BASIC_URL)
                .build()

        return retrofit.create(OAuth2::class.java)
    }

    private fun getToken() {
        val token = authorization.getToken("user", "user", "password")

        token.subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.i("Token ok", result.expiresIn.toString())
                    myPreference.setToken(result)
                },
                        { error -> Log.i("Token nie ok", error.toString()) })
    }


}
