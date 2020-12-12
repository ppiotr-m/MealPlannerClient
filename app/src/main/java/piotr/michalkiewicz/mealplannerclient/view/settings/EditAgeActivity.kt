package piotr.michalkiewicz.mealplannerclient.view.settings

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_age.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.NutritionProfileSettings
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG

class EditAgeActivity : DataPassingActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_age)

        Log.d(TAG, "adasdsadadas")

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        confirmAgeBtn.setOnClickListener {
            setNewAgeAndFinish()
        }
        cancelAgeBtn.setOnClickListener {
            finish()
        }
    }

    private fun setNewAgeAndFinish() {
        if (checkInput()) {
            val userData = getDataFromIntent()
            createNutritionProfileSettingsIfNull(userData)
   //         userData.userSettings.nutritionProfileSettings?.age = ageET.text.toString().toInt()
            setDataForParentActivity(userData)
            finish()
        } else {
            Toast.makeText(this, R.string.age_out_of_range, Toast.LENGTH_SHORT).show()
        }
    }

    private fun createNutritionProfileSettingsIfNull(userAccount: UserAccount) {
        if (userAccount.userSettings.nutritionProfileSettings == null) {
            userAccount.userSettings.nutritionProfileSettings = NutritionProfileSettings()
        }
    }

    private fun checkInput(): Boolean {
        if (ageET.text.toString().toInt() in 3..130) return true
        return false
    }
}