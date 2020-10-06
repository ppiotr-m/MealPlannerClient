package piotr.michalkiewicz.mealplannerclient.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.auth.LoginClient
import piotr.michalkiewicz.mealplannerclient.auth.LoginListener
import piotr.michalkiewicz.mealplannerclient.auth.MyPreference
import piotr.michalkiewicz.mealplannerclient.user.SignUpServiceGenerator
import piotr.michalkiewicz.mealplannerclient.view.login.service.FakeUserData
import java.util.*

class LoginFragment : Fragment() {
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

        checkLoginState()
        setOnClickListeners()
        navController = findNavController()
    }

    private fun checkLoginState() {
        val refreshToken = MyPreference().getRefreshToken()
        if (refreshToken == null || refreshToken.length < 10) {
            return
        }
        loginClient.refreshToken(Objects.requireNonNull(refreshToken), object : LoginListener {
            override fun loginFailed() {}
            override fun loginSuccessful() {
                navController.navigate(R.id.action_loginFragment_to_homeFragment)
            }
        })
    }

    private fun setOnClickListeners() {
        loginBtn.setOnClickListener { login(emailET.text.toString(), passwordET.text.toString()) }

        createAccountTV.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        loginNoAccBtn.setOnClickListener {
            signUpServiceGenerator = SignUpServiceGenerator()
            singUpTempAccount()
        }
    }

    private fun singUpTempAccount() {
        val fakeUsername = FakeUserData.createFakeUserName()
        signUpServiceGenerator.singUpPhoneMemory(fakeUsername)
    }

    private fun login(username: String, password: String) {
        val dialog = LoginLoadingDialog(activity)
        dialog.startLoadingDialog()
        loginClient.login(username, password, object : LoginListener {
            override fun loginSuccessful() {
                dialog.dismissDialog()
                navController.navigate(R.id.action_loginFragment_to_homeFragment)
            }

            override fun loginFailed() {
                dialog.dismissDialog()
                Toast.makeText(activity, R.string.login_failed, Toast.LENGTH_LONG).show()
            }
        })
    }
}