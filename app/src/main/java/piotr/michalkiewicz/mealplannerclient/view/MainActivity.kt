package piotr.michalkiewicz.mealplannerclient.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.menu.fragments.HomeScreenFragment

class MainActivity : AppCompatActivity(), HomeScreenFragment.HomeScreenStartListener {

    private lateinit var navController: NavController

    companion object {
        lateinit var MY_PREFERENCSES: SharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initMyPreferences()
        navController = ((supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
                ?: return) as NavHostFragment).navController
        setSupportActionBar(findViewById(R.id.toolbar))
        initTopToolbar()
        setBottomNavigationMenu()
    }

    private fun initMyPreferences() {
        MY_PREFERENCSES = applicationContext.getSharedPreferences(
                ConstantValues.MY_PREFERENCE_NAME,
                MODE_PRIVATE
        )
    }

    private fun initTopToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }


    private fun setBottomNavigationMenu() {
        NavigationUI.setupWithNavController(bottom_navigation, navController)
    }

    private fun showTopAndBottomToolbar() {
        topToolbarLayout.visibility = View.VISIBLE
        bottom_navigation.visibility = View.VISIBLE
    }

    override fun onHomeScreenStarted() {
        showTopAndBottomToolbar()
    }
}