package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.nutrition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentNutritionGeneralTabBinding
import piotr.michalkiewicz.mealplannerclient.nutrition.Injection
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutirtionGeneralTabViewModel

class NutritionGeneralTabFragment : Fragment() {

    private lateinit var nutritionGeneralViewModel: NutirtionGeneralTabViewModel
    private lateinit var binding: FragmentNutritionGeneralTabBinding
    private val nutritionUiModel = MutableLiveData<NutritionUiModel>()

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

        binding.lifecycleOwner = this

        initViewModel()
    }

    private fun initViewModel() {
        nutritionGeneralViewModel = ViewModelProvider(
            this,
            Injection.provideGeneralViewModelFactory()
        ).get(NutirtionGeneralTabViewModel::class.java)

        binding.viewmodel = nutritionGeneralViewModel
    }
}