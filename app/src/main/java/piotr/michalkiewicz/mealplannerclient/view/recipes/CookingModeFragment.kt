package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentCookingModeBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel.RecipeViewModel

class CookingModeFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var binding: FragmentCookingModeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCookingModeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        init()
    }

    private fun init() {
        initViewModel()
    }

    private fun initViewModel() {
        recipeViewModel = ViewModelProvider(
            requireActivity(),
            Injection.provideRecipesViewModelFactory(requireContext())
        ).get(RecipeViewModel::class.java)

        binding.viewModel = recipeViewModel
    }

}