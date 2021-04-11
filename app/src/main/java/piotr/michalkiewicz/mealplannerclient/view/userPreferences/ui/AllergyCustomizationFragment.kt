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
import kotlinx.coroutines.runBlocking
import piotr.michalkiewicz.mealplannerclient.PersonalizationGraphDirections
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentAllergyCustomizationBinding
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PERSONALIZATION_PROCESS
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.USER_PREFERENCES_FRAGMENT
import piotr.michalkiewicz.mealplannerclient.utils.EspressoIdlingResource
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Resource

class AllergyCustomizationFragment : PersonalizationCustomFragment() {

    private val args: AllergyCustomizationFragmentArgs by navArgs()
    private lateinit var binding: FragmentAllergyCustomizationBinding
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

    private fun setupViewModel() {
        viewModel = getViewModel(args.originOfNavigation)
        viewModel.initAllergyButtonsData()
    }

    private fun initButtonDataObserver() {
        EspressoIdlingResource.increment()
        viewModel.allergyButtonsDataReady.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                takeActionBasedOnStatus(viewModel.getAllAllergiesDataResource())
                EspressoIdlingResource.decrement()
            }
        })
    }

    private fun setupBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_allergy_customization,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initConfirmButton() {
        setConfirmBtnText()
        setConfirmBtnOnClickListener()

    }

    private fun setConfirmBtnText() {
        if (args.originOfNavigation == USER_PREFERENCES_FRAGMENT) {
            binding.confirmBtn.text = getString(R.string.confirm_btn_alt_text)
        }
    }

    private fun setConfirmBtnOnClickListener() {
        binding.confirmBtn.setOnClickListener {
            runBlocking {
                viewModel.setAllergies(getAllMarkedButtonsNames())
            }
            if (args.originOfNavigation == PERSONALIZATION_PROCESS) {
                viewModel.markCustomizationDone()
                navController.navigate(PersonalizationGraphDirections.actionGlobalHomeScreenFragment())
            } else if (args.originOfNavigation == USER_PREFERENCES_FRAGMENT) {
                navController.navigate(R.id.action_allergyCustomizationFragment2_to_userPreferencesFragment)
            }
        }
    }

    private fun takeActionBasedOnStatus(resource: Resource<List<String>?>?) {
        if (resource != null) {
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let {
                        initButtonsLayout(it)
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

    private fun initButtonsLayout(allAllergiesList: List<String>) {
        addButtonsToLayout(binding.allergiesLinearLayout, allAllergiesList, 1)
        markButtonsOnInit(binding.allergiesLinearLayout)
    }

    private fun markButtonsOnInit(allergiesLinearLayout: MaterialButtonToggleGroup?) {
        for (i in 0 until (allergiesLinearLayout ?: return).childCount) {
            val v = allergiesLinearLayout.getChildAt(i) as Button
            if (viewModel.getUserAllergies()?.contains(v.text.toString()) == true) {
                markButton(v)
            }
        }
    }

    private fun markButton(v: Button) {
        binding.allergiesLinearLayout.check(v.id)
    }

    private fun getAllMarkedButtonsNames(): ArrayList<String> {
        val allergiesList = ArrayList<String>()
        binding.allergiesLinearLayout.checkedButtonIds.forEach {
            allergiesList.add(findButtonNameById(it))
        }
        return allergiesList
    }

    private fun findButtonNameById(id: Int): String {
        //TODO needs to be handled in the future
        return activity?.findViewById<Button>(id)?.text.toString()
    }
}