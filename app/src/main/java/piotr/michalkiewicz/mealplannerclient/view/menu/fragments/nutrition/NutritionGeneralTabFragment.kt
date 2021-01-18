package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.nutrition

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentNutritionGeneralTabBinding
import piotr.michalkiewicz.mealplannerclient.nutrition.Injection
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionGeneralTabViewModel
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import piotr.michalkiewicz.mealplannerclient.utils.Resource

class NutritionGeneralTabFragment : Fragment() {

    private lateinit var nutritionGeneralViewModel: NutritionGeneralTabViewModel
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
        nutritionGeneralViewModel = ViewModelProvider(
            this,
            Injection.provideGeneralViewModelFactory()
        ).get(NutritionGeneralTabViewModel::class.java)
    }

    private fun setupObservers() {
        nutritionGeneralViewModel.uiModel.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "Observer triggered, it == null?: " + (it == null))

            if(it != null) {
                Log.i(
                    TAG,
                    "NutritionGeneralTabFragment::Observer::it.nutrientPercentages==null:" +
                            (it.nutrientsPercentages == null)
                )
            } else{
                Log.i(
                    TAG,
                    "NutritionGeneralTabFragment::Observer it = null"
                )
            }
        })

        binding.viewmodel = nutritionGeneralViewModel
    }
}