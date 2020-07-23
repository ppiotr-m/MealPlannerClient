package piotr.michalkiewicz.mealplannerclient.view.activities.authorization

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.account.AccountDataStorage
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.user.repository.UserRepository
import piotr.michalkiewicz.mealplannerclient.view.activities.menus.MainMenuActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {
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
            signUp(emailET.text.toString(), passwordET.text.toString(), confirmPasswordET.text.toString())
        }
    }

    private fun signUp(email: String, password: String, passwordConfrimation: String){
        if(password!=(passwordConfrimation)) {
            showPasswordConfirmationFailureToast()
            return
        }
        val repository = UserRepository(this)
        repository.signUp(UserAccount.createMockUserAccountWithParams(email, password), object : Callback<UserAccount>{
            override fun onResponse(call: Call<UserAccount>, response: Response<UserAccount>) {
                showSignUpSuccessfulToast()
                AccountDataStorage.storeAccountData(response.body())
                startActivity(Intent(this@RegistrationActivity, MainMenuActivity::class.java))
            }

            override fun onFailure(call: Call<UserAccount>, t: Throwable) {
                showSignUpFailureServerSideToast()
            }
        })
    }

    private fun showSignUpSuccessfulToast(){
        this@RegistrationActivity.runOnUiThread{
            Toast.makeText(this@RegistrationActivity, R.string.sign_up_successful, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showPasswordConfirmationFailureToast(){
        this@RegistrationActivity.runOnUiThread{
            Toast.makeText(this@RegistrationActivity, R.string.password_confirmation_failed, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSignUpFailureServerSideToast(){
        this@RegistrationActivity.runOnUiThread{
            Toast.makeText(this@RegistrationActivity, R.string.sign_up_failed, Toast.LENGTH_SHORT).show()
        }
    }
}
