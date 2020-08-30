package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.CHECKED_BUTTON_COLOR
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.DEFAULT_BUTTON_COLOR
import piotr.michalkiewicz.mealplannerclient.view.personalization.PersonalizationFragment
import piotr.michalkiewicz.mealplannerclient.view.utils.ConstantValues.Companion.DIS_LIKE_INGREDIENTS_CUSTOMIZATION_BUTTONS
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

class DisIngredientsCustomizationFragment : PersonalizationFragment(), View.OnClickListener {

    private val productsList = ArrayList<String>()
    private lateinit var confirmBtn: Button
    private var goBack = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_disliked_ingredients_cust, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(shouldGoBack: Boolean) = DisIngredientsCustomizationFragment().apply {
            goBack = shouldGoBack
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallback = context as FragmentCallback
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val buttonsLayout = activity?.findViewById<LinearLayout>(R.id.linearLayoutIngredientsButtons)
        initConfirmButton()
        addButtonsToLayout(buttonsLayout, DIS_LIKE_INGREDIENTS_CUSTOMIZATION_BUTTONS, 1)
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
        confirmBtn = activity?.findViewById(R.id.confirmButton)!!

        confirmBtn.setOnClickListener {
            fragmentCallback.onListSelect(productsList, this)
            if (goBack) {
                closeFragment()
            } else {
                runRecipeTypeCustomizationFragment()
            }
        }
    }

    private fun runRecipeTypeCustomizationFragment() {
        activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.dietCustomizationFragment, RecipeTypeCustomizationPersonalizationFragment.newInstance(shouldGoBack = false))
                ?.addToBackStack(null)
                ?.commit()
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