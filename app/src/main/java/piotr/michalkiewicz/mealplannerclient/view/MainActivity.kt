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

        binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeScreenFragment, R.id.cookbookScreenFragment, R.id.shoppingListFragment),
            drawerLayout
        )

        init(binding)
    }

    private fun init(binding: ActivityMainBinding) {

        initMyPreferences()
        //TODO add function for defining navController
        //TODO add function for defining appBarConfig
        //TODO add function for defining binding
        setSupportActionBar(toolbar)
        initTopToolbar(binding)
        setBottomNavigationMenu()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun initMyPreferences() {

        MY_PREFERENCSES = applicationContext.getSharedPreferences(
            ConstantValues.MY_PREFERENCE_NAME,
            MODE_PRIVATE
        )
    }

    private fun initTopToolbar(binding: ActivityMainBinding) {

        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

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