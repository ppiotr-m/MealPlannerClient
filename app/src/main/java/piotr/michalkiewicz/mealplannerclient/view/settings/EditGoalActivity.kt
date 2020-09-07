package piotr.michalkiewicz.mealplannerclient.view.settings

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_goal.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.NutritionProfileSettings
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.user.model.WeightGoal
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG

class EditGoalActivity : DataPassingActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_goal)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        confirmGoalBtn.setOnClickListener {
            setNewGoalAndFinish()
        }
        cancelGoalBtn.setOnClickListener {
            finish()
        }
    }

    private fun setNewGoalAndFinish() {
        val weightGoal = createWeightGoalFromInput()
        if (weightGoal == null) {
            Toast.makeText(this, R.string.incorrect_format, Toast.LENGTH_SHORT).show()
            return
        } else {
            val userData = getDataFromIntent()
            Log.d(TAG, "User data: " + (userData == null))
            createNutritionProfileSettingsIfNull(userData)
            userData.userSettings.nutritionProfileSettings?.goal = weightGoal
            setDataForParentActivity(userData)
            finish()
        }
    }

    private fun createNutritionProfileSettingsIfNull(userAccount: UserAccount) {
        if (userAccount.userSettings.nutritionProfileSettings == null) {
            userAccount.userSettings.nutritionProfileSettings = NutritionProfileSettings()
        }
    }

    private fun createWeightGoalFromInput(): WeightGoal? {
        val firstCharacter = goalET.text.toString()[0]

        if (firstCharacter == "+".single()) {
            val inputNumber = goalET.text.toString().substring(1).toFloatOrNull()
            if (inputNumber != null) {
                return WeightGoal(inputNumber, false)
            }
        } else if (firstCharacter == "-".single()) {
            val inputNumber = goalET.text.toString().substring(1).toFloatOrNull()
            if (inputNumber != null) {
                return WeightGoal(inputNumber, true)
            }
        } else if (firstCharacter == "0".single() && goalET.text.toString().length == 1) {
            return WeightGoal(0f, false)
        }

        return null
    }
}