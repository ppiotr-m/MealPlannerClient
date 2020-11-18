package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.children
import kotlinx.android.synthetic.main.fragment_meals_number_customization.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreference
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.CHECKED_BUTTON_COLOR
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.DEFAULT_BUTTON_COLOR
import piotr.michalkiewicz.mealplannerclient.view.personalization.service.PersonalizationCustomFragment

class MealsNumberCustomizationPersonalizationCustomFragment : PersonalizationCustomFragment(),
    View.OnClickListener {

    private var initBaseValues = false
    private val BASIC_PORTION_CUSTOMIZATION_BUTTONS = mutableListOf("1", "2", "3", "4")
    private val BASIC_COOKING_TIME_CUSTOMIZATION_BUTTONS = mutableListOf("15", "30", "45", "60")
    private val BASIC_MEALS_PER_CUSTOMIZATION_BUTTONS = mutableListOf("1", "2", "3", "4", "5")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meals_number_customization, container, false)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val portionAmountButtonsLayout =
            activity?.findViewById<LinearLayout>(R.id.portionAmountLayout)
        val cookingTimeButtonsLayout =
            activity?.findViewById<LinearLayout>(R.id.cookingTimePreferenceLayout)
        val mealsAmountButtonsLayout = activity?.findViewById<LinearLayout>(R.id.mealPerPlanLayout)

        addButtonsToLayout(portionAmountButtonsLayout, BASIC_PORTION_CUSTOMIZATION_BUTTONS, 1)
        addButtonsToLayout(cookingTimeButtonsLayout, BASIC_COOKING_TIME_CUSTOMIZATION_BUTTONS, 2)
        addButtonsToLayout(mealsAmountButtonsLayout, BASIC_MEALS_PER_CUSTOMIZATION_BUTTONS, 3)

        initConfirmButton()
        initCustomizationButtons(
            listOf(
                portionAmountButtonsLayout,
                cookingTimeButtonsLayout,
                mealsAmountButtonsLayout
            )
        )
        initBaseButtonsValues()

        super.onViewStateRestored(savedInstanceState)
    }

    private fun initBaseButtonsValues() {
        val userPreferenceValues = getUserPreferenceValues()

        for ((key, value) in userPreferenceValues) {
            when (key) {
                UserPreference::portionPreferences.name -> initBasicButtons(
                    value, activity?.findViewById(R.id.portionAmountLayout)
                        ?: return
                )
                UserPreference::cookingTimePreference.name -> initBasicButtons(
                    value, activity?.findViewById(R.id.cookingTimePreferenceLayout)
                        ?: return
                )
                UserPreference::mealsPerMealPlanPreference.name -> initBasicButtons(
                    value, activity?.findViewById(R.id.mealPerPlanLayout)
                        ?: return
                )
            }
        }
    }

    private fun getUserPreferenceValues(): Map<String, Int> {
        val portionPreference = personalizationViewModel.getPortionPreferences() ?: 4
        val cookingTimePreference = personalizationViewModel.getCookingTimePreferences() ?: 30
        val mealPerPlanPreference = personalizationViewModel.getMealsPerPlanPreferences() ?: 4

        return mapOf(
            UserPreference::portionPreferences.name to portionPreference,
            UserPreference::cookingTimePreference.name to cookingTimePreference,
            UserPreference::mealsPerMealPlanPreference.name to mealPerPlanPreference
        )
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

        when (parent.id) {
            R.id.portionAmountLayout -> personalizationViewModel.setPortionPreferences(element.toInt())
            R.id.cookingTimePreferenceLayout -> personalizationViewModel.setCookingTimePreferences(
                element.toInt()
            )
            R.id.mealPerPlanLayout -> personalizationViewModel.setMealsPerPlanPreferences(element.toInt())
        }
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

    private fun initBasicButtons(v: Int, parent: LinearLayout) {
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
        confirmBtn.setOnClickListener {
            navController.navigate(R.id.action_mealsNumberCustomizationPersonalizationFragment_to_allergyCustomizationFragment)
        }
    }
}