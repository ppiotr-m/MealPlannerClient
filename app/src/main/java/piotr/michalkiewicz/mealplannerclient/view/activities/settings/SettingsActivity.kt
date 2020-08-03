package piotr.michalkiewicz.mealplannerclient.view.activities.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.content_recipe.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.support.Constants
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.SETTINGS_DATA
import piotr.michalkiewicz.mealplannerclient.view.interfaces.InitializableView
import piotr.michalkiewicz.mealplannerclient.view.presenters.SettingsActivityPresenter

class SettingsActivity : AppCompatActivity(), InitializableView<UserAccount> {

    private val presenter = SettingsActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        init();
    }

    private fun init(){
        setOnClickListeners()
        presenter.initSettingsViewWithData()
    }

    private fun setOnClickListeners() {
        editPasswordBtn.setOnClickListener{
            val intent = Intent(this@SettingsActivity, EditPasswordActivity::class.java)
            intent.putExtra(SETTINGS_DATA, presenter.data)
            startActivity(intent)
        }
        editLocationBtn.setOnClickListener{
            startActivity(Intent(this@SettingsActivity, EditLocationActivity::class.java))
        }
        increaseCookingTimeBtn.setOnClickListener {
            increasePreferredCookingTime()
        }
        subtractCookingTimeBtn.setOnClickListener {
            decreasePrefferedCookingTime()
        }
        addPortionsBtn.setOnClickListener{
            increasePortionsPerMeal()
        }
        subtractPortionsBtn.setOnClickListener{
            decreasePortionsPerMeal()
        }
        addMealsPerMealPlanBtn.setOnClickListener {
            increaseMealsPerMealplan()
        }
        subtractMealsBtn.setOnClickListener {
            decreaseMealsPerMealplan()
        }

    }

    private fun increasePortionsPerMeal(){
        val currentValue = portionsTV.text.toString().toInt()
        presenter.increasePortionsPerMeal()
        portionsTV.setText((currentValue+1).toString())
    }
    private fun decreasePortionsPerMeal(){
        val currentValue = portionsTV.text.toString().toInt()
        presenter.decreasePortionsPerMeal()
        portionsTV.setText((currentValue-1).toString())
    }
    private fun increaseMealsPerMealplan(){
        val currentValue = mealsPerMealPlanTV.text.toString().toInt()
        presenter.increaseMealsPerMealPlan()
        mealsPerMealPlanTV.setText((currentValue+1).toString())
    }
    private fun decreaseMealsPerMealplan(){
        val currentValue = mealsPerMealPlanTV.text.toString().toInt()
        presenter.decreaseMealsPerMealPlan()
        mealsPerMealPlanTV.setText((currentValue-1).toString())
    }
    private fun increasePreferredCookingTime(){
        val currentValue = preferedCookingTimeTV.text.toString().toInt()
        presenter.increasePreferredCookingTime()
        preferedCookingTimeTV.setText((currentValue+5).toString())
    }
    private fun decreasePrefferedCookingTime(){
        val currentValue = preferedCookingTimeTV.text.toString().toInt()
        presenter.decreasePreferredCookingTime()
        if(currentValue>5) {
            preferedCookingTimeTV.setText((currentValue - 5).toString())
        }
    }

    override fun initWithData(data: UserAccount?, frameNr: Int) {
        emailTV.text = data?.email
        passwordTV.text = data?.password
        preferedCookingTimeTV.text = data?.userSettings?.cookingTimePreference?.toString()
        portionsTV.text = data?.userSettings?.portionPreferences.toString()
        mealsPerMealPlanTV.text = data?.userSettings?.mealsPerMealPlanPreference.toString()
        sexTV.text = data?.userSettings?.nutritionProfileSettings?.sex?.value.toString()
        heightTV.text = data?.userSettings?.nutritionProfileSettings?.height.toString()
        weightTV.text = data?.userSettings?.nutritionProfileSettings?.weight.toString()
        usernameTV.text = data?.username
    }

}
