package piotr.michalkiewicz.mealplannerclient.view.login_and_signup

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_registration.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.user.service_generator.UserServiceGenerator
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private val HTTP_OK_CODE = 200
    private val MIN_EMAIL_LENGTH = 6
    private val MIN_PASSWORD_LENGTH = 6
    private val MAX_PASSWORD_LENGTH = 50
    private val EMAIL_REGEX = Regex("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        init()
    }

    private fun init(){
        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        createAccountBtn.setOnClickListener {
            signUp()
        }
    }

    private fun signUp(){
        if(!validateEmail()){
            return
        }
        if(!validatePassword()) {
            return
        }

        val userService = UserServiceGenerator()
        userService.signUp(UserAccount.createMockUserAccountWithParams(emailET.text.toString(),
                passwordET.text.toString(), usernameET.text.toString()), object : Callback<UserAccount>{

            override fun onResponse(call: Call<UserAccount>, response: Response<UserAccount>) {
                if(response.code()==HTTP_OK_CODE){
                    showSignUpSuccessfulToast()
                    finish()
                }
                else{
                    showSignUpFailureServerSideToast()
                }
                Log.i(ConstantValues.TAG, "Response: " + response.message() +"\n" + response.toString())
            }

            override fun onFailure(call: Call<UserAccount>, t: Throwable) {
                showSignUpFailureServerSideToast()
            }
        })
    }

    private fun validateEmail(): Boolean {
        if(emailET.text.toString().length<MIN_EMAIL_LENGTH)
        if(emailET.text.toString().matches(EMAIL_REGEX)) return true

        showInvalidEmailToast()
        return false
    }

    private fun showInvalidEmailToast(){
        this@SignUpActivity.runOnUiThread{
            Toast.makeText(this@SignUpActivity, R.string.invalid_email,
                    Toast.LENGTH_SHORT).show()
        }
    }

    private fun validatePassword(): Boolean{
        if(passwordET.text.toString().length<MIN_PASSWORD_LENGTH ||
                passwordET.text.toString().length>MAX_PASSWORD_LENGTH){
            showPasswordTooShortFailureToast()
            return false
        }
        if(passwordET.text.toString() != confirmPasswordET.text.toString()){
            showPasswordConfirmationFailureToast()
            return false
        }
        return true
    }

    private fun showSignUpSuccessfulToast(){
        this@SignUpActivity.runOnUiThread{
            Toast.makeText(this@SignUpActivity, R.string.sign_up_successful,
                    Toast.LENGTH_SHORT).show()
        }
    }

    private fun showPasswordConfirmationFailureToast(){
        this@SignUpActivity.runOnUiThread{
            Toast.makeText(this@SignUpActivity, R.string.password_confirmation_failed,
                    Toast.LENGTH_SHORT).show()
        }
    }

    private fun showPasswordTooShortFailureToast(){
        this@SignUpActivity.runOnUiThread{
            Toast.makeText(this@SignUpActivity, R.string.password_too_short,
                    Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSignUpFailureServerSideToast(){
        this@SignUpActivity.runOnUiThread{
            Toast.makeText(this@SignUpActivity, R.string.sign_up_failed,
                    Toast.LENGTH_SHORT).show()
        }
    }
}
