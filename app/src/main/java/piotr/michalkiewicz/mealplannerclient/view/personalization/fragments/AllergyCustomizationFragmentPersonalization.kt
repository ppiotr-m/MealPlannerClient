package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_allergy_customization.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.personalization.service.PersonalizationCustomFragment
import piotr.michalkiewicz.mealplannerclient.view.personalization.service.PersonalizationService

class AllergyCustomizationFragmentPersonalization : PersonalizationCustomFragment(), View.OnClickListener {

    private val allergiesList = ArrayList<String>()
    private val personalizationService: PersonalizationService = PersonalizationService()
    private lateinit var allergiesNames: List<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fetchButtonsNames()
        return inflater.inflate(R.layout.fragment_allergy_customization, container, false)
    }

    private fun fetchButtonsNames() {
        allergiesNames = recipeServiceValuesDownloader.getAllAllergiesNames()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val allergiesLinearLayout = activity?.findViewById<LinearLayout>(R.id.allergiesLinearLayout)
        addButtonsToLayout(allergiesLinearLayout, allergiesNames, 1)
        initConfirmButton()
        initButtonsFromLayout(allergiesLinearLayout)

        super.onViewStateRestored(savedInstanceState)
    }

    private fun initButtonsFromLayout(linearLayout: LinearLayout?) {
        val buttonsIds = ArrayList<Int>()

        for (i in 0 until linearLayout!!.childCount) {
            val v: View = linearLayout.getChildAt(i)
            if (v is Button) {
                buttonsIds.add(v.id)
            }
        }
        for (buttonId in buttonsIds) {
            addClick(buttonId)
        }
    }

    private fun initConfirmButton() {
        confirmBtn.setOnClickListener {
            upDateViewModel()
            finishCustomization()
            navController.navigate(R.id.action_allergyCustomizationFragment_to_homeScreenFragment)
        }
    }

    private fun finishCustomization() {
        val userPreference = personalizationViewModel.getUserPreference()
        userPreference?.let {
            personalizationService.updateUserPreferences(it, context)
            personalizationViewModel.clearUserPreference()
        }
    }

    private fun upDateViewModel() {
        personalizationViewModel.setAllergies(allergiesList)
    }

    private fun addClick(id: Int) {
        try {
            requireView().findViewById<View>(id).setOnClickListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View) {
        val element = activity?.findViewById<Button>(v.id)?.text.toString()
        markButton(element, v)
    }

    private fun markButton(element: String, v: View) {
        if (!allergiesList.contains(element)) {
            activity?.findViewById<Button>(v.id)?.setBackgroundColor(ConstantValues.CHECKED_BUTTON_COLOR)
            allergiesList.add(element)
        } else {
            activity?.findViewById<Button>(v.id)?.setBackgroundColor(ConstantValues.DEFAULT_BUTTON_COLOR)
            allergiesList.remove(element)
        }
    }
}