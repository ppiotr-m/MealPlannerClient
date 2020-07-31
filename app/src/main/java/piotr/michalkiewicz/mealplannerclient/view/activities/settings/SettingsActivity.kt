package piotr.michalkiewicz.mealplannerclient.view.activities.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_settings.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.support.Constants
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
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
            startActivity(Intent(this@SettingsActivity, EditPasswordActivity::class.java))
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
        portionsTV.setText((currentValue+1).toString())
    }
    private fun decreasePortionsPerMeal(){
        val currentValue = portionsTV.text.toString().toInt()
        portionsTV.setText((currentValue-1).toString())
    }
    private fun increaseMealsPerMealplan(){
        val currentValue = mealsPerMealPlanTV.text.toString().toInt()
        mealsPerMealPlanTV.setText((currentValue+1).toString())
    }
    private fun decreaseMealsPerMealplan(){
        val currentValue = mealsPerMealPlanTV.text.toString().toInt()
        mealsPerMealPlanTV.setText((currentValue-1).toString())
    }
    private fun increasePreferredCookingTime(){
        val currentValue = preferedCookingTimeTV.text.toString().toInt()
   //     if(currentValue<)
        preferedCookingTimeTV.setText((currentValue+5).toString())
    }
    private fun decreasePrefferedCookingTime(){
        val currentValue = preferedCookingTimeTV.text.toString().toInt()
        if(currentValue>5) {
            preferedCookingTimeTV.setText((currentValue - 5).toString())
        }
    }

    override fun initWithData(data: UserAccount?, frameNr: Int) {
        Log.i(Constants.TAG, "User data: " + data?.email)
        Log.i(Constants.TAG, "Init with data, 2")
    }

}
