package piotr.michalkiewicz.mealplannerclient.view

import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.renderscript.ScriptGroup
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import kotlinx.android.synthetic.main.activity_main.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.ActivityMainBinding
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.menu.fragments.HomeScreenFragment

class MainActivity : AppCompatActivity(), HomeScreenFragment.HomeScreenStartListener {

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    companion object {
        lateinit var MY_PREFERENCSES: SharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        initMyPreferences()
        createBinding()
        setupNavController()
        setSupportActionBar(toolbar)
        initTopToolbar()
        setBottomNavigationMenu()
    }

    private fun createBinding() {
        binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun initMyPreferences() {
        MY_PREFERENCSES = applicationContext.getSharedPreferences(
            ConstantValues.MY_PREFERENCE_NAME,
            MODE_PRIVATE
        )
    }

    private fun initTopToolbar() {
        drawerLayout = binding.drawerLayout
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeScreenFragment,
                R.id.cookbookScreenFragment,
                R.id.shoppingListFragment,
                R.id.nutritionScreenFragment
            ),
            drawerLayout
        )

        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun setBottomNavigationMenu() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)

        NavigationUI.setupWithNavController(
            bottom_navigation,
            (navHostFragment ?: return).findNavController()
        )
    }

    private fun showTopAndBottomToolbar() {
        topToolbarLayout.visibility = View.VISIBLE
        bottom_navigation.visibility = View.VISIBLE
    }

    override fun onHomeScreenStarted() {
        showTopAndBottomToolbar()
    }
}