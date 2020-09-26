package piotr.michalkiewicz.mealplannerclient.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.auth.LoginClient
import piotr.michalkiewicz.mealplannerclient.auth.LoginListener
import piotr.michalkiewicz.mealplannerclient.auth.MyPreference
import piotr.michalkiewicz.mealplannerclient.user.SignUpServiceGenerator
import piotr.michalkiewicz.mealplannerclient.view.login.service.FakeUserData
import piotr.michalkiewicz.mealplannerclient.view.menu.MainMenuActivity
import java.util.*

<<<<<<<< HEAD:app/src/main/java/piotr/michalkiewicz/mealplannerclient/view/login/LoginFragment.kt
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
                val myIntent = Intent(activity, MainMenuActivity::class.java)
                startActivity(myIntent)
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
        loginBtn.setOnClickListener { login(loginET.text.toString(), passwordET.text.toString()) }

        loginNoAccountBtn.setOnClickListener {
            //userServiceGenerator = UserServiceGenerator()
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
        val intent = Intent(activity, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun login(username: String, password: String) {
        val dialog = LoginLoadingDialog(activity)
        dialog.startLoadingDialog()
        loginClient.login(username, password, object : LoginListener {
            override fun loginSuccessful() {
                dialog.dismissDialog()
                val myIntent = Intent(activity, MainMenuActivity::class.java) // todo if customization done main else cusomization
                startActivity(myIntent)
            }

            override fun loginFailed() {
                dialog.dismissDialog()

                if(activity is AppCompatActivity){
                    (activity as AppCompatActivity).runOnUiThread {Toast.makeText(activity, R.string.login_failed, Toast.LENGTH_LONG).show()}
                }
                Log.i("LoginFragment", "loginFailed() happened")
            }
        })
    }

========
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
>>>>>>>> origin/feature/nav_refactor:app/src/main/java/piotr/michalkiewicz/mealplannerclient/view/login/LoginActivity.kt
}