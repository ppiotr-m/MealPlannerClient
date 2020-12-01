package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.nutrition

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentNutritionGeneralTabBinding
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentNutritionScreenBinding
import piotr.michalkiewicz.mealplannerclient.nutrition.Injection
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionScreenViewModel

class NutritionGeneralTabFragment : Fragment() {

    private lateinit var nutritionScreenViewModel: NutritionScreenViewModel
    private lateinit var binding: FragmentNutritionGeneralTabBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNutritionGeneralTabBinding.inflate(inflater)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nutritionScreenViewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(NutritionScreenViewModel::class.java)
    }
}