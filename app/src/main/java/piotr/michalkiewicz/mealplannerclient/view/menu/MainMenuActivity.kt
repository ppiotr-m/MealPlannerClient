package piotr.michalkiewicz.mealplannerclient.view.menu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.view.main_menu.fragments.ShoppingListFragment
import piotr.michalkiewicz.mealplannerclient.view.menu.fragments.CookbookScreenFragment
import piotr.michalkiewicz.mealplannerclient.view.menu.fragments.HomeScreenFragment
import piotr.michalkiewicz.mealplannerclient.view.menu.fragments.NutritionScreenFragment
import piotr.michalkiewicz.mealplannerclient.view.settings.SettingsActivity

class MainMenuActivity : AppCompatActivity() {
    private var bottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        init()
    }

    private fun init() {
        initTopToolbar()
        assignUIElements()
        setBottomNavigationMenu()
        setFragment(HomeScreenFragment())
    }

    private fun initTopToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { v: View? -> }
    }

    private fun assignUIElements() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)
    }

    private fun setBottomNavigationMenu() {
        bottomNavigationView!!.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_home -> selectedFragment = HomeScreenFragment()
                R.id.navigation_cookbook -> selectedFragment = CookbookScreenFragment()
                R.id.navigation_mealplans -> selectedFragment = ShoppingListFragment()
                R.id.navigation_nutrition -> selectedFragment = NutritionScreenFragment()
            }
            if (selectedFragment != null) {
                setFragment(selectedFragment)
                true
            } else {
                false
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.mainMenuFragmentContainer,
                fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            //          startActivity(new Intent(MainMenuActivity.this, SettingsActivity.class));
        }
        if (item.itemId == R.id.action_user) {
            startActivity(Intent(this@MainMenuActivity, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}