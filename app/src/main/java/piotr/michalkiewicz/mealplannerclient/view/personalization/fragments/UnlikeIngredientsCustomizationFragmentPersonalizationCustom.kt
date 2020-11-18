package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_disliked_ingredients_cust.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.CHECKED_BUTTON_COLOR
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.DEFAULT_BUTTON_COLOR
import piotr.michalkiewicz.mealplannerclient.view.personalization.service.PersonalizationCustomFragment

class UnlikeIngredientsCustomizationFragmentPersonalizationCustom : PersonalizationCustomFragment(),
    View.OnClickListener {

    private val productsList = ArrayList<String>()
    private lateinit var products: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fetchButtonsNames()
        return inflater.inflate(R.layout.fragment_disliked_ingredients_cust, container, false)
    }

    private fun fetchButtonsNames() {
        products = recipeServiceValuesDownloader.getAllProductsNames()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val buttonsLayout =
            activity?.findViewById<LinearLayout>(R.id.linearLayoutIngredientsButtons)
        initConfirmButton()
        addButtonsToLayout(buttonsLayout, products, 1)
        initIngredientsButtons(buttonsLayout)

        super.onViewStateRestored(savedInstanceState)
    }

    private fun initIngredientsButtons(linearLayout: LinearLayout?) {
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
            updateViewModel()
            navController.navigate(R.id.action_disIngredientsCustomizationFragment_to_recipeTypeCustomizationPersonalizationFragment)
        }
    }

    private fun updateViewModel() {
        personalizationViewModel.setUnLikeIngredients(productsList)
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
        if (!productsList.contains(element)) {
            activity?.findViewById<Button>(v.id)?.setBackgroundColor(CHECKED_BUTTON_COLOR)
            productsList.add(element)
        } else {
            activity?.findViewById<Button>(v.id)?.setBackgroundColor(DEFAULT_BUTTON_COLOR)
            productsList.remove(element)
        }
    }
}