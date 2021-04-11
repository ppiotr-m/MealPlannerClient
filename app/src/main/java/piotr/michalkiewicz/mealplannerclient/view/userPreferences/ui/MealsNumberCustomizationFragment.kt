package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButtonToggleGroup
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentMealsNumberCustomizationBinding
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreferences
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PERSONALIZATION_PROCESS
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.USER_PREFERENCES_FRAGMENT
import piotr.michalkiewicz.mealplannerclient.utils.EspressoIdlingResource
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Resource

class MealsNumberCustomizationFragment : PersonalizationCustomFragment(),
    View.OnClickListener {

    private val BASIC_PORTION_CUSTOMIZATION_BUTTONS = mutableListOf("1", "2", "3", "4")
    private val BASIC_COOKING_TIME_CUSTOMIZATION_BUTTONS = mutableListOf("15", "30", "45", "60")
    private val BASIC_MEALS_PER_CUSTOMIZATION_BUTTONS = mutableListOf("1", "2", "3", "4", "5")
    private lateinit var userPreferenceValues: MutableMap<String, Int>
    private val args: MealsNumberCustomizationFragmentArgs by navArgs()
    private lateinit var binding: FragmentMealsNumberCustomizationBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_meals_number_customization,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        navController = findNavController()
        viewModel = getViewModel(args.originOfNavigation)
        viewModel.initMealsNumbersCustomizationButtonsData()
        EspressoIdlingResource.increment()
        viewModel.mealsNumbersCustomizationButtonsDataReady.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled().let {
                takeActionBasedOnResult(viewModel.userPreferencesResource.value!!)
                EspressoIdlingResource.decrement()
            }
        })

        initConfirmButton()
    }

    private fun takeActionBasedOnResult(resource: Resource<UserPreferences?>){
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                resource.data?.let { it1 ->
                    initAllButtons()
                    initBaseButtonsValues()
                }
            }
            Resource.Status.ERROR -> {
                Log.i("UserPreferencesFragment", "Error!")
                Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
            }
            Resource.Status.LOADING ->
                Log.i("DislikedIngredientsFrat", "Loading")
        }
    }

    private fun initAllButtons() {
        addButtonsToLayout(binding.portionAmountLayout, BASIC_PORTION_CUSTOMIZATION_BUTTONS, 1)
        addButtonsToLayout(
            binding.cookingTimePreferenceLayout,
            BASIC_COOKING_TIME_CUSTOMIZATION_BUTTONS,
            2
        )
        addButtonsToLayout(binding.mealPerPlanLayout, BASIC_MEALS_PER_CUSTOMIZATION_BUTTONS, 3)

        initCustomizationButtons(
            listOf(
                binding.portionAmountLayout,
                binding.cookingTimePreferenceLayout,
                binding.mealPerPlanLayout
            )
        )
    }

    private fun initCustomizationButtons(linearLayouts: List<MaterialButtonToggleGroup>) {

        linearLayouts.forEach {
            for (i in 0 until it.childCount) {
                val v: View = it.getChildAt(i)
                if (v is Button) {
                    v.layoutParams.width = 200
                    addClick(v.id)
                }
            }
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
        val parent: MaterialButtonToggleGroup = v.parent as MaterialButtonToggleGroup

        when (parent.id) {
            R.id.portionAmountLayout -> userPreferenceValues[UserPreferences::portionPreferences.name] =
                element.toInt()
            R.id.cookingTimePreferenceLayout ->
                userPreferenceValues[UserPreferences::cookingTimePreference.name] = element.toInt()
            R.id.mealPerPlanLayout ->
                userPreferenceValues[UserPreferences::mealsPerMealPlanPreference.name] =
                    element.toInt()
        }
        markButton(v, parent)
    }

    private fun markButton(v: View, parent: MaterialButtonToggleGroup) {
        parent.children.iterator().forEach {
            if (it.id == v.id) {
                parent.check(v.id)
            }
        }
    }

    private fun initBaseButtonsValues() {
        userPreferenceValues = getUserCookingPreferenceValues()

        for ((key, value) in userPreferenceValues) {
            when (key) {
                UserPreferences::portionPreferences.name -> initBasicButtons(
                    value, binding.portionAmountLayout
                )
                UserPreferences::cookingTimePreference.name -> initBasicButtons(
                    value, binding.cookingTimePreferenceLayout
                )
                UserPreferences::mealsPerMealPlanPreference.name -> initBasicButtons(
                    value, binding.mealPerPlanLayout
                )
            }
        }
    }

    private fun getUserCookingPreferenceValues(): MutableMap<String, Int> {
        val portionPreference = viewModel.getUserPortionPreferences() ?: 4
        val cookingTimePreference = viewModel.getUserCookingTimePreferences() ?: 30
        val mealPerPlanPreference = viewModel.getUserMealsPerPlanPreferences() ?: 4

        return mutableMapOf(
            UserPreferences::portionPreferences.name to portionPreference,
            UserPreferences::cookingTimePreference.name to cookingTimePreference,
            UserPreferences::mealsPerMealPlanPreference.name to mealPerPlanPreference
        )
    }

    private fun initBasicButtons(v: Int, parent: MaterialButtonToggleGroup) {
        parent.children.iterator().forEach {
            it as Button
            if (it.text.toString() == v.toString()) {
                parent.check(it.id)
            }
        }
    }

    private fun initConfirmButton() {
        val originOfNavigation: String = args.originOfNavigation
        val btn = requireView().findViewById<View>(R.id.confirmBtn) as Button

        verifyConfirmBtnName(originOfNavigation, btn)
        setConfirmBtnOnClickListener(originOfNavigation, btn)
    }

    private fun verifyConfirmBtnName(originOfNavigation: String, btn: Button) {
        if (originOfNavigation == USER_PREFERENCES_FRAGMENT) {
            btn.text = getString(R.string.confirm_btn_alt_text)
        }
    }

    private fun setConfirmBtnOnClickListener(originOfNavigation: String, btn: Button) {
        btn.setOnClickListener {
            viewModel.setUserMealsPerPlanPreferences(userPreferenceValues)
            if (originOfNavigation == PERSONALIZATION_PROCESS) {
                navController.navigate(R.id.action_mealsNumberCustomizationPersonalizationFragment_to_allergyCustomizationFragment)
            } else if (originOfNavigation == USER_PREFERENCES_FRAGMENT) {
                navController.navigate(R.id.action_mealsNumberCustomizationFragment_to_userPreferencesFragment)
            }
        }
    }

}