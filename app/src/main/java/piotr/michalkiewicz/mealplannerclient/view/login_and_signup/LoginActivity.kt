package piotr.michalkiewicz.mealplannerclient.view.login_and_signup

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
import piotr.michalkiewicz.mealplannerclient.user.service_generator.SignUpServiceGenerator
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.login_and_signup.service.FakeUserData
import piotr.michalkiewicz.mealplannerclient.view.main_menu.MainMenuActivity
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBtn: Button
    private lateinit var loginNoAccountBtn: Button
    private lateinit var loginET: EditText
    private lateinit var passwordET: EditText
    private lateinit var createAccountClickableTV: View
    private val loginClient = LoginClient()
//    private lateinit var userServiceGenerator: UserServiceGenerator
    private lateinit var signUpServiceGenerator: SignUpServiceGenerator

    override fun onCreate(savedInstanceState: Bundle?) {
        // I doesn't have idea yest where to put it it Must be Activity
        myPreferences = applicationContext.getSharedPreferences(ConstantValues.MY_PREFERENCE_NAME, MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        assingUiElements()
        checkLoginState()
        setOnClickListeners()
    }

    private fun checkLoginState() {
        val refreshToken = MyPreference().getRefreshToken()
        if (refreshToken == null || refreshToken.length < 10) {
            return
        }
        loginClient.refreshToken(Objects.requireNonNull(refreshToken), object : LoginListener {
            override fun loginFailed() {}
            override fun loginSuccessful() {
                val myIntent = Intent(this@LoginActivity, MainMenuActivity::class.java)
                startActivity(myIntent)
            }
        })
    }

    private fun assingUiElements() {
        loginET = findViewById(R.id.emailET)
        passwordET = findViewById(R.id.passwordET)
        createAccountClickableTV = findViewById(R.id.createAccountTV)
        loginBtn = findViewById(R.id.loginBtn)
        loginNoAccountBtn = findViewById(R.id.loginNoAccBtn)
    }

    private fun setOnClickListeners() {
        loginBtn.setOnClickListener { login(loginET.text.toString(), passwordET.text.toString()) }

        loginNoAccountBtn.setOnClickListener {
//            userServiceGenerator = UserServiceGenerator()
            signUpServiceGenerator = SignUpServiceGenerator()
            singUpTempAccount()
        }
    }

    private fun singUpTempAccount() {
        val fakeUsername = FakeUserData.createFakeUserName()
//        userServiceGenerator.singUpPhoneMemory(fakeUsername)
        signUpServiceGenerator.singUpPhoneMemory(fakeUsername)
    }

    fun startCreateAccountActivity(v: View?) {
        val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun login(username: String, password: String) {
        val dialog = LoginLoadingDialog(this)
        dialog.startLoadingDialog()
        loginClient.login(username, password, object : LoginListener {
            override fun loginSuccessful() {
                dialog.dismissDialog()
                val myIntent = Intent(this@LoginActivity, MainMenuActivity::class.java) // todo if customization done main else cusomization
                startActivity(myIntent)
            }

            override fun loginFailed() {
                dialog.dismissDialog()
                runOnUiThread { Toast.makeText(this@LoginActivity, R.string.login_failed, Toast.LENGTH_LONG).show() }
            }
        })
    }

    companion object {
        lateinit var myPreferences: SharedPreferences
    }
}