package piotr.michalkiewicz.mealplannerclient.view.activities.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.SETTINGS_DATA
import piotr.michalkiewicz.mealplannerclient.view.interfaces.InitializableView
import piotr.michalkiewicz.mealplannerclient.view.presenters.SettingsActivityPresenter

class SettingsActivity : AppCompatActivity(), InitializableView<UserAccount> {

    private val presenter = SettingsActivityPresenter(this)

    private val editPasswordActivityContract = registerForActivityResult(EditPasswordActivityContract()) {
        result ->
        if(result == null) throw RuntimeException()
        presenter.data = result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        init()
    }

    private fun init(){
        setOnClickListeners()
        presenter.initSettingsViewWithData()
    }

    private fun setOnClickListeners() {
        editPasswordBtn.setOnClickListener{

            editPasswordActivityContract.launch(presenter.data)
/*
            val intent = Intent(this@SettingsActivity, EditPasswordActivity::class.java)
            intent.putExtra(SETTINGS_DATA, presenter.data)
            startActivity(intent)

 */
        }
        editLocationBtn.setOnClickListener{
            startActivity(Intent(this@SettingsActivity, EditLocationActivity::class.java))
        }
        increaseCookingTimeBtn.setOnClickListener {
            increasePreferredCookingTime()
        }
        subtractCookingTimeBtn.setOnClickListener {
            decreasePreferredCookingTime()
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
        presenter.increasePortionsPerMeal()
        portionsTV.text = presenter.portionsPerMeal.toString()
    }
    private fun decreasePortionsPerMeal(){
        presenter.decreasePortionsPerMeal()
        portionsTV.text = presenter.portionsPerMeal.toString()
    }
    private fun increaseMealsPerMealplan(){
        presenter.increaseMealsPerMealPlan()
        mealsPerMealPlanTV.text = presenter.mealsPerMealPlan.toString()
    }
    private fun decreaseMealsPerMealplan(){
        presenter.decreaseMealsPerMealPlan()
        mealsPerMealPlanTV.text = presenter.mealsPerMealPlan.toString()
    }
    private fun increasePreferredCookingTime(){
        presenter.increasePreferredCookingTime()
        preferedCookingTimeTV.text = presenter.preferredCookingTime.toString()
    }
    private fun decreasePreferredCookingTime(){
        presenter.decreasePreferredCookingTime()
        preferedCookingTimeTV.text = presenter.preferredCookingTime.toString()
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

    inner class EditPasswordActivityContract : ActivityResultContract<UserAccount, UserAccount>(){
        override fun createIntent(context: Context, input: UserAccount?): Intent {
            return Intent(context, EditPasswordActivity::class.java).apply{
                putExtra(SETTINGS_DATA, input)
            }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): UserAccount? {
            return if(resultCode == RESULT_OK) intent?.getSerializableExtra(SETTINGS_DATA) as UserAccount else
                null
        }
    }

    companion object {
        @JvmStatic val RESULT_OK = 5
    }

}
