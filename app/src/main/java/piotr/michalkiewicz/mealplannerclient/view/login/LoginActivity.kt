package piotr.michalkiewicz.mealplannerclient.view.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.auth.LoginClient
import piotr.michalkiewicz.mealplannerclient.auth.LoginListener
import piotr.michalkiewicz.mealplannerclient.auth.MyPreference
import piotr.michalkiewicz.mealplannerclient.user.SignUpServiceGenerator
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.login.service.FakeUserData
import piotr.michalkiewicz.mealplannerclient.view.menu.MainMenuActivity
import java.util.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // I doesn't have idea yest where to put it it Must be Activity
//        MY_PREFERENCSES = applicationContext.getSharedPreferences(ConstantValues.MY_PREFERENCE_NAME, AppCompatActivity.MODE_PRIVATE)
        //setContentView(R.layout.activity_main2)
    }
    companion object {
 //       lateinit var MY_PREFERENCSES: SharedPreferences
    }
}