package piotr.michalkiewicz.mealplannerclient.view.main_menu.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentCookbookScreenBinding
import piotr.michalkiewicz.mealplannerclient.recipes.paging.Injection
import piotr.michalkiewicz.mealplannerclient.recipes.paging.ui.RecipesAdapter
import piotr.michalkiewicz.mealplannerclient.recipes.paging.ui.RecipesSearchViewModel
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues

class CookbookScreenFragment : Fragment() {

    private lateinit var binding: FragmentCookbookScreenBinding
    private lateinit var viewModel: RecipesSearchViewModel
    private val adapter = RecipesAdapter()
    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCookbookScreenBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
                .get(RecipesSearchViewModel::class.java)

        val decoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        binding.temporaryRecipesRecyclerView.addItemDecoration(decoration)

        initAdapter()
        search("Standard")

    }

    private fun initAdapter(){
        binding.temporaryRecipesRecyclerView.adapter = adapter
    }

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchRecipe(query).collectLatest {
                adapter.submitData(it)
            }
        }

    }

    private fun init(root: View) {
        assignUIElements(root)
    }

    private fun assignUIElements(root: View) {
    }
}