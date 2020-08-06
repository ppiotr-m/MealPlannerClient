package piotr.michalkiewicz.mealplannerclient.view.activities.settings

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.view.interfaces.InitializableView
import piotr.michalkiewicz.mealplannerclient.view.presenters.SettingsActivityPresenter

class SettingsActivity : AppCompatActivity(), InitializableView<UserAccount>, ActivityResultCaller {

    private val presenter = SettingsActivityPresenter(this)
    private val MALE = "Male"
    private val FEMALE = "Female"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        init()
    }

    override fun onResume() {
        super.onResume()

        initWithData(presenter.data, 1)
    }

    private fun init(){
        setOnClickListeners()
        presenter.initSettingsViewWithData()
        initSexSelectionRadioGroup()
    }

    private fun setOnClickListeners() {
        editPasswordBtn.setOnClickListener{
            setLauncherForActivityResult(EditPasswordActivityContract())
        }
        editLocationBtn.setOnClickListener{
            setLauncherForActivityResult(EditLocationActivityContract())
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
        sexSelectionRadioGroup.setOnCheckedChangeListener { _, i ->
            if(i == R.id.maleRadioBtn){
                if(findViewById<RadioButton>(i).isChecked){
                    presenter.data?.sex = MALE
                }
            }
            else{
                if(findViewById<RadioButton>(i).isChecked){
                    presenter.data?.sex = FEMALE
                }
            }
        }

    }

    private fun setLauncherForActivityResult(contract: ActivityResultContract<UserAccount, UserAccount>){
        val launcher = registerForActivityResult(contract){
            if(it!=null) {
                presenter.data = it
            }
        }
        launcher?.launch(presenter.data)
    }

    private fun initSexSelectionRadioGroup(){
        if(presenter.data.sex==null) return
        if(presenter.data.sex == MALE) sexSelectionRadioGroup.check(R.id.maleRadioBtn)
        else sexSelectionRadioGroup.check(R.id.femaleRadioBtn)
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
        locationTV.text = data?.location
        preferedCookingTimeTV.text = data?.userSettings?.cookingTimePreference?.toString()
        portionsTV.text = data?.userSettings?.portionPreferences.toString()
        mealsPerMealPlanTV.text = data?.userSettings?.mealsPerMealPlanPreference.toString()
        heightTV.text = data?.userSettings?.nutritionProfileSettings?.height.toString()
        weightTV.text = data?.userSettings?.nutritionProfileSettings?.weight.toString()
        usernameTV.text = data?.username
    }

    companion object {
        @JvmStatic val RESULT_OK : Int = 1033
    }
}
