package piotr.michalkiewicz.mealplannerclient.view.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_registration.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.SignUpServiceGenerator
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationFragment : Fragment() {
    private val HTTP_OK_CODE = 200
    private val HTTP_OK_CODE_CREATED = 201
    private val MIN_EMAIL_LENGTH = 6
    private val MIN_PASSWORD_LENGTH = 6
    private val MAX_PASSWORD_LENGTH = 50
    private val EMAIL_REGEX =
        Regex("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        createAccountBtn.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        if (!checkIfAllFieldsFilled()) return
        if (!validateEmail()) return
        if (!validatePassword()) return

        val userService = SignUpServiceGenerator()
        userService.signUp(UserAccount.createUserAccount(
            emailET.text.toString(),
            passwordET.text.toString(), usernameET.text.toString()
        ), object : Callback<UserAccount> {

            override fun onResponse(call: Call<UserAccount>, response: Response<UserAccount>) {
                if (response.code() == HTTP_OK_CODE || response.code() == HTTP_OK_CODE_CREATED) {
                    showSignUpSuccessfulToast()
                    //finish()
                } else {
                    showSignUpFailureServerSideToast()
                }
                Log.i(
                    ConstantValues.TAG,
                    "Response: " + response.message() + "\n" + response.toString()
                )
            }

            override fun onFailure(call: Call<UserAccount>, t: Throwable) {
                showSignUpFailureServerSideToast()
            }
        })
    }

    private fun validateEmail(): Boolean {
        if (emailET.text.toString().length >= MIN_EMAIL_LENGTH &&
            emailET.text.toString().matches(EMAIL_REGEX)
        ) return true

        emailET.error = resources.getString(R.string.invalid_email)
        return false
    }

    private fun validatePassword(): Boolean {
        if (passwordET.text.toString().length < MIN_PASSWORD_LENGTH ||
            passwordET.text.toString().length > MAX_PASSWORD_LENGTH
        ) {
            passwordET.error = resources.getString(R.string.password_too_short)
            return false
        }
        if (passwordET.text.toString() != confirmPasswordET.text.toString()) {
            confirmPasswordET.error = resources.getString(R.string.password_confirmation_failed)
            return false
        }
        return true
    }

    private fun showSignUpSuccessfulToast() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).runOnUiThread {
                Toast.makeText(
                    activity, R.string.sign_up_successful,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showSignUpFailureServerSideToast() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).runOnUiThread {
                Toast.makeText(
                    activity, R.string.sign_up_failed,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showIncompleteDataToast() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).runOnUiThread {
                Toast.makeText(
                    activity, R.string.sign_up_incomplete_data,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun checkIfAllFieldsFilled(): Boolean {
        if (usernameET.text.toString()
                .isEmpty() || emailET.text.isEmpty() || passwordET.text.isEmpty()
            || confirmPasswordET.text.isEmpty()
        ) {
            showIncompleteDataToast()
            return false
        } else {
            return true
        }
    }
}
