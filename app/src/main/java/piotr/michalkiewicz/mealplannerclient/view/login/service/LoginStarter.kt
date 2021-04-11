package piotr.michalkiewicz.mealplannerclient.view.login.service

import android.os.Handler
import android.os.Looper
import androidx.navigation.NavController
import kotlinx.coroutines.runBlocking
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.auth.LoginClient
import piotr.michalkiewicz.mealplannerclient.auth.LoginListener
import piotr.michalkiewicz.mealplannerclient.user.UserServiceGenerator
import piotr.michalkiewicz.mealplannerclient.view.login.LoginFragment
import piotr.michalkiewicz.mealplannerclient.view.login.LoginLoadingDialog
import piotr.michalkiewicz.mealplannerclient.view.login.RegistrationFragment
import piotr.michalkiewicz.mealplannerclient.view.utils.ToastRunner

class LoginStarter(
    private val navController: NavController,
    private val fragmentName: String
) : LoginListener {

    private val loginClient = LoginClient()
    private var dialog: LoginLoadingDialog = LoginLoadingDialog()
    private lateinit var userServiceGenerator: UserServiceGenerator

    fun login(username: String, password: String) {
        dialog.startLoadingDialog()
        userServiceGenerator = UserServiceGenerator()

        loginClient.login(username, password, this)
    }

    override fun loginSuccessful() {
        var personalizationDone: Boolean = false
        runBlocking {
            personalizationDone = userServiceGenerator.getUserSettings()?.customizationDone ?: false
        }
        dialog.dismissDialog()
        Handler(Looper.getMainLooper()).post {
            if (personalizationDone) {
                navController.navigate(R.id.action_loginFragment_to_homeScreenFragment)
            } else {
                runPersonalizationActivity()
            }
        }
    }

    override fun loginFailed() {
        dialog.dismissDialog()
        ToastRunner.runLongToast(R.string.login_failed)
    }

    private fun runPersonalizationActivity() {
        when (fragmentName) {
            LoginFragment::class.java.name -> navController.navigate(R.id.action_loginFragment_to_startCustomizationFragment)
            RegistrationFragment::class.java.name -> navController.navigate(R.id.action_registrationFragment_to_startCustomizationFragment)
        }
    }
}