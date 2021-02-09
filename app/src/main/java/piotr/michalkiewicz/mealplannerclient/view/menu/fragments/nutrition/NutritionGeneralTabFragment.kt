package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.nutrition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentNutritionGeneralTabBinding
import piotr.michalkiewicz.mealplannerclient.nutrition.Injection
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionSharedViewModel

class NutritionGeneralTabFragment : Fragment() {

    private lateinit var nutritionSharedViewModel: NutritionSharedViewModel
    private lateinit var binding: FragmentNutritionGeneralTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNutritionGeneralTabBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        initViewModel()
        setupObservers()
    }

    private fun initViewModel() {
        nutritionSharedViewModel = ViewModelProvider(
            requireActivity(),
            Injection.provideGeneralViewModelFactory()
        ).get(NutritionSharedViewModel::class.java)
    }

    private fun setupObservers() {
        nutritionSharedViewModel.uiModelLiveData.observe(viewLifecycleOwner, {})

        binding.viewModel = nutritionSharedViewModel
    }

}