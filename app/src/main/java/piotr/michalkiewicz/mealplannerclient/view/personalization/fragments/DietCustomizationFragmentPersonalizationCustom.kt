package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.view.personalization.service.PersonalizationCustomFragment

class DietCustomizationFragmentPersonalizationCustom : PersonalizationCustomFragment(), View.OnClickListener {

    private val buttonsIds = ArrayList<Int>()
    private lateinit var diets: List<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fetchButtonsNames()
        return inflater.inflate(R.layout.fragment_diet_customization, container, false)
    }

    private fun fetchButtonsNames() {
        diets = recipeServiceValuesDownloader.getAllDietsNames()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val buttonsLayout = activity?.findViewById<LinearLayout>(R.id.linearButtonsDiet)
        addButtonsToLayout(buttonsLayout, diets, 1)
        initDietCustomizationButtons(buttonsLayout)

        super.onViewStateRestored(savedInstanceState)
    }

    private fun initDietCustomizationButtons(linearLayout: LinearLayout?) {
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

    private fun addClick(id: Int) {
        try {
            requireView().findViewById<View>(id).setOnClickListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View) {
        upDateViewModel(v)
        navController.navigate(R.id.action_dietCustomizationFragment_to_disIngredientsCustomizationFragment)
    }

    private fun upDateViewModel(v: View) {
        personalizationViewModel.setDiet(activity?.findViewById<Button>(v.id)?.text.toString())
    }
}