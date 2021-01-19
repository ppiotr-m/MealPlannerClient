package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.nutrition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentNutritionVitaminTargetsTabBinding
import piotr.michalkiewicz.mealplannerclient.nutrition.Injection
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionVitaminsTabViewModel

class NutritionVitaminTabFragment : Fragment() {

    private lateinit var nutritionVitaminsViewModel: NutritionVitaminsTabViewModel
    private lateinit var binding: FragmentNutritionVitaminTargetsTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNutritionVitaminTargetsTabBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        initViewModel()
        setupObservers()
    }

    private fun initViewModel() {
        nutritionVitaminsViewModel = ViewModelProvider(
            this,
            Injection.provideGeneralViewModelFactory()
        ).get(NutritionVitaminsTabViewModel::class.java)
    }

    private fun setupObservers() {
        nutritionVitaminsViewModel.uiModel.observe(
            viewLifecycleOwner,
            Observer { }) // TODO Resource.LOADING etc. - handle this

        binding.viewmodel = nutritionVitaminsViewModel
    }
}