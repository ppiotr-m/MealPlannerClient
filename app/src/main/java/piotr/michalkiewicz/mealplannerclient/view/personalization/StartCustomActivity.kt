package piotr.michalkiewicz.mealplannerclient.view.personalization

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.UserServiceGenerator
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreference
import piotr.michalkiewicz.mealplannerclient.view.personalization.fragments.*
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

class StartCustomActivity : AppCompatActivity(), FragmentCallback {

    private lateinit var startCustomizationBtn: Button
    private lateinit var skipCustomizationBtn: Button
    private lateinit var fragmentTransaction: FragmentTransaction
    private val userPreference = UserPreference()
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
            //Here it opens MainMenu
            //val myIntent = Intent(this@StartCustomActivity, MainMenuActivity::class.java)
            updateUserSettings()
            //startActivity(myIntent)
        }
    }

    override fun onVariableSelect(variable: String, from: Fragment) {
        when (from::class) {
            DietCustomizationFragment::class -> userPreference.diet = variable
        }
    }

    override fun onVariableSelectMulti(variable: String, from: Fragment, fieldName: String) {
        when (from::class) {
            MealsNumberCustomizationPersonalizationFragment::class -> {
                when (fieldName) {
                    UserPreference::portionPreferences.name -> userPreference.portionPreferences = variable.toInt()
                    UserPreference::cookingTimePreference.name -> userPreference.cookingTimePreference = variable.toInt()
                    UserPreference::mealsPerMealPlanPreference.name -> userPreference.mealsPerMealPlanPreference = variable.toInt()
                }
            }
        }
    }

    override fun onListSelect(variable: List<String>, from: Fragment) {
        when (from::class) {
            DisIngredientsCustomizationFragment::class -> userPreference.unlikeIngredients = variable
            AllergyCustomizationFragment::class -> userPreference.allergies = variable
            RecipeTypeCustomizationPersonalizationFragment::class -> userPreference.recipeTypes = variable
            CuisineCustomizationFragment::class -> userPreference.cuisine = variable
        }
    }

    fun updateUserSettings(){
        userServiceGenerator = UserServiceGenerator()
        userServiceGenerator.updateUserPreference(userPreference)
    }
}