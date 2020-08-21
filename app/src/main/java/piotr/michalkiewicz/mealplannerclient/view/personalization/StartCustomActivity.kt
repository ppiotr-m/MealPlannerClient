package piotr.michalkiewicz.mealplannerclient.view.personalization

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.view.main_menu.MainMenuActivity
import piotr.michalkiewicz.mealplannerclient.view.personalization.fragments.DietCustomFragment
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

class StartCustomActivity : AppCompatActivity(), FragmentCallback {

    private lateinit var startCustomizationBtn: Button
    private lateinit var skipCustomizationBtn: Button
    private lateinit var fragmentTransaction: FragmentTransaction

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
            fragmentTransaction.add(R.id.dietCustomizationFragment, DietCustomFragment.newInstance("value"))
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit()

            Log.i("eee", "eee")
            // TODO Start customization here
        })

        skipCustomizationBtn.setOnClickListener(View.OnClickListener {
            val myIntent = Intent(this@StartCustomActivity, MainMenuActivity::class.java)
            startActivity(myIntent)
        })

        fun FragmentManager.instantiate(className: String): Fragment {
            return fragmentFactory.instantiate(ClassLoader.getSystemClassLoader(), className)
        }

    }

    override fun onVariableSelect(variable: String, from: Fragment) {
        Log.i("onVariableSelect", variable)
        Log.i("from", from.toString())
    }
}