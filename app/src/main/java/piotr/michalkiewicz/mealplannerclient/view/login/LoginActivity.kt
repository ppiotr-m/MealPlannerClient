package piotr.michalkiewicz.mealplannerclient.view.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.auth.LoginClient
import piotr.michalkiewicz.mealplannerclient.auth.LoginListener
import piotr.michalkiewicz.mealplannerclient.auth.MyPreference
import piotr.michalkiewicz.mealplannerclient.databinding.ActivityLoginBinding
import piotr.michalkiewicz.mealplannerclient.user.SignUpServiceGenerator
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.login.service.FakeUserData
import piotr.michalkiewicz.mealplannerclient.view.menu.MainMenuActivity
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBtn: Button
    private lateinit var loginNoAccountBtn: Button
    private lateinit var loginET: EditText
    private lateinit var passwordET: EditText
    private lateinit var createAccountClickableTV: View
    private val loginClient = LoginClient()
    private lateinit var signUpServiceGenerator: SignUpServiceGenerator
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // I don't have idea yest where to put it it Must be Activity
        MY_PREFERENCSES = applicationContext.getSharedPreferences(ConstantValues.MY_PREFERENCE_NAME, MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        assingUiElements()
        checkLoginState()
        setOnClickListeners()
        setContentView(binding.root)
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
        loginET = binding.emailET
        passwordET = binding.passwordET
        loginBtn = binding.loginBtn
        loginNoAccountBtn = binding.loginNoAccBtn
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

    fun startCreateAccountActivity() {
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
        lateinit var MY_PREFERENCSES: SharedPreferences
    }
}