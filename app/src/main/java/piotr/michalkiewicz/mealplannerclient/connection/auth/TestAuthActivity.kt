package piotr.michalkiewicz.mealplannerclient.connection.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.schedulers.Schedulers
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.connection.ApiInterface


class TestAuthActivity : AppCompatActivity() {

    private lateinit var myPreference: MyPreference
    private lateinit var testApiService: TestApiService
    private lateinit var testTestApiSecond: TestApiSecond
    private lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_auth)  // loading animation ?

        myPreference = MyPreference(this)
        testApiService = ServiceGenerator().getTestApiService(this)
        testTestApiSecond = ServiceGenerator().getTestApiServiceSecond(this)
        apiInterface = ServiceGenerator().getApiInterface(this)

//for test start here

        val pingUser = findViewById<View>(R.id.button) as Button
        val pingAdmin = findViewById<View>(R.id.button2) as Button
        val pingNoAuth = findViewById<View>(R.id.button3) as Button
        val showToken = findViewById<View>(R.id.button4) as Button

        pingNoAuth.setOnClickListener {
            val tokenNoAuth = testTestApiSecond.pingNoAuth()
            tokenNoAuth.subscribeOn(Schedulers.io()).subscribe({ result ->
                Log.i("result no auth: ", result)
            }, { er -> Log.i("ERROR no auth: ", er.toString()) })
        }

        pingAdmin.setOnClickListener {
            val tokenNoAuth = testApiService.getRandom(1)
            tokenNoAuth.subscribeOn(Schedulers.io()).subscribe({ result ->
                Log.i("result no auth: ", result.toString())
            }, { er -> Log.i("ERROR no auth: ", er.toString()) })

//             apiInterface.randomRecipes.execute()
        }


        pingUser.setOnClickListener {
            val tokenNoAuth = testApiService.pingUser()
            tokenNoAuth.subscribeOn(Schedulers.io()).subscribe({ result ->
                Log.i("result no auth: ", result)
            }, { er -> Log.i("ERROR no auth: ", er.toString()) })
        }

        showToken.setOnClickListener {


        }

//for test end here

//        LoginClient().login(this, "user", "user")
    }
}
