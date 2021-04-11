package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.content_start_customization.*
import kotlinx.coroutines.runBlocking
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.UserServiceGenerator

class StartCustomizationFragment : PersonalizationCustomFragment() {

    private val userServiceGenerator = UserServiceGenerator()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.start_customization_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        putAwayCustomizationBtn.setOnClickListener {
            navController.navigate(R.id.action_startCustomizationFragment_to_homeScreenFragment)
        }

        skipCustomizationBtn.setOnClickListener {
            markCustomizationDone()
            navController.navigate(R.id.action_startCustomizationFragment_to_homeScreenFragment)
        }

        startCustomizationBtn.setOnClickListener {
            navController.navigate(R.id.action_startCustomizationFragment_to_dietCustomizationFragment)
        }
    }

    fun markCustomizationDone() {
        runBlocking {
            val helperUserSettings = userServiceGenerator.getUserSettings()
            helperUserSettings?.customizationDone = true
            if (helperUserSettings != null) {
                userServiceGenerator.updateUserSettings(helperUserSettings)
            }
        }
    }
}