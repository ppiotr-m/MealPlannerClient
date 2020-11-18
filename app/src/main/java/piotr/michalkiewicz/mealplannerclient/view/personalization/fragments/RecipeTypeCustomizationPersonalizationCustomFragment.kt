package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_recipe_type_customization.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.personalization.service.PersonalizationCustomFragment

class RecipeTypeCustomizationPersonalizationCustomFragment : PersonalizationCustomFragment(), View.OnClickListener {

    private val recipeTypeList = ArrayList<String>()
    private lateinit var typeNames: List<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fetchButtonsNames()
        return inflater.inflate(R.layout.fragment_recipe_type_customization, container, false)
    }

    private fun fetchButtonsNames() {
        typeNames = recipeServiceValuesDownloader.getAllRecipeTypesNames()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val buttonsLayout = activity?.findViewById<LinearLayout>(R.id.linearLayoutRecipeTypesButtons)
        initConfirmButton()
        addButtonsToLayout(buttonsLayout, typeNames, 3)
        initCustomizationButtons(buttonsLayout)

        super.onViewStateRestored(savedInstanceState)
    }

    private fun initCustomizationButtons(linearLayout: LinearLayout?) {
        val buttonsIds = ArrayList<Int>()

        for (i in 0 until (linearLayout ?: return).childCount) {
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
            navController.navigate(R.id.action_recipeTypeCustomizationPersonalizationFragment_to_cuisineCustomizationFragment)
        }
    }

    private fun upDateViewModel() {
        personalizationViewModel.setRecipeTypeSettings(recipeTypeList)
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
        if (!recipeTypeList.contains(element)) {
            activity?.findViewById<Button>(v.id)?.setBackgroundColor(ConstantValues.CHECKED_BUTTON_COLOR)
            recipeTypeList.add(element)
        } else {
            activity?.findViewById<Button>(v.id)?.setBackgroundColor(ConstantValues.DEFAULT_BUTTON_COLOR)
            recipeTypeList.remove(element)
        }
    }
}