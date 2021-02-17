package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui

import  android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentDislikedIngredientsCustBinding
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PERSONALIZATION_PROCESS
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.USER_PREFERENCES_FRAGMENT
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Resource

class DislikedIngredientsCustomizationFragment : PersonalizationCustomFragment() {

    private val args: DislikedIngredientsCustomizationFragmentArgs by navArgs()
    private lateinit var binding: FragmentDislikedIngredientsCustBinding

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
            R.layout.fragment_disliked_ingredients_cust,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupViewModel() {
        viewModel = getViewModel(args.originOfNavigation)
        viewModel.initDislikedButtonsData()
    }

    private fun initButtonDataObserver() {
        viewModel.dislikedButtonsDataReady.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                takeActionBasedOnStatus(viewModel.getAllProductsDataResource())
            }
        })
    }

    private fun initConfirmButton() {
        verifyConfirmBtnName()
        setConfirmBtnOnClickListener()
    }

    private fun verifyConfirmBtnName() {
        if (args.originOfNavigation == USER_PREFERENCES_FRAGMENT) {
            binding.confirmBtn.text = getString(R.string.confirm_btn_alt_text)
        }
    }

    private fun setConfirmBtnOnClickListener() {
        binding.confirmBtn.setOnClickListener {
            viewModel.setDislikedIngredients(getAllMarkedButtonsNames())
            if (args.originOfNavigation == PERSONALIZATION_PROCESS) {
                navController.navigate(R.id.action_disIngredientsCustomizationFragment_to_recipeTypeCustomizationPersonalizationFragment)
            } else if (args.originOfNavigation == USER_PREFERENCES_FRAGMENT) {
                navController.navigate(R.id.action_dislikedIngredientsCustomizationFragment_to_userPreferencesFragment)
            }
        }
    }

    private fun takeActionBasedOnStatus(resource: Resource<List<String>?>?) {
        if (resource != null) {
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { it1 ->
                        initButtonsLayout(it1)
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
    }

    private fun initButtonsLayout(allProductsList: List<String>) {
        addButtonsToLayout(binding.linearLayoutIngredientsButtons, allProductsList, 1)
        browseThroughAndMarkButtonsOnInit(binding.linearLayoutIngredientsButtons)
    }

    private fun browseThroughAndMarkButtonsOnInit(buttonsLayout: LinearLayout) {
        for (i in 0 until buttonsLayout.childCount) {
            val v = buttonsLayout.getChildAt(i) as Button
            if (isInUserDislikedIngredients(v) == true) {
                markButton(v)
            }
        }
    }

    private fun isInUserDislikedIngredients(v: Button): Boolean? {
        return viewModel.getUserDislikedIngredients()?.contains(v.text.toString())
    }

    private fun markButton(v: Button) {
        binding.linearLayoutIngredientsButtons.check(v.id)
    }

    private fun getAllMarkedButtonsNames(): ArrayList<String> {
        val allergiesList = ArrayList<String>()
        binding.linearLayoutIngredientsButtons.checkedButtonIds.forEach {
            allergiesList.add(findButtonNameById(it))
        }
        return allergiesList
    }

    private fun findButtonNameById(id: Int): String {
        return activity?.findViewById<Button>(id)?.text.toString()
    }
}
