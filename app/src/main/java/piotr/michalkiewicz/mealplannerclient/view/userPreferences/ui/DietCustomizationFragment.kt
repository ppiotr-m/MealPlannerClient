package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui

import android.os.Bundle
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
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentDietCustomizationBinding
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PERSONALIZATION_PROCESS
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.USER_PREFERENCES_FRAGMENT
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Resource

class DietCustomizationFragment : PersonalizationCustomFragment(), View.OnClickListener {

    private val args: DietCustomizationFragmentArgs by navArgs()
    private lateinit var binding: FragmentDietCustomizationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupBinding(inflater, container)
        setupViewModel()
        initButtonsDataObserver()
        return binding.root
    }

    private fun setupBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_diet_customization,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupViewModel() {
        viewModel = getViewModel(args.originOfNavigation)
        viewModel.initDietsButtonsData()
    }

    private fun initButtonsDataObserver() {
        viewModel.dietsButtonsDataReady.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                takeActionBasedOnStatus(viewModel.getAllDietsDataResource())
            }
        })
    }

    private fun takeActionBasedOnStatus(resource: Resource<List<String>?>?) {
        if (resource != null) {
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { allDiets ->
                        initButtonsLayout(allDiets)
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

    private fun initButtonsLayout(allDiets: List<String>) {
        val buttonsLayout = binding.linearButtonsDiet
        addButtonsToLayout(buttonsLayout, allDiets, 1)
        initDietCustomizationButtons(buttonsLayout)
        markButtonOnInit()
    }

    private fun markButtonOnInit() {
        for (i in 0 until binding.linearButtonsDiet.childCount) {
            val v = binding.linearButtonsDiet.getChildAt(i) as Button
            if (viewModel.getUserDietPreference() == v.text.toString()) {
                markButton(v)
            }
        }
    }

    private fun markButton(v: View) {
        binding.linearButtonsDiet.check(v.id)
    }

    private fun initDietCustomizationButtons(linearLayout: LinearLayout?) {
        for (i in 0 until (linearLayout ?: return).childCount) {
            val v: View = linearLayout.getChildAt(i)
            if (v is Button) {
                addClick(v.id)
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
        viewModel.setUserDietPreference(activity?.findViewById<Button>(v.id)?.text.toString())
        when (args.originOfNavigation) {
            PERSONALIZATION_PROCESS -> {
                navController.navigate(R.id.action_dietCustomizationFragment_to_disIngredientsCustomizationFragment)
            }
            USER_PREFERENCES_FRAGMENT -> {
                navController.navigate(R.id.action_dietCustomizationFragment2_to_userPreferencesFragment)
            }
            else -> {
                //error log?
            }
        }
    }
}