package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.content_start_customization.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.view.personalization.service.PersonalizationCustomFragment
import piotr.michalkiewicz.mealplannerclient.view.personalization.service.PersonalizationService

class StartCustomizationFragmentPersonalizationCustom : PersonalizationCustomFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.start_customization_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        putAwayCustomizationBtn.setOnClickListener{
            navController.navigate(R.id.action_startCustomizationFragment_to_homeScreenFragment)
        }

        skipCustomizationBtn.setOnClickListener{
            PersonalizationService().markCustomizationDone()
            navController.navigate(R.id.action_startCustomizationFragment_to_homeScreenFragment)
        }

        startCustomizationBtn.setOnClickListener{
            navController.navigate(R.id.action_startCustomizationFragment_to_dietCustomizationFragment)
        }
    }
}