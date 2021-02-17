package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentRecipeTypeCustomizationBinding
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PERSONALIZATION_PROCESS
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.USER_PREFERENCES_FRAGMENT
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Resource

class RecipeTypeCustomizationFragment : PersonalizationCustomFragment() {

    private val args: RecipeTypeCustomizationFragmentArgs by navArgs()
    private lateinit var binding: FragmentRecipeTypeCustomizationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupBinding(inflater, container)
        setupViewModel()
        initButtonDataObserver()
        initConfirmButton()
        return binding.root
    }

    private fun setupBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_type_customization,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupViewModel() {
        viewModel = getViewModel(args.originOfNavigation)
        viewModel.initRecipeTypeCustomizationButtonsData()
    }

    private fun initButtonDataObserver() {
        viewModel.recipeTypeCustomizationButtonsDataReady.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                takeActionBasedOnStatus(viewModel.getAllRecipeTypeDataResource())
            }
        })
    }

    private fun takeActionBasedOnStatus(resource: Resource<List<String>?>?) {
        if (resource != null) {
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    Log.i("UserPreferencesFragment", "allRecipeTypes triggered!")
                    initButtonsLayout(resource.data)
                }
                Resource.Status.ERROR -> {
                    Log.i("UserPreferencesFragment", "Error!")
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                }
            }
        }
    }

    private fun initButtonsLayout(recipeTypeList: List<String>?) {
        val buttonsLayout = binding.linearLayoutRecipeTypesButtons

        if (recipeTypeList != null) {
            addButtonsToLayout(buttonsLayout, recipeTypeList, 1)
            markButtonsOnInit(buttonsLayout)
        }
    }

    private fun initConfirmButton() {
        verifyConfirmBtnName()
        setConfirmBtnOnClickListener()
    }

    private fun verifyConfirmBtnName() {
        if (args.originOfNavigation != PERSONALIZATION_PROCESS) {
            binding.confirmBtn.text = getString(R.string.confirm_btn_alt_text)
        }
    }

    private fun setConfirmBtnOnClickListener() {
        binding.confirmBtn.setOnClickListener {
            viewModel.setUserRecipeTypes(getAllMarkedButtonsNames())

            if (args.originOfNavigation == PERSONALIZATION_PROCESS) {
                navController.navigate(R.id.action_recipeTypeCustomizationPersonalizationFragment_to_cuisineCustomizationFragment)
            } else if (args.originOfNavigation == USER_PREFERENCES_FRAGMENT) {
                navController.navigate(R.id.action_recipeTypeCustomizationFragment_to_userPreferencesFragment)
            }
        }
    }

    private fun markButtonsOnInit(buttonsLayout: MaterialButtonToggleGroup?) {
        if (buttonsLayout != null) {
            browseThroughAndMarkButtons(buttonsLayout)
        }
    }

    private fun browseThroughAndMarkButtons(buttonsLayout: MaterialButtonToggleGroup) {
        for (i in 0 until buttonsLayout.childCount) {
            val v: View = buttonsLayout.getChildAt(i)
            if (v is MaterialButton) {
                val recipeType = v.text.toString()
                if (doesUserRecipeTypesContainGivenType(recipeType)) {
                    markButton(v)
                }
            }
        }
    }

    private fun doesUserRecipeTypesContainGivenType(type: String): Boolean {
        return viewModel.getUserRecipeTypePreference()?.contains(type) ?: false
    }

    private fun markButton(v: Button) {
        binding.linearLayoutRecipeTypesButtons.check(v.id)
    }

    private fun getAllMarkedButtonsNames(): ArrayList<String> {
        val allergiesList = ArrayList<String>()
        binding.linearLayoutRecipeTypesButtons.checkedButtonIds.forEach {
            allergiesList.add(findButtonNameById(it))
        }
        return allergiesList
    }

    private fun findButtonNameById(id: Int): String {
        return activity?.findViewById<Button>(id)?.text.toString()
    }
}

