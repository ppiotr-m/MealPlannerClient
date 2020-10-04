package piotr.michalkiewicz.mealplannerclient.view.login

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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.auth.LoginClient
import piotr.michalkiewicz.mealplannerclient.auth.LoginListener
import piotr.michalkiewicz.mealplannerclient.auth.MyPreference
import piotr.michalkiewicz.mealplannerclient.user.SignUpServiceGenerator
import piotr.michalkiewicz.mealplannerclient.view.login.service.FakeUserData
import java.util.*

class LoginFragment : Fragment() {
    private lateinit var loginBtn: Button
    private lateinit var loginNoAccountBtn: Button
    private lateinit var loginET: EditText
    private lateinit var passwordET: EditText
    private lateinit var createAccountClickableTV: View
    private val loginClient = LoginClient()
    private lateinit var signUpServiceGenerator: SignUpServiceGenerator
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarSetup(view)
        assingUiElements(view)
        checkLoginState()
        setOnClickListeners()
        navController = findNavController()

    }

    private fun toolbarSetup(view: View){
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }
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
                //val myIntent = Intent(activity, MainMenuActivity::class.java)
                //startActivity(myIntent)
                navController.navigate(R.id.action_loginFragment_to_mainMenuFragment)
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

        createAccountClickableTV.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registrationFragment)
        }

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

    private fun login(username: String, password: String) {
        val dialog = LoginLoadingDialog(activity)
        dialog.startLoadingDialog()
        loginClient.login(username, password, object : LoginListener {
            override fun loginSuccessful() {
                dialog.dismissDialog()
                navController.navigate(R.id.action_loginFragment_to_mainMenuFragment)
            }

            override fun loginFailed() {
                dialog.dismissDialog()
                if (activity is AppCompatActivity) {
                    (activity as AppCompatActivity).runOnUiThread {
                        Toast.makeText(
                            activity,
                            R.string.login_failed,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                Log.i("LoginFragment", "loginFailed() happened")
            }
        })
    }

}