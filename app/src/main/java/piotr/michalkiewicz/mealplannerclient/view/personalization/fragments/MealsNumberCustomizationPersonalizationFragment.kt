package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.children
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreference
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.CHECKED_BUTTON_COLOR
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.DEFAULT_BUTTON_COLOR
import piotr.michalkiewicz.mealplannerclient.view.personalization.PersonalizationFragment
import piotr.michalkiewicz.mealplannerclient.view.utils.ConstantValues.Companion.BASIC_COOKING_TIME_CUSTOMIZATION_BUTTONS
import piotr.michalkiewicz.mealplannerclient.view.utils.ConstantValues.Companion.BASIC_MEALS_PER_CUSTOMIZATION_BUTTONS
import piotr.michalkiewicz.mealplannerclient.view.utils.ConstantValues.Companion.BASIC_PORTION_CUSTOMIZATION_BUTTONS
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

class MealsNumberCustomizationPersonalizationFragment : PersonalizationFragment(), View.OnClickListener {

    private lateinit var confirmBtn: Button
    private var goBack = true
    private var initBaseValues = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_meals_number_customization, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(shouldGoBack: Boolean, shouldInitBaseValues: Boolean) = MealsNumberCustomizationPersonalizationFragment().apply {
            goBack = shouldGoBack
            initBaseValues = shouldInitBaseValues
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val portionAmountButtonsLayout = activity?.findViewById<LinearLayout>(R.id.portionAmountLayout)
        val cookingTimeButtonsLayout = activity?.findViewById<LinearLayout>(R.id.cookingTimePreferenceLayout)
        val mealsAmountButtonsLayout = activity?.findViewById<LinearLayout>(R.id.mealPerPlanLayout)

        addButtonsToLayout(portionAmountButtonsLayout, BASIC_PORTION_CUSTOMIZATION_BUTTONS, 1)
        addButtonsToLayout(cookingTimeButtonsLayout, BASIC_COOKING_TIME_CUSTOMIZATION_BUTTONS, 2)
        addButtonsToLayout(mealsAmountButtonsLayout, BASIC_MEALS_PER_CUSTOMIZATION_BUTTONS, 3)

        initConfirmButton()
        initCustomizationButtons(listOf(portionAmountButtonsLayout, cookingTimeButtonsLayout, mealsAmountButtonsLayout))
        if (initBaseValues) {
            initBaseButtonsValues()
        }
        super.onViewStateRestored(savedInstanceState)
    }

    private fun initBaseButtonsValues() {
        val baseValues = UserPreference.getBaseCookingValues()

        for ((key, value) in baseValues) {
            when (key) {
                UserPreference::portionPreferences.name -> markButton(value, activity?.findViewById(R.id.portionAmountLayout)!!)
                UserPreference::cookingTimePreference.name -> markButton(value, activity?.findViewById(R.id.cookingTimePreferenceLayout)!!)
                UserPreference::mealsPerMealPlanPreference.name -> markButton(value, activity?.findViewById(R.id.mealPerPlanLayout)!!)
            }
        }
    }

    private fun initCustomizationButtons(linearLayouts: List<LinearLayout?>) {
        val buttonsIds = ArrayList<Int>()

        linearLayouts.forEach {
            for (i in 0 until it!!.childCount) {
                val v: View = it.getChildAt(i)
                if (v is Button) {
                    v.layoutParams.width = 150
                    buttonsIds.add(v.id)
                }
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
        val element = activity?.findViewById<Button>(v.id)?.text.toString()
        val parent: LinearLayout = v.parent as LinearLayout
        var fieldName = ""

        when (parent.id) {
            R.id.portionAmountLayout -> fieldName = UserPreference::portionPreferences.name
            R.id.cookingTimePreferenceLayout -> fieldName = UserPreference::cookingTimePreference.name
            R.id.mealPerPlanLayout -> fieldName = UserPreference::mealsPerMealPlanPreference.name
        }
        fragmentCallback.onVariableSelectMulti(element, this, fieldName)
        markButton(v, parent)
    }

    private fun markButton(v: View, parent: LinearLayout) {
        parent.children.iterator().forEach {
            if (it.id != v.id) {
                it.setBackgroundColor(DEFAULT_BUTTON_COLOR)
            } else {
                it.setBackgroundColor(CHECKED_BUTTON_COLOR)
            }
        }
    }

    private fun markButton(v: Int, parent: LinearLayout) {
        parent.children.iterator().forEach {
            it as Button
            if (it.text.toString() != v.toString()) {
                it.setBackgroundColor(DEFAULT_BUTTON_COLOR)
            } else {
                it.setBackgroundColor(CHECKED_BUTTON_COLOR)
            }
        }
    }

    private fun initConfirmButton() {
        confirmBtn = activity?.findViewById(R.id.confirmButton)!!

        confirmBtn.setOnClickListener {
            if (goBack) {
                closeFragment()
            } else {
                runAllergyCustomizationFragment()
            }
        }
    }

    private fun runAllergyCustomizationFragment() {
        activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.dietCustomizationFragment, AllergyCustomizationFragment.newInstance(shouldGoBack = false))
                ?.addToBackStack(null)
                ?.commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallback = context as FragmentCallback
    }
}