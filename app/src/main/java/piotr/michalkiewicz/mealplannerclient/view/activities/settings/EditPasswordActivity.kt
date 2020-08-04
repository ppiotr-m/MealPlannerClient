package piotr.michalkiewicz.mealplannerclient.view.activities.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_password.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.user.repository.UserRepository
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.SETTINGS_DATA
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_password)

        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        cancelPasswordEditBtn.setOnClickListener {
            finish()
        }
        confirmPasswordEditBtn.setOnClickListener {
            saveNewPassword()
        }
    }

    private fun saveNewPassword(){
        if(!checkIfPasswordMatchesCurrent()){
            Toast.makeText(this, R.string.password_not_match_current, Toast.LENGTH_SHORT).show()
            return
        }
        if(!checkIfPasswordConfirmed()){
            Toast.makeText(this, R.string.password_confirmation_failed, Toast.LENGTH_SHORT).show()
            return
        }

        val settingsData = getDataFromIntent()
        settingsData?.password = newPasswordET.text.toString()

        val userRepository = UserRepository(this)
        /*
        userRepository.saveUserAccountData(settingsData, object : Callback<UserAccount>{
            override fun onResponse(call: Call<UserAccount>, response: Response<UserAccount>) {
                Log.i(TAG, "Password change successful")
                setDataForParentActivity(settingsData)
                finish()
            }
            override fun onFailure(call: Call<UserAccount>, t: Throwable) {
                Log.i(TAG, "Password change failed")
            }
        })

         */
    }

    private fun getDataFromIntent(): UserAccount?{
        return intent.getSerializableExtra(SETTINGS_DATA) as? UserAccount
    }

    private fun setDataForParentActivity(data : UserAccount?){
        val intent = Intent()
        intent.putExtra(SETTINGS_DATA, data)
        setResult(SettingsActivity.RESULT_OK, intent)
    }

    private fun checkIfPasswordMatchesCurrent(): Boolean{
        val settingsData = intent.getSerializableExtra(SETTINGS_DATA) as? UserAccount

        return settingsData?.password.toString() == currentPasswordET.text.toString()
    }

    private fun checkIfPasswordConfirmed(): Boolean{
        return newPasswordET.text.toString() == confirmNewPasswordET.text.toString()
    }

}
