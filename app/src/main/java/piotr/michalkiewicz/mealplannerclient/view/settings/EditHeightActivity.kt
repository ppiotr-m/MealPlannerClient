package piotr.michalkiewicz.mealplannerclient.view.settings

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_height.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.NutritionProfileSettings
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount

class EditHeightActivity : DataPassingActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_height)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        confirmWeightBtn.setOnClickListener {
            setNewHeightAndFinish()
        }
        cancelWeightBtn.setOnClickListener {
            finish()
        }
    }

    private fun setNewHeightAndFinish(){
        if(checkInput()) {
            val userData = getDataFromIntent()
            createNutritionProfileSettingsIfNull(userData)
   //         userData.userSettings.nutritionProfileSettings?.height = heightET.text.toString().toInt()
            setDataForParentActivity(userData)
            finish()
        } else {
            Toast.makeText(this, R.string.height_out_of_range, Toast.LENGTH_SHORT).show()
        }
    }

    private fun createNutritionProfileSettingsIfNull(userAccount: UserAccount) {
        if (userAccount.userSettings.nutritionProfileSettings == null) {
            userAccount.userSettings.nutritionProfileSettings = NutritionProfileSettings()
        }
    }

    private fun checkInput(): Boolean {
        if (heightET.text.toString().toInt() in 100..300) return true
        return false
    }
}