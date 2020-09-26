package piotr.michalkiewicz.mealplannerclient.view.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.auth.LoginClient
import piotr.michalkiewicz.mealplannerclient.auth.LoginListener
import piotr.michalkiewicz.mealplannerclient.auth.MyPreference
import piotr.michalkiewicz.mealplannerclient.user.SignUpServiceGenerator
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.login.service.FakeUserData
import piotr.michalkiewicz.mealplannerclient.view.menu.MainMenuActivity
import java.util.*

class LoginFragment : Fragment() {
    private lateinit var loginBtn: Button
    private lateinit var loginNoAccountBtn: Button
    private lateinit var loginET: EditText
    private lateinit var passwordET: EditText
    private lateinit var createAccountClickableTV: View
    private val loginClient = LoginClient()
    //    private lateinit var userServiceGenerator: UserServiceGenerator
    private lateinit var signUpServiceGenerator: SignUpServiceGenerator

    // View initialization logic
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }
        assingUiElements(view)
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
                //Here we need to add opening new fragment with main menu
                //val myIntent = Intent(this@LoginActivity, MainMenuActivity::class.java)
                //startActivity(myIntent)
            }
        })
    }

    private fun assingUiElements(view: View) {
        loginET = view.findViewById(R.id.emailET)
        passwordET = view.findViewById(R.id.passwordET)
        createAccountClickableTV = view.findViewById(R.id.createAccountTV)
        loginBtn = view.findViewById(R.id.loginBtn)
        loginNoAccountBtn = view.findViewById(R.id.loginNoAccBtn)
    }

    private fun setOnClickListeners() {
        //loginBtn.setOnClickListener { login(loginET.text.toString(), passwordET.text.toString()) }

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
        //val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
        //startActivity(intent)
    }
/*
    private fun login(username: String, password: String) {
        //val dialog = LoginLoadingDialog(this)
        //dialog.startLoadingDialog()
        loginClient.login(username, password, object : LoginListener {
            override fun loginSuccessful() {
                //dialog.dismissDialog()
                //val myIntent = Intent(this@LoginActivity, MainMenuActivity::class.java) // todo if customization done main else cusomization
                //startActivity(myIntent)
            }

            override fun loginFailed() {
                dialog.dismissDialog()
                //runOnUiThread { Toast.makeText(this@LoginActivity, R.string.login_failed, Toast.LENGTH_LONG).show() }
            }
        })
    }
*/

}