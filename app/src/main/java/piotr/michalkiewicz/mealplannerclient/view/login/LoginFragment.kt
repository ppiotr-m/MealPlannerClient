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
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.view.login.service.LoginStarter
import piotr.michalkiewicz.mealplannerclient.view.login.service.TempUserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginFragment : Fragment() {
    private val loginClient = LoginClient()
    private lateinit var loginStarter: LoginStarter
    private lateinit var signUpServiceGenerator: SignUpServiceGenerator
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        checkLoginState()
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        navController = findNavController()
        loginStarter = LoginStarter(
            navController,
            this::class.java.name
        )
    }

    private fun checkLoginState() {
        val refreshToken = MyPreference().getRefreshToken()
        if (refreshToken == null || refreshToken.length < 10) {
            return
        }
        loginClient.refreshToken(Objects.requireNonNull(refreshToken), object : LoginListener {
            override fun loginFailed() {}
            override fun loginSuccessful() {
                navController.navigate(R.id.action_loginFragment_to_homeScreenFragment)
            }
        })
    }

    private fun setOnClickListeners() {
        loginBtn.setOnClickListener {
            loginStarter.login(
                emailET.text.toString(),
                passwordET.text.toString()
            )
        }

        createAccountTV.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        loginNoAccBtn.setOnClickListener {
            signUpServiceGenerator = SignUpServiceGenerator()
            singUpTempAccount()
        }
    }

    private fun singUpTempAccount() {
        val tempUsername = TempUserData.createTempUserName()
        signUpServiceGenerator.singUpPhoneMemory(tempUsername, object : Callback<UserAccount> {
            override fun onFailure(call: Call<UserAccount>, t: Throwable) {
                Toast.makeText(activity, R.string.login_no_acc_error, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<UserAccount>, response: Response<UserAccount>) {
                val userAccount = response.body() ?: return
                loginStarter.login(userAccount.username, userAccount.username)
            }
        })
    }
}


