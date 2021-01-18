package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.nutrition

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentNutritionMineralsTargetsTabBinding
import piotr.michalkiewicz.mealplannerclient.nutrition.Injection
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionMineralsTabViewModel
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues

class NutritionMineralsTabFragment : Fragment() {

    private lateinit var nutritionMineralsViewModel: NutritionMineralsTabViewModel
    private lateinit var binding: FragmentNutritionMineralsTargetsTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNutritionMineralsTargetsTabBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        initViewModel()
        setupObservers()
    }

    private fun initViewModel() {
        nutritionMineralsViewModel = ViewModelProvider(
            this,
            Injection.provideGeneralViewModelFactory()
        ).get(NutritionMineralsTabViewModel::class.java)
    }

    private fun setupObservers() {
        nutritionMineralsViewModel.uiModel.observe(viewLifecycleOwner, Observer {
            Log.d(ConstantValues.TAG, "Observer triggered, it == null?: " + (it == null))

            if(it != null) {
                Log.i(
                    ConstantValues.TAG,
                    "NutritionGeneralTabFragment::Observer::it.nutrientPercentages==null:" +
                            (it.nutrientsPercentages == null)
                )
            } else{
                Log.i(
                    ConstantValues.TAG,
                    "NutritionGeneralTabFragment::Observer it = null"
                )
            }
        })

        binding.viewmodel = nutritionMineralsViewModel
    }
}