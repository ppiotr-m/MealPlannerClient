package piotr.michalkiewicz.mealplannerclient.view.menu

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.view.menu.fragments.ShoppingListFragment
import piotr.michalkiewicz.mealplannerclient.view.menu.fragments.CookbookScreenFragment
import piotr.michalkiewicz.mealplannerclient.view.menu.fragments.HomeScreenFragment
import piotr.michalkiewicz.mealplannerclient.view.menu.fragments.NutritionScreenFragment
import piotr.michalkiewicz.mealplannerclient.view.settings.SettingsActivity

class MainMenuFragment : Fragment(){

    private var bottomNavigationView: BottomNavigationView? = null
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }
    private fun init(view: View) {
        navController = findNavController()
        initTopToolbar(view)
        assignUIElements(view)
        setBottomNavigationMenu()
        setFragment(HomeScreenFragment())
    }

    private fun initTopToolbar(view: View) {
        val toolbar: Toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            (activity as AppCompatActivity).getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { v: View? -> }
    }

    private fun assignUIElements(view: View) {
        bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
    }

    private fun setBottomNavigationMenu() {
        bottomNavigationView!!.setOnNavigationItemSelectedListener { item ->
            /*
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
            }*/
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_home -> navController.navigate(R.id.action_mainMenuFragment_to_homeScreenFragment)
                R.id.navigation_cookbook -> navController.navigate(R.id.action_mainMenuFragment_to_cookbookScreenFragment)
                //R.id.navigation_ -> navController.navigate(R.id.action_mainMenuFragment_to_shoppingListFragment)
                //R.id.navigation_nutrition -> selectedFragment = NutritionScreenFragment()
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
        //getFragmentManager()?.beginTransaction().replace(R.id.mainMenuFragmentContainer,
        //        fragment).commit()
    }

    //fun onCreateOptionsMenu(menu: Menu?): Boolean {
      //b  getMenuInflater().inflate(R.menu.top_toolbar_menu, menu)
        // return super.onCreateOptionsMenu(menu!!)
    //}

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