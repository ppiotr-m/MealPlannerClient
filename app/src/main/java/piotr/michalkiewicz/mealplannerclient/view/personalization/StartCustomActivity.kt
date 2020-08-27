package piotr.michalkiewicz.mealplannerclient.view.personalization

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings
import piotr.michalkiewicz.mealplannerclient.view.main_menu.MainMenuActivity
import piotr.michalkiewicz.mealplannerclient.view.personalization.fragments.DietCustomFragment
import piotr.michalkiewicz.mealplannerclient.view.personalization.fragments.DisIngredientsCustomFragment
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

class StartCustomActivity : AppCompatActivity(), FragmentCallback {

    private lateinit var startCustomizationBtn: Button
    private lateinit var skipCustomizationBtn: Button
    private lateinit var fragmentTransaction: FragmentTransaction
    private val userSettings = UserSettings()

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
        startCustomizationBtn.setOnClickListener(View.OnClickListener {
//            AllergyCustomizationActivity.java
//            DislikedIngredientsCustomization
//            MealsNumberCustomizationActivity
//            ServingsCustomizationActivity
//            CustomizationFinishActivity
            fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.dietCustomizationFragment, DietCustomFragment.newInstance(false))
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

            // TODO Start customization here
        })

        skipCustomizationBtn.setOnClickListener {
            val myIntent = Intent(this@StartCustomActivity, MainMenuActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun onVariableSelect(variable: String, from: Fragment) {
        when(from::class){
            DietCustomFragment::class -> userSettings.diets = variable
        }
        Log.i("onVariableSelect", userSettings.toString())
    }

    override fun onListSelect(variable: List<String>, from: Fragment) {
        when(from::class){
            DisIngredientsCustomFragment::class -> userSettings.unlikeIngredients = variable
        }
        Log.i("onListSelect", userSettings.toString())
    }
}