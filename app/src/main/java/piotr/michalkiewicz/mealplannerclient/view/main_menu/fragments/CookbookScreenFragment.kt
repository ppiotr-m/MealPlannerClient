package piotr.michalkiewicz.mealplannerclient.view.main_menu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentCookbookScreenBinding
import piotr.michalkiewicz.mealplannerclient.recipes.paging.ui.RecipesAdapter
import piotr.michalkiewicz.mealplannerclient.recipes.paging.ui.RecipesSearchViewModel

class CookbookScreenFragment : Fragment() {

    private lateinit var binding: FragmentCookbookScreenBinding
    private lateinit var viewModel: RecipesSearchViewModel
    private val adapter = RecipesAdapter()
    private var searchJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

  //      binding = FragmentCookbookScreenBinding.inflate(layoutInflater)

  //      init(binding.root)
        return null
    }

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRecipe(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    /*
    private fun updateRepoListFromInput() {
        binding.searchRecipe.text.trim().let {
            if (it.isNotEmpty()) {
                binding.list.scrollToPosition(0)
                search(it.toString())
            }
        }
    }
     */

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun init(root: View) {
        assignUIElements(root)
    }

    private fun assignUIElements(root: View) {
    }
}