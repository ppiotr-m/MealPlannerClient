package piotr.michalkiewicz.mealplannerclient.view.personalization

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings
import piotr.michalkiewicz.mealplannerclient.user.service_generator.UserServiceGenerator
import piotr.michalkiewicz.mealplannerclient.view.main_menu.MainMenuActivity
import piotr.michalkiewicz.mealplannerclient.view.personalization.fragments.*
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

class StartCustomActivity : AppCompatActivity(), FragmentCallback {

    private lateinit var startCustomizationBtn: Button
    private lateinit var skipCustomizationBtn: Button
    private lateinit var fragmentTransaction: FragmentTransaction
    private val userSettings = UserSettings()
    private lateinit var userServiceGenerator: UserServiceGenerator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_customization)

        initButtons()
        implButtons()
    }

    private fun initButtons() {
        startCustomizationBtn = findViewById(R.id.startCustomizationBtn)
        skipCustomizationBtn = findViewById(R.id.skipCustomizationBtn)
    }

    private fun implButtons() {
        startCustomizationBtn.setOnClickListener {
            fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.dietCustomizationFragment, DietCustomizationFragment.newInstance(false))
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        skipCustomizationBtn.setOnClickListener {
            val myIntent = Intent(this@StartCustomActivity, MainMenuActivity::class.java)
            updateUserSettings()
            startActivity(myIntent)
        }
    }

    override fun onVariableSelect(variable: String, from: Fragment) {
        when (from::class) {
            DietCustomizationFragment::class -> userSettings.diets = variable
        }
    }

    override fun onVariableSelectMulti(variable: String, from: Fragment, fieldName: String) {
        when (from::class) {
            MealsNumberCustomizationFragment::class -> {
                when (fieldName) {
                    UserSettings::portionPreferences.name -> userSettings.portionPreferences = variable.toInt()
                    UserSettings::cookingTimePreference.name -> userSettings.cookingTimePreference = variable.toInt()
                    UserSettings::mealsPerMealPlanPreference.name -> userSettings.mealsPerMealPlanPreference = variable.toInt()
                }
            }
        }
    }

    override fun onListSelect(variable: List<String>, from: Fragment) {
        when (from::class) {
            DisIngredientsCustomizationFragment::class -> userSettings.unlikeIngredients = variable
            AllergyCustomizationFragment::class -> userSettings.allergies = variable
            RecipeTypeCustomizationFragment::class -> userSettings.recipeTypes = variable
        }
    }

    fun updateUserSettings(){
        userSettings.customizationDone = true
        userServiceGenerator = UserServiceGenerator()
        userServiceGenerator.updateUserSettings(userSettings)
    }
}