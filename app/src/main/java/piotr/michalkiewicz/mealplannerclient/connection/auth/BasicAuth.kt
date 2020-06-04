package piotr.michalkiewicz.mealplannerclient.connection.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.schedulers.Schedulers
import piotr.michalkiewicz.mealplannerclient.R


class BasicAuth : AppCompatActivity() {

    private lateinit var myPreference: MyPreference
    private lateinit var testApiService: TestApiService
    private lateinit var testSecondTestApi: SecondTestApi
//    private val accountManager: AccountManager = AccountManager.get(this) toDo  http://blog.udinic.com/2013/04/24/write-your-own-android-authenticator/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_auth)  // loading animation ?

        myPreference = MyPreference(this)
        testApiService = ServiceGenerator().getApiService(this)
        testSecondTestApi = ServiceGenerator().getApiServiceSecond(this)

//for test start here

        val pingUser = findViewById<View>(R.id.button) as Button
        val pingAdmin = findViewById<View>(R.id.button2) as Button
        val pingNoAuth = findViewById<View>(R.id.button3) as Button
        val showToken = findViewById<View>(R.id.button4) as Button

        pingNoAuth.setOnClickListener {
            val tokenNoAuth = testSecondTestApi.pingNoAuth()
            tokenNoAuth.subscribeOn(Schedulers.io()).subscribe({ result ->
                Log.i("result no auth: ", result)
            }, { er -> Log.i("ERROR no auth: ", er.toString()) })
        }

        pingAdmin.setOnClickListener {
            val tokenNoAuth = testApiService.pingAdmin()
            tokenNoAuth.subscribeOn(Schedulers.io()).subscribe({ result ->
                Log.i("result no auth: ", result)
            }, { er -> Log.i("ERROR no auth: ", er.toString()) })
        }


        pingUser.setOnClickListener {
            val tokenNoAuth = testApiService.pingUser()
            tokenNoAuth.subscribeOn(Schedulers.io()).subscribe({ result ->
                Log.i("result no auth: ", result)
            }, { er -> Log.i("ERROR no auth: ", er.toString()) })
        }

        showToken.setOnClickListener {
            Log.i("TOKEN TEST: ", myPreference.getRefreshToken().toString())
        }

//for test end here

        LoginClient().login(this, "user", "user")
    }

    override fun onDestroy() {
        this.isDestroyed
        super.onDestroy()
    }
}
