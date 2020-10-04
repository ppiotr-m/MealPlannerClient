package piotr.michalkiewicz.mealplannerclient.view.menu

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.content_main_menu.*
import kotlinx.android.synthetic.main.fragment_main_menu.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.view.settings.SettingsActivity

class MainMenuFragment : Fragment() {
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        navController = findNavController()
        initTopToolbar(view)
        setBottomNavigationMenu()
    }

    private fun initTopToolbar(view: View) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { v: View? -> }
    }

    private fun setBottomNavigationMenu() {
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.action_mainMenuFragment_to_homeScreenFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_cookbook -> {
                    navController.navigate(R.id.action_mainMenuFragment_to_cookbookScreenFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            //          startActivity(new Intent(MainMenuActivity.this, SettingsActivity.class));
        }
        if (item.itemId == R.id.action_user) {
            startActivity(Intent(activity, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}