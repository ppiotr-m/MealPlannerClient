package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_cuisine_customization.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.view.personalization.service.PersonalizationCustomFragment

class CuisineCustomizationFragmentPersonalizationCustom : PersonalizationCustomFragment(), View.OnClickListener {

    private val cuisineList = ArrayList<String>()
    private lateinit var cuisineNames: List<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fetchButtonsNames()
        return inflater.inflate(R.layout.fragment_cuisine_customization, container, false)
    }

    private fun fetchButtonsNames() {
        cuisineNames = recipeServiceValuesDownloader.getAllCuisinesNames()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val buttonsLayout = activity?.findViewById<LinearLayout>(R.id.linearLayoutCuisineButtons)
        initConfirmButton()
        addButtonsToLayout(buttonsLayout, cuisineNames, 3)
        initCustomizationButtons(buttonsLayout)

        super.onViewStateRestored(savedInstanceState)
    }

    private fun initCustomizationButtons(linearLayout: LinearLayout?) {
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
            navController.navigate(R.id.action_cuisineCustomizationFragment_to_mealsNumberCustomizationPersonalizationFragment)
        }
    }

    private fun upDateViewModel() {
        personalizationViewModel.setCuisine(cuisineList)
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
        if (!cuisineList.contains(element)) {
            activity?.findViewById<Button>(v.id)?.setBackgroundColor(piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.CHECKED_BUTTON_COLOR)
            cuisineList.add(element)
        } else {
            activity?.findViewById<Button>(v.id)?.setBackgroundColor(piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.DEFAULT_BUTTON_COLOR)
            cuisineList.remove(element)
        }
    }
}