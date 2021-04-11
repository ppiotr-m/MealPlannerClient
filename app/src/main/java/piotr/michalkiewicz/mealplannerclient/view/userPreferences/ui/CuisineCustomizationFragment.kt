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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButtonToggleGroup
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentCuisineCustomizationBinding
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PERSONALIZATION_PROCESS
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.USER_PREFERENCES_FRAGMENT
import piotr.michalkiewicz.mealplannerclient.utils.EspressoIdlingResource
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Resource

class CuisineCustomizationFragment : PersonalizationCustomFragment() {

    private val args: CuisineCustomizationFragmentArgs by navArgs()
    private lateinit var binding: FragmentCuisineCustomizationBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        initButtonDataObserver()
        initConfirmButton()
        navController = findNavController()
    }

    private fun setupBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cuisine_customization,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupViewModel() {
        viewModel = getViewModel(args.originOfNavigation)
        viewModel.initCuisineCustomizationButtonsData()
    }

    private fun initButtonDataObserver() {
        EspressoIdlingResource.increment()
        viewModel.cuisineButtonsDataReady.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                takeActionBasedOnStatus(viewModel.getAllCuisineDataResource())
                EspressoIdlingResource.decrement()
            }
        })
    }

    private fun initConfirmButton() {
        verifyConfirmBtnText()
        setConfirmBtnOnClickListener()
    }

    private fun verifyConfirmBtnText() {
        if (args.originOfNavigation != PERSONALIZATION_PROCESS) {
            binding.confirmBtn.text = getString(R.string.confirm_btn_alt_text)
        }
    }

    private fun setConfirmBtnOnClickListener() {
        binding.confirmBtn.setOnClickListener {
            viewModel.setCuisines(getAllMarkedButtonsNames())
            if (args.originOfNavigation == PERSONALIZATION_PROCESS) {
                navController.navigate(R.id.action_cuisineCustomizationFragment_to_mealsNumberCustomizationPersonalizationFragment)
            } else if (args.originOfNavigation == USER_PREFERENCES_FRAGMENT) {
                navController.navigate(R.id.action_cuisineCustomizationFragment2_to_userPreferencesFragment)
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

    private fun initButtonsLayout(allCuisinesList: List<String>) {
        addButtonsToLayout(binding.linearLayoutCuisineButtons, allCuisinesList, 1)
        markButtonsOnInit(binding.linearLayoutCuisineButtons)
    }

    private fun markButtonsOnInit(buttonsLayout: MaterialButtonToggleGroup?) {
        if (buttonsLayout != null) {
            for (i in 0 until buttonsLayout.childCount) {
                val v = buttonsLayout.getChildAt(i) as Button
                if (viewModel.getUserCuisines()?.contains(v.text.toString()) == true) {
                    markButton(v)
                }
            }
        }
    }

    private fun markButton(v: Button) {
        binding.linearLayoutCuisineButtons.check(v.id)
    }

    private fun getAllMarkedButtonsNames(): ArrayList<String> {
        val allergiesList = ArrayList<String>()
        binding.linearLayoutCuisineButtons.checkedButtonIds.forEach {
            allergiesList.add(findButtonNameById(it))
        }
        return allergiesList
    }

    private fun findButtonNameById(id: Int): String {
        return activity?.findViewById<Button>(id)?.text.toString()
    }
}