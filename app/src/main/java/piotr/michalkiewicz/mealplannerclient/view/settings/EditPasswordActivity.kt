package piotr.michalkiewicz.mealplannerclient.view.settings

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_password.*
import piotr.michalkiewicz.mealplannerclient.R

class EditPasswordActivity : DataPassingActivity() {

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
        settingsData.password = newPasswordET.text.toString()
        setDataForParentActivity(settingsData)
        finish()
    }

    private fun checkIfPasswordMatchesCurrent(): Boolean{
        val settingsData = getDataFromIntent()

        return settingsData.password == currentPasswordET.text.toString()
    }

    private fun checkIfPasswordConfirmed(): Boolean{
        return newPasswordET.text.toString() == confirmNewPasswordET.text.toString()
    }

}
